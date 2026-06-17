
package com.tpi.gpdrl.Repartidor.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.tpi.gpdrl.Pedido.Service.AsignacionService;
import com.tpi.gpdrl.Repartidor.Service.RepartidorService;
import com.tpi.gpdrl.Seguridad.Service.SessionService;
import com.tpi.gpdrl.Pedido.Service.DetallePedidoService;
import com.tpi.gpdrl.Entity.DetallePedido;
import com.tpi.gpdrl.Entity.EstadoOrden;
import com.tpi.gpdrl.Entity.Repartidor;
import com.tpi.gpdrl.Entity.Asignacion;

@Controller
@PreAuthorize("hasAnyRole('REPARTIDOR')")
public class ControllerRepartidor {

    @Autowired
    private AsignacionService asignacionService;
    @Autowired
    private RepartidorService repartidorService;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private SessionService sessionService;

    // seccion de repartidor
    //
    //
    //
    // pantalla de repartidor con un pedido en curso

    @GetMapping("/repartidor")
    public String consultarRepartidorPedido(Model model,
            RedirectAttributes redirectAttributes) {

        // Asignacion asignacion = new Asignacion();

        Repartidor repartidor = sessionService.repartidorSession();
        // // evaluando si hay asignacion para el repartidor
        // for (Asignacion asignacion : asignacionService.listadoAsignacion()) {
        // if (asignacion.getRepartidor().getIdRepartidor() == id
        // && asignacion.getEstadoOrden().getTipoEstado() != "Terminado") {

        // Repartidor repartidor = repartidorService.obtenerRepartidorPorId(id);

        // model.addAttribute("asignacion", asignacion);
        // model.addAttribute("repartidor", repartidor);
        // model.addAttribute("titulo", "Pedido en curso");
        // return "/Repartidor/Repartidor";
        // }
        // }

        List<Asignacion> asignaciones = asignacionService.asignacionesRepartidor(repartidor.getIdRepartidor());
        model.addAttribute("asignaciones", asignaciones);
        model.addAttribute("repartidor", repartidor);
        model.addAttribute("titulo", "Pedidos asignados");

        return "Repartidor/Repartidor";
    }

    /*
     * @GetMapping("/repartidor/{id}")
     * public String consultarRepartidorPedido(@PathVariable("id") int id, Model
     * model) {
     * 
     * Repartidor repartidor = repartidorService.obtenerRepartidorPorId(id);
     * List<Asignacion> asignaciones = asignacionService.listadoAsignacion();
     * Asignacion asignacion =
     * asignacionService.encontrarPrimeraAsignacion(asignaciones, id);
     * 
     * if (asignacion != null) {
     * 
     * model.addAttribute("asignacion", asignacion);
     * model.addAttribute("repartidor", repartidor);
     * model.addAttribute("titulo", "Pedido en curso");
     * return "/Repartidor/Repartidor";
     * }
     * return null;
     * 
     * }
     */

    // cambiar estado de repartidor

    @GetMapping("/repartidor/estado/")
    public String cambiarEstadoRepartidor(Model model) {

        Repartidor repartidor = sessionService.repartidorSession();
        if (repartidor.isEstado() == false) {
            repartidor.setEstado(true);
            model.addAttribute("repartidor", repartidor);
            repartidorService.guardarRepartidor(repartidor);

        } else {
            repartidor.setEstado(false);
            repartidorService.guardarRepartidor(repartidor);
        }

        return "redirect:/repartidor";
    }

    @GetMapping("/repartidor/Repartidor")
    public String repatidor(Model model) {
        model.addAttribute("titulo", "Repartidor");
        return "Repartidor/Repartidor";
    }

    // Seccion de pedido en curso
    //
    //
    //
    // pantalla de pedido en curso
    @GetMapping("/repartidor/pedidoencurso/{id}")
    public String pedidoEnCurso(@PathVariable("id") int idAsinacion, Model model) {
        model.addAttribute("titulo", "Pedido en curso");
        Asignacion asignacion = asignacionService.obtenerAsignacionPorId(idAsinacion);

        // Buscando ubicacion del cliente
        // List<Ubicacion> ubicaciones = ubicacionService.obtenerUbicaciones();
        // for (Ubicacion ubicacion : ubicaciones) {
        // if (ubicacion.getCliente().getIdCliente() ==
        // asignacion.getPedido().getCliente().getIdCliente()) {
        // model.addAttribute("ubicacionCliente", ubicacion);
        // }
        // }

        List<DetallePedido> detallePedidos = detallePedidoService
                .obtenerPedidoPorId(asignacion.getPedido().getIdPedido());

        model.addAttribute("asignacion", asignacion);
        model.addAttribute("detalles", detallePedidos);
        return "Repartidor/pedidoEnCurso";
    }

    // editar estado de asignacion

    @GetMapping("/asignacion/estado/{id}")
    public String actualizarEstadoAsignacion(@PathVariable("id") int idAsignacion,
            RedirectAttributes redirectAttributes) {

        Asignacion asignacion = asignacionService.obtenerAsignacionPorId(idAsignacion);
        EstadoOrden estadoOrden = asignacionService.obtenerEstadoOrdenPorId(3);
        asignacion.setEstadoOrden(estadoOrden);
        asignacionService.guardarAsignacion(asignacion);

        // model.addAttribute("EstadoDeAsignacion", estadoOrden);

        redirectAttributes.addFlashAttribute("success", "Pedido finalizado con exito");
        return "redirect:/repartidor";
    }

    /*
     * @GetMapping("/asignacion/estado/{id}")
     * public String cambiarEstadoAsignacion(@PathVariable int id) {
     * Asignacion asignacionExistente =
     * asignacionService.obtenerAsignacionPorId(id);
     * List<Asignacion> asignaciones = asignacionService.listadoAsignacion();
     * 
     * for (Asignacion asignacion : asignaciones) {
     * if (asignacion.getIdAsignacion() == id) {
     * int IdEstado = asignacion.getEstadoOrden().getIdEstadoOrden();
     * EstadoOrden estadoOrden =
     * asignacionService.obtenerEstadoOrdenPorId(IdEstado);
     * estadoOrden.setTipoEstado("Terminado");
     * asignacionService.guardarEstadoOrden(estadoOrden);
     * 
     * break;
     * 
     * }
     * }
     * 
     * return "redirect:/repartidor/" +
     * asignacionExistente.getRepartidor().getIdRepartidor();
     * }
     */

}
