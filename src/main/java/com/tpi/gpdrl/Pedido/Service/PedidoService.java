package com.tpi.gpdrl.Pedido.Service;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tpi.gpdrl.Entity.Pedido;
import com.tpi.gpdrl.Factura.Repository.FacturaRepository;
/*import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;*/
import com.tpi.gpdrl.Cliente.Repository.ClienteRepository;
import com.tpi.gpdrl.Entity.Cliente;
import com.tpi.gpdrl.Entity.EstadoOrden;
import com.tpi.gpdrl.Entity.Factura;
import com.tpi.gpdrl.Pedido.Repository.EstadoOrdenRepository;
import com.tpi.gpdrl.Pedido.Repository.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;
    @Autowired
    private FacturaRepository facturaRepository;
    // @Autowired
    // private DetallePedidoService detallePedidoService;

    public List<Pedido> listadoPedido() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(int idPedido) {
        return pedidoRepository.findById(idPedido).orElse(null);
    }

    public Pedido guardarPedidoP(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void guardarPedido(Pedido pedido) {

        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechaActual = new Date(tiempoMilisegundos);

        pedido.setFechaPedido(fechaActual);
        pedidoRepository.save(pedido);
    }

    public void guardarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Pedido retornarPedido(Pedido pedido) {

        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechaActual = new Date(tiempoMilisegundos);

        pedido.setFechaPedido(fechaActual);
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(int idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

    public EstadoOrden obtenerEstadoPorId(int idEstadoOrden) {
        return estadoOrdenRepository.findById(idEstadoOrden).orElse(null);
    }

    public void guardarFactura(Factura nuevaFactura) {
        long tiempoMilisegundos = System.currentTimeMillis();
        Date fechaActual = new Date(tiempoMilisegundos);
        nuevaFactura.setFechaEmision(fechaActual);
        facturaRepository.save(nuevaFactura);
    }

    public Factura factura(int idFactura) {
        return facturaRepository.findFacturaById(idFactura);
    }

    public void eliminarFactura(Factura factura) {
        facturaRepository.delete(factura);
    }

    /*
     * public byte[] generarFacturaPDF(Pedido pedido) throws DocumentException,
     * IOException {
     * // Crear un documento PDF
     * Document document = new Document(PageSize.A4);
     * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
     * PdfWriter writer = PdfWriter.getInstance(document, outputStream);
     * 
     * // Abrimos el documento para agregar contenido
     * document.open();
     * 
     * // Titulo de la factura
     * Paragraph title = new Paragraph("Factura de Pedido",
     * FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
     * title.setAlignment(Element.ALIGN_CENTER);
     * document.add(title);
     * 
     * // Información del pedido
     * document.add(new Paragraph("Fecha de Entrega: " +
     * pedido.getFechaEntregaP()));
     * document.add(new Paragraph("Tiempo Estimado: " +
     * pedido.getTiempoEstimado()));
     * document.add(new Paragraph("Descripción del Pedido: " +
     * pedido.getDescripcionOrden()));
     * 
     * // Información del cliente
     * document.add(new Paragraph("Cliente: " +
     * pedido.getCliente().getNombreCliente() + " " +
     * pedido.getCliente().getApellidoCliente()));
     * document.add(new Paragraph("Dirección: " +
     * pedido.getUbicacion().getDireccion()));
     * 
     * // Buscar los detalles del pedido a través de la relación con idPedido
     * List<DetallePedido> detalles =
     * detallePedidoService.obtenerPedidoPorId(pedido.getIdPedido());
     * 
     * // Detalle de productos
     * document.add(new Paragraph("Productos:"));
     * PdfPTable table = new PdfPTable(4);
     * table.setWidthPercentage(100);
     * table.addCell("Producto");
     * table.addCell("Tipo");
     * table.addCell("Cantidad");
     * table.addCell("Precio");
     * 
     * // Agregar cada detalle al PDF
     * for (DetallePedido detalle : detalles) {
     * table.addCell(detalle.getProducto().getNombreProducto());
     * table.addCell(detalle.getProducto().getTipoProducto());
     * table.addCell(String.valueOf(detalle.getCantidadDetalle()));
     * table.addCell(String.valueOf(detalle.getSubTotal()));
     * }
     * 
     * document.add(table);
     * 
     * // Total a pagar
     * document.add(new Paragraph("Total a Pagar: " + pedido.getTotalAPagar()));
     * 
     * // Cerramos el documento
     * document.close();
     * 
     * // Devolvemos el PDF en formato byte array
     * return outputStream.toByteArray();
     * }
     */
}
