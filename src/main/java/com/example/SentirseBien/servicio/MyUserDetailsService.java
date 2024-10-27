package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Cliente;
import com.example.SentirseBien.Entidad.Empleado;
import com.example.SentirseBien.repositorio.ClienteRepositorio;
import com.example.SentirseBien.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Cargando usuario: " + email);

        Empleado empleado = empleadoRepositorio.findByEmail(email);
        if (empleado != null) {
            // Asigna el rol de "ADMIN" si el nombre del empleado es "admin"
            String rol = switch (empleado.getNombre().toLowerCase()) {
                case "admin" -> "ADMIN";
                case "secretaria" -> "SECRETARIA";
                default -> "EMPLEADO"; // Por defecto
            };
            return new org.springframework.security.core.userdetails.User(
                    empleado.getEmail(),
                    empleado.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(rol))
            );
        }

        Cliente cliente = clienteRepositorio.findByEmail(email);
        if (cliente != null) {
            return new org.springframework.security.core.userdetails.User(
                    cliente.getEmail(),
                    cliente.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("CLIENTE"))
            );
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }


}
