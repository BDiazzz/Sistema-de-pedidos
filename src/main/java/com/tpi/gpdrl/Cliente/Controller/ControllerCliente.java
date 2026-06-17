package com.tpi.gpdrl.Cliente.Controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tpi.gpdrl.Cliente.Service.CarritoDTO;
import com.tpi.gpdrl.Cliente.Service.ClienteService;
import com.tpi.gpdrl.Cliente.Service.UbicacionService;
import com.tpi.gpdrl.Entity.Asignacion;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.Cupon;
import com.tpi.gpdrl.Entity.DetallePedido;
import com.tpi.gpdrl.Entity.Factura;
import com.tpi.gpdrl.Entity.Menu;
import com.tpi.gpdrl.Entity.MetodoPago;
import com.tpi.gpdrl.Entity.Pedido;
import com.tpi.gpdrl.Entity.Producto;
import com.tpi.gpdrl.Entity.Puntos;
import com.tpi.gpdrl.Entity.RegistroCupon;
import com.tpi.gpdrl.Entity.Repartidor;
import com.tpi.gpdrl.Entity.Ubicacion;
import com.tpi.gpdrl.Entity.Usuario;
import com.tpi.gpdrl.Factura.Service.FacturaService;
import com.tpi.gpdrl.Menu.Service.MenuService;
import com.tpi.gpdrl.Menu.Service.ProductoService;
import com.tpi.gpdrl.MetodoPago.Service.MetodoPagoService;
import com.tpi.gpdrl.Pedido.Service.AsignacionService;
import com.tpi.gpdrl.Pedido.Service.DetallePedidoService;
import com.tpi.gpdrl.Pedido.Service.PedidoService;
import com.tpi.gpdrl.Programa_lealtad.Service.CuponService;
import com.tpi.gpdrl.Programa_lealtad.Service.RegistroCuponService;
import com.tpi.gpdrl.Puntos.Service.PuntosService;
import com.tpi.gpdrl.Repartidor.Service.RepartidorService;
import com.tpi.gpdrl.Seguridad.Service.RolService;
import com.tpi.gpdrl.Seguridad.Service.SessionService;
import com.tpi.gpdrl.Seguridad.Service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@PreAuthorize("hasAnyRole('CLIENTE','ADMINISTRADOR')")
@Controller
@RequestMapping("/Cliente")
public class ControllerCliente {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private UbicacionService ubicacionService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CuponService cuponService;
    @Autowired
    private PuntosService puntosService;
    @Autowired
    private MetodoPagoService metodoPagoService;
    @Autowired
    private RegistroCuponService registroCuponService;
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private RepartidorService repartidorService;
    @Autowired
    private AsignacionService asignacionService;

    @GetMapping("/CrearCuenta")
    @PreAuthorize("isAnonymous()")
    public String registrarCliente(Model model) {

        Cliente cliente = new Cliente();

        cliente.setUsuario(new Usuario());
        model.addAttribute("titulo", "Crear Cuenta");
        model.addAttribute("cliente", cliente);

        return "Cliente/crearCuentaCliente";
    }

    @PostMapping("/guardarCliente")
    @PreAuthorize("isAnonymous()")
    public String guardarCliente(@ModelAttribute Cliente cliente, Model model) {

        System.out.println("correo = " + cliente.getUsuario().getCorreoUsuario());
        String contrasenia = passwordEncoder.encode(cliente.getUsuario().getContraseniaUsuario());

        System.out.println("Contraseña " + contrasenia);
        cliente.getUsuario().setRoles(rolService.obtenerCliente());
        cliente.getUsuario().setContraseniaUsuario(contrasenia);
        Usuario usuarioGuardado = usuarioService.guardarRetornarU(cliente.getUsuario());
        cliente.setUsuario(usuarioGuardado);

        clienteService.guardarCliente(cliente);

        return "redirect:/login";
    }

