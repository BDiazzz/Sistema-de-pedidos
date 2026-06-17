package com.tpi.gpdrl.Programa_lealtad.Service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.RegistroCupon;
import com.tpi.gpdrl.Programa_lealtad.Repository.RegistroCuponRepository;

@Service
public class RegistroCuponService {

    @Autowired
    private RegistroCuponRepository registroCuponRepository;

    public void guardarRegistro(RegistroCupon registroCupon) {
        //Se toma la fecha del sistema
        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechDate = new Date(tiempoMilisegundos);
        //Se asigna la fecha
        registroCupon.setFechaUso(fechDate);
        registroCuponRepository.save(registroCupon);
    }
}
