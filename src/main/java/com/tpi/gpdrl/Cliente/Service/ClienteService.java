package com.tpi.gpdrl.Cliente.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tpi.gpdrl.Cliente.Repository.ClienteRepository;
import com.tpi.gpdrl.Entity.Cliente;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    /*public void guardarCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }*/
    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listaClientes(){
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Cliente clientePorid(int idCliente){
        return clienteRepository.findById(idCliente).orElse(null);
    }
    
    public Cliente buscarIdCliente(int idCliente){ 
        return clienteRepository.findById(idCliente).orElse(null);
    }
    public Cliente obtenerClienteporIdUsuario(int idUsuario){ 
        return clienteRepository.findByIdUsuario(idUsuario);
    }
}
