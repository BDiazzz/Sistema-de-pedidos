document.querySelectorAll('.editar-btn').forEach(button => {
    button.addEventListener('click', function() {
        const idAsignacion = parseInt(this.getAttribute('data-id-asignacion'), 10);
        document.getElementById('idAsignacionInput').value = idAsignacion;

        console.log('ID Asignación:', idAsignacion);  // Esto imprimirá el ID en la consola
        console.log('Valor del campo oculto:', document.getElementById('idAsignacionInput').value);  // Esto imprime el valor asignado al input
    });
});
