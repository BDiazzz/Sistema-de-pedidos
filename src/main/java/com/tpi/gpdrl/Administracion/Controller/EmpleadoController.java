package com.tpi.gpdrl.Administracion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Administracion.Service.EmpleadoService;
import com.tpi.gpdrl.Entity.Empleado;
import com.tpi.gpdrl.Entity.Repartidor;
import com.tpi.gpdrl.Entity.Rol;
import com.tpi.gpdrl.Entity.Usuario;
import com.tpi.gpdrl.Repartidor.Service.RepartidorService;
import com.tpi.gpdrl.Seguridad.Service.RolService;
import com.tpi.gpdrl.Seguridad.Service.UsuarioService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RepartidorService repartidorService;

    // Consultar empleado
    @GetMapping("/consultar/{id}")
    public String consultarEmpleado(@PathVariable("id") int id, Model model) {

        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
        model.addAttribute("titulo", "Consultar Empleado");
        model.addAttribute("empleado", empleado);
        return "Empleado/consultarEmpleado"; // Plantilla para consultar empleado
    }

    // Mostrar lista de empleados con alertas
    @GetMapping
    public String verEmpleados(Model model) {
        model.addAttribute("titulo", "Empleados");
        model.addAttribute("empleados", empleadoService.obtenerTodosEmpleados());
        return "Empleado/ListaEmpleados";
    }

    // Crear nuevo empleado
    @GetMapping("/nuevo")
    public String nuevoEmpleado(Model model) {

        Empleado empleado = new Empleado();
        empleado.setUsuario(new Usuario()); // Inicializar el usuario dentro del empleado
        List<Rol> listadoRol = rolService.listadoRol();
        model.addAttribute("titulo", "Crear Empleado");
        model.addAttribute("empleado", empleado);
        model.addAttribute("listadoRol", listadoRol);

        return "Empleado/crearEmpleado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(
            @ModelAttribute Empleado empleado, @RequestParam("roles") String roles,
            RedirectAttributes redirectAttributes) {

        try {
            if (roles.equals("REPARTIDOR")) {

                System.out.println("repatirdor vato");
                Repartidor repartidor = new Repartidor();

                repartidor.setNombreRepartidor(empleado.getNombreEmpleado());
                repartidor.setApellidoRepartidor(empleado.getApellidoEmpleado());
                repartidor.setSexoRepartidor(empleado.getGeneroEmpleado());
                repartidor.setFechaNacimientoR(empleado.getFechaNacimiento());
                repartidor.setEstado(false);

                // Encryptando el password
                String password = passwordEncoder.encode(empleado.getUsuario().getContraseniaUsuario());
                empleado.getUsuario().setContraseniaUsuario(password);
                empleado.getUsuario().setRoles(rolService.obtenerRol(roles));
                Usuario usuarioGuardado = usuarioService.guardarRetornarU(empleado.getUsuario());
                repartidor.setUsuario(usuarioGuardado);

                repartidorService.guardarRepartidor(repartidor);
            } else {

                // Guarda el usuario
                String password = passwordEncoder.encode(empleado.getUsuario().getContraseniaUsuario());
                empleado.getUsuario().setContraseniaUsuario(password);
                empleado.getUsuario().setRoles(rolService.obtenerRol(roles));
                Usuario usuarioGuardado = usuarioService.guardarRetornarU(empleado.getUsuario());
                empleado.setUsuario(usuarioGuardado);

                // Guarda el empleado
                empleadoService.guardarEmpleado(empleado);

            }

            redirectAttributes.addFlashAttribute("success", "Empleado creado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el empleado.");
            System.out.println("Error: " + e);
        }
        return "redirect:/empleados";
    }

    // Editar empleado
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable int id, Model model) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
        Usuario usuario = usuarioService.buscarPorId(empleado.getUsuario().getIdUsuario());
        List<Rol> listadoRol = rolService.listadoRol();
        model.addAttribute("titulo", "Editar Empleado");
        model.addAttribute("empleado", empleado);
        model.addAttribute("listadoRol", listadoRol);
        model.addAttribute("usuario", usuario);
        return "Empleado/editarEmpleado";
    }

    @PostMapping("/editar")
    public String editarEmpleado(
            @ModelAttribute Empleado empleado, @RequestParam("roles") String roles,
            RedirectAttributes redirectAttributes) {

        Empleado empleadoExistente = empleadoService.obtenerEmpleadoPorId(empleado.getIdEmpleado());
        if (empleadoExistente == null) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar.");
            return "redirect:/empleados";
        }
        try {

            if (roles.equals("REPARTIDOR")) {

                System.out.println("repatirdor vato");
                Repartidor repartidor = new Repartidor();

                repartidor.setNombreRepartidor(empleado.getNombreEmpleado());
                repartidor.setApellidoRepartidor(empleado.getApellidoEmpleado());
                repartidor.setSexoRepartidor(empleado.getGeneroEmpleado());
                repartidor.setFechaNacimientoR(empleado.getFechaNacimiento());
                repartidor.setEstado(false);

                // Encryptando el password
                String password = passwordEncoder.encode(empleado.getUsuario().getContraseniaUsuario());
                empleado.getUsuario().setContraseniaUsuario(password);
                empleado.getUsuario().setRoles(rolService.obtenerRol(roles));
                Usuario usuarioGuardado = usuarioService.guardarRetornarU(empleado.getUsuario());
                repartidor.setUsuario(usuarioGuardado);

                repartidorService.guardarRepartidor(repartidor);
            } else {

                if (empleado.getUsuario() != null) {
                    // Guarda el empleado
                    empleadoService.guardarEmpleado(empleado);
                    System.out.println("tiene usuario");
                } else {
                    // Guarda el usuario
                    String password = passwordEncoder.encode(empleado.getUsuario().getContraseniaUsuario());
                    empleado.getUsuario().setContraseniaUsuario(password);
                    empleado.getUsuario().setRoles(rolService.obtenerRol(roles));
                    Usuario usuarioGuardado = usuarioService.guardarRetornarU(empleado.getUsuario());
                    empleado.setUsuario(usuarioGuardado);

                    // Guarda el empleado
                    empleadoService.guardarEmpleado(empleado);
                }

            }
            redirectAttributes.addFlashAttribute("info", "Empleado actualizado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el empleado.");
        }
        return "redirect:/empleados";
    }

    // Eliminar empleado
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            empleadoService.eliminarEmpleado(id);
            redirectAttributes.addFlashAttribute("warning", "Empleado eliminado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el empleado.");
        }
        return "redirect:/empleados";
    }
}
