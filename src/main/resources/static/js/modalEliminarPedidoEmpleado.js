document.addEventListener('DOMContentLoaded', function () {
    var confirmDeleteModal = document.getElementById('confirmDeleteModal');
    
    // Asignar el evento para mostrar el modal y capturar el id del pedido
    confirmDeleteModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget; // Botón que abrió el modal
        var pedidoId = button.getAttribute('data-id'); // ID del pedido desde el botón
        
        // Asignar el ID al botón de confirmación para que se pase al eliminar
        var confirmDeleteButton = document.getElementById('confirmDeleteButton');
        confirmDeleteButton.onclick = function() {
            // Realizar la eliminación usando AJAX
            fetch('/Pedidos/deletePedido/' + pedidoId, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json'
                }
            })
            .then(response => {
                if (response.ok) {
                    // Cerrar el modal después de eliminar el pedido
                    var modal = bootstrap.Modal.getInstance(confirmDeleteModal);
                    modal.hide();

                    // Eliminar la fila correspondiente en la tabla
                    var row = button.closest('tr');
                    row.remove();
                    
                    // Aquí puedes mostrar un mensaje de éxito (opcional)
                    alert("Pedido eliminado con éxito!");
                } else {
                    // Mostrar un mensaje de error si la eliminación falló
                    alert("¡Error al eliminar el pedido!");
                }
            })
            .catch(error => {
                console.error('Error en la solicitud de eliminación:', error);
                alert("¡Error al eliminar el pedido!");
            });
        };
    });
});