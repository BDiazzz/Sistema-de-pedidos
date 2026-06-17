document
    .getElementById("cancelarProducto")
    .addEventListener("click", function () {
        var form = document.getElementById("formProducto");
        form.reset();
        form.classList.remove("was-validated"); // Eliminar la clase de validación
    });

$(document).ready(function () {
    $('.editar-btn').on('click', function () {
        var idProducto = $(this).data('id');
        var nombreProducto = $(this).data('nombreproducto');
        var descripcionProducto = $(this).data('descripcionproducto');
        var tipoProducto = $(this).data('tipoproducto');
        var precioProducto = $(this).data('precioproducto');
        var existenciaProducto = $(this).data('existenciaproducto');
        var nombreImgen = $(this).data('nombreimg');
        var enlaceimagen = $(this).data('enlaceimgen');
        var idImagen = $(this).data('idimagen');

        // Asignar valores a los campos del modal
        $('#idProducto').val(idProducto);
        $('#editNombreProducto').val(nombreProducto); // Usar .val()
        $('#editDescripcionProducto').val(descripcionProducto);
        $('#editPrecio').val(precioProducto);
        $('#editTipoProducto').val(tipoProducto);
        $('#editExistencia').prop('checked', existenciaProducto);
        $('#editNombreImgen').val(nombreImgen);
        $('#editEnlaceimagen').val(enlaceimagen);
        $('#editIdImagen').val(idImagen);
        $('#editVerImagen').attr('src', enlaceimagen);
    });
});

$(document).ready(function () {
    $('.ver-btn').on('click', function () {
        // Obtener los datos del producto desde los atributos personalizados
        var idProducto = $(this).data('id');
        var nombreProducto = $(this).data('nombreproducto');
        var descripcionProducto = $(this).data('descripcionproducto');
        var tipoProducto = $(this).data('tipoproducto');
        var precioProducto = $(this).data('precioproducto');
        var existenciaProducto = $(this).data('existenciaproducto');
        var imageProducto = $(this).data('verimagen');

        // Asignar los valores a los campos del modal correctamente
        $('#verNombreProducto').text(nombreProducto);
        $('#verDescripcionProducto').text(descripcionProducto);
        $('#verTipoProducto').text(tipoProducto);
        $('#verPrecio').text('$' + precioProducto);
        $('#verExistencia').text(existenciaProducto);

        // Si necesitas trabajar con inputs ocultos, también puedes asignar valores a ellos
        $('#idProducto').val(idProducto);
        $('#editExistencia').val(existenciaProducto);
        // Asignar el enlace de la imagen al atributo src del elemento img
        $('#verImagen').attr('src', imageProducto);
    });
});

