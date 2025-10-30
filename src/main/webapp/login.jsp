<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - StudentEventHub</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>📚 StudentEventHub</h1>
        <p>Iniciá sesión para gestionar tus eventos</p>
    </div>

    <!-- Mensaje de registro exitoso -->
    <% String registered = request.getParameter("registered");
        if (registered != null && registered.equals("true")) { %>
    <div class="alert success">
        ✓ Registro exitoso. Ya podés iniciar sesión.
    </div>
    <% } %>

    <!-- Mensaje de logout exitoso -->
    <% String logout = request.getParameter("logout");
        if (logout != null && logout.equals("true")) { %>
    <div class="alert success">
        ✓ Sesión cerrada correctamente.
    </div>
    <% } %>

    <!-- Mensaje de error del servidor -->
    <% String error = (String) request.getAttribute("error");
        if (error != null) { %>
    <div class="alert error">
        ⚠️ <%= error %>
    </div>
    <% } %>

    <form id="loginForm" method="post" action="login">
        <!-- Campo: Email -->
        <div class="form-group">
            <label for="email">Email</label>
            <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="tu.email@universidad.edu"
                    value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
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
            <div class="error-message" id="passwordError">La contraseña es obligatoria</div>
        </div>

        <!-- Botón Submit -->
        <button type="submit" class="btn">Iniciar sesión</button>
    </form>

    <div class="footer-text">
        ¿No tenés cuenta? <a href="register.jsp">Registrate acá</a>
    </div>
</div>

<script src="js/login.js"></script>
</body>
</html>