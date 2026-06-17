package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAsignacion;
    private Date fechaAsignacion;

    @ManyToOne
    Pedido pedido;
    @ManyToOne
    Repartidor repartidor;
    @ManyToOne
    EstadoOrden EstadoOrden;

    public Asignacion() {
    }

    public Asignacion(int idAsignacion, Date fechaAsignacion, Pedido pedido, Repartidor repartidor,
            EstadoOrden estadoOrden) {
        this.idAsignacion = idAsignacion;
        this.fechaAsignacion = fechaAsignacion;
        this.pedido = pedido;
        this.repartidor = repartidor;
        EstadoOrden = estadoOrden;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public EstadoOrden getEstadoOrden() {
        return EstadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        EstadoOrden = estadoOrden;
    }

}
