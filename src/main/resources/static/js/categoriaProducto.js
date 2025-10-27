// Gestión de categorías en el formulario de productos

document.addEventListener('DOMContentLoaded', function() {
    const btnNuevaCategoria = document.getElementById('btnNuevaCategoria');
    const btnGuardarCategoria = document.getElementById('btnGuardarCategoria');
    const btnCancelarCategoria = document.getElementById('btnCancelarCategoria');
    const divNuevaCategoria = document.getElementById('divNuevaCategoria');
    const inputNuevaCategoria = document.getElementById('nuevaCategoria');
    const selectCategoria = document.getElementById('categoria');

    // Mostrar campo para nueva categoría
    if (btnNuevaCategoria) {
        btnNuevaCategoria.addEventListener('click', function() {
            divNuevaCategoria.style.display = 'block';
            inputNuevaCategoria.focus();
        });
    }

    // Guardar nueva categoría
    if (btnGuardarCategoria) {
        btnGuardarCategoria.addEventListener('click', function() {
            const nombreCategoria = inputNuevaCategoria.value.trim();
            
            if (nombreCategoria === '') {
                alert('Por favor ingresa un nombre para la categoría');
                return;
            }

            // Verificar si la categoría ya existe
            const categoriaExiste = Array.from(selectCategoria.options).some(option => 
                option.text.toLowerCase() === nombreCategoria.toLowerCase()
            );

            if (categoriaExiste) {
                alert('Esta categoría ya existe');
                return;
            }

            // Agregar la nueva categoría al select
            const newOption = document.createElement('option');
            newOption.value = nombreCategoria;
            newOption.text = nombreCategoria;
            newOption.selected = true;
            selectCategoria.appendChild(newOption);

            // Limpiar y ocultar el campo
            inputNuevaCategoria.value = '';
            divNuevaCategoria.style.display = 'none';

            // Mostrar mensaje de éxito
            mostrarMensaje('Categoría agregada correctamente. Será guardada al crear el producto.', 'success');
        });
    }

    // Cancelar nueva categoría
    if (btnCancelarCategoria) {
        btnCancelarCategoria.addEventListener('click', function() {
            inputNuevaCategoria.value = '';
            divNuevaCategoria.style.display = 'none';
        });
    }

    // Permitir agregar con Enter
    if (inputNuevaCategoria) {
        inputNuevaCategoria.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                btnGuardarCategoria.click();
            }
        });
    }
});

// Función auxiliar para mostrar mensajes
function mostrarMensaje(mensaje, tipo) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${tipo} alert-dismissible fade show`;
    alertDiv.role = 'alert';
    alertDiv.innerHTML = `
        ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    const container = document.querySelector('.header') || document.querySelector('main');
    if (container) {
        container.insertAdjacentElement('afterend', alertDiv);
        
        // Auto cerrar después de 4 segundos
        setTimeout(() => {
            alertDiv.remove();
        }, 4000);
    }
}
