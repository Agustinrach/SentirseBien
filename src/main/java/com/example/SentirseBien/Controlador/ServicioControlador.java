package com.example.SentirseBien.Controlador;

import com.example.SentirseBien.Entidad.Servicio;
import com.example.SentirseBien.repositorio.ServicioRepositorio;
import com.example.SentirseBien.servicio.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ServicioControlador {

    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/servicio")
    public String listarServicios(Model modelo) {
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "servicio";
    }
}
