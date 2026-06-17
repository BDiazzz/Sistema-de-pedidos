package com.tpi.gpdrl.Cliente.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tpi.gpdrl.Cliente.Service.UbicacionService;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Ubicacion;
import com.tpi.gpdrl.Seguridad.Service.SessionService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/Cliente/Ubicacion")
@PreAuthorize("hasAnyRole('CLIENTE')")
public class ControllerUbicacion {

    @Autowired
    private UbicacionService ubicacionService;
    // @Autowired
    // private ClienteService clienteService;
    @Autowired
    private SessionService sessionService;

    @GetMapping("/")
    public String verUbicaciones(Model model) {

        Cliente cliente = sessionService.clienteSession();
        List<Ubicacion> listaUbicaciones = ubicacionService.obtenerUbicacionesPorCliente(cliente);
        model.addAttribute("titulo", "Ubicaciones");
        model.addAttribute("Ubicaciones", listaUbicaciones);
        model.addAttribute("tituloMenu", "Mis direcciones");
        model.addAttribute("urlAtras", "/Cliente/perfil");

        return "Ubicacion/listaUbicaciones";
    }

    @GetMapping("/Crear")
    public String crearUbicacionEntrega(Model model) {

        // Cliente clientes=new Cliente();
        // clientes.setApellidoCliente("PErez");
        // clientes.setNombreCliente("Pedro");
        // clienteService.guardarCliente(clientes);

        Cliente cliente = sessionService.clienteSession();

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setCliente(cliente);
        // model.addAttribute("cliente", cliente);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("titulo", "Agregar Ubicacion");
        return "Ubicacion/crearUbicacion";
    }

    @PostMapping("/Guardar")
    public String guardarUbicacion(@ModelAttribute Ubicacion ubicacion, RedirectAttributes redirectAttributes) {

        try {
            ubicacionService.guardarUbicacion(ubicacion);

            redirectAttributes.addFlashAttribute("success", "Ubicacion guardada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la ubicacion.");
            System.out.println("Error: " + e);
        }

        return "redirect:/Cliente/Ubicacion/";
    }

    @GetMapping("/Eliminar/{id}")
    public String eliminarEmpleado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            ubicacionService.eliminarUbicacion(id);
            redirectAttributes.addFlashAttribute("warning", "Ubicacion eliminada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la ubicacion.");
        }
        return "redirect:/Cliente/Ubicacion/";
    }

    // Editar empleado
    @GetMapping("/Editar/{id}")
    public String mostrarFormularioEdicionUbicacion(@PathVariable int id, Model model) {
        Ubicacion ubicacion = ubicacionService.obtenerUbicacionPorId(id);
        model.addAttribute("titulo", "Editar Ubicacion");
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("tituloMenu", "");
        model.addAttribute("urlAtras", "/Cliente/Ubicacion/");
        
        return "Ubicacion/editarUbicacion";
    }

    @PostMapping("/Editar/{id}")
    public String editarUbicacion(
            @PathVariable int id,
            @ModelAttribute Ubicacion ubicacion,
            RedirectAttributes redirectAttributes) {

        try {
            Ubicacion ubicacionExistente = ubicacionService.obtenerUbicacionPorId(id);
            ubicacionExistente.setDireccion(ubicacion.getDireccion());
            ubicacionExistente.setNumeroCasa(ubicacion.getNumeroCasa());
            ubicacionExistente.setReferencia(ubicacion.getReferencia());
            ubicacionExistente.setTipo(ubicacion.getTipo());
            ubicacionExistente.setUbicacionActiva(ubicacion.isUbicacionActiva());

            ubicacionService.guardarUbicacion(ubicacionExistente);
            redirectAttributes.addFlashAttribute("info", "  Ubicacion actualizada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la ubicacion.");
        }
        return "redirect:/Cliente/Ubicacion/";
    }

    @PostMapping("/cambiarUbicacion")
    public String cambiarUbicacion(@RequestParam("ubicacionActiva") boolean ubicacionActiva,
            @RequestParam("idUbicacion") int idUbicacion) {
        // Actualizar la ubicacion activa
        Ubicacion ubicacion = ubicacionService.obtenerUbicacionPorId(idUbicacion);
        ubicacion.setUbicacionActiva(ubicacionActiva);
        ubicacionService.guardarUbicacion(ubicacion); // Guardar el cambio en la base de datos

        // También debes asegurarte de desactivar las otras ubicaciones si hay alguna
        // activa
        List<Ubicacion> ubicacionesCliente = ubicacionService.obtenerUbicacionesPorCliente(ubicacion.getCliente());
        for (Ubicacion u : ubicacionesCliente) {
            if (u.getIdUbicacion() != idUbicacion) {
                u.setUbicacionActiva(false);
                ubicacionService.guardarUbicacion(u);
            }
        }

        return "redirect:/Cliente/ConfirmarPedido";
    }

}
