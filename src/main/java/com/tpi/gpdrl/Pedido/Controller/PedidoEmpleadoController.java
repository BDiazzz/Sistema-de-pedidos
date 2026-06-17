package com.tpi.gpdrl.Pedido.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import com.itextpdf.text.DocumentException;
import com.tpi.gpdrl.Cliente.Service.ClienteService;
import com.tpi.gpdrl.Cliente.Service.UbicacionService;
import com.tpi.gpdrl.Entity.Asignacion;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.DetallePedido;
import com.tpi.gpdrl.Entity.EstadoOrden;
import com.tpi.gpdrl.Entity.Factura;
import com.tpi.gpdrl.Entity.MetodoPago;
import com.tpi.gpdrl.Entity.Pedido;
import com.tpi.gpdrl.Entity.Producto;
import com.tpi.gpdrl.Entity.Repartidor;
import com.tpi.gpdrl.Entity.Ubicacion;
import com.tpi.gpdrl.Menu.Service.ProductoService;
import com.tpi.gpdrl.MetodoPago.Service.MetodoPagoService;
import com.tpi.gpdrl.Pedido.Service.PedidoService;
import com.tpi.gpdrl.Repartidor.Service.RepartidorService;
import com.tpi.gpdrl.Pedido.Service.AsignacionService;
import com.tpi.gpdrl.Pedido.Service.DetallePedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

/*import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayInputStream;*/

@Controller
@RequestMapping("/Pedidos")
@PreAuthorize("hasAnyRole('REGISTRO','ADMINISTRADOR')")
public class PedidoEmpleadoController {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private AsignacionService asignacionService;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private RepartidorService repartidorService;
    @Autowired
    private UbicacionService ubicacionService;
    @Autowired
    private MetodoPagoService metodoPagoService;

    // lista pedidos
    @GetMapping("")
    public String verListaPedidos(Model model) {
        Pedido pedido = new Pedido();
        List<Pedido> listadPedidos = pedidoService.listadoPedido();
        List<Cliente> listClientes = clienteService.listaClientes();
        model.addAttribute("titulo", "Lista de Pedidos");
        model.addAttribute("listClientes", listClientes);
        model.addAttribute("pedido", pedido);
        model.addAttribute("listadoPedidos", listadPedidos);
        return "Pedido/listaPedidos";
    }

    @PostMapping("/seleccionarCliente")
    public String seleccionarCliente(@RequestParam("idCliente") int idCliente) {
        Pedido pedido = new Pedido();

        Cliente clienteSeleccionado = clienteService.clientePorid(idCliente);
        pedido.setCliente(clienteSeleccionado);
        pedido = pedidoService.retornarPedido(pedido);
        return "redirect:/Pedidos/agregar/" + pedido.getIdPedido();
    }

    // lista de asignaciones
    @GetMapping("/listaAsignaciones")
    public String consultarPedidoEmpleado(Model model) {
        Asignacion asignacion = new Asignacion();
        List<Asignacion> listadAsignaciones = asignacionService.listadoAsignacion();
        Repartidor repartidor = new Repartidor();
        List<Repartidor> listadRepartidores = repartidorService.repartidorDisponible();
        model.addAttribute("titulo", "Lista de Asignaciones");
        model.addAttribute("asignacion", asignacion);
        model.addAttribute("listadoAsignaciones", listadAsignaciones);
        model.addAttribute("repartidor", repartidor);
        model.addAttribute("listadoRepartidores", listadRepartidores);
        return "Pedido/listaAsignaciones";
    }

    // actualizar repartidor desde el modal que esta en la plantilla
    // listaAsignaciones
    @PostMapping("/asignacion/actualizarRepartidor")
    public String actualizarRepartidorAsignacion(@ModelAttribute Asignacion asignacion, RedirectAttributes attributes) {

        try {

            System.out.println("Repartidor seleccionado" + asignacion.getRepartidor().getNombreRepartidor());

            asignacionService.guardarAsignacion(asignacion);

            attributes.addFlashAttribute("info", "¡Repartidor actualizado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al actualizar el repartidor!");
        }

        return "redirect:/Pedidos/listaAsignaciones";
    }

