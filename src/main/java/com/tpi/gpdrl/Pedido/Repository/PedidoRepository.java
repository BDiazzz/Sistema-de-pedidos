package com.tpi.gpdrl.Pedido.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpi.gpdrl.Entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    Optional<Pedido> findByIdPedido(int idPedido);

}

