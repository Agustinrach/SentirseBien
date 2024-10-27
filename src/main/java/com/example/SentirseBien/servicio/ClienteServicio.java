package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Cliente;


import java.util.List;

public interface ClienteServicio {

    public List<Cliente> listarCliente();
    public Cliente guardarCliente(Cliente c);
    public Cliente obtenerEmpleadoPorId(Long Id);
    public Cliente actualizarCliente(Cliente c);
    public void eliminarCliente(Long Id);
    public Cliente buscarPorEmail(String email);
}

