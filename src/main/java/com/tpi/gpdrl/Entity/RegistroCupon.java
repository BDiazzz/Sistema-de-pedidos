package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RegistroCupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date fechaUso;

    @ManyToOne
    private Cupon cupon;
    @ManyToOne
    private Cliente cliente;

    public RegistroCupon() {
    }

    public RegistroCupon(int id, Date fechaUso, Cupon cupon, Cliente cliente) {
        this.id = id;
        this.fechaUso = fechaUso;
        this.cupon = cupon;
        this.cliente = cliente;
    }

    public RegistroCupon(Date fechaUso, Cupon cupon, Cliente cliente) {
        this.fechaUso = fechaUso;
        this.cupon = cupon;
        this.cliente = cliente;
    }

    public RegistroCupon(Cupon cupon, Cliente cliente) {
        this.cupon = cupon;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(Date fechaUso) {
        this.fechaUso = fechaUso;
    }

    public Cupon getCupon() {
        return cupon;
    }

    public void setCupon(Cupon cupon) {
        this.cupon = cupon;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
