package com.tpi.gpdrl.Programa_lealtad.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Cupon;
import com.tpi.gpdrl.Entity.RegistroCupon;
import com.tpi.gpdrl.Programa_lealtad.Repository.CuponRepository;
import com.tpi.gpdrl.Programa_lealtad.Repository.RegistroCuponRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CuponService {

    // inyeccion de dependencias
    @Autowired
    private CuponRepository cuponRepository;
    @Autowired
    private RegistroCuponRepository registroCuponRepository;

    // operaciones crud
    public List<Cupon> mostrarCupones() {
        return cuponRepository.findByEliminadoFalse();
    }

    public List<Cupon> mostrarCuponesPorUsuario(int idCliente) {

        List<Cupon> cupones = cuponRepository.findByEliminadoFalse(); // Cupones disponibles
        List<RegistroCupon> cuponesUsados = registroCuponRepository.registrosPorCliente(idCliente); // Cupones usados
        List<Cupon> cuponesFiltrados = new ArrayList<>(); // Lista filtrada

        for (Cupon cupon : cupones) { // Recorre la lista de cupones
            boolean bandera = true; // Reinicia la bandera para cada cupón
            for (RegistroCupon registro : cuponesUsados) {
                if (cupon.getIdCupon() == registro.getId()) { // Compara los IDs
                    bandera = false; // Marca como usado
                    break; // Sal del bucle interno
                }
            }
            if (bandera) { // Si el cupón no está usado
                cuponesFiltrados.add(cupon); // Agrega el cupón a la lista filtrada
            }
        }

        return cuponesFiltrados; // Devuelve la lista de cupones disponibles
    }

    public void guardarCupon(Cupon cupon) {
        cuponRepository.save(cupon);
    }

    public void eliminarCupon(int id) {
        // Busca el cupon por ID
        Optional<Cupon> optionalCupon = cuponRepository.findById(id);
        if (optionalCupon.isPresent()) {
            Cupon cupon = optionalCupon.get();
            cupon.setEliminado(true); // Cambia el atributo a true
            cuponRepository.save(cupon); // Actualiza el registro en la base de datos
        } else {
            throw new EntityNotFoundException("No se encontró un cupón con el ID: " + id);
        }
    }

    public void guardarLista(List<Cupon> list) {
        cuponRepository.saveAll(list);
    }

    public int contarCuponesActivos() {
        return cuponRepository.contarCuponesActivos();
    }

    public void desactivarTodosLosCupones() {
        List<Cupon> cupones = cuponRepository.findAll(); // Obtener todos los cupones
        for (Cupon cupon : cupones) {
            cupon.setActivo(false); // Asignar activo = false
        }
        cuponRepository.saveAll(cupones); // Guardar todos los cambios en la base de datos
    }

    public void activarTodosLosCupones() {
        List<Cupon> cupones = cuponRepository.findAll(); // Obtener todos los cupones
        for (Cupon cupon : cupones) {
            cupon.setActivo(true); // Asignar activo = false
        }
        cuponRepository.saveAll(cupones); // Guardar todos los cambios en la base de datos
    }

    public Cupon buscaCuponPorId(int idCupon) {
        return cuponRepository.findById(idCupon).orElse(null);
    }
}
