package com.tpi.gpdrl.Seguridad.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpi.gpdrl.Entity.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {


    // Obtener rol de administrador
    @Query("SELECT r FROM Rol r WHERE r.rolName = 'CLIENTE'")
    Set<Rol> encontrarCliente();

    @Query("SELECT r FROM Rol r WHERE r.idRol = :idRol")
    Set<Rol> encontrarRol(@Param("idRol") Integer idRol);

    @Query("SELECT r FROM Rol r WHERE r.rolName =:rol")
    Set<Rol> obtenerRol(String rol);
    
}
