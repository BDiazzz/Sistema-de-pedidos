package com.tpi.gpdrl.Pedido.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tpi.gpdrl.Entity.Asignacion;

import jakarta.transaction.Transactional;

public interface AsignacionRepository extends JpaRepository<Asignacion, Integer> {

    @Query("SELECT a FROM Asignacion a WHERE a.pedido.idPedido=:idPedido")
    Asignacion asignacionPedido(int idPedido);

    @Query("SELECT a FROM Asignacion a WHERE a.fechaAsignacion=:hoy")
    List<Asignacion> asignacionesHoy(Date hoy);   

    @Transactional
    @Modifying
    @Query("DELETE FROM Asignacion a WHERE a.pedido.idPedido = :idPedido")
    void eliminarAsignacionPorPedido(int idPedido);

    @Query("SELECT a FROM Asignacion a WHERE a.repartidor.idRepartidor=:idRepartidor")
    List<Asignacion> asingacionesRepartidor(int idRepartidor);

    @Query("SELECT a FROM Asignacion a WHERE a.pedido.cliente.idCliente=:idCliente ORDER BY a.fechaAsignacion desc , a.idAsignacion desc")
    List<Asignacion> pedidosPorClientes(int idCliente);
}
