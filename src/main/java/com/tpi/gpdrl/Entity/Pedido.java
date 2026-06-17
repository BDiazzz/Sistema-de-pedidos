package com.tpi.gpdrl.Entity;



import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;
    private double totalAPagar;
    private String tiempoEstimado;
    private Date fechaPedido;
    private Date fechaEntregaP;
    private String descripcionOrden;
   

    @ManyToOne
    private Factura factura;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Cupon cupon;
    @ManyToOne
    private Puntos puntos;
    @ManyToOne
    private Ubicacion ubicacion;

    

    public Pedido() {
    }

    public Pedido(double totalAPagar, String tiempoEstimado, Date fechaPedido, Date fechaEntregaP,
            String descripcionOrden, Factura factura, Cliente cliente, Cupon cupon, Puntos puntos,
            Ubicacion ubicacion) {
        this.totalAPagar = totalAPagar;
        this.tiempoEstimado = tiempoEstimado;
        this.fechaPedido = fechaPedido;
        this.fechaEntregaP = fechaEntregaP;
        this.descripcionOrden = descripcionOrden;
        this.factura = factura;
        this.cliente = cliente;
        this.cupon = cupon;
        this.puntos = puntos;
        this.ubicacion = ubicacion;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaEntregaP() {
        return fechaEntregaP;
    }

    public void setFechaEntregaP(Date fechaEntregaP) {
        this.fechaEntregaP = fechaEntregaP;
    }

    public String getDescripcionOrden() {
        return descripcionOrden;
    }

    public void setDescripcionOrden(String descripcionOrden) {
        this.descripcionOrden = descripcionOrden;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cupon getCupon() {
        return cupon;
    }

    public void setCupon(Cupon cupon) {
        this.cupon = cupon;
    }

    public Puntos getPuntos() {
        return puntos;
    }

    public void setPuntos(Puntos puntos) {
        this.puntos = puntos;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    
}
