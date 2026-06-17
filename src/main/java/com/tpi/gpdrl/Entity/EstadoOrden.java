package com.tpi.gpdrl.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EstadoOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstadoOrden;
    private String tipoEstado;

    public EstadoOrden() {
    }

    public EstadoOrden(int idEstadoOrden, String tipoEstado) {
        this.idEstadoOrden = idEstadoOrden;
        this.tipoEstado = tipoEstado;
    }

    public int getIdEstadoOrden() {
        return idEstadoOrden;
    }

    public void setIdEstadoOrden(int idEstadoOrden) {
        this.idEstadoOrden = idEstadoOrden;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

}
