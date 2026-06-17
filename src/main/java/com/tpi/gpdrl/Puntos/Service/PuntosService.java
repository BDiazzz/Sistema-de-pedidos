package com.tpi.gpdrl.Puntos.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Puntos;
import com.tpi.gpdrl.Puntos.Repository.PuntosRepository;

@Service
public class PuntosService {
    @Autowired
    private PuntosRepository puntosRepository;

    // public Integer suma(int idCliente){
    //     return puntosRepository.sumaPuntos(idCliente);

    // }

    public List<Puntos> listadoPuntosCliente(int idCliente){

        return puntosRepository.listadoPuntos(idCliente);
    }

    public void desactivarTodosLosPuntos() {
        List<Puntos> puntos = puntosRepository.findAll(); // Obtener todos los cupones
        for (Puntos p : puntos) {
            p.setPuntosActivos(false);; // Asignar activo = false
        }
        puntosRepository.saveAll(puntos); // Guardar todos los cambios en la base de datos
    }

    public void activarTodosLosPuntos() {
        List<Puntos> puntos = puntosRepository.findAll(); // Obtener todos los cupones
        for (Puntos p : puntos) {
            p.setPuntosActivos(true);
        }
        puntosRepository.saveAll(puntos); // Guardar todos los cambios en la base de datos
    }

    public long contarPuntosActivos(){
        return puntosRepository.contarPuntossActivos();
    }

    public void guardarPunto(Puntos punto){
        puntosRepository.save(punto);
    }

    public int totalPuntos(int idCliente) {
        Integer puntos = puntosRepository.sumaPuntos(idCliente);
        return puntos != null ? puntos : 0; // Retorna 0 si es null
    }

    public Puntos guardarPuntoR(Puntos punto){
        return puntosRepository.save(punto);
    }
    
    public int calcularPuntos(double montoCompra, int puntosPorDolar) {
        double puntos = montoCompra * puntosPorDolar;
        return (int) Math.round(puntos); // Redondea al número entero más cercano
    }
    
}
