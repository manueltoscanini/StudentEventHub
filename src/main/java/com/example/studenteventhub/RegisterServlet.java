package com.example.studenteventhub;

import DAO.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar el formulario de registro
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener datos del formulario
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Validaciones básicas
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 3. Validar formato de email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("error", "Email inválido");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 4. Validar longitud de contraseña
        if (password.length() < 4) {
            request.setAttribute("error", "La contraseña debe tener al menos 4 caracteres");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            // 5. Crear el estudiante (ya encripta la contraseña automáticamente)
            studentDAO.crearStudent(name.trim(), email.trim(), password);

            // 6. Redirigir al login con mensaje de éxito
            response.sendRedirect("login.jsp?registered=true");

        } catch (RuntimeException e) {
            // 7. Manejar errores (ej: email duplicado)
            String errorMsg = "Error al registrar. El email podría estar en uso.";
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}