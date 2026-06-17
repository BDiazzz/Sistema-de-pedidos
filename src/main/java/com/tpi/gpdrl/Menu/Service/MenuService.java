package com.tpi.gpdrl.Menu.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Menu;
import com.tpi.gpdrl.Menu.Repository.MenuRepository;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> listadoMenu() {
        return menuRepository.findAll();
    }

    public void guardarMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public void deleteMenu(int idMenu) {
        menuRepository.deleteById(idMenu);
    }

    public Menu buscarPorId(int idMenu) {
        return menuRepository.findById(idMenu).orElse(null);
    }

    public List<Menu> menuActivo() {
        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechDate = new Date(tiempoMilisegundos);
        // System.out.println(fechDate);
        return menuRepository.findByFechaCreacion(fechDate);
    }
}
