document.addEventListener('DOMContentLoaded', () => {
    const modalEliminar = document.getElementById('modalEliminar');
    const btnConfirmarEliminar = document.getElementById('confirmarEliminar');

    modalEliminar.addEventListener('show.bs.modal', (event) => {
        const button = event.relatedTarget;
        const idEmpleado = button.getAttribute('data-id');
        btnConfirmarEliminar.href = `/empleados/eliminar/${idEmpleado}`;
    });
});
