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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Entity.Menu;
import com.tpi.gpdrl.Entity.Producto;
import com.tpi.gpdrl.Menu.Service.MenuService;
import com.tpi.gpdrl.Menu.Service.ProductoService;

@Controller
@RequestMapping("/Menu")
@PreAuthorize("hasAnyRole('MENU','ADMINISTRADOR')")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String menu(Model model) {

        List<Menu> listadoMenu = menuService.listadoMenu();
        Menu menu = new Menu();
        List<Producto> listadoProductos = productoService.listadoProducto();

        model.addAttribute("titulo", "Menu del día");
        model.addAttribute("menu", menu);
        model.addAttribute("listadoMenu", listadoMenu);
        model.addAttribute("listadoProducto", listadoProductos);

        return "Menu/Menu";
    }

    @PostMapping("/guardarMenu")
    public String guardarMenu(@ModelAttribute Menu menu, RedirectAttributes redirectAttributes) {

        try {
            System.out.println("producto: " + menu.getProducto().getNombreProducto());
            menuService.guardarMenu(menu);
            redirectAttributes.addFlashAttribute("success", "Registro guardado con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "¡Error al hacer el registro!");
        }

        return "redirect:/Menu/";
    }

    @GetMapping("/deleteMenu/{idMenu}")
    public String deleteMenu(RedirectAttributes attributes, @PathVariable("idMenu") int idMenu) {

        try {
            menuService.deleteMenu(idMenu);
            attributes.addFlashAttribute("warning", "¡Menu eliminado con éxito!");
            System.out.println("entro");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al eliminar el menu!");
        }
        return "redirect:/Menu/";
    }

    @PostMapping("/actualizar")
    public String editarMenu(@ModelAttribute Menu menu, RedirectAttributes redirectAttributes){
        try {
            menuService.guardarMenu(menu);
            redirectAttributes.addFlashAttribute("info","Registro actulizado con exito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","¡Error al actualizar el registro!");
            e.printStackTrace();
        }

        return "redirect:/Menu/";
    }
}