    @GetMapping("/verCarrito")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public String verCarrito(Model model, HttpSession session) {
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new CarritoDTO();
        }
        model.addAttribute("carrito", carrito);
        model.addAttribute("titulo", "carrito");
        model.addAttribute("tituloMenu", "Tu carrito");
        model.addAttribute("urlAtras", "/Cliente/RealizarPedido");
        return "Cliente/verCarritoCliente";
    }

    // lo nuevo
    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/RealizarPedido")
    public String realizarPedido(Model model, HttpSession session) {
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new CarritoDTO();
            session.setAttribute("carrito", carrito);
        }

        List<Menu> listadoMenu = menuService.menuActivo();

        model.addAttribute("titulo", "Menu del día");
        model.addAttribute("tituloMenu", "Menu del día");
        model.addAttribute("listadoMenu", listadoMenu);
        model.addAttribute("carrito", carrito);
        model.addAttribute("activeOrdernar", true);

        return "Cliente/realizarPedidoCliente";
    }

    @PostMapping("/agregarPedido")
    public String agregarProducto(@RequestParam int idProducto, @RequestParam int cantidad, HttpSession session) {
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new CarritoDTO();
            session.setAttribute("carrito", carrito);
        }

        Producto producto = productoService.buscarPorId(idProducto);
        if (producto != null) {
            carrito.agregarProducto(producto, cantidad);
        }

        return "redirect:/Cliente/RealizarPedido";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/ConfirmarPedido")
    public String confirmarPedido(Model model, HttpSession session) {
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        Cliente cliente = sessionService.clienteSession();

        Ubicacion ubicacion = ubicacionService.obtenerUbicacionActiva(cliente.getIdCliente());

        // Pedido pedido = pedidoService.buscarPorId(idPedido);
        List<Ubicacion> listadoUbiscaciones = ubicacionService.obtenerUbicacionesPorCliente(cliente);
        int puntos = puntosService.totalPuntos(cliente.getIdCliente());
        model.addAttribute("titulo", "Confirmar Pedido");
        model.addAttribute("cliente", cliente);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("listadoUbicaciones", listadoUbiscaciones);
        model.addAttribute("carrito", carrito);
        model.addAttribute("cantidadPuntos", puntos + 6000);
        model.addAttribute("tituloMenu", "Confirmar tu pedido");
        model.addAttribute("urlAtras", "/Cliente/verCarrito");

        return "Cliente/confirmarPedidoCliente";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @PostMapping("/finalizarPedido")
    public String finalizarPedido(HttpSession session,
            @RequestParam(value = "MetodoPago", required = false) String metodoPago) {

        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");

        if (carrito == null || carrito.getDetalles().isEmpty()) {
            return "redirect:/Cliente/RealizarPedido";
        }
        // Obtenemos el tipo de metodo de pago
        MetodoPago tipoPago = metodoPagoService.obtenerMetodoPago(metodoPago);

        int puntosUsar = 0;
        double nuevoTotal = 0;

        Cliente cliente = sessionService.clienteSession();

        Pedido pedido = new Pedido();
        // Tomando el tiempo del sistema
        long tiempoMilisegundos = System.currentTimeMillis();
        pedido.setFechaPedido(new Date(tiempoMilisegundos));
        pedido.setCliente(cliente);
        pedido.setFechaEntregaP(new Date(tiempoMilisegundos));

        // Registrando cupon si existe
        if (carrito.getCupon() != null) {
            RegistroCupon registroCupon = new RegistroCupon(carrito.getCupon(), cliente);
            registroCuponService.guardarRegistro(registroCupon);

            // Caculando el nuevo total usando un cupon
            nuevoTotal = carrito.calcularTotal()
                    - (carrito.calcularTotal() * (carrito.getCupon().getPorcentajeDescuento()) / 100);
            pedido.setCupon(carrito.getCupon());
            pedido.setTotalAPagar(nuevoTotal);
            System.out.println("Nuevo total= " + nuevoTotal);

        } else if (carrito.getPuntos() != null) {
            puntosUsar = (carrito.getPuntos().getCantidadPuntos()) / 100;
            System.out.println("puntos a usar: " + puntosUsar);
            Puntos puntos = carrito.getPuntos();
            puntos.setCliente(cliente);
            puntos.setCantidadPuntos(-puntosUsar);

            // guardamos los puntos usados
            puntos = puntosService.guardarPuntoR(puntos);
            // Calculando el nuevo total usando puntos
            nuevoTotal = carrito.calcularTotal() - puntosUsar;
            System.out.println("Nuevo total= " + nuevoTotal);
            // modificamos los datos del pedido
            pedido.setPuntos(puntos);
            pedido.setTotalAPagar(nuevoTotal);

        } else {// en el caso de que no seleccionarse un un cupo o puntos solo se extrae el
                // total del carrito
            pedido.setTotalAPagar(carrito.calcularTotal());
        }

        // se crea una nueva factura
        Factura factura = new Factura();
        factura.setMetodoPago(tipoPago);
        factura.setFechaEmision(new Date(tiempoMilisegundos));
        factura.setTotalPago(pedido.getTotalAPagar());

        // se guarda la factura y retorno objeto a pedido
        pedido.setFactura(facturaService.guardarFactura(factura));// se le asigna una factura al pedido
        // Se obtiene la ubicacion activa del cliente y se asocia al pedido
        pedido.setUbicacion(ubicacionService.obtenerUbicacionActiva(cliente.getIdCliente()));// se trae la ubicacion
                                                                                             // activa del cliente

        // Buscamos repartidor activo
        Repartidor repartidor = repartidorService.disponible();
        if (repartidor != null) {
            System.out.println("Repartidor disponible: " + repartidor.getNombreRepartidor());
            // Asignar pedido
        } else {
            System.out.println("No hay repartidores disponibles para hoy.");
        }

        pedido.setTiempoEstimado("35 minutos.");
        // Asignar otros atributos del pedido como cliente, ubicación, etc.

        Pedido pedidoGuardado = pedidoService.guardarPedidoP(pedido);

        // Asignar otros atributos del pedido como cliente, ubicación, etc.

        // se crea la nueva asignacion
        Asignacion asignacion = new Asignacion();
        asignacion.setFechaAsignacion(new Date(tiempoMilisegundos));// Se le asigna la fecha del sistema
        asignacion.setPedido(pedidoGuardado);// se le asigna el pedido
        asignacion.setRepartidor(repartidor);// se le asigna un repartido con disponibilidad
        asignacion.setEstadoOrden(pedidoService.obtenerEstadoPorId(1));// se le asigna el estado de iniciado
        asignacionService.guardarAsignacion(asignacion);

        // List<DetallePedido> detalles =
        // carrito.getDetalles().values().stream().toList();
        for (DetallePedido detalle : carrito.getDetalles().values()) {
            detalle.setPedido(pedidoGuardado);
            detallePedidoService.guardarDetalle(detalle);
        }

        if (carrito.getPuntos() == null) {
            // Registro de puntos
            double total = pedido.getTotalAPagar();
            Puntos puntos = new Puntos();

            int puntosPorDolar = 5;
            int puntosOtorgados = puntosService.calcularPuntos(total, puntosPorDolar);

            puntos.setCantidadPuntos(puntosOtorgados);
            puntos.setCliente(cliente);
            puntos.setFechaTransaccion(new Date(tiempoMilisegundos));
            puntos.setPuntosActivos(true);
            puntosService.guardarPunto(puntos);
        }

        session.removeAttribute("carrito");

        return "redirect:/Cliente/RegistrosPedidos";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String eliminarCarrito(@PathVariable("idProducto") int idProducto, HttpSession session) {

        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        try {
            carrito.eliminarProducto(idProducto);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/Cliente/verCarrito";
    }

    @PostMapping("/actualizarCantidad")
    @ResponseBody
    public ResponseEntity<String> actualizarCantidad(@RequestParam int idProducto, @RequestParam int cantidad,
            HttpSession session) {
        // System.out.println("Entro");
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        if (carrito != null) {
            if (cantidad > 0) {
                DetallePedido detalle = carrito.getDetalles().get(idProducto);
                if (detalle != null) {
                    detalle.setCantidadDetalle(cantidad);
                    detalle.setSubTotal(detalle.getProducto().getPrecioProducto() * cantidad);

                    return ResponseEntity.ok("Cantidad actualizada");

                }
            } else {
                carrito.eliminarProducto(idProducto);
                return ResponseEntity.ok("Producto eliminado");
            }
        }

        return ResponseEntity.badRequest().body("Error al actualizar la cantidad");
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/RegistrosPedidos")
    public String registrosPedidos(Model model) {

        Cliente cliente = sessionService.clienteSession();
        List<Asignacion> listadoPedidos = asignacionService.listadoPedidos(cliente.getIdCliente());

        model.addAttribute("titulo", "Pedidos");
        model.addAttribute("tituloMenu", "Mis pedidos");
        model.addAttribute("activePedidos", true);
        model.addAttribute("listadoPedidos", listadoPedidos);
        return "Cliente/registrosPedido";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/detalle/{idAsignacion}")
    public String detallesPedido(Model model, @PathVariable("idAsignacion") int idAsignacion) {

        Asignacion asignacion = asignacionService.ObtnerIdasignacion(idAsignacion);
        List<DetallePedido> detalles = detallePedidoService.obtenerPedidoPorId(asignacion.getPedido().getIdPedido());

        double descuento = 0,  totalSinDescuento = 0, porcentajeDescuento = 0;

        if (asignacion.getPedido().getCupon() != null) {

            porcentajeDescuento = ((double) asignacion.getPedido().getCupon().getPorcentajeDescuento()) / 100;
            totalSinDescuento = (asignacion.getPedido().getTotalAPagar()) / (1 - porcentajeDescuento);
            descuento = totalSinDescuento * (porcentajeDescuento);
            System.out.println("Descuento: " + descuento);

        }
        model.addAttribute("titulo", "Detalles");
        model.addAttribute("detalles", detalles);
        model.addAttribute("asignacion", asignacion);
        model.addAttribute("descuento", descuento);
        model.addAttribute("totalSinDescuento", totalSinDescuento);
        model.addAttribute("tituloMenu", "Detalles del pedido");
        model.addAttribute("urlAtras", "/Cliente/RegistrosPedidos");

        return "Cliente/detallesPedido";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/canjearCupon")
    public String canjearCupon(Model model) {

        Cliente cliente = sessionService.clienteSession();
        // creando lista de cupones que contiene la informacion de metodo
        List<Cupon> listadoCupones = cuponService.mostrarCuponesPorUsuario(cliente.getIdCliente());

        model.addAttribute("titulo", "Canjear Cupon");
        model.addAttribute("tituloMenu", "Mis cupones");
        model.addAttribute("urlAtras", "/Cliente/verCarrito");
        // mandando lista de cupones
        model.addAttribute("cupones", listadoCupones);
        return "Cliente/canjearCupon";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/agregandoCupon/{idCupon}")
    public String confirmarCupon(@PathVariable("idCupon") int idCupon, HttpSession session) {
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");

        if (carrito == null || carrito.getDetalles().isEmpty()) {
            return "redirect:/Cliente/RealizarPedido";
        }
        Cupon cupon = cuponService.buscaCuponPorId(idCupon);

        if (cupon != null) {
            carrito.agregarCupon(cupon);
            System.out.println("Entro");
        }
        return "redirect:/Cliente/ConfirmarPedido";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @PostMapping("/confirmarCupones")
    public String confirmarPuntos(@RequestParam(value = "puntos") int cantidadPuntos, HttpSession session) {
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");

        Puntos puntos = new Puntos();
        puntos.setCantidadPuntos(cantidadPuntos);
        carrito.agregarPuntos(puntos);
        System.out.println("trae " + carrito.getPuntos().getCantidadPuntos());

        return "redirect:/Cliente/ConfirmarPedido";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/perfil")
    public String perfil(Model model) {
        Cliente cliente = sessionService.clienteSession();

        model.addAttribute("titulo", "Perfil");
        model.addAttribute("cliente", cliente);
        model.addAttribute("activePerson", true);
        return "Cliente/perfil";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/datosPersonales")
    public String datosPersonales(Model model) {
        Cliente cliente = sessionService.clienteSession();

        model.addAttribute("titulo", "Datos Personales");
        model.addAttribute("cliente", cliente);
        model.addAttribute("tituloMenu", "Mis datos Personales");
        model.addAttribute("urlAtras", "/Cliente/perfil");
        return "Cliente/datosPersonales";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @PostMapping("/editarDatos")
    public String editarPerfil(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {

        try {
            clienteService.guardarCliente(cliente);
            redirectAttributes.addFlashAttribute("success", "¡Datos personales editados con exito!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "¡Error al editar tus satos personales !");
        }
        return "redirect:/Cliente/perfil";
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/InformacionPersonal")
    public String informacionCliente(Model model) {
        Cliente cliente = sessionService.clienteSession();
        model.addAttribute("titulo", "Datos Personales");
        model.addAttribute("cliente", cliente);
        model.addAttribute("tituloMenu", "Información personal");
        model.addAttribute("urlAtras", "/Cliente/perfil");
        return "Cliente/informacionCliente";
    }
}
