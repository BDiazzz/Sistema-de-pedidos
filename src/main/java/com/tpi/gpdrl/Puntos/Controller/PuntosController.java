package com.tpi.gpdrl.Puntos.Controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Puntos;
import com.tpi.gpdrl.Puntos.Service.PuntosService;
import com.tpi.gpdrl.Seguridad.Service.SessionService;

@Controller
@PreAuthorize("hasAnyRole('CLIENTE','ADMINISTRADOR')")
public class PuntosController {

    @Autowired
    private PuntosService puntosService;
    @Autowired
    private SessionService sessionService;

    @GetMapping("/registroPuntos")
    public String verRegistroPuntos(Model model) {

        Cliente cliente=sessionService.clienteSession();
        List<Puntos> puntos = puntosService.listadoPuntosCliente(cliente.getIdCliente());
        puntos.sort(Comparator.comparing(Puntos::getFechaTransaccion).reversed());
        int totalPuntos = puntosService.totalPuntos(cliente.getIdCliente());
        int dolares = totalPuntos/100;
        model.addAttribute("dolares", dolares);
        model.addAttribute("dolares2", dolares+1);
        model.addAttribute("valorProgreso", totalPuntos%100);
        model.addAttribute("puntos", puntos);
        model.addAttribute("titulo", "Registro de puntos");
        model.addAttribute("sumaPuntos", totalPuntos);
        model.addAttribute("activePuntos", true);
        return "Puntos/registroPuntos";
    }
    
}
