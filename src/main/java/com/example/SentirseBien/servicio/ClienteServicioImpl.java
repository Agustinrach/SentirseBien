package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Cliente;
import com.example.SentirseBien.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicioImpl implements ClienteServicio{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarCliente() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente guardarCliente(Cliente c) {
        c.setPassword(passwordEncoder.encode(c.getPassword()));
        return clienteRepositorio.save(c);
    }

    @Override
    public Cliente obtenerEmpleadoPorId(Long Id) {
        return clienteRepositorio.findById(Id).get();
    }

    @Override
    public Cliente actualizarCliente(Cliente c) {

        if (c.getPassword() != null && !c.getPassword().isEmpty()) {
            c.setPassword(passwordEncoder.encode(c.getPassword()));
        }
        return clienteRepositorio.save(c);
    }

    @Override
    public void eliminarCliente(Long Id) {
        clienteRepositorio.deleteById(Id);

    }

    @Override
    public Cliente buscarPorEmail(String email) {
            return clienteRepositorio.findByEmail(email);

    }


}
