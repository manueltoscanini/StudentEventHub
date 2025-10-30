<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - StudentEventHub</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>📚 StudentEventHub</h1>
        <p>Creá tu cuenta para gestionar tus eventos académicos</p>
    </div>

    <!-- Mensaje de error del servidor -->
    <% String error = (String) request.getAttribute("error");
        if (error != null) { %>
    <div class="alert error">
         <%= error %>
    </div>
    <% } %>

    <form id="registerForm" method="post" action="register">
        <!-- Campo: Nombre -->
        <div class="form-group">
            <label for="name">Nombre completo</label>
            <input
                    type="text"
                    id="name"
                    name="name"
                    placeholder="Juan Pérez"
                    value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>"
                    required>
            <div class="error-message" id="nameError">El nombre es obligatorio</div>
        </div>

        <!-- Campo: Email -->
        <div class="form-group">
            <label for="email">Email institucional</label>
            <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="juan.perez@universidad.edu"
                    value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>"
                    required>
            <div class="error-message" id="emailError">Ingresá un email válido</div>
        </div>

        <!-- Campo: Contraseña -->
        <div class="form-group">
            <label for="password">Contraseña</label>
            <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="••••"
                    required>
            <div class="password-requirements">Mínimo 4 caracteres</div>
            <div class="error-message" id="passwordError">La contraseña debe tener al menos 4 caracteres</div>
        </div>

        <!-- Botón Submit -->
        <button type="submit" class="btn">Crear cuenta</button>
    </form>

    <div class="footer-text">
        ¿Ya tenés cuenta? <a href="login.jsp">Iniciá sesión</a>
    </div>
</div>

<script src="js/register.js"></script>
</body>
</html>