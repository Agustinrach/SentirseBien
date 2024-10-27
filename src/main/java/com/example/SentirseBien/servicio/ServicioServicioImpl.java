package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Servicio;
import com.example.SentirseBien.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ServicioServicioImpl implements ServicioServicio{

    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Override
    public List<Servicio> listarServicios() {
        return servicioRepositorio.findAll();

    }

    @Override
    public Servicio obtenerServicioPorId(Long Id) {
        return servicioRepositorio.findById(Id).get();
    }
}
