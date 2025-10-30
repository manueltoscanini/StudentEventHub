// ========================================
// VALIDACIÓN DEL FORMULARIO DE REGISTRO
// ========================================

document.getElementById('registerForm').addEventListener('submit', function(e) {
    let isValid = true;

    // Limpiar errores previos
    clearErrors();

    // Validar nombre
    const name = document.getElementById('name');
    if (name.value.trim() === '') {
        showError('name', 'nameError');
        isValid = false;
    }

    // Validar email
    const email = document.getElementById('email');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email.value.trim())) {
        showError('email', 'emailError');
        isValid = false;
    }

    // Validar contraseña
    const password = document.getElementById('password');
    if (password.value.length < 4) {
        showError('password', 'passwordError');
        isValid = false;
    }

    // Prevenir submit si hay errores
    if (!isValid) {
        e.preventDefault();
    }
});

// ========================================
// FUNCIONES AUXILIARES
// ========================================

function showError(inputId, errorId) {
    const input = document.getElementById(inputId);
    const error = document.getElementById(errorId);

    input.classList.add('error-input');
    error.classList.add('show');

    // Focus en el primer campo con error
    if (!document.querySelector('.error-input:focus')) {
        input.focus();
    }
}

function clearErrors() {
    document.querySelectorAll('.error-message').forEach(msg => {
        msg.classList.remove('show');
    });
    document.querySelectorAll('input').forEach(input => {
        input.classList.remove('error-input');
    });
}

// ========================================
// LIMPIAR ERRORES AL ESCRIBIR
// ========================================

document.querySelectorAll('input').forEach(input => {
    input.addEventListener('input', function() {
        this.classList.remove('error-input');
        const errorId = this.id + 'Error';
        const errorElement = document.getElementById(errorId);
        if (errorElement) {
            errorElement.classList.remove('show');
        }
    });
});

// ========================================
// VALIDACIÓN EN TIEMPO REAL (OPCIONAL)
// ========================================

// Email: mostrar tick verde si es válido
document.getElementById('email').addEventListener('blur', function() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailRegex.test(this.value.trim())) {
        this.style.borderColor = '#27AE60';
    }
});

// Contraseña: mostrar fuerza
document.getElementById('password').addEventListener('input', function() {
    const requirements = document.querySelector('.password-requirements');
    const length = this.value.length;

    if (length === 0) {
        requirements.textContent = 'Mínimo 4 caracteres';
        requirements.style.color = '#7F8C8D';
    } else if (length < 4) {
        requirements.textContent = `Faltan ${4 - length} caracteres`;
        requirements.style.color = '#E74C3C';
    } else if (length >= 4 && length < 8) {
        requirements.textContent = '✓ Contraseña válida (débil)';
        requirements.style.color = '#F39C12';
    } else {
        requirements.textContent = '✓ Contraseña fuerte';
        requirements.style.color = '#27AE60';
    }
});