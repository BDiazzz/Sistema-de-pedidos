document
    .getElementById("cancelarMenu")
    .addEventListener("click", function () {
        var form = document.getElementById("formMenu");
        form.reset();
        form.classList.remove("was-validated"); // Eliminar la clase de validación
    });
$(document).ready(function () {
    $('.editar-btn').on('click', function () {
        var idMenu = $(this).data('id');
        var fechaCreación = $(this).data('fecha');
        var producto = $(this).data('producto');
       

        // Asignar valores a los campos del modal
        $('#idMenu').val(idMenu);
        $('#editFecha').val(fechaCreación); // Usar .val()
        $('#editProduct').val(producto);
        
    });
});
