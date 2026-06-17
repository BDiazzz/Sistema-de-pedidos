(() => {
    'use strict'

    // Selecciona todos los formularios que tienen la clase "needs-validation"
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
        const fechaInput = form.querySelector('input[type="date"]');

        // Listener para validar en tiempo real
        fechaInput.addEventListener('input', () => {
            const fechaActual = new Date();
            const fechaSeleccionada = new Date(fechaInput.value);

            // Compara las fechas
            if (fechaInput.value && fechaSeleccionada <= fechaActual) {
                fechaInput.setCustomValidity('La fecha debe ser mayor a la fecha actual');
                fechaInput.classList.add('is-invalid'); // Añade clase de error
            } else {
                fechaInput.setCustomValidity('');
                fechaInput.classList.remove('is-invalid'); // Remueve clase de error
                fechaInput.classList.add('is-valid'); // Añade clase de éxito
            }
        });

        // Añade un listener para el evento "submit"
        form.addEventListener('submit', event => {
            let isValid = form.checkValidity();

            if (!isValid) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        }, false);
    });
})();