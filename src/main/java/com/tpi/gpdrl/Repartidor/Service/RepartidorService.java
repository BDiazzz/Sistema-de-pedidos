package com.tpi.gpdrl.Repartidor.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Asignacion;
import com.tpi.gpdrl.Entity.Repartidor;
import com.tpi.gpdrl.Pedido.Repository.AsignacionRepository;
import com.tpi.gpdrl.Repartidor.Repository.RepartidorRepository;

@Service
public class RepartidorService {
    @Autowired
    private RepartidorRepository repartidorRepository;
    @Autowired
    private AsignacionRepository asignacionRepository;

    public Repartidor obtenerRepartidorIdUsuario(Integer idUsuario) {
        return repartidorRepository.findByIdUsuario(idUsuario);
    }

    public List<Repartidor> obtenerRepartidores() {
        return repartidorRepository.findAll();
    }

    public Repartidor obtenerRepartidorPorId(int id) {
        return repartidorRepository.findById(id).orElse(null);
    }

    public List<Repartidor> listadoRepartidores() {
        return repartidorRepository.findAll();
    }

    public Repartidor buscarPorId(int idRepartidor) {
        return repartidorRepository.findById(idRepartidor).orElse(null);
    }

    public void guardarRepartidor(Repartidor repartidor) {
        repartidorRepository.save(repartidor);
    }

    public List<Repartidor> repartidorDisponible() {
        // Crear un conjunto para evitar duplicados
        Set<Repartidor> repartidoresDisponiblesSet = new HashSet<>();

        // Obtener repartidores activos
        List<Repartidor> repartidoresActivos = repartidorRepository.repartidoresActivos();

        // for (Repartidor repartidor : repartidoresActivos) {
        // System.out.println("Repartidores activos: " +
        // repartidor.getNombreRepartidor());
        // }

        // Obtener las asignaciones del día actual
        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechaHoy = new Date(tiempoMilisegundos);
        System.out.println("Fecha de hoy: " + fechaHoy);

        List<Asignacion> listaAsignacions = asignacionRepository.asignacionesHoy(fechaHoy);

        // for (Asignacion asignacion : listaAsignacions) {
        // if (asignacion.getRepartidor() != null) {
        // System.out.println(
        // "Repartidor asignado el día de hoy: " +
        // asignacion.getRepartidor().getNombreRepartidor());
        // } else {
        // System.out.println("Asignación sin repartidor");
        // }
        // }

        // Iterar sobre los repartidores activos
        for (Repartidor repartidor : repartidoresActivos) {
            // Filtrar asignaciones del repartidor para el día de hoy, validando que el
            // repartidor no sea null
            List<Asignacion> asignacionesDelRepartidor = listaAsignacions.stream()
                    .filter(asignacion -> asignacion.getRepartidor() != null) // Validar que el repartidor no sea null
                    .filter(asignacion -> asignacion.getRepartidor().equals(repartidor))
                    .collect(Collectors.toList());

            // Si el repartidor no tiene asignaciones para hoy, agregarlo al conjunto
            if (asignacionesDelRepartidor.isEmpty()) {
                repartidoresDisponiblesSet.add(repartidor);
            }

            // Verificar si todas las asignaciones del repartidor están finalizadas
            boolean todasFinalizadas = asignacionesDelRepartidor.stream()
                    .allMatch(asignacion -> "FINALIZADO".equals(asignacion.getEstadoOrden().getTipoEstado()));

            // Si todas las asignaciones están finalizadas, agregar el repartidor al
            // conjunto
            if (todasFinalizadas) {
                repartidoresDisponiblesSet.add(repartidor);
            }
        }

        // Convertir el conjunto a lista y retornar
        return new ArrayList<>(repartidoresDisponiblesSet);
    }

    public Repartidor disponible() {
        // Obtener repartidores activos
        List<Repartidor> repartidoresActivos = repartidorRepository.repartidoresActivos();

        // for (Repartidor repartidor : repartidoresActivos) {
        //     System.out.println("Repartidores activos: " + repartidor.getNombreRepartidor());
        // }

        // Obtener las asignaciones del día actual
        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechaHoy = new Date(tiempoMilisegundos);
        System.out.println("Fecha de hoy: " + fechaHoy);

        List<Asignacion> listaAsignacions = asignacionRepository.asignacionesHoy(fechaHoy);

        // for (Asignacion asignacion : listaAsignacions) {
        //     if (asignacion.getRepartidor() != null) {
        //         System.out.println(
        //                 "Repartidor asignado el día de hoy: " + asignacion.getRepartidor().getNombreRepartidor());
        //     } else {
        //         System.out.println("Asignación sin repartidor");
        //     }
        // }

        // Iterar sobre los repartidores activos
        for (Repartidor repartidor : repartidoresActivos) {
            // Filtrar asignaciones del repartidor para el día de hoy, validando que el
            // repartidor no sea null
            List<Asignacion> asignacionesDelRepartidor = listaAsignacions.stream()
                    .filter(asignacion -> asignacion.getRepartidor() != null) // Validar que el repartidor no sea null
                    .filter(asignacion -> asignacion.getRepartidor().equals(repartidor))
                    .collect(Collectors.toList());

            // Si el repartidor no tiene asignaciones para hoy, devolverlo como disponible
            if (asignacionesDelRepartidor.isEmpty()) {
                return repartidor;
            }

            // Verificar si todas las asignaciones del repartidor están finalizadas
            boolean todasFinalizadas = asignacionesDelRepartidor.stream()
                    .allMatch(asignacion -> "FINALIZADO".equals(asignacion.getEstadoOrden().getTipoEstado()));

            // Si todas las asignaciones están finalizadas, devolver el repartidor
            if (todasFinalizadas) {
                System.out.println("Repartidor disponible: " + repartidor.getNombreRepartidor());
                return repartidor;
            }
        }

        // Si no se encuentra ningún repartidor disponible
        return null;
    }

}
