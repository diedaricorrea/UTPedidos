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
