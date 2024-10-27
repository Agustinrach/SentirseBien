package com.example.SentirseBien.Entidad;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne con Cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Relación ManyToMany con Servicio
    @ManyToMany
    @JoinTable(
            name = "reserva_servicio", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "reserva_id"), // Columna que referencia a la reserva
            inverseJoinColumns = @JoinColumn(name = "servicio_id") // Columna que referencia al servicio
    )
    private List<Servicio> servicios;

    @Column(nullable = false)
    private LocalDateTime fechaHora;  // Fecha y hora de la reserva

    @Column(nullable = false)
    private double total;  // Total de la reserva

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;  // Método de pago (ej. "DEBITO", "CREDITO")

    // Constructor vacío
    public Reserva() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
