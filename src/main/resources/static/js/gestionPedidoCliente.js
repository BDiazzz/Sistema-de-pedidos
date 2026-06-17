$(document).ready(function () {
    // Abre el modal y asigna los datos del producto
    $('.ver-btn').on('click', function () {
        var idMenu = $(this).data('idmenu');
        var idProducto = $(this).data('idproducto');
        var nombreProducto = $(this).data('nombreproducto');
        var descripcionProducto = $(this).data('descripcion');
        var precioProducto = $(this).data('precio');
        var imageProducto = $(this).data('img');

        // Asignar los valores a los campos del modal
        $('#verNombreProducto').text(nombreProducto);
        $('#verDescripcionProducto').text(descripcionProducto);
        $('#verPrecio').text('$' + precioProducto);
        $('#verImagen').attr('src', imageProducto);

        // Asignar los valores a los inputs ocultos
        $('#idProducto').val(idProducto);
        $('#idMenu').val(idMenu);

        // Actualizar el subtotal inicial
        updateSubtotal(precioProducto, 1);  // Llamamos a la función con el precio y la cantidad inicial (1)
    });
    document.querySelectorAll('.read-more').forEach(button => {
        button.addEventListener('click', function () {
            const description1 = this.previousElementSibling;
            description1.classList.add('expanded'); this.classList.add('d-none');
            this.nextElementSibling.classList.remove('d-none');
        });
    });
    document.querySelectorAll('.read-less').forEach(button => {
        button.addEventListener('click', function () {
            const description1 = this.previousElementSibling.previousElementSibling;
            description1.classList.remove('expanded'); this.classList.add('d-none');
            this.previousElementSibling.classList.remove('d-none');
        });
    });
    // Actualizar la cantidad cuando se presionan los botones de incremento o decremento
    document.getElementById('increaseQuantity').addEventListener('click', function () {
        var quantityInput = document.getElementById('quantityInput');
        quantityInput.value = parseInt(quantityInput.value) + 1;
        updateSubtotal($('#verPrecio').text().replace('$', ''), quantityInput.value);  // Actualizar subtotal
    });

    document.getElementById('decreaseQuantity').addEventListener('click', function () {
        var quantityInput = document.getElementById('quantityInput');
        if (parseInt(quantityInput.value) > 1) { 
            quantityInput.value = parseInt(quantityInput.value) - 1;
            updateSubtotal($('#verPrecio').text().replace('$', ''), quantityInput.value);  // Actualizar subtotal
        }
    });

    // Restablecer la cantidad a 1 cuando el modal se cierra
    $('#modalWhopper').on('hidden.bs.modal', function () {
        $('#quantityInput').val(1);  // Restablece la cantidad a 1
        updateSubtotal($('#verPrecio').text().replace('$', ''), 1);  // Restablece el subtotal con la cantidad 1
    });
});

// Función para actualizar el subtotal
function updateSubtotal(price, quantity) {
    var subtotal = (parseFloat(price) * parseInt(quantity)).toFixed(2);
    $('#subtotal').text('$' + subtotal);  // Actualiza el subtotal en el modal
}
 