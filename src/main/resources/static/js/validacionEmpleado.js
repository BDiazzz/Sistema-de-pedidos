(() => {
    'use strict';

    // Selecciona todos los formularios con la clase "needs-validation"
    const forms = document.querySelectorAll('.needs-validation');

    Array.from(forms).forEach(form => {
        const fechaInput = form.querySelector('input[type="date"]');

        // Valida el campo de fecha en tiempo real
        fechaInput.addEventListener('input', () => {
            const fechaActual = new Date();
            const fechaSeleccionada = new Date(fechaInput.value);

            // Ajusta ambas fechas al inicio del día
            fechaActual.setHours(0, 0, 0, 0);
            fechaSeleccionada.setHours(0, 0, 0, 0);

            // Calcula la diferencia en años
            const diferenciaAnios = fechaActual.getFullYear() - fechaSeleccionada.getFullYear();
            const mesActual = fechaActual.getMonth();
            const diaActual = fechaActual.getDate();
            const mesNacimiento = fechaSeleccionada.getMonth();
            const diaNacimiento = fechaSeleccionada.getDate();

            // Revisa si es futura o si es menor de 18 años
            if (fechaSeleccionada > fechaActual) {
                fechaInput.setCustomValidity('La fecha no puede ser en el futuro.');
                fechaInput.classList.add('is-invalid');
                fechaInput.classList.remove('is-valid');
            } else if (
                diferenciaAnios < 18 || 
                (diferenciaAnios === 18 && (mesNacimiento > mesActual || (mesNacimiento === mesActual && diaNacimiento > diaActual)))
            ) {
                fechaInput.setCustomValidity('Debe tener al menos 18 años de edad.');
                fechaInput.classList.add('is-invalid');
                fechaInput.classList.remove('is-valid');
            } else {
                fechaInput.setCustomValidity('');
                fechaInput.classList.remove('is-invalid');
                fechaInput.classList.add('is-valid');
            }
        });

        // Valida el formulario completo al enviarlo
        form.addEventListener('submit', event => {
            // Verifica la validez general del formulario
            const isValid = form.checkValidity();

            // Si hay errores, cancela el envío y muestra validaciones
            if (!isValid) {
                event.preventDefault();
                event.stopPropagation();
            }

            // Añade la clase para mostrar mensajes de validación
            form.classList.add('was-validated');
        }, false);
    });
})();
