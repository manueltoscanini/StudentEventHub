package com.example.studenteventhub;

import DAO.StudentDAO;
import Models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar el formulario de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener datos del formulario
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Validaciones básicas
        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Email y contraseña son obligatorios");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            // 3. Autenticar con el DAO
            Student student = studentDAO.autenticarStudent(email.trim(), password);

            if (student != null) {
                // 4. Login exitoso: crear sesión
                HttpSession session = request.getSession();
                session.setAttribute("student", student);
                session.setAttribute("studentId", student.getId());
                session.setAttribute("studentName", student.getNombre());
                session.setMaxInactiveInterval(30 * 60); // 30 minutos

                // 5. Redirigir al dashboard de eventos
                response.sendRedirect("events");

            } else {
                // 6. Credenciales incorrectas
                request.setAttribute("error", "Email o contraseña incorrectos");
                request.setAttribute("email", email); // Mantener el email
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (RuntimeException e) {
            // 7. Error en la BD
            e.printStackTrace();
            request.setAttribute("error", "Error al iniciar sesión. Intentá de nuevo.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}