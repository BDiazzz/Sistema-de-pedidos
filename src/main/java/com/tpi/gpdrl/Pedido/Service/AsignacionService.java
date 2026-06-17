package com.tpi.gpdrl.Pedido.Service;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tpi.gpdrl.Entity.Asignacion;
import com.tpi.gpdrl.Entity.EstadoOrden;
import com.tpi.gpdrl.Pedido.Repository.EstadoOrdenRepository;
import com.tpi.gpdrl.Pedido.Repository.AsignacionRepository;

@Service
public class AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;

    // @Autowired
    // private RepartidorRepository repartidorRepository;

    public List<Asignacion> listadoAsignacion() {
        return asignacionRepository.findAll();
    }

    public Asignacion asignacion(int idPedido) {
        return asignacionRepository.asignacionPedido(idPedido);
    }

    public Asignacion obtenerAsignacionPorId(int idAsignacion) {
        return asignacionRepository.findById(idAsignacion).orElse(null);
    }

    public List<EstadoOrden> listadoEstadoOrden() {
        return estadoOrdenRepository.findAll();
    }

    public EstadoOrden obtenerEstadoOrdenPorId(int IdEstadoOrden) {
        return estadoOrdenRepository.findById(IdEstadoOrden).orElse(null);
    }

    public void guardarEstadoOrden(EstadoOrden estadoOrden) {
        estadoOrdenRepository.save(estadoOrden);
    }

    public Asignacion encontrarPrimeraAsignacion(List<Asignacion> asignaciones, int idRepartidor) {
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getEstadoOrden().getTipoEstado() != "Terminado"
                    && asignacion.getRepartidor().getIdRepartidor() == idRepartidor) {
                return asignacion;
            }
        }
        return null;
    }

    public void guardarAsignacion(Asignacion asignacion) {
        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechaActual = new Date(tiempoMilisegundos);
        asignacion.setFechaAsignacion(fechaActual);
        asignacionRepository.save(asignacion);
    }

    public Asignacion ObtnerIdasignacion(int idAsignacion) {
        return asignacionRepository.findById(idAsignacion).orElse(null);
    }

    public boolean existeAsignacionPorPedido(int idPedido) {
        return asignacionRepository.existsById(idPedido); // Consulta en el repositorio para verificar si existe alguna
                                                          // asignación con ese idPedido
    }

    public void eliminarAsignacionPorPedido(int idPedido) {
        asignacionRepository.eliminarAsignacionPorPedido(idPedido);
        ;
    }

    public List<Asignacion> asignacionesRepartidor(int idRepartidor) {
        return asignacionRepository.asingacionesRepartidor(idRepartidor);
    }

    public List<Asignacion> listadoPedidos(int idCliente) {
        return asignacionRepository.pedidosPorClientes(idCliente);
    }
}
