function verDetallesPedido(id) {
    fetch(`/pedidos/detalle/${id}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('detalleId').textContent = data.id;
            document.getElementById('detalleCliente').textContent = data.cliente.nombre;
            document.getElementById('detalleFecha').textContent = new Date(data.fechaPedido).toLocaleDateString();
            document.getElementById('detalleEstado').textContent = data.estado;
            document.getElementById('detalleRepartidor').textContent = data.repartidor.nombre;
            document.getElementById('detalleTotal').textContent = data.total;
        });
}