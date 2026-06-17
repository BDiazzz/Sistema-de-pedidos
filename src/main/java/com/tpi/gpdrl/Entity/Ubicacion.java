package com.tpi.gpdrl.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUbicacion;
    private String direccion;
    private String numeroCasa;
    private String referencia;
    private String tipo;
    private boolean ubicacionActiva;
    @ManyToOne
    Cliente cliente;

    public Ubicacion() {
    }

    public Ubicacion(int idUbicacion, String direccion, String numeroCasa, String referencia, String tipo,
            boolean ubicacionActiva, Cliente cliente) {
        this.idUbicacion = idUbicacion;
        this.direccion = direccion;
        this.numeroCasa = numeroCasa;
        this.referencia = referencia;
        this.tipo = tipo;
        this.ubicacionActiva = ubicacionActiva;
        this.cliente = cliente;
    }

    public Ubicacion(String direccion, String numeroCasa, String referencia, String tipo, boolean ubicacionActiva,
            Cliente cliente) {
        this.direccion = direccion;
        this.numeroCasa = numeroCasa;
        this.referencia = referencia;
        this.tipo = tipo;
        this.ubicacionActiva = ubicacionActiva;
        this.cliente = cliente;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isUbicacionActiva() {
        return ubicacionActiva;
    }

    public void setUbicacionActiva(boolean ubicacionActiva) {
        this.ubicacionActiva = ubicacionActiva;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
