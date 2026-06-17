package com.tpi.gpdrl.Menu.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Entity.Producto;
import com.tpi.gpdrl.Menu.Service.GoogleDriverService;
import com.tpi.gpdrl.Menu.Service.ProductoService;

@Controller
@RequestMapping("/Productos")
@PreAuthorize("hasAnyRole('MENU','ADMINISTRADOR')")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private GoogleDriverService googleDriveService;

    @GetMapping("/listado")
    public String verProductos(Model model) {

        Producto producto = new Producto();
        List<Producto> listadProductos = productoService.listadoProducto();

        model.addAttribute("titulo", "Productos");
        model.addAttribute("producto", producto);
        model.addAttribute("listadoProductos", listadProductos);

        return "Menu/Productos";
    }

    @PostMapping("/guardarProducto")
    public String guardarProducto(@ModelAttribute Producto producto,
            @RequestParam(value = "datosImg", required = false) MultipartFile archivoImg,
            RedirectAttributes attributes) {
        String idImagen;
        String enlaceImagen;
        try {
            if (!archivoImg.isEmpty()) {
                // Subir la imagen a Google Drive y obtener el enlace público
                idImagen = productoService.subirImagen(archivoImg);
                enlaceImagen = "https://drive.google.com/thumbnail?id=" + idImagen;
                producto.setIdImagen(idImagen);
                producto.setEnlaceImagen(enlaceImagen); // Asignar el enlace de Google Drive al producto
                producto.setNombreImg(archivoImg.getOriginalFilename()); // Puedes guardar el nombre de la imagen
                // producto.setDatosImg(null); // No guardamos los bytes de la imagen en la BD
                producto.setExistenciaProducto(true);
                System.out.println("Enlace imagen " + enlaceImagen);
                System.out.println("idImagen " + idImagen);
            }
            productoService.guardarProducto(producto);
            attributes.addFlashAttribute("success", "¡Producto guardado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al guardar al producto!");
        }

        return "redirect:/Productos/listado";
    }

    // @PostMapping("/actualizarProducto")
    // public String actualizarProducto(@ModelAttribute Producto producto,
    // RedirectAttributes attributes) {

    // try {

    // // if (producto.getExistenciaProducto() == null) {
    // // producto.setExistenciaProducto(false);
    // // }
    // System.out.println("existencia=" + producto.getExistenciaProducto());
    // productoService.guardarProducto(producto);
    // attributes.addFlashAttribute("info", "¡Producto actualizado con éxito!");
    // } catch (Exception e) {
    // attributes.addFlashAttribute("error", "¡Error al actualizar al producto!");
    // }

    // return "redirect:/Productos";
    // }

    @PostMapping("/actualizarProducto")
    public String actualizarProducto(@ModelAttribute Producto producto,
            @RequestParam(required = false) MultipartFile archivoImg,
            @RequestParam(required = false) boolean eliminarImagen,
            RedirectAttributes attributes) {
        try {
            Producto productoExistente = productoService.buscarPorId(producto.getIdProducto());

            // Manejar eliminación de imagen
            if (eliminarImagen && productoExistente.getIdImagen() != null) {
                googleDriveService.eliminarArchivo(productoExistente.getIdImagen());
                producto.setIdImagen(null);
                producto.setEnlaceImagen(null);
            } else {
                producto.setIdImagen(productoExistente.getIdImagen());
                producto.setEnlaceImagen(productoExistente.getEnlaceImagen());
            }

            // Manejar nueva imagen
            if (archivoImg != null && !archivoImg.isEmpty()) {
                // String idCarpeta = "1McaLuKWBXWhDk_h4DpFsqUgAuTZUvgci"; // ID de la carpeta en Drive
                String idImagenNueva = productoService.subirImagen(archivoImg);
                String enlaceImagen = "https://drive.google.com/thumbnail?id=" + idImagenNueva;

                // Actualizar producto
                producto.setIdImagen(idImagenNueva);
                producto.setEnlaceImagen(enlaceImagen);
                producto.setNombreImg(archivoImg.getOriginalFilename());

                // Eliminar imagen anterior si existe
                if (productoExistente.getIdImagen() != null) {
                    googleDriveService.eliminarArchivo(productoExistente.getIdImagen());
                }
            }

            // Guardar producto actualizado
            productoService.guardarProducto(producto);
            attributes.addFlashAttribute("info", "¡Producto actualizado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al actualizar el producto!");
            e.printStackTrace();
        }

        return "redirect:/Productos/listado";
    }

    @GetMapping("/deleteProducto/{idProducto}")
    public String eliminarProducto(RedirectAttributes attributes, @PathVariable("idProducto") int idProducto) {
        try {
            Producto productoExistente = productoService.buscarPorId(idProducto);            
            productoService.eliminarProducto(idProducto);
            // Eliminar imagen anterior si existe
            if (productoExistente.getIdImagen() != null) {
                googleDriveService.eliminarArchivo(productoExistente.getIdImagen());
            }
            attributes.addFlashAttribute("warning", "¡Producto eliminado con éxito!");
        } catch (Exception e) {
            System.out.println(e);
            attributes.addFlashAttribute("error", "¡Error al eliminar al producto!");

        }
        return "redirect:/Productos/listado";

    }
}
