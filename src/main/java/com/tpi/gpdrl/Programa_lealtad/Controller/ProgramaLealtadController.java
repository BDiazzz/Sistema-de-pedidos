package com.tpi.gpdrl.Programa_lealtad.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Entity.Cupon;
import com.tpi.gpdrl.Programa_lealtad.Service.CuponService;
import com.tpi.gpdrl.Puntos.Service.PuntosService;

@Controller
@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
public class ProgramaLealtadController {

    // inyectando metodos
    @Autowired
    private CuponService cuponService;
    @Autowired
    private PuntosService puntosService;

    // mostrar pantalla de gestion de programa de lealtad
    @GetMapping("/gestionProgramaLealtad")
    public String gestionProgramaLealtad(Model model) {

        boolean cuponActivo = cuponService.contarCuponesActivos() > 0 ? true : false;
        boolean puntoActivo = puntosService.contarPuntosActivos() > 0 ? true : false;

        Cupon nuevoCupon = new Cupon();
        model.addAttribute("titulo", "Gestion Programa Lealtad");
        model.addAttribute("nuevoCupon", nuevoCupon);
        model.addAttribute("banderaCupones", cuponActivo);
        model.addAttribute("banderaPuntos", puntoActivo);

        // creando lista de cupones que contiene la informacion de metodo
        List<Cupon> listadoCupones = cuponService.mostrarCupones();
        // mandando lista de cupones
        model.addAttribute("cupones", listadoCupones);
        return "Programa_lealtad/gestionProgramaLealtad";
    }

    @PostMapping("/crearCupon")
    public String crearCupon(@ModelAttribute Cupon cupon, Model model, RedirectAttributes attributes) {
        if (cuponService.contarCuponesActivos() == 0){
            cupon.setActivo(false);
        }
        else{
            cupon.setActivo(true);
        }
        cupon.setEliminado(false);
        cuponService.guardarCupon(cupon);
        attributes.addFlashAttribute("success", "¡Cupon registrado con éxito!");
        return "redirect:gestionProgramaLealtad"; // Muestra la vista del formulario
    }

    @GetMapping("/deleteCupon/{idCupon}")
    public String eliminarCupon(@PathVariable("idCupon") int idCupon, RedirectAttributes attributes) {
        try {
            cuponService.eliminarCupon(idCupon);
            attributes.addFlashAttribute("warning", "¡Cupon eliminado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al eliminar al cupon!");
        }
        return "redirect:/gestionProgramaLealtad";
    }

    @PostMapping("/activarDesactivar")
    public String activarDesactivar(@RequestParam String tipoEntidad, @RequestParam String accion) {
        if ("puntos".equals(tipoEntidad)) {
            // Lógica para activar o desactivar puntos
            if ("activar".equals(accion)) {
                // Activa los puntos
                puntosService.activarTodosLosPuntos();
            } else {
                // Desactiva los puntos
                puntosService.desactivarTodosLosPuntos();
            }
        } else if ("cupones".equals(tipoEntidad)) {
            // Lógica para activar o desactivar cupones
            if ("activar".equals(accion)) {
                // Activa los cupones
                cuponService.activarTodosLosCupones();
            } else {
                // Desactiva los cupones
                cuponService.desactivarTodosLosCupones();
            }
        }
        return "redirect:/gestionProgramaLealtad"; // Redirige a la página deseada después de la acción
    }
}
