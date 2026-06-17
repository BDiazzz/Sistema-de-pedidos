    document.addEventListener('DOMContentLoaded', function () {
        // Leer más y leer menos
        document.querySelectorAll('.read-more').forEach(button => {
            button.addEventListener('click', function () {
                const description = this.previousElementSibling;
                description.classList.add('expanded');
                this.classList.add('d-none');
                this.nextElementSibling.classList.remove('d-none');
            });
        });

        document.querySelectorAll('.read-less').forEach(button => {
            button.addEventListener('click', function () {
                const description = this.previousElementSibling.previousElementSibling;
                description.classList.remove('expanded');
                this.classList.add('d-none');
                this.previousElementSibling.classList.remove('d-none');
            });
        });

        // Manejo de cantidades en el carrito
        function actualizarCantidad(productId, newQuantity) {
            fetch(`/Cliente/actualizarCantidad`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `idProducto=${productId}&cantidad=${newQuantity}`,
            })
                .then(response => response.text())
                .then(response => {
                    console.log('Cantidad actualizada:', response);
                    location.reload(); // Recargar para reflejar cambios
                })
                .catch(error => console.error('Error al actualizar la cantidad:', error));
        }

        function eliminarProducto(productId) {
            fetch(`/Cliente/eliminar/${productId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then(response => response.text())
                .then(() => {
                    console.log('Producto eliminado');
                    location.reload();
                })
                .catch(error => console.error('Error al eliminar el producto:', error));
        }

        // Actualizar botones de disminuir cantidad
        function updateDecreaseButton(productId, quantity) {
            const decreaseButton = document.getElementById('decrease-' + productId);
            if (quantity === 1) {
                decreaseButton.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
                    </svg>`;
            } else {
                decreaseButton.innerHTML = '-';
            }
        }

        // Eventos de botones de aumento y disminución
        document.querySelectorAll('.increase-quantity').forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.id.split('-')[1];
                const quantityInput = document.getElementById('quantityInput-' + productId);
                const newQuantity = parseInt(quantityInput.value) + 1;

                actualizarCantidad(productId, newQuantity);
            });
        });

        document.querySelectorAll('.decrease-quantity').forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.id.split('-')[1];
                const quantityInput = document.getElementById('quantityInput-' + productId);
                const currentQuantity = parseInt(quantityInput.value);

                if (currentQuantity === 1) {
                    updateDecreaseButton(productId, 1); // Muestra el icono antes de eliminar
                    eliminarProducto(productId);
                } else if (currentQuantity > 1) {
                    const newQuantity = currentQuantity - 1;
                    updateDecreaseButton(productId, newQuantity); // Actualiza botón antes de la llamada
                    actualizarCantidad(productId, newQuantity);
                }
            });
        });
    });

