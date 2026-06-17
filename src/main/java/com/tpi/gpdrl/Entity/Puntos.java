package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Puntos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPuntos;
    private int cantidadPuntos;
    private Date fechaTransaccion;
    private boolean puntosActivos;

    @ManyToOne
    Cliente cliente;

    public Puntos() {
    }

    public Puntos(int idPuntos, int cantidadPuntos, Date fechaTransaccion, boolean puntosActivos, Cliente cliente) {
        this.idPuntos = idPuntos;
        this.cantidadPuntos = cantidadPuntos;
        this.fechaTransaccion = fechaTransaccion;
        this.puntosActivos = puntosActivos;
        this.cliente = cliente;
    }

    public Puntos(int cantidadPuntos, Date fechaTransaccion, boolean puntosActivos, Cliente cliente) {
        this.cantidadPuntos = cantidadPuntos;
        this.fechaTransaccion = fechaTransaccion;
        this.puntosActivos = puntosActivos;
        this.cliente = cliente;
    }

    public int getIdPuntos() {
        return idPuntos;
    }

    public void setIdPuntos(int idPuntos) {
        this.idPuntos = idPuntos;
    }

    public int getCantidadPuntos() {
        return cantidadPuntos;
    }

    public void setCantidadPuntos(int cantidadPuntos) {
        this.cantidadPuntos = cantidadPuntos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isPuntosActivos() {
        return puntosActivos;
    }

    public void setPuntosActivos(boolean puntosActivos) {
        this.puntosActivos = puntosActivos;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

}
