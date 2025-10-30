package com.example.studenteventhub;

import DAO.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/event/delete")
public class EventDeleteServlet extends HttpServlet {

    private EventDAO eventDAO;

    @Override
    public void init() throws ServletException {
        eventDAO = new EventDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. Obtener ID del estudiante
        Integer studentId = (Integer) session.getAttribute("studentId");

        // 3. Obtener ID del evento a eliminar
        String eventIdStr = request.getParameter("eventId");

        if (eventIdStr == null || eventIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/events?error=id_invalido");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);

            // 4. Eliminar evento (solo si pertenece al estudiante)
            eventDAO.eliminarEvent(eventId, studentId);

            // 5. Redirigir con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/events?success=evento_eliminado");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/events?error=id_invalido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/events?error=error_eliminar");
        }
    }
}
