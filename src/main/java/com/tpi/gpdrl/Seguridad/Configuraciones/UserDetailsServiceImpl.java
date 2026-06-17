package com.tpi.gpdrl.Seguridad.Configuraciones;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Usuario;
import com.tpi.gpdrl.Seguridad.Service.UsuarioService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Petición a BD
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
                
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        usuario.getRoles()
                .forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRolName()))));

        usuario.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getTipoPermiso())));

        return new User(usuario.getCorreoUsuario(),
                        usuario.getContraseniaUsuario(),
                        usuario.isEnabled(), // enabled
                        usuario.isAccountNoExpired(), // accountNonExpired
                        usuario.isCredentialNoExpired(), // credentialsNonExpired
                        usuario.isAccountLocked(), // accountNonLocked
                        authorityList);
    }
}