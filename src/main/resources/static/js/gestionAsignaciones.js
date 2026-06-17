$(document).ready(function () {
    $('.editar-btn').on('click', function () {
        var idasignacion = $(this).data('idasignacion');
        var fecha = $(this).data('fecha');
        var pedido = $(this).data('pedido');
        var repartidor = $(this).data('repartidor');
        var estado = $(this).data('estado');
        

        // Asignar valores a los campos del modal
        $('#editIdasignacion').val(idasignacion);
        $('#editFecha').val(fecha); // Usar .val()
        $('#editPedido').val(pedido);
        $('#editRepartidor').val(repartidor);
        $('#editEstado').val(estado);
        
    });
});