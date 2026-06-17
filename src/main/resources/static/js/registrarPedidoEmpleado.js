let productosSeleccionados = []; // Array para almacenar los productos seleccionados

    // Función para agregar un producto a la tabla
    function agregarProducto() {
        const selectProducto = document.getElementById('selectProducto');
        const cantidadInput = document.getElementById('selectCantidad');
        
        // Obtener el ID y nombre del producto seleccionado
        const idProducto = selectProducto.value;
        const nombreProducto = selectProducto.options[selectProducto.selectedIndex].text;
        const cantidad = parseInt(cantidadInput.value);

        if (!idProducto || cantidad <= 0) {
            alert('Debe seleccionar un producto y una cantidad válida.');
            return;
        }

        // Verificar si el producto ya está en la tabla
        const productoExistente = productosSeleccionados.find(prod => prod.idProducto === idProducto);
        if (productoExistente) {
            alert('El producto ya está agregado. Edítelo si desea cambiar la cantidad.');
            return;
        }

        // Obtener datos adicionales del producto (opcionalmente desde el backend si no están en el modal)
        const precioProducto = parseFloat(selectProducto.selectedOptions[0].getAttribute('data-precio'));
        const subtotal = (precioProducto * cantidad).toFixed(2);

        // Agregar el producto al array y actualizar la tabla
        productosSeleccionados.push({ idProducto, nombreProducto, cantidad, precioProducto, subtotal });
        actualizarTabla();
        limpiarModal();
    }

    // Función para actualizar la tabla
    function actualizarTabla() {
        const tabla = document.getElementById('productosTabla');
        tabla.innerHTML = ''; // Limpiar tabla antes de llenarla

        productosSeleccionados.forEach((producto, index) => {
            const fila = document.createElement('tr');
            fila.innerHTML = `
                <th scope="row">${index + 1}</th>
                <td>${producto.nombreProducto}</td>
                <td>${producto.tipoProducto}</td>
                <td>${producto.descripcionProducto}</td> <!-- Reemplaza por la descripción si lo tienes -->
                <td>${producto.precioProducto}</td>
                <td>${producto.cantidad}</td>
                <td>${producto.subtotal}</td>
                <td class="text-center">
                    <button class="btn btn-primary" onclick="editarProducto(${index})">Editar</button>
                    <button class="btn btn-danger" onclick="eliminarProducto(${index})">Eliminar</button>
                </td>
            `;
            tabla.appendChild(fila);
        });
    }

    // Función para eliminar un producto
    function eliminarProducto(index) {
        productosSeleccionados.splice(index, 1); // Eliminar del array
        actualizarTabla(); // Actualizar la tabla
    }    

    function limpiarModal() {
        document.getElementById('selectProducto').value = '';
        document.getElementById('selectCantidad').value = 1;
        const modal = bootstrap.Modal.getInstance(document.getElementById('productosModal'));
        modal.hide();
    }

    $(document).ready(function () {
        $('.editar-btn').on('click', function () {
            var idDetalle = $(this).data('id');
            var producto = $(this).data('producto');
            var pedido = $(this).data('idpedido');
            var cantidad = $(this).data('cantidad');            
            
    
            // Asignar valores a los campos del modal
            $('#editidDetalle').val(idDetalle);
            $('#editProducto').val(producto); // Usar .val()
            $('#editPedido').val(pedido);
            $('#editcantidad').val(cantidad);
            
            
        });
    });