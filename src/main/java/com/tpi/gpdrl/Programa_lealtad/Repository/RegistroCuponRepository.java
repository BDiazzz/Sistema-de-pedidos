package com.tpi.gpdrl.Programa_lealtad.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tpi.gpdrl.Entity.RegistroCupon;

public interface RegistroCuponRepository extends JpaRepository<RegistroCupon,Integer>{
 
    @Query("SELECT r FROM RegistroCupon r WHERE r.cliente.idCliente=:idCliente")
    List<RegistroCupon> registrosPorCliente(int idCliente);
}