    // mostrar formulario para agregar nuevo pedido
    @GetMapping("/agregar/{idPedido}")
    public String mostrarFormularioAgregarPedido(@PathVariable("idPedido") int idPedido, Model model) {
        // Cliente cliente = new Cliente();
        // Asignacion asignacion = new Asignacion();
        Pedido pedido = pedidoService.buscarPorId(idPedido);

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        List<DetallePedido> detalles = detallePedidoService.obtenerPedidoPorId(idPedido);
        // List<Cliente> listClientes = clienteService.listaClientes();
        List<Producto> listProductos = productoService.listadoProducto();
        model.addAttribute("titulo", "Registrar Pedido");
        model.addAttribute("pedido", pedido);
        model.addAttribute("detallePedido", detallePedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("listProductos", listProductos);
        // model.addAttribute("cliente", pedido.getCliente());

        return "Pedido/registrarPedidoEmpleado";
    }

    // editar pedido desde el listado de pedidos
    @GetMapping("/editar/{idPedido}")
    public String editarPedido(RedirectAttributes attributes, @PathVariable("idPedido") int idPedido, Model model) {
        Pedido pedido = pedidoService.buscarPorId(idPedido);
        DetallePedido detallePedido = detallePedidoService.pedidoPorId(idPedido);
        List<DetallePedido> detalles = detallePedidoService.obtenerPedidoPorId(idPedido);
        List<Producto> listProductos = productoService.listadoProducto();
        // Ubicacion ubicacion =
        // ubicacionService.obtenerUbicacionActiva(pedido.getCliente().getIdCliente());
        // model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("titulo", "Editar Pedido");
        model.addAttribute("pedido", pedido);
        model.addAttribute("detallePedido", detallePedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("listProductos", listProductos);
        attributes.addFlashAttribute("warning", "¡Pedido editado con éxito!");

        return "Pedido/editarPedidoEmpleado";
    }

    @PostMapping("/cancelarPedido/{idPedido}")
    public String cancelarPedido(@PathVariable("idPedido") int idPedido, RedirectAttributes attributes) {
        try {
            // Verificar si el pedido está asociado a alguna asignación
            boolean tieneAsignacion = asignacionService.existeAsignacionPorPedido(idPedido);

            if (!tieneAsignacion) {
                // Si no tiene asignaciones, eliminar el pedido
                List<DetallePedido> detallePedidos = detallePedidoService.obtenerPedidoPorId(idPedido);
                boolean tieneDetallePedido = detallePedidos != null && !detallePedidos.isEmpty();

                if (tieneDetallePedido) {
                    detallePedidoService.eliminarDetallesPorPedido(detallePedidos);
                    pedidoService.eliminarPedido(idPedido);
                    attributes.addFlashAttribute("success", "¡Pedido cancelado con éxito!");
                } else {
                    pedidoService.eliminarPedido(idPedido);
                    attributes.addFlashAttribute("success", "¡Pedido cancelado con éxito!");
                }
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("warning", "¡Se ha cancelado la edicion del Pedido!");
            e.printStackTrace();
        }
        return "redirect:/Pedidos"; // Redirige a la lista de pedidos
    }

    // agregar nuevo pedido
    @PostMapping("/guardarPedido/{idPedido}")
    public String agregarPedidoEmpleado(@PathVariable("idPedido") int idPedido, @ModelAttribute Pedido pedido,
            RedirectAttributes attributes) {

        try {

            List<DetallePedido> detalles = detallePedidoService.obtenerPedidoPorId(idPedido);
            // Validar si no hay detalles
            if (detalles == null || detalles.isEmpty()) {
                attributes.addFlashAttribute("error", "¡Tiene que agregar productos al pedido!");
                return "redirect:/Pedidos/agregar/" + idPedido; // Redirige de nuevo a la página de registar el pedido
            }
            EstadoOrden estado = pedidoService.obtenerEstadoPorId(1);
            Asignacion asignacion = null;

            asignacion = asignacionService.asignacion(idPedido);
            if (asignacion == null) {
                asignacion = new Asignacion();
            }

            // Repartidor repsRepartidor = repartidorService.buscarPorId(1);

            asignacion.setPedido(pedido);
            asignacion.setEstadoOrden(estado);
            // asignacion.setRepartidor(repsRepartidor);

            asignacionService.guardarAsignacion(asignacion);

            double totalPedido = 0.0;

            // aca se saca el subtotal
            for (DetallePedido detallePedido : detalles) {
                totalPedido += detallePedido.getSubTotal();
            }

            // formatear el total
            totalPedido = Double.parseDouble(String.format("%.2f", totalPedido));

            // obteniendo direccion
            Ubicacion ubicacion = ubicacionService.obtenerUbicacionActiva(pedido.getCliente().getIdCliente());

            pedido.setUbicacion(ubicacion);
            pedido.setTotalAPagar(totalPedido);

            if (pedido.getFactura() == null) {
                // Si no existe factura, crear una nueva
                Factura nuevaFactura = new Factura();
                nuevaFactura.setTotalPago(totalPedido);
                MetodoPago metodoPago = metodoPagoService.obtenerMetodoPago("EFECTIVO"); // tipo de pago en efectivo

                nuevaFactura.setMetodoPago(metodoPago);
                pedidoService.guardarFactura(nuevaFactura);

                pedido.setFactura(nuevaFactura); // Asignar la nueva factura al pedido
                // Guardar el pedido con la nueva factura asociada
            } else {
                // Si ya existe una factura, actualizar su información
                Factura facturaExistente = pedidoService.factura(pedido.getFactura().getIdFactura());
                facturaExistente.setTotalPago(totalPedido);
                pedidoService.guardarFactura(facturaExistente);
            }
            pedidoService.guardarPedido(pedido);

            attributes.addFlashAttribute("success", "¡Pedido guardado con éxito!");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            attributes.addFlashAttribute("error", "¡Error al guardar al pedido!");
        }
        return "redirect:/Pedidos";
    }

    // sirve para agregar productos al pedido
    @PostMapping("/guardarDetalle")
    public String guardarDetalle(@ModelAttribute DetallePedido detallePedido) {

        double subTotal = detallePedido.getCantidadDetalle() * detallePedido.getProducto().getPrecioProducto();
        double total = detallePedido.getPedido().getTotalAPagar();
        total += subTotal;

        // aqui se formatea a dos decimales
        subTotal = Double.parseDouble(String.format("%.2f", subTotal));
        total = Double.parseDouble(String.format("%.2f", total));

        detallePedido.setSubTotal(subTotal);
        detallePedido.getPedido().setTotalAPagar(total);

        detallePedido.setPedido(pedidoService.retornarPedido(detallePedido.getPedido()));
        detallePedidoService.guardarDetalle(detallePedido);

        return "redirect:/Pedidos/agregar/" + detallePedido.getPedido().getIdPedido();
    }

    // sirve para actualizar productos del pedido
    @PostMapping("/actualizarDetalle")
    public String actuakizarDetalle(@ModelAttribute DetallePedido detallePedido) {

        detallePedidoService.guardarDetalle(detallePedido);

        return "redirect:/Pedidos/agregar/" + detallePedido.getPedido().getIdPedido();
    }

    // menu de inicio
    @GetMapping("/Inicio")
    public String ventanaInicio(Model model) {
        return "/Pedido/inicioEmpleado";
    }

    // Consultar detalles de pedido
    @PreAuthorize("hasAnyRole('DESPACHO','ADMINISTRADOR','REGISTRO')")
    @GetMapping("/consultar/{idAsignacion}")
    public String consultarDetallePedido(@PathVariable("idAsignacion") int idAsignacion, Model model) {
        // DetallePedido detallePedido = detallePedidoService.obtenerPedidoPorId(id);
        Asignacion asignacion = asignacionService.ObtnerIdasignacion(idAsignacion);

        int idPedido = asignacion.getPedido().getIdPedido();
        List<DetallePedido> detallePedido = detallePedidoService.obtenerPedidoPorId(idPedido);

        model.addAttribute("titulo", "Consultar Pedido");
        model.addAttribute("detallePedido", detallePedido);
        model.addAttribute("asignacion", asignacion);
        return "Pedido/consultarAsignacionPedido";
    }

    // eliminar pedido desde el listado de pedidos
    @GetMapping("/deletePedido/{idPedido}")
    public String eliminarPedido(RedirectAttributes attributes, @PathVariable("idPedido") int idPedido) {
        try {
            Pedido pedido = pedidoService.buscarPorId(idPedido);
            // Primero eliminamos todos los detalles de pedidos relacionados
            List<DetallePedido> detallePedidos = detallePedidoService.obtenerPedidoPorId(idPedido);
            detallePedidoService.eliminarDetallesPorPedido(detallePedidos);

            // Borrammos la asignación
            asignacionService.eliminarAsignacionPorPedido(idPedido);
            // De ultimo se eliminar el pedido

            pedidoService.eliminarPedido(idPedido);
            pedidoService.eliminarFactura(pedido.getFactura());
            attributes.addFlashAttribute("warning", "¡Pedido eliminado con éxito!");
        } catch (Exception e) {
            System.out.println(e);
            attributes.addFlashAttribute("error", "¡Error al eliminar al pedido!");

        }
        return "redirect:/Pedidos";
    }

    // eliminar productos del pedido
    @GetMapping("/deleteDetallePedido/{idPedido}/{idDetalle}")
    public String eliminarProductoDelPedido(@PathVariable("idPedido") int idPedido,
            @PathVariable("idDetalle") int idDetalle,
            RedirectAttributes attributes) {

        try {
            detallePedidoService.eliminarPorId(idDetalle);

            attributes.addFlashAttribute("warning", "¡Producto eliminado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al eliminar el producto!");
        }
        List<DetallePedido> listDetallePedidos = detallePedidoService.obtenerPedidoPorId(idPedido);
        double total = 0;
        Pedido pedido = pedidoService.buscarPorId(idPedido);

        // proceso de actualizar el total a pagar luego de eliminar un producto
        for (DetallePedido detallePedido : listDetallePedidos) {
            total += detallePedido.getSubTotal();
            System.out.println("tota= " + total);
        }

        pedido.setTotalAPagar(total);
        pedidoService.guardarPedido(pedido);

        return "redirect:/Pedidos/agregar/" + idPedido;
    }

    // actualizar seleccion de productos en el pedido
    @PostMapping("/actualizarProducto/")
    public String actualizarProducto(@ModelAttribute DetallePedido detallePedido, RedirectAttributes attributes) {

        try {
            double subTotal = detallePedido.getCantidadDetalle() * detallePedido.getProducto().getPrecioProducto();

            DetallePedido detalleAnterior = detallePedidoService.pedidoPorId(detallePedido.getIdDetallePedido());

            double total = detallePedido.getPedido().getTotalAPagar();
            System.out.println("Total original = " + total);
            total -= detalleAnterior.getSubTotal();
            System.out.println("Total resta = " + total);
            total += subTotal;
            System.out.println("Total final = " + total);
            detallePedido.setSubTotal(subTotal);
            detallePedido.getPedido().setTotalAPagar(total);

            detallePedido.setPedido(pedidoService.retornarPedido(detallePedido.getPedido()));
            detallePedidoService.guardarDetalle(detallePedido);
            attributes.addFlashAttribute("info", "¡Producto actualizado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al actualizar al producto!");
        }

        return "redirect:/Pedidos/agregar/" + detallePedido.getPedido().getIdPedido();
    }

    /* modulo despacho de pedidos */
    @PreAuthorize("hasAnyRole('DESPACHO','ADMINISTRADOR')")
    @GetMapping("/listaPedidosDespacho")
    public String verListaPedidosDespacho(Model model) {
        // asignacion = new Asignacion();
        List<Asignacion> listadAsignaciones = asignacionService.listadoAsignacion();
        List<Asignacion> asignacionesEnProceso = new ArrayList<>();

        // Filtrar las asignaciones con estado "En proceso" (ID = 1)
        for (Asignacion asignacion : listadAsignaciones) {
            if (asignacion.getEstadoOrden() != null && asignacion.getEstadoOrden().getIdEstadoOrden() == 1) {
                asignacionesEnProceso.add(asignacion);
            }
        }

        model.addAttribute("titulo", "Pedidos Despacho");
        // model.addAttribute("asignacion", new Asignacion());
        model.addAttribute("listadoAsignaciones", asignacionesEnProceso);

        return "Pedido/listaPedidosDespacho";
    }
    @PreAuthorize("hasAnyRole('DESPACHO','ADMINISTRADOR')")
    @PostMapping("/guardarlistaPedidosDespacho")
    public String despacharPedidoEmpleado(@RequestParam("idAsignacion") int idAsignacion,
            RedirectAttributes attributes) {
        try {

            Asignacion asignacion = asignacionService.ObtnerIdasignacion(idAsignacion);
            // Establecer el nuevo estado
            EstadoOrden estado = pedidoService.obtenerEstadoPorId(2); // Estado 'Completado'
            asignacion.setEstadoOrden(estado);

            Repartidor repartidor = repartidorService.disponible();
            asignacion.setRepartidor(repartidor);
            asignacionService.guardarAsignacion(asignacion);

            attributes.addFlashAttribute("success", "¡Estado modificado con éxito!");
        } catch (Exception e) {
            System.out.println(e);
            attributes.addFlashAttribute("error", "¡Error al modificar estado del pedido!");
        }
        return "redirect:/Pedidos/listaPedidosDespacho";
    }

    /*
     * @GetMapping("/generarFacturaPDF/{idPedido}")
     * public ResponseEntity<byte[]> generarFacturaPDF(@PathVariable("idPedido") int
     * idPedido)
     * throws DocumentException, IOException {
     * // Buscar el pedido en la base de datos
     * Pedido pedido = pedidoService.buscarPorId(idPedido);
     * 
     * if (pedido == null) {
     * return ResponseEntity.notFound().build(); // Retornar 404 si el pedido no
     * existe
     * }
     * 
     * // Llamamos al servicio que genera el PDF
     * byte[] pdfBytes = pedidoService.generarFacturaPDF(pedido);
     * 
     * if (pdfBytes == null || pdfBytes.length == 0) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
     * body("Error al generar el PDF".getBytes());
     * }
     * 
     * // Convertimos los bytes del PDF en un InputStreamResource
     * // ByteArrayInputStream byteArrayInputStream = new
     * // ByteArrayInputStream(pdfBytes);
     * // InputStreamResource inputStreamResource = new
     * // InputStreamResource(byteArrayInputStream);
     * 
     * // Devolvemos el PDF con las cabeceras adecuadas para mostrarlo en el
     * navegador
     * return ResponseEntity.ok()
     * .header("Content-Disposition", "inline; filename=factura_" +
     * pedido.getIdPedido() + ".pdf")
     * .body(pdfBytes);
     * }
     */
}
