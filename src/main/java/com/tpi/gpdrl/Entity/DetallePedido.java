package com.tpi.gpdrl.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetallePedido;
    private int cantidadDetalle;
    private double subTotal;

    @ManyToOne
    Pedido pedido;
    @ManyToOne
    Producto producto;

    public DetallePedido() {
    }

    public DetallePedido(int cantidadDetalle, double subTotal, Pedido pedido, Producto producto) {
        this.cantidadDetalle = cantidadDetalle;
        this.subTotal = subTotal;
        this.pedido = pedido;
        this.producto = producto;
    }

    public DetallePedido(int idDetallePedido, int cantidadDetalle, double subTotal, Pedido pedido, Producto producto) {
        this.idDetallePedido = idDetallePedido;
        this.cantidadDetalle = cantidadDetalle;
        this.subTotal = subTotal;
        this.pedido = pedido;
        this.producto = producto;
    }

    public int getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(int idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public int getCantidadDetalle() {
        return cantidadDetalle;
    }

    public void setCantidadDetalle(int cantidadDetalle) {
        this.cantidadDetalle = cantidadDetalle;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
