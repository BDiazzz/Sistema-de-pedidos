package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private Date fechaNacimiento;
    private String generoEmpleado;
    private String duiEmpleado;

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;



    public Empleado() {
    }

    public Empleado(String nombreEmpleado, String apellidoEmpleado, Date fechaNacimiento, String generoEmpleado,
            String duiEmpleado, Usuario usuario) {
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.fechaNacimiento = fechaNacimiento;
        this.generoEmpleado = generoEmpleado;
        this.duiEmpleado = duiEmpleado;
        this.usuario = usuario;
    }

    public Empleado(int idEmpleado, String nombreEmpleado, String apellidoEmpleado, Date fechaNacimiento,
            String generoEmpleado, String duiEmpleado, Usuario usuario) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.fechaNacimiento = fechaNacimiento;
        this.generoEmpleado = generoEmpleado;
        this.duiEmpleado = duiEmpleado;
        this.usuario = usuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGeneroEmpleado() {
        return generoEmpleado;
    }

    public void setGeneroEmpleado(String generoEmpleado) {
        this.generoEmpleado = generoEmpleado;
    }

    public String getDuiEmpleado() {
        return duiEmpleado;
    }

    public void setDuiEmpleado(String duiEmpleado) {
        this.duiEmpleado = duiEmpleado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
