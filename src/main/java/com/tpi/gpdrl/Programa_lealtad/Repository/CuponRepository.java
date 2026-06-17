package com.tpi.gpdrl.Programa_lealtad.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tpi.gpdrl.Entity.Cupon;

public interface CuponRepository extends JpaRepository<Cupon,Integer>{
    
    @Query("SELECT COUNT(c) FROM Cupon c WHERE c.activo = true")
    Integer contarCuponesActivos();

    List<Cupon> findByEliminadoFalse();
}
