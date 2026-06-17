package com.tpi.gpdrl.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactura;
    private double totalPago;
    private Date fechaEmision;

    @ManyToOne
    private MetodoPago metodoPago;

    public Factura() {
    }

    public Factura(double totalPago, Date fechaEmision, MetodoPago metodoPago) {
        this.totalPago = totalPago;
        this.fechaEmision = fechaEmision;
        this.metodoPago = metodoPago;
    }

    public Factura(int idFactura, double totalPago, Date fechaEmision, MetodoPago metodoPago) {
        this.idFactura = idFactura;
        this.totalPago = totalPago;
        this.fechaEmision = fechaEmision;
        this.metodoPago = metodoPago;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

}
