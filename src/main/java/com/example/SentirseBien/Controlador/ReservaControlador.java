package com.example.SentirseBien.Controlador;

import com.example.SentirseBien.Entidad.Cliente;
import com.example.SentirseBien.Entidad.Reserva;
import com.example.SentirseBien.Entidad.Servicio;
import com.example.SentirseBien.servicio.ClienteServicio;
import com.example.SentirseBien.servicio.ReservaServicio;
import com.example.SentirseBien.servicio.ServicioServicio;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private ServicioServicio servicioServicio;  // Inyectamos el ServicioServicio para manejar los servicios

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/reservas")
    public String listarReservas(Model modelo) {
        modelo.addAttribute("reservas", reservaServicio.listarReservas());
        return "reservas";
    }

    @GetMapping("/hacerreserva")
    public String mostrarFormReserva(Model modelo, Principal principal) {
        // Obtener el nombre del cliente autenticado
        String nombreCliente = principal.getName();  // Aquí se asume que el nombre está en el campo "username" del cliente autenticado
        Reserva new_reserva = new Reserva();
        modelo.addAttribute("reserva", new_reserva);
        modelo.addAttribute("nombreCliente", nombreCliente);  // Pasa el nombre del cliente al modelo
        List<Servicio> serviciosDisponibles = servicioServicio.listarServicios();
        serviciosDisponibles.forEach(servicio -> System.out.println(servicio.getNombre()));
        modelo.addAttribute("serviciosDisponibles", serviciosDisponibles);
        return "crear_reservas";
    }


    @PostMapping("/reserva")
    public ResponseEntity<byte[]> guardarReserva(@ModelAttribute("reserva") Reserva reserva) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Buscar al cliente autenticado por su nombre de usuario
        Cliente cliente = clienteServicio.buscarPorEmail(username);
        reserva.setCliente(cliente);

        // Convertir los IDs de los servicios seleccionados en objetos Servicio
        List<Servicio> servicios = reserva.getServicios().stream()
                .map(servicio -> servicioServicio.obtenerServicioPorId(servicio.getId()))
                .collect(Collectors.toList());
        reserva.setServicios(servicios);  // Asignar los servicios a la reserva

        // Calcular el total de los servicios
        double total = servicios.stream().mapToDouble(Servicio::getPrecio).sum();
        reserva.setTotal(total);  // Asignar el total a la reserva

        // Guardar la reserva en la base de datos
        reservaServicio.guardarReserva(reserva);

        // Crear el documento PDF
        try {
            // Crear un documento PDF
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // Cargar la imagen desde un archivo (por ejemplo, un logo)
            String imagePath = "src/main/resources/static/img/logo sentirse bien.png"; // Asegúrate de usar la ruta correcta
            Image image = Image.getInstance(imagePath);
            image.scaleToFit(100, 50); // Redimensionar la imagen según sea necesario
            document.add(image); // Agregar la imagen al documento

            // Agregar contenido al PDF con los datos de la reserva
            document.add(new Paragraph("Detalles de la Reserva"));
            document.add(new Paragraph("Nombre del Cliente: " + reserva.getCliente().getNombre()));
            document.add(new Paragraph("Fecha de la Reserva: " + reserva.getFechaHora().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

            // Recorrer la lista de servicios y agregarlos al PDF
            document.add(new Paragraph("Servicios reservados:"));
            for (Servicio servicio : reserva.getServicios()) {
                document.add(new Paragraph("- " + servicio.getNombre() + " ($" + servicio.getPrecio() + ")"));
            }

            // Agregar el total formateado
            document.add(new Paragraph("Total: $" + String.format("%.2f", reserva.getTotal())));
            document.add(new Paragraph("Método de Pago: " + reserva.getMetodoPago()));

            document.close();

            // Configurar las cabeceras HTTP para descargar el archivo PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "reserva_" + reserva.getCliente().getNombre() + ".pdf");

            // Retornar el PDF como respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


}
