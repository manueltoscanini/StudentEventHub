document.getElementById('loginForm').addEventListener('submit', function(e) {
    let isValid = true;

    // Limpiar errores previos
    clearErrors();

    // Validar email
    const email = document.getElementById('email');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email.value.trim())) {
        showError('email', 'emailError');
        isValid = false;
    }

    // Validar contraseña
    const password = document.getElementById('password');
    if (password.value.trim() === '') {
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

//limpia error al escribir

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

// se autocompleta el imail si viene de registro
window.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const registered = urlParams.get('registered');

    if (registered === 'true') {
        // Mostrar animación de éxito
        const successAlert = document.querySelector('.alert.success');
        if (successAlert) {
            successAlert.style.animation = 'slideDown 0.5s ease';
        }
    }
});