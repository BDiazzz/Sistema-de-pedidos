package com.tpi.gpdrl.MetodoPago.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.MetodoPago;
import com.tpi.gpdrl.MetodoPago.Repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public MetodoPago obtenerMetodoPago(String tipoPago){
        return metodoPagoRepository.findByTipoPago(tipoPago);
    }
}
