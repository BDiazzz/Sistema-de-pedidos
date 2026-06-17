package com.tpi.gpdrl.Menu.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpi.gpdrl.Entity.Menu;
import java.sql.Date;


public interface MenuRepository extends JpaRepository<Menu,Integer>{
    
    List<Menu> findByFechaCreacion(Date fechaCreacion);
}
