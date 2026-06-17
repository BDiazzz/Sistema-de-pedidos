package com.tpi.gpdrl.Seguridad.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tpi.gpdrl.Entity.Repartidor;
import com.tpi.gpdrl.Cliente.Service.ClienteService;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Usuario;
import com.tpi.gpdrl.Repartidor.Service.RepartidorService;

@Service
public class SessionService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RepartidorService repartidorService;
    @Autowired
    private ClienteService clienteService;

    // @PreAuthorize("hasARole('REPARTIDOR')")
    public Repartidor repartidorSession() {
        String correoUsuario = "No encontrado";
        Repartidor repartidor = new Repartidor();
    
        // Obtenemos la autenticación del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userObject = (User) authentication.getPrincipal();
            correoUsuario = userObject.getUsername();
        }
    
        // Buscamos el usuario por correo
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correoUsuario);
        // Obtenemos el repartidor
        
    
        if (usuario != null) {
            repartidor = repartidorService.obtenerRepartidorIdUsuario(usuario.getIdUsuario());
        } else {
            // Opcional: Lanza una excepción si no se encuentra la empresa
            throw new IllegalStateException("El usuario no tiene una empresa asociada.");
        }
    
        return repartidor;
    }

   
    public Cliente clienteSession() {
        String correoUsuario = "No encontrado";
        Cliente cliente = new Cliente();
    
        // Obtenemos la autenticación del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userObject = (User) authentication.getPrincipal();
            correoUsuario = userObject.getUsername();
        }
    
        // Buscamos el usuario por correo
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correoUsuario);
        // Obtenemos el repartidor
        
    
        if (usuario != null) {
            cliente = clienteService.obtenerClienteporIdUsuario(usuario.getIdUsuario());
        } else {
            // Opcional: Lanza una excepción si no se encuentra la empresa
            throw new IllegalStateException("El usuario no tiene una empresa asociada.");
        }
    
        return cliente;
    }

    
    public Usuario usuarioSession(){
        String correoUsuario = "No encontrado";
    
        // Obtenemos la autenticación del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userObject = (User) authentication.getPrincipal();
            correoUsuario = userObject.getUsername();
        }
    
        // Buscamos el usuario por correo y obtenemos su información
        return usuarioService.obtenerUsuarioPorCorreo(correoUsuario);
    }
}