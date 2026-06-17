package com.tpi.gpdrl.Factura.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Factura;
import com.tpi.gpdrl.Factura.Repository.FacturaRepository;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    
    public Factura guardarFactura(Factura factura){
        return facturaRepository.save(factura);
    }
}
