package com.tpi.gpdrl.Administracion.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Administracion.Repository.EmpleadoRepository;
import com.tpi.gpdrl.Entity.Empleado;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodosEmpleados() {
        return empleadoRepository.findAll();
    }

    public void guardarEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public void eliminarEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }
}
