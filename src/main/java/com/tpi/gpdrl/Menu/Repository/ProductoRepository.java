package com.tpi.gpdrl.Menu.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpi.gpdrl.Entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByNombreProducto(String nombreProducto);

}
