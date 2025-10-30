package Models;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {
    private int idEvent;
    private int studentId;
    private String title;
    private String description;
    private String eventType; // parcial, entrega, otro
    private LocalDate eventDate;
    private Time startTime;
    private Time endTime;

    public Event(int idEvent, int studentId, String title, String description,
                 String eventType, LocalDate eventDate, Time startTime, Time endTime) {
        this.idEvent = idEvent;
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event() {}

    // Getters
    public int getIdEvent() {
        return idEvent;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    // Setters
    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    // Mostrar info con formato de fecha español y hora Uruguay
    public void mostrarInfo() {
        DateTimeFormatter formatoUY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("=== Evento ===");
        System.out.println("ID: " + idEvent);
        System.out.println("Título: " + title);
        System.out.println("Descripción: " + description);
        System.out.println("Tipo: " + eventType);
        System.out.println("Fecha: " + eventDate.format(formatoUY));
        System.out.println("Inicio: " + startTime + " - Fin: " + endTime);
        System.out.println("ID Estudiante: " + studentId);
    }
}
