package com.tpi.gpdrl.Puntos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Puntos;

public interface PuntosRepository extends JpaRepository<Puntos,Integer>{

    @Query("SELECT Sum(p.cantidadPuntos) FROM Puntos p WHERE p.cliente.idCliente=:idCliente")
    public Integer sumaPuntos(int idCliente);

    @Query("SELECT p FROM Puntos p WHERE p.cliente.idCliente=:idCliente")
    List<Puntos> listadoPuntos(int idCliente);

    List<Puntos> findByCliente(Cliente cliente);

    @Query("SELECT COUNT(p) FROM Puntos p WHERE p.puntosActivos = true")
    Integer contarPuntossActivos();

    
}
