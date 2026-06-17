package com.tpi.gpdrl.Seguridad.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Rol;
import com.tpi.gpdrl.Seguridad.Repository.RolRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listadoRol() {
        return rolRepository.findAll();
    }

    public Rol obteneRolPorId(int idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

    public Set<Rol> obtenerCliente() {
        Set<Rol> roles = rolRepository.encontrarCliente(); // Encontramos el admministrador
        return roles;
    }

    public Set<Rol> obtenerRol(String rol) {
        Set<Rol> roles = rolRepository.obtenerRol(rol); // Encontramos el admministrador
        return roles;
    }
}
