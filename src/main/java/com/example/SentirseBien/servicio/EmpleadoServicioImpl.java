package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.SentirseBien.repositorio.EmpleadoRepositorio;

import java.util.Collections;
import java.util.List;

@Service
public class EmpleadoServicioImpl implements EmpleadoServicio {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado e) {
        e.setPassword(passwordEncoder.encode(e.getPassword()));
        return empleadoRepositorio.save(e);
    }

    @Override
    public Empleado obtenerEmpleadoPorId(Long Id) {
        return empleadoRepositorio.findById(Id).get();
    }

    @Override
    public Empleado actualizarEmpleado(Empleado e) {

        if (e.getPassword() != null && !e.getPassword().isEmpty()) {
            e.setPassword(passwordEncoder.encode(e.getPassword()));
        }
        return empleadoRepositorio.save(e);
    }

    @Override
    public void eliminarEmpleado(Long Id) {
        empleadoRepositorio.deleteById(Id);
    }


    public Empleado crearAdmin() {
        // Verifica si el empleado "admin" ya existe
        if (empleadoRepositorio.findByEmail("admin@example.com") == null) {
            Empleado adminEmpleado = new Empleado();
            adminEmpleado.setNombre("secretaria");
            adminEmpleado.setApellido("Secretaria");
            adminEmpleado.setEmail("secretaria@gmail.com");
            adminEmpleado.setTelefono("3624031760");
            adminEmpleado.setPassword(passwordEncoder.encode("secretaria")); // Encripta la contraseña

            // Guarda el empleado en la base de datos
            return empleadoRepositorio.save(adminEmpleado);
        }
        // Retorna null si el admin ya existe
        return null;
    }


}
