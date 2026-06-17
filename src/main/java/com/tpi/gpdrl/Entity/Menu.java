package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMenu;
    private Date fechaCreacion;

    @ManyToOne
    Producto producto;

    public Menu() {
    }

    public Menu(Date fechaCreacion, Producto producto) {
        this.fechaCreacion = fechaCreacion;
        this.producto = producto;
    }

    public Menu(int idMenu, Date fechaCreacion, Producto producto) {
        this.idMenu = idMenu;
        this.fechaCreacion = fechaCreacion;
        this.producto = producto;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
