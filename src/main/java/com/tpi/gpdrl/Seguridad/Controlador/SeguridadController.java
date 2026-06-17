package com.tpi.gpdrl.Seguridad.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Entity.Usuario;
import com.tpi.gpdrl.Seguridad.Clase.PasswordGenerator;
import com.tpi.gpdrl.Seguridad.Clase.envioCorreo;
import com.tpi.gpdrl.Seguridad.Service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SeguridadController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private envioCorreo correoService;
    
    // Muestra el Login para iniciar sesion
    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(Model model, @RequestParam(name = "error", required = false) String error,
            HttpServletRequest request) {
        model.addAttribute("titulo", "Iniciar Sesión");
        if (error != null) {
            String errorMessage = (String) request.getSession().getAttribute("error");
            model.addAttribute("error", errorMessage);
            request.getSession().removeAttribute("error"); // Remove the error attribute from the session
        }
        return "Seguridad/iniciarSesion";
    }

    @GetMapping("/recuperarContra")
    @PreAuthorize("isAnonymous()")
    public String recuperarContra(Model model) {
        model.addAttribute("titulo", "Recuperar Contraseña");
        return "Seguridad/recuperarContra";
    }

    @PostMapping("/seguridad/recuperar")
    @PreAuthorize("isAnonymous()")
    public String recuperarContraseña(@RequestParam String correo, RedirectAttributes attribute) {
        if (correo == null || correo.isEmpty()) {
            attribute.addFlashAttribute("error", "Debes proporcionar un correo válido.");
            return "redirect:/recuperarContra";
        }
    
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
            
            // Armado de correo
            String titulo = "Recuperación de contraseña";
            String descripcion = "Has solicitado recuperar tu contraseña. Tu nueva contraseña es la siguiente:";
            String consideracion = "Si no solicitaste este cambio, por favor ignora este mensaje o contacta con nuestro soporte.";
            String contrasena = PasswordGenerator.generateRandomPassword(8);
    
            // Si tiene el rol ADMINISTRADOR, actualizar la contraseña
            usuario.setContraseniaUsuario(passwordEncoder.encode(contrasena));
            usuarioService.guardarUsuario(usuario);
            // Enviar correo
            correoService.sendEmail(titulo, descripcion, usuario.getCorreoUsuario(), contrasena, consideracion);
            attribute.addFlashAttribute("success", "Nuevas credenciales enviadas a tu correo.");
            return "redirect:/login";

    
        } catch (Exception e) {
            attribute.addFlashAttribute("error", "El correo no esta registrado.");
            return "redirect:/recuperarContra";

        }
    }
    
    /* 
     * VER PLANTILLA DE RECUPERACIÓN DE CORREO
     */
    @GetMapping("/Recuperar")
    public String verRecuperar(Model model) {
        model.addAttribute("titulo", "Recuperar Contraseña");
        return "Seguridad/Recuperacion";
    }

    // Mostrar pantalla de cerrar sesion
    @GetMapping("/logout-success")
    @PreAuthorize("isAnonymous()")
    public String logoutSuccess() {
        return "Seguridad/logout";
    }
}
