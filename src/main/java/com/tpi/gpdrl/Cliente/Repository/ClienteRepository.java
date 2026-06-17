package com.tpi.gpdrl.Cliente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tpi.gpdrl.Entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

    // Obtener usuario por correo
    @Query("SELECT c FROM Cliente c WHERE c.Usuario.idUsuario = :idUsuario")
    Cliente findByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
