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

@WebServlet("/event/edit")
public class EventEditServlet extends HttpServlet {

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
        String eventIdStr = request.getParameter("eventId");
        String title = request.getParameter("title");
        String eventType = request.getParameter("eventType");
        String eventDateStr = request.getParameter("eventDate");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String description = request.getParameter("description");

        // 4. Validaciones básicas
        if (eventIdStr == null || eventIdStr.trim().isEmpty() ||
                title == null || title.trim().isEmpty() ||
                eventType == null || eventType.trim().isEmpty() ||
                eventDateStr == null || eventDateStr.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/events?error=campos_vacios");
            return;
        }

        try {
            // 5. Convertir ID
            int eventId = Integer.parseInt(eventIdStr);

            // 6. Convertir fecha
            LocalDate eventDate = LocalDate.parse(eventDateStr);

            // 7. Convertir horas (pueden ser null)
            Time startTime = (startTimeStr != null && !startTimeStr.isEmpty())
                    ? Time.valueOf(startTimeStr + ":00")
                    : null;

            Time endTime = (endTimeStr != null && !endTimeStr.isEmpty())
                    ? Time.valueOf(endTimeStr + ":00")
                    : null;

            // 8. Crear objeto Event
            Event event = new Event();
            event.setIdEvent(eventId);
            event.setStudentId(studentId);
            event.setTitle(title.trim());
            event.setEventType(eventType);
            event.setEventDate(eventDate);
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            event.setDescription(description != null ? description.trim() : null);

            // 9. Actualizar en BD
            eventDAO.actualizarEvent(event);

            // 10. Redirigir con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/events?success=evento_actualizado");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/events?error=id_invalido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/events?error=error_actualizar");
        }
    }
}
