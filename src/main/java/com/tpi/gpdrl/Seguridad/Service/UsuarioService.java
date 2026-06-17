package com.tpi.gpdrl.Seguridad.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Usuario;
import com.tpi.gpdrl.Seguridad.Repository.UsuarioRepository;

@Service
public class UsuarioService {
    //@Autowired
    //private RolService rolService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void guardarUsuario(Usuario usuario){
        //falta
        usuarioRepository.save(usuario);
    }
    // guarda el usuario y lo retorna
    public Usuario guardarRetornarU(Usuario usuario){
        
        usuario.setPrimerIngreso(false); // Primer ingreso
        usuario.setEnabled(true); // Activo
        usuario.setAccountNoExpired(true); // Cuenta expirada
        usuario.setAccountLocked(true); // Bloqueado
        usuario.setCredentialNoExpired(true); // Credenciales Expiradas
        return usuarioRepository.save(usuario);
    }
    public Usuario buscarPorId(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    /*
     * OBTENER USUARIO POR CORREO
     * 1. Usado para loggear al usuario por medio de correo electronico.
     */
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.usuarioByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario " + correo + " no existe.");
        }
        return usuario;
    }

}
