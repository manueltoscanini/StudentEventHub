package DAO;

import ConectionDB.ConnectionDB;
import Models.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // Crear un nuevo evento
    public void crearEvent(Event event) {
        String sql = "INSERT INTO events (student_id, title, description, event_type, event_date, start_time, end_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, event.getStudentId());
            ps.setString(2, event.getTitle());
            ps.setString(3, event.getDescription());
            ps.setString(4, event.getEventType());
            ps.setDate(5, Date.valueOf(event.getEventDate())); // LocalDate a Date
            ps.setTime(6, event.getStartTime());
            ps.setTime(7, event.getEndTime());

            int filas = ps.executeUpdate();
            System.out.println(" Evento creado. Filas insertadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear evento", e);
        }
    }

    // Actualizar un evento existente
    public void actualizarEvent(Event event) {
        String sql = "UPDATE events SET title = ?, description = ?, event_type = ?, event_date = ?, start_time = ?, end_time = ? " +
                "WHERE id = ? AND student_id = ?";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getEventType());
            ps.setDate(4, Date.valueOf(event.getEventDate()));
            ps.setTime(5, event.getStartTime());
            ps.setTime(6, event.getEndTime());
            ps.setInt(7, event.getIdEvent());
            ps.setInt(8, event.getStudentId());

            int filas = ps.executeUpdate();
            System.out.println(" Evento actualizado. Filas modificadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar evento", e);
        }
    }

    // Eliminar un evento
    public void eliminarEvent(int idEvent, int studentId) {
        String sql = "DELETE FROM events WHERE id = ? AND student_id = ?";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvent);
            ps.setInt(2, studentId);

            int filas = ps.executeUpdate();
            System.out.println(" Evento eliminado. Filas afectadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar evento", e);
        }
    }

    // Obtener todos los eventos de un estudiante
    public List<Event> getEventosPorStudent(int studentId) {
        List<Event> lista = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE student_id = ? ORDER BY event_date, start_time";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("event_type"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time"),
                            rs.getTime("end_time")
                    );
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener eventos", e);
        }

        return lista;
    }

    // Obtener eventos de un estudiante por fecha específica
    public List<Event> getEventosPorFecha(int studentId, java.time.LocalDate fecha) {
        List<Event> lista = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE student_id = ? AND event_date = ? ORDER BY start_time";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("event_type"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time"),
                            rs.getTime("end_time")
                    );
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener eventos por fecha", e);
        }

        return lista;
    }

    // Obtener eventos de un estudiante por tipo de evento
    public List<Event> getEventosPorTipo(int studentId, String tipoEvento) {
        List<Event> lista = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE student_id = ? AND event_type = ? ORDER BY event_date, start_time";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setString(2, tipoEvento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("event_type"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time"),
                            rs.getTime("end_time")
                    );
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener eventos por tipo", e);
        }

        return lista;
    }

    // Obtener un evento específico por ID
    public Event getEventoPorId(int idEvent, int studentId) {
        String sql = "SELECT * FROM events WHERE id = ? AND student_id = ?";

        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvent);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Event(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("event_type"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time"),
                            rs.getTime("end_time")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener evento por ID", e);
        }

        return null;
    }


}
