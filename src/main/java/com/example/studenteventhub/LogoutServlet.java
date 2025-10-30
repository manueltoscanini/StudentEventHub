package com.example.studenteventhub;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener la sesión actual (si existe)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. Invalidar la sesión (elimina todos los atributos)
            session.invalidate();
            System.out.println("Sesión cerrada correctamente");
        }

        // 3. Redirigir al login con mensaje de logout
        response.sendRedirect("login.jsp?logout=true");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // para llamar post
        doGet(request, response);
    }
}