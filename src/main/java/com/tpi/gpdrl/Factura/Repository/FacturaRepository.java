package com.tpi.gpdrl.Factura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tpi.gpdrl.Entity.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
      @Query("SELECT f FROM Factura f WHERE f.idFactura = :idFactura")
    Factura findFacturaById(int idFactura);
}
