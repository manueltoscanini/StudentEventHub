package com.example.studenteventhub;

import DAO.EventDAO;
import Models.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/events")
public class EventListServlet extends HttpServlet {

    private EventDAO eventDAO;

    @Override
    public void init() throws ServletException {
        eventDAO = new EventDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificar que hay sesión activa
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Obtener el ID del estudiante logueado
        Integer studentId = (Integer) session.getAttribute("studentId");

        if (studentId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 3. Obtener todos los eventos del estudiante
            List<Event> events = eventDAO.getEventosPorStudent(studentId);

            // 4. Pasar la lista de eventos al JSP
            request.setAttribute("events", events);

            // 5. Mostrar el dashboard
            request.getRequestDispatcher("/events.jsp").forward(request, response);

        } catch (RuntimeException e) {
            // 6. Manejo de errores
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar los eventos");
            request.getRequestDispatcher("/events.jsp").forward(request, response);
        }
    }
}