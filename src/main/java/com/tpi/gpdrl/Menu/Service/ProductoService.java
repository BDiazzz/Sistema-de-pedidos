package com.tpi.gpdrl.Menu.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpi.gpdrl.Entity.Producto;
import com.tpi.gpdrl.Menu.Repository.ProductoRepository;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
// import com.google.api.services.drive.model.FileContent;
import com.google.api.services.drive.model.Permission;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private GoogleDriverService driverService;

    public List<Producto> listadoProducto() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(int idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }

    public Producto buscarPorNombre(String nombreProducto) {
        return productoRepository.findByNombreProducto(nombreProducto).orElse(null);
    }

    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public void eliminarProducto(int idProducto) {
        productoRepository.deleteById(idProducto);
    }

    public String subirImagen(MultipartFile archivo) throws IOException {
        // Guardar temporalmente el archivo
        Path tempFile = Files.createTempFile("upload-", archivo.getOriginalFilename());
        Files.copy(archivo.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        // Crear metadatos para el archivo en Google Drive
        File fileMetadata = new File();
        fileMetadata.setName(archivo.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList("1McaLuKWBXWhDk_h4DpFsqUgAuTZUvgci")); // Especificar el ID de
                                                                                                 // la carpeta

        // Subir el archivo a Google Drive
        FileContent mediaContent = new FileContent(archivo.getContentType(), tempFile.toFile());
        File uploadedFile = driverService.getDriveService()
                .files()
                .create(fileMetadata, mediaContent)
                .setFields("id, webViewLink")
                .execute();

        // Cambiar los permisos para hacerlo público
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader"); // Permite solo la lectura
        driverService.getDriveService()
                .permissions()
                .create(uploadedFile.getId(), permission)
                .execute();
        Files.delete(tempFile);
        
        // return uploadedFile.getWebViewLink();
        // Crear el enlace directo de la imagen
        // String imagenLink = "https://drive.google.com/thumbnail?id=" + uploadedFile.getId();
        // Eliminar el archivo temporal

        // Retornar el id de la imagen
        return uploadedFile.getId();
    }
    
}