package com.tpi.gpdrl.Cliente.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tpi.gpdrl.Cliente.Repository.UbicacionRepository;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Ubicacion;

@Service
public class UbicacionService {
    @Autowired
    private UbicacionRepository ubicacionRepository;

    /*public void guardarUbicacion(int IdCliente, Ubicacion ubicacion){
        // Buscar al cliente por su ID
        return ubicacionRepository.findById(idProducto, ubicacion).orElse(null);
    }

        // Asignar la ubicación al cliente
        ubicacion.setCliente(cliente);

        // Guardar la ubicación
        ubicacionRepository.save(ubicacion);
    }*/
    public List<Ubicacion> obtenerUbicaciones(){
        return ubicacionRepository.findAll();
            }

    public void guardarUbicacion(Ubicacion ubicacion){
        ubicacionRepository.save(ubicacion);
    }
    
    public List<Ubicacion> obtenerUbicacionesPorCliente(Cliente cliente) {
        return ubicacionRepository.findByCliente(cliente);
    }
    public void eliminarUbicacion(int id) {
        ubicacionRepository.deleteById(id);
    }
    public Ubicacion obtenerUbicacionPorId(int id) {
        return ubicacionRepository.findById(id).orElse(null);
    }
    public Ubicacion obtenerUbicacionActiva(int idCliente){
        return ubicacionRepository.ubicacionActiva(idCliente);
    }
}
