package com.tpi.gpdrl.Pedido.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tpi.gpdrl.Entity.DetallePedido;
import com.tpi.gpdrl.Entity.Producto;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    @Query("SELECT d FROM  DetallePedido d WHERE d.pedido.idPedido=:idPedido")
    List<DetallePedido> detalles(int idPedido);

    void save(Producto producto);
}
