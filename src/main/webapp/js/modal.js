
// ========================================
// CONTROL DEL MODAL DE EVENTOS
// ========================================

// Abrir modal
function openModal() {
    const modal = document.getElementById('eventModal');
    modal.classList.add('active');
    document.body.style.overflow = 'hidden'; // Prevenir scroll del body
}

// Cerrar modal
function closeModal() {
    const modal = document.getElementById('eventModal');
    modal.classList.remove('active');
    document.body.style.overflow = 'auto';

    // Limpiar formulario
    document.getElementById('eventForm').reset();
    clearErrors();
    
    // Resetear contadores de caracteres
    const titleCharCount = document.getElementById('titleCharCount');
    const charCount = document.getElementById('charCount');
    
    if (titleCharCount) {
        titleCharCount.textContent = '0';
        const titleCounterDiv = titleCharCount.closest('.char-counter');
        if (titleCounterDiv) titleCounterDiv.classList.remove('limit');
    }
    
    if (charCount) {
        charCount.textContent = '0';
        const descCounterDiv = charCount.closest('.char-counter');
        if (descCounterDiv) descCounterDiv.classList.remove('limit');
    }
}

// Cerrar modal al hacer click fuera de él
document.getElementById('eventModal')?.addEventListener('click', function(e) {
    if (e.target === this) {
        closeModal();
    }
});

// Cerrar modal con tecla ESC
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        closeModal();
        closeDeleteModal();
        closeEditModal();
    }
});

// ========================================
// VALIDACIÓN DEL FORMULARIO
// ========================================

document.getElementById('eventForm')?.addEventListener('submit', function(e) {
    e.preventDefault();

    if (validateForm()) {
        this.submit();
    }
});

function validateForm() {
    let isValid = true;
    clearErrors();

    // Validar título
    const title = document.getElementById('title');
    if (title.value.trim() === '') {
        showError('title', 'titleError');
        isValid = false;
    }

    // Validar tipo de evento
    const eventType = document.getElementById('eventType');
    if (eventType.value === '') {
        showError('eventType', 'eventTypeError');
        isValid = false;
    }

    // Validar fecha
    const eventDate = document.getElementById('eventDate');
    if (eventDate.value === '') {
        showError('eventDate', 'eventDateError');
        isValid = false;
    }

    // Validar que hora de fin sea mayor que hora de inicio
    const startTime = document.getElementById('startTime');
    const endTime = document.getElementById('endTime');

    if (startTime.value && endTime.value) {
        if (startTime.value >= endTime.value) {
            alert('La hora de fin debe ser posterior a la hora de inicio');
            isValid = false;
        }
    }

    return isValid;
}

function showError(inputId, errorId) {
    const input = document.getElementById(inputId);
    const error = document.getElementById(errorId);

    input.classList.add('error');
    error.classList.add('show');
}

function clearErrors() {
    document.querySelectorAll('.error-message').forEach(msg => {
        msg.classList.remove('show');
    });
    document.querySelectorAll('input, select, textarea').forEach(input => {
        input.classList.remove('error');
    });
}

// Limpiar errores al escribir
document.querySelectorAll('#eventForm input, #eventForm select, #eventForm textarea').forEach(input => {
    input.addEventListener('input', function() {
        this.classList.remove('error');
        const errorId = this.id + 'Error';
        const errorElement = document.getElementById(errorId);
        if (errorElement) {
            errorElement.classList.remove('show');
        }
    });
});

// ========================================
// ESTABLECER FECHA MÍNIMA (HOY)
// ========================================

