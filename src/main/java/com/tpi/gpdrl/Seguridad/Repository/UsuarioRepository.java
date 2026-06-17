package com.tpi.gpdrl.Seguridad.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tpi.gpdrl.Entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    // Obtener usuario por correo
    @Query("SELECT u FROM Usuario u WHERE u.correoUsuario = :correo")
    Usuario usuarioByCorreo(@Param("correo") String correo);
}
