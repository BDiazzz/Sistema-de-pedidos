document.addEventListener('DOMContentLoaded', function() {
    const modalOpciones = document.getElementById('modalOpciones');
    const direccionModal = document.getElementById('direccionModal');
    const referenciaModal = document.getElementById('referenciaModal');
    const editarDireccion = document.getElementById('editarDireccion');
    const eliminarDireccion = document.getElementById('eliminarDireccion');

    modalOpciones.addEventListener('show.bs.modal', function(event) {
        const button = event.relatedTarget; // El botón que activó el modal
        const idUbicacion = button.getAttribute('data-id');
        const direccion = button.closest('.address-card').querySelector('.title').innerText;
        const referencia = button.closest('.address-card').querySelector('.description').innerText;

        // Actualiza el modal con la dirección y referencia
        direccionModal.innerText = direccion;
        referenciaModal.innerText = referencia;

        // Actualiza los enlaces de editar y eliminar con el ID de la ubicación
        editarDireccion.href = `/Cliente/Ubicacion/Editar/${idUbicacion}`;
        eliminarDireccion.href = `/Cliente/Ubicacion/Eliminar/${idUbicacion}`;
    });
});
