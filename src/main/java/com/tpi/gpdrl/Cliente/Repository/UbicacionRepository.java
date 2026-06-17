package com.tpi.gpdrl.Cliente.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Ubicacion;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    List<Ubicacion> findByCliente(Cliente cliente);
 
    @Query("Select u FROM Ubicacion u WHERE u.ubicacionActiva=true AND  u.cliente.idCliente=:idCliente")
    public Ubicacion ubicacionActiva(int idCliente);

}
