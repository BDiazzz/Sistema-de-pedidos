package com.tpi.gpdrl.Entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Column(unique = true, nullable = false) // Debe ser único y nunca nulo
    private String rolName;
    
    @ManyToAny
    @JoinTable(name = "rol_permiso", joinColumns = @JoinColumn(name = "id_rol"), inverseJoinColumns = @JoinColumn(name = "id_permiso"))
    Set<Permiso> permisos = new HashSet<>(); // Relación de rol → permiso

    public Rol() {
    }

    public Rol(int idRol, String rolName) {
        this.idRol = idRol;
        this.rolName = rolName;
    }

    public Rol(int idRol, String rolName, Set<Permiso> permisos) {
        this.idRol = idRol;
        this.rolName = rolName;
        this.permisos = permisos;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

}
