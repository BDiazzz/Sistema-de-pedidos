package com.tpi.gpdrl.Cliente.Service;

import java.util.HashMap;
import java.util.Map;

import com.tpi.gpdrl.Entity.Cupon;
import com.tpi.gpdrl.Entity.DetallePedido;
import com.tpi.gpdrl.Entity.Producto;
import com.tpi.gpdrl.Entity.Puntos;

public class CarritoDTO {
    private Map<Integer, DetallePedido> detalles;
    private Cupon cupon;
    private Puntos puntos;

    public CarritoDTO() {
        this.detalles = new HashMap<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (detalles.containsKey(producto.getIdProducto())) {
            DetallePedido detalle = detalles.get(producto.getIdProducto());
            detalle.setCantidadDetalle(detalle.getCantidadDetalle() + cantidad);
            detalle.setSubTotal(detalle.getSubTotal() + (producto.getPrecioProducto() * cantidad));
        } else {
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidadDetalle(cantidad);
            detalle.setSubTotal(producto.getPrecioProducto() * cantidad);
            detalles.put(producto.getIdProducto(), detalle);
        }
    }

    public void agregarCupon(Cupon cupon) {
        this.cupon=cupon;
    }

    public Cupon getCupon() {
        return cupon;
    }

    public Puntos getPuntos() {
        return puntos;
    }

    public void agregarPuntos(Puntos puntos) {
        this.puntos = puntos;
    }

    public void eliminarProducto(int idProducto) {
        detalles.remove(idProducto);
    }

    public double calcularTotal() {
        return detalles.values().stream().mapToDouble(DetallePedido::getSubTotal).sum();
    }

    public Map<Integer, DetallePedido> getDetalles() {
        return detalles;
    }
}