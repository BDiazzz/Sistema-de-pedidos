package com.tpi.gpdrl.Pedido.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.DetallePedido;
import com.tpi.gpdrl.Pedido.Repository.DetallePedidoRepository;
import com.tpi.gpdrl.Entity.Producto;

@Service
public class DetallePedidoService {
  @Autowired
  private DetallePedidoRepository detallePedidoRepository;

  public List<DetallePedido> obtenerPedidoPorId(int id) {
    // return detallePedidoRepository.findById(id).orElse(null);
    return detallePedidoRepository.detalles(id);
  }

  public void guardarDetalle(DetallePedido detallePedido) {
    detallePedidoRepository.save(detallePedido);
  }

  public DetallePedido pedidoPorId(int id) {
    return detallePedidoRepository.findById(id).orElse(null);
  }

  // public DetallePedido guardarDetalle(DetallePedido detalle) {
  //   return detallePedidoRepository.save(detalle);
  // }

  public void guardarProducto(Producto producto) {
    DetallePedido detallePedido = new DetallePedido();
    // detallePedido.setCantidadDetalle(0);
    detallePedidoRepository.save(detallePedido);
  }

  public void eliminarPorId(int id) {
    detallePedidoRepository.deleteById(id);
  }

  public void eliminarDetallesPorPedido(List<DetallePedido> lista) {
    detallePedidoRepository.deleteAll(lista);
  }
}
