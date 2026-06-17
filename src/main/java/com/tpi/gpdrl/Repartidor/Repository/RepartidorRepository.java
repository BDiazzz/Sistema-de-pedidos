package com.tpi.gpdrl.Repartidor.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tpi.gpdrl.Entity.Repartidor;

public interface RepartidorRepository extends JpaRepository<Repartidor, Integer> {

    // Obtener usuario por correo
    @Query("SELECT r FROM Repartidor r WHERE r.usuario.idUsuario = :idUsuario")
    Repartidor findByIdUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT r FROM Repartidor r WHERE r.estado=true ")
    List<Repartidor> repartidoresActivos();   

    
}