window.addEventListener('DOMContentLoaded', function() {
    const dateInput = document.getElementById('eventDate');
    const editDateInput = document.getElementById('editEventDate');
    const today = new Date().toISOString().split('T')[0];
    
    if (dateInput) {
        dateInput.setAttribute('min', today);
    }
    
    if (editDateInput) {
        editDateInput.setAttribute('min', today);
    }
    
    // Contador de caracteres para título
    const titleInput = document.getElementById('title');
    const titleCharCount = document.getElementById('titleCharCount');
    
    if (titleInput && titleCharCount) {
        titleInput.addEventListener('input', function() {
            const currentLength = this.value.length;
            titleCharCount.textContent = currentLength;
            
            const charCounterDiv = titleCharCount.closest('.char-counter');
            if (currentLength >= 90) {
                charCounterDiv.classList.add('limit');
            } else {
                charCounterDiv.classList.remove('limit');
            }
        });
    }
    
    // Contador de caracteres para descripción
    const descriptionTextarea = document.getElementById('description');
    const charCount = document.getElementById('charCount');
    
    if (descriptionTextarea && charCount) {
        descriptionTextarea.addEventListener('input', function() {
            const currentLength = this.value.length;
            const maxLength = 500;
            
            charCount.textContent = currentLength;
            
            const charCounterDiv = charCount.closest('.char-counter');
            // Cambiar color cuando se acerca al límite
            if (currentLength >= maxLength * 0.9) {
                charCounterDiv.classList.add('limit');
            } else {
                charCounterDiv.classList.remove('limit');
            }
        });
    }
    
    // Contador de caracteres para el modal de edición - título
    const editTitleInput = document.getElementById('editTitle');
    const editTitleCharCount = document.getElementById('editTitleCharCount');
    
    if (editTitleInput && editTitleCharCount) {
        editTitleInput.addEventListener('input', function() {
            const currentLength = this.value.length;
            editTitleCharCount.textContent = currentLength;
            
            const charCounterDiv = editTitleCharCount.closest('.char-counter');
            if (currentLength >= 90) {
                charCounterDiv.classList.add('limit');
            } else {
                charCounterDiv.classList.remove('limit');
            }
        });
    }
    
    // Contador de caracteres para el modal de edición - descripción
    const editDescriptionTextarea = document.getElementById('editDescription');
    const editCharCount = document.getElementById('editCharCount');
    
    if (editDescriptionTextarea && editCharCount) {
        editDescriptionTextarea.addEventListener('input', function() {
            const currentLength = this.value.length;
            const maxLength = 500;
            
            editCharCount.textContent = currentLength;
            
            const charCounterDiv = editCharCount.closest('.char-counter');
            if (currentLength >= maxLength * 0.9) {
                charCounterDiv.classList.add('limit');
            } else {
                charCounterDiv.classList.remove('limit');
            }
        });
    }
});

// ========================================
// MODAL DE CONFIRMACIÓN DE ELIMINACIÓN
// ========================================

function openDeleteModal(button) {
    const eventId = button.getAttribute('data-event-id');
    const eventTitle = button.getAttribute('data-event-title');
    
    // Establecer valores en el modal
    document.getElementById('eventIdToDelete').value = eventId;
    document.getElementById('eventToDeleteTitle').textContent = eventTitle;
    
    // Mostrar modal
    const modal = document.getElementById('deleteModal');
    modal.classList.add('active');
    document.body.style.overflow = 'hidden';
}

function closeDeleteModal() {
    const modal = document.getElementById('deleteModal');
    modal.classList.remove('active');
    document.body.style.overflow = 'auto';
    
    // Limpiar valores
    document.getElementById('eventIdToDelete').value = '';
    document.getElementById('eventToDeleteTitle').textContent = '';
}

// Cerrar modal de eliminación al hacer click fuera de él
document.getElementById('deleteModal')?.addEventListener('click', function(e) {
    if (e.target === this) {
        closeDeleteModal();
    }
});

// ========================================
// MODAL DE EDICIÓN DE EVENTOS
// ========================================

