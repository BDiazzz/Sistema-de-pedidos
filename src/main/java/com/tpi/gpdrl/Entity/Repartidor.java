package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Repartidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRepartidor;
    private String nombreRepartidor;
    private String apellidoRepartidor;
    private String sexoRepartidor;
    private Date fechaNacimientoR;
    private boolean estado;

    @ManyToOne
    private Usuario usuario;

    public Repartidor() {
    }

    public Repartidor(String nombreRepartidor, String apellidoRepartidor, String sexoRepartidor, Date fechaNacimientoR,
            boolean estado, Usuario usuario) {
        this.nombreRepartidor = nombreRepartidor;
        this.apellidoRepartidor = apellidoRepartidor;
        this.sexoRepartidor = sexoRepartidor;
        this.fechaNacimientoR = fechaNacimientoR;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Repartidor(int idRepartidor, String nombreRepartidor, String apellidoRepartidor, String sexoRepartidor,
            Date fechaNacimientoR, boolean estado, Usuario usuario) {
        this.idRepartidor = idRepartidor;
        this.nombreRepartidor = nombreRepartidor;
        this.apellidoRepartidor = apellidoRepartidor;
        this.sexoRepartidor = sexoRepartidor;
        this.fechaNacimientoR = fechaNacimientoR;
        this.estado = estado;
        this.usuario = usuario;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    public void setNombreRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }

    public String getApellidoRepartidor() {
        return apellidoRepartidor;
    }

    public void setApellidoRepartidor(String apellidoRepartidor) {
        this.apellidoRepartidor = apellidoRepartidor;
    }

    public String getSexoRepartidor() {
        return sexoRepartidor;
    }

    public void setSexoRepartidor(String sexoRepartidor) {
        this.sexoRepartidor = sexoRepartidor;
    }

    public Date getFechaNacimientoR() {
        return fechaNacimientoR;
    }

    public void setFechaNacimientoR(Date fechaNacimientoR) {
        this.fechaNacimientoR = fechaNacimientoR;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
