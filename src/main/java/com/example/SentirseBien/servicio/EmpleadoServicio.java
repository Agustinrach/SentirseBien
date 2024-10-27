package com.example.SentirseBien.servicio;

import com.example.SentirseBien.Entidad.Empleado;

import java.util.List;

public interface EmpleadoServicio {

    public List<Empleado> listarEmpleados();
    public Empleado guardarEmpleado(Empleado e);
    public Empleado crearAdmin();
    public Empleado obtenerEmpleadoPorId(Long Id);
    public Empleado actualizarEmpleado(Empleado e);
    public void eliminarEmpleado(Long Id);
}