function openEditModal(button) {
    const eventId = button.getAttribute('data-event-id');
    const eventTitle = button.getAttribute('data-event-title');
    const eventType = button.getAttribute('data-event-type');
    const eventDate = button.getAttribute('data-event-date');
    const eventStart = button.getAttribute('data-event-start');
    const eventEnd = button.getAttribute('data-event-end');
    const eventDescription = button.getAttribute('data-event-description');
    
    // Establecer valores en el formulario
    document.getElementById('editEventId').value = eventId;
    document.getElementById('editTitle').value = eventTitle;
    document.getElementById('editEventType').value = eventType;
    document.getElementById('editEventDate').value = eventDate;
    document.getElementById('editStartTime').value = eventStart || '';
    document.getElementById('editEndTime').value = eventEnd || '';
    document.getElementById('editDescription').value = eventDescription || '';
    
    // Actualizar contadores de caracteres
    document.getElementById('editTitleCharCount').textContent = eventTitle.length;
    document.getElementById('editCharCount').textContent = (eventDescription || '').length;
    
    // Mostrar modal
    const modal = document.getElementById('editModal');
    modal.classList.add('active');
    document.body.style.overflow = 'hidden';
}

function closeEditModal() {
    const modal = document.getElementById('editModal');
    modal.classList.remove('active');
    document.body.style.overflow = 'auto';
    
    // Limpiar formulario
    document.getElementById('editForm').reset();
    
    // Resetear contadores
    const editTitleCharCount = document.getElementById('editTitleCharCount');
    const editCharCount = document.getElementById('editCharCount');
    
    if (editTitleCharCount) {
        editTitleCharCount.textContent = '0';
        const titleCounterDiv = editTitleCharCount.closest('.char-counter');
        if (titleCounterDiv) titleCounterDiv.classList.remove('limit');
    }
    
    if (editCharCount) {
        editCharCount.textContent = '0';
        const descCounterDiv = editCharCount.closest('.char-counter');
        if (descCounterDiv) descCounterDiv.classList.remove('limit');
    }
}

// Cerrar modal de edición al hacer click fuera de él
document.getElementById('editModal')?.addEventListener('click', function(e) {
    if (e.target === this) {
        closeEditModal();
    }
});

// Validación del formulario de edición
document.getElementById('editForm')?.addEventListener('submit', function(e) {
    e.preventDefault();

    if (validateEditForm()) {
        this.submit();
    }
});

function validateEditForm() {
    let isValid = true;
    clearEditErrors();

    // Validar título
    const title = document.getElementById('editTitle');
    if (title.value.trim() === '') {
        showError('editTitle', 'editTitleError');
        isValid = false;
    }

    // Validar tipo de evento
    const eventType = document.getElementById('editEventType');
    if (eventType.value === '') {
        showError('editEventType', 'editEventTypeError');
        isValid = false;
    }

    // Validar fecha
    const eventDate = document.getElementById('editEventDate');
    if (eventDate.value === '') {
        showError('editEventDate', 'editEventDateError');
        isValid = false;
    }

    // Validar que hora de fin sea mayor que hora de inicio
    const startTime = document.getElementById('editStartTime');
    const endTime = document.getElementById('editEndTime');

    if (startTime.value && endTime.value) {
        if (startTime.value >= endTime.value) {
            alert('La hora de fin debe ser posterior a la hora de inicio');
            isValid = false;
        }
    }

    return isValid;
}

function clearEditErrors() {
    const editModal = document.getElementById('editModal');
    if (editModal) {
        editModal.querySelectorAll('.error-message').forEach(msg => {
            msg.classList.remove('show');
        });
        editModal.querySelectorAll('input, select, textarea').forEach(input => {
            input.classList.remove('error');
        });
    }
}

// Limpiar errores al escribir en formulario de edición
document.querySelectorAll('#editForm input, #editForm select, #editForm textarea').forEach(input => {
    input.addEventListener('input', function() {
        this.classList.remove('error');
        const errorId = this.id + 'Error';
        const errorElement = document.getElementById(errorId);
        if (errorElement) {
            errorElement.classList.remove('show');
        }
    });
});