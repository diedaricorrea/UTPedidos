const modalDetalle = document.getElementById('modalFecha');

modalDetalle.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const nombre = button.getAttribute('data-nombre');
    const precio = button.getAttribute('data-precio');
    const id = button.getAttribute('data-id');

    const modalNombre = modalDetalle.querySelector('#modalNombre');
    const modalPrecio = modalDetalle.querySelector('#modalPrecio');
    const modalId = modalDetalle.querySelector('#modalId');
    modalId.value = id;

    modalNombre.textContent = nombre;
    modalPrecio.textContent = 'Precio: S/. ' + precio;
    modalId.textContent = id;

});


function abrirModal(button) {
    document.getElementById('modalDetalle').style.display = 'block';
    document.getElementById('modalNombre').innerText = button.dataset.nombre;
    document.getElementById('modalPrecio').innerText = 'S/ ' + button.dataset.precio;
    document.getElementById('nombreProducto').value = button.dataset.nombre;
    document.getElementById('idProducto').value = button.dataset.id
}

function cerrarModal() {
    document.getElementById('modalDetalle').style.display = 'none';
}