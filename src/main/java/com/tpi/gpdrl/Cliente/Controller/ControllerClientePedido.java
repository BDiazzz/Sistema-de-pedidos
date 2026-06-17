package com.tpi.gpdrl.Cliente.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Cliente.Service.ClienteService;
import com.tpi.gpdrl.Cliente.Service.UbicacionService;
import com.tpi.gpdrl.Seguridad.Service.UsuarioService;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Ubicacion;
import com.tpi.gpdrl.Entity.Usuario;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/Cliente")
public class ControllerClientePedido {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private UbicacionService ubicacionService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistrarCliente(Model model) {
        Cliente cliente = new Cliente();
        Ubicacion ubicacion = new Ubicacion();
        Usuario usuario = new Usuario();
        model.addAttribute("titulo", "Registrar nuevo cliente");
        model.addAttribute("cliente", cliente);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("usuario", usuario);
        return "Cliente/registrarClientePedido";
    }

    @PostMapping("/guardar")
    public String registrarCliente(@ModelAttribute Cliente cliente, @ModelAttribute Usuario usuario,
            @ModelAttribute Ubicacion ubicacion, RedirectAttributes attributes) {
        System.out.println("Direccion: " + ubicacion.getDireccion());
        try {
            // Asignar una contraseña por defecto
        usuario.setContraseniaUsuario("defaultPassword123");
        
        // Guardar cliente
        Cliente cliente2 = clienteService.guardarCliente(cliente);
        
        // Asociar ubicación con el cliente
        ubicacion.setCliente(cliente2);
        ubicacion.setUbicacionActiva(true);
        
        // Guardar usuario y asociarlo al cliente
        Usuario usuario2 = usuarioService.guardarRetornarU(usuario);
        cliente2.setUsuario(usuario2);
        clienteService.guardarCliente(cliente2);
        
        // Guardar ubicación
        ubicacionService.guardarUbicacion(ubicacion);
            attributes.addFlashAttribute("success", "¡Cliente registrado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al registrar el cliente!");
            e.printStackTrace(); // Para ver el error en la consola
            attributes.addFlashAttribute("error", "¡Error al registrar el cliente: " + e.getMessage() + "!");
        }

        return "redirect:/Pedidos";

    }
}
