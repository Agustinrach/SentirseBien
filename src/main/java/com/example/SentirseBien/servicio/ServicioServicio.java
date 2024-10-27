package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Empleado;
import com.example.SentirseBien.Entidad.Servicio;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ServicioServicio {
    public List<Servicio> listarServicios();
    public Servicio obtenerServicioPorId(Long Id);
}
