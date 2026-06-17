package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String generoCliente;
    private Date fechaNacimientoC;
    private String numeroTelefono;

    @ManyToOne
    Usuario Usuario;

    public Cliente() {
    }

    
    public Cliente(int idCliente, String nombreCliente, String apellidoCliente, String generoCliente,
            Date fechaNacimientoC, String numeroTelefono, com.tpi.gpdrl.Entity.Usuario usuario) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.generoCliente = generoCliente;
        this.fechaNacimientoC = fechaNacimientoC;
        this.numeroTelefono = numeroTelefono;
        Usuario = usuario;
    }


    public Cliente(String nombreCliente, String apellidoCliente, String generoCliente, Date fechaNacimientoC,
            String numeroTelefono, com.tpi.gpdrl.Entity.Usuario usuario) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.generoCliente = generoCliente;
        this.fechaNacimientoC = fechaNacimientoC;
        this.numeroTelefono = numeroTelefono;
        Usuario = usuario;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getGeneroCliente() {
        return generoCliente;
    }

    public void setGeneroCliente(String generoCliente) {
        this.generoCliente = generoCliente;
    }

    public Date getFechaNacimientoC() {
        return fechaNacimientoC;
    }

    public void setFechaNacimientoC(Date fechaNacimientoC) {
        this.fechaNacimientoC = fechaNacimientoC;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario usuario) {
        Usuario = usuario;
    }


    public String getNumeroTelefono() {
        return numeroTelefono;
    }


    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    

}
