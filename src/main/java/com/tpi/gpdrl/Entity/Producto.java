package com.tpi.gpdrl.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.Lob;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    private String nombreProducto;
    private String tipoProducto;
    private String descripcionProducto;
    private double precioProducto;
    private Boolean existenciaProducto;

    // @Lob
    // private byte[] datosImg;
    private String nombreImg; // El nombre de la imagen (puede ser útil para algunas consultas)
    private String enlaceImagen; // Nuevo atributo para almacenar el enlace de la imagen en Google Drive
    private String idImagen; // id de la imagen
    

    public Producto() {
    }

    public Producto(String nombreProducto, String tipoProducto, String descripcionProducto, double precioProducto,
            Boolean existenciaProducto, String nombreImg, String enlaceImagen, String idImagen) {
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.existenciaProducto = existenciaProducto;
        this.nombreImg = nombreImg;
        this.enlaceImagen = enlaceImagen;
        this.idImagen = idImagen;
    }

    public Producto(int idProducto, String nombreProducto, String tipoProducto, String descripcionProducto,
            double precioProducto, Boolean existenciaProducto, String nombreImg, String enlaceImagen, String idImagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.existenciaProducto = existenciaProducto;
        this.nombreImg = nombreImg;
        this.enlaceImagen = enlaceImagen;
        this.idImagen = idImagen;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }    

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }  

    // public byte[] getDatosImg() {
    // return datosImg;
    // }

    // public void setDatosImg(byte[] datosImg) {
    // this.datosImg = datosImg;
    // }

    public String getNombreImg() {
        return nombreImg;
    }

    public void setNombreImg(String nombreImg) {
        this.nombreImg = nombreImg;
    }

    public Boolean getExistenciaProducto() {
        return existenciaProducto;
    }

    public void setExistenciaProducto(Boolean existenciaProducto) {
        this.existenciaProducto = existenciaProducto;
    }

    public String getEnlaceImagen() {
        return enlaceImagen;
    }

    public void setEnlaceImagen(String enlaceImagen) {
        this.enlaceImagen = enlaceImagen;
    }

}
