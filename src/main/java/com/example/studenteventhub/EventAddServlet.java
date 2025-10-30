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
import java.sql.Time;
import java.time.LocalDate;

@WebServlet("/event/add")
public class EventAddServlet extends HttpServlet {

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

        // 3. Obtener datos del formulario
        String title = request.getParameter("title");
        String eventType = request.getParameter("eventType");
        String eventDateStr = request.getParameter("eventDate");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String description = request.getParameter("description");

        // 4. Validaciones básicas
        if (title == null || title.trim().isEmpty() ||
                eventType == null || eventType.trim().isEmpty() ||
                eventDateStr == null || eventDateStr.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/events?error=campos_vacios");
            return;
        }

        try {
            // 5. Convertir fecha
            LocalDate eventDate = LocalDate.parse(eventDateStr);

            // 6. Convertir horas (pueden ser null)
            Time startTime = (startTimeStr != null && !startTimeStr.isEmpty())
                    ? Time.valueOf(startTimeStr + ":00")
                    : null;

            Time endTime = (endTimeStr != null && !endTimeStr.isEmpty())
                    ? Time.valueOf(endTimeStr + ":00")
                    : null;

            // 7. Crear objeto Event
            Event event = new Event();
            event.setStudentId(studentId);
            event.setTitle(title.trim());
            event.setEventType(eventType);
            event.setEventDate(eventDate);
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            event.setDescription(description != null ? description.trim() : null);

            // 8. Guardar en BD
            eventDAO.crearEvent(event);

            // 9. Redirigir al dashboard con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/events?success=evento_creado");

        } catch (Exception e) {
            // 10. Manejo de errores
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/events?error=error_crear");
        }
    }
}