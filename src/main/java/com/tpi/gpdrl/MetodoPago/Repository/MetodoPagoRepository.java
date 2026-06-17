package com.tpi.gpdrl.MetodoPago.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpi.gpdrl.Entity.MetodoPago;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    MetodoPago findByTipoPago(String tipoPago);
}
