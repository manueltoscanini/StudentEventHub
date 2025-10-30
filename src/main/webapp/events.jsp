<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Models.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    // Verificar sesión
    if (session == null || session.getAttribute("student") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String studentName = (String) session.getAttribute("studentName");
    List<Event> events = (List<Event>) request.getAttribute("events");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Eventos - StudentEventHub</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/modal.css">
</head>
<body>
<div class="dashboard-container">
    <!-- SIDEBAR (Barra lateral) -->
    <aside class="sidebar">
        <div class="sidebar-header">
            <h1>📚 StudentEventHub</h1>
            <div class="user-info">
                <span class="icon">👤</span>
                <span><%= studentName %></span>
            </div>
        </div>

        <nav>
            <ul class="sidebar-menu">
                <li>
                    <a href="events" class="active">
                        <span class="icon">📅</span>
                        <span>Mis Eventos</span>
                    </a>
                </li>
                <li>
                    <a href="profile.jsp">
                        <span class="icon">⚙️</span>
                        <span>Editar Perfil</span>
                    </a>
                </li>
                <li>
                    <a href="logout" class="logout">
                        <span class="icon">🚪</span>
                        <span>Cerrar Sesión</span>
                    </a>
                </li>
            </ul>
        </nav>
    </aside>

    <!-- CONTENIDO PRINCIPAL -->
    <main class="main-content">
        <!-- Mensajes de éxito/error -->
        <% 
            String success = request.getParameter("success");
            String error = request.getParameter("error");
            if (success != null) {
        %>
        <div class="alert alert-success">
            <% if ("evento_creado".equals(success)) { %>
                ✅ ¡Evento creado exitosamente!
            <% } else if ("evento_eliminado".equals(success)) { %>
                ✅ ¡Evento eliminado exitosamente!
            <% } else if ("evento_actualizado".equals(success)) { %>
                ✅ ¡Evento actualizado exitosamente!
            <% } %>
        </div>
        <% 
            } else if (error != null) {
        %>
        <div class="alert alert-error">
            <% if ("campos_vacios".equals(error)) { %>
                ❌ Por favor completa todos los campos obligatorios
            <% } else if ("error_crear".equals(error)) { %>
                ❌ Ocurrió un error al crear el evento. Intenta nuevamente.
            <% } else if ("error_eliminar".equals(error)) { %>
                ❌ Ocurrió un error al eliminar el evento. Intenta nuevamente.
            <% } else if ("error_actualizar".equals(error)) { %>
                ❌ Ocurrió un error al actualizar el evento. Intenta nuevamente.
            <% } else if ("id_invalido".equals(error)) { %>
                ❌ ID de evento inválido.
            <% } %>
        </div>
        <% } %>
        
        <!-- HEADER CON TÍTULO Y BOTÓN -->
        <div class="content-header">
            <h2>Todos los eventos</h2>
            <button type="button" class="btn-create" onclick="openModal()">
                <span class="icon">+</span>
                <span>Crear Evento</span>
            </button>
        </div>

        <!-- LISTA DE EVENTOS -->
        <% 
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (events != null && !events.isEmpty()) { 
        %>
        <div class="events-container">
            <% for (Event event : events) { %>
            <div class="event-card">
                <div class="event-card-header">
                    <div>
                        <h3><%= event.getTitle() %></h3>
                        <span class="event-type <%= event.getEventType() != null ? event.getEventType().toLowerCase() : "otro" %>">
                                        <%= event.getEventType() != null ? event.getEventType() : "Otro" %>
                                    </span>
                    </div>
                </div>

                <div class="event-card-body">
                    <div class="event-info">
                        <span class="icon">📅</span>
                        <span><%= event.getEventDate().format(formatoFecha) %></span>
                    </div>

                    <% if (event.getStartTime() != null) { %>
                    <div class="event-info">
                        <span class="icon">🕐</span>
                        <span><%= event.getStartTime() %><% if (event.getEndTime() != null) { %> - <%= event.getEndTime() %><% } %></span>
                    </div>
                    <% } %>

                    <% if (event.getDescription() != null && !event.getDescription().isEmpty()) { %>
                    <p class="event-description"><%= event.getDescription() %></p>
                    <% } %>
                </div>

                <div class="event-card-footer">
                    <button type="button" 
                            class="btn-edit"
                            data-event-id="<%= event.getIdEvent() %>"
                            data-event-title="<%= event.getTitle() %>"
                            data-event-type="<%= event.getEventType() %>"
                            data-event-date="<%= event.getEventDate() %>"
                            data-event-start="<%= event.getStartTime() != null ? event.getStartTime().toString().substring(0, 5) : "" %>"
                            data-event-end="<%= event.getEndTime() != null ? event.getEndTime().toString().substring(0, 5) : "" %>"
                            data-event-description="<%= event.getDescription() != null ? event.getDescription() : "" %>"
                            onclick="openEditModal(this)">
                        ✏️ Editar
                    </button>
                    <button type="button" 
                            class="btn-delete" 
                            data-event-id="<%= event.getIdEvent() %>" 
                            data-event-title="<%= event.getTitle() %>" 
                            onclick="openDeleteModal(this)">
                        🗑️ Eliminar
                    </button>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <!-- ESTADO VACÍO -->
        <div class="empty-state">
            <div class="icon">📭</div>
            <h3>No tenés eventos registrados</h3>
            <p>Creá tu primer evento para empezar a organizar tus actividades académicas</p>
            <button type="button" class="btn-create" onclick="openModal()">
                <span class="icon">+</span>
                <span>Crear mi primer evento</span>
            </button>
        </div>
        <% } %>
    </main>
</div>

<!-- MODAL DE CONFIRMACIÓN DE ELIMINACIÓN -->
<div id="deleteModal" class="modal-overlay">
    <div class="modal modal-small">
        <div class="modal-header">
            <h2>⚠️ Confirmar eliminación</h2>
            <button type="button" class="modal-close" onclick="closeDeleteModal()">×</button>
        </div>
        <div class="modal-body">
            <p class="delete-message">¿Estás seguro de que quieres eliminar el evento <strong id="eventToDeleteTitle"></strong>?</p>
            <p class="delete-warning">Esta acción no se puede deshacer.</p>
            
            <form id="deleteForm" action="event/delete" method="post">
                <input type="hidden" id="eventIdToDelete" name="eventId" value="">
                
                <div class="modal-footer">
                    <button type="button" class="btn-cancel" onclick="closeDeleteModal()">No, cancelar</button>
                    <button type="submit" class="btn-delete-confirm">Sí, eliminar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- MODAL PARA CREAR EVENTO -->
<div id="eventModal" class="modal-overlay">
    <div class="modal">
        <div class="modal-header">
            <h2>Crear nuevo evento</h2>
            <button type="button" class="modal-close" onclick="closeModal()">×</button>
        </div>
        <div class="modal-body">
            <!-- Mensajes de error o éxito (opcional, para feedback dinámico) -->
            <div id="formAlert" class="modal-alert"></div>

            <form id="eventForm" action="event/add" method="post">
                <div class="form-group">
                    <label for="title">Título del evento <span class="required">*</span></label>
                    <input type="text" id="title" name="title" maxlength="100" required>
                    <div class="char-counter">
                        <span id="titleCharCount">0</span> / 100 caracteres
                    </div>
                    <div id="titleError" class="error-message">El título es obligatorio.</div>
                </div>

                <div class="form-group">
                    <label for="eventType">Tipo de evento <span class="required">*</span></label>
                    <select id="eventType" name="eventType" required>
                        <option value="">Selecciona un tipo</option>
                        <option value="Académico">Académico</option>
                        <option value="Deportivo">Deportivo</option>
                        <option value="Cultural">Cultural</option>
                        <option value="Social">Social</option>
                        <option value="Parcial">Parcial</option>
                        <option value="Entrega">Entrega</option>
                        <option value="Exposicion">Exposicion</option>
                        <option value="Otro">Otro</option>
                    </select>
                    <div id="eventTypeError" class="error-message">Selecciona un tipo de evento.</div>
                </div>

                <div class="form-group">
                    <label for="eventDate">Fecha <span class="required">*</span></label>
                    <input type="date" id="eventDate" name="eventDate" required>
                    <div id="eventDateError" class="error-message">La fecha es obligatoria.</div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="startTime">Hora de inicio</label>
                        <input type="time" id="startTime" name="startTime">
                    </div>
                    <div class="form-group">
                        <label for="endTime">Hora de fin</label>
                        <input type="time" id="endTime" name="endTime">
                    </div>
                </div>

                <div class="form-group">
                    <label for="description">Descripción <span class="form-help">(máx. 500 caracteres)</span></label>
                    <textarea id="description" name="description" maxlength="500" placeholder="Detalles del evento..." rows="4"></textarea>
                    <div class="char-counter">
                        <span id="charCount">0</span> / 500 caracteres
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn-cancel" onclick="closeModal()">Cancelar</button>
                    <button type="submit" class="btn-submit">Crear evento</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- MODAL PARA EDITAR EVENTO -->
<div id="editModal" class="modal-overlay">
    <div class="modal">
        <div class="modal-header">
            <h2>Editar evento</h2>
            <button type="button" class="modal-close" onclick="closeEditModal()">×</button>
        </div>
        <div class="modal-body">
            <form id="editForm" action="event/edit" method="post">
                <input type="hidden" id="editEventId" name="eventId" value="">
                
                <div class="form-group">
                    <label for="editTitle">Título del evento <span class="required">*</span></label>
                    <input type="text" id="editTitle" name="title" maxlength="100" required>
                    <div class="char-counter">
                        <span id="editTitleCharCount">0</span> / 100 caracteres
                    </div>
                    <div id="editTitleError" class="error-message">El título es obligatorio.</div>
                </div>

                <div class="form-group">
                    <label for="editEventType">Tipo de evento <span class="required">*</span></label>
                    <select id="editEventType" name="eventType" required>
                        <option value="">Selecciona un tipo</option>
                        <option value="Académico">Académico</option>
                        <option value="Deportivo">Deportivo</option>
                        <option value="Cultural">Cultural</option>
                        <option value="Social">Social</option>
                        <option value="Parcial">Parcial</option>
                        <option value="Entrega">Entrega</option>
                        <option value="Exposicion">Exposicion</option>
                        <option value="Otro">Otro</option>
                    </select>
                    <div id="editEventTypeError" class="error-message">Selecciona un tipo de evento.</div>
                </div>

                <div class="form-group">
                    <label for="editEventDate">Fecha <span class="required">*</span></label>
                    <input type="date" id="editEventDate" name="eventDate" required>
                    <div id="editEventDateError" class="error-message">La fecha es obligatoria.</div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="editStartTime">Hora de inicio</label>
                        <input type="time" id="editStartTime" name="startTime">
                    </div>
                    <div class="form-group">
                        <label for="editEndTime">Hora de fin</label>
                        <input type="time" id="editEndTime" name="endTime">
                    </div>
                </div>

                <div class="form-group">
                    <label for="editDescription">Descripción <span class="form-help">(máx. 500 caracteres)</span></label>
                    <textarea id="editDescription" name="description" maxlength="500" placeholder="Detalles del evento..." rows="4"></textarea>
                    <div class="char-counter">
                        <span id="editCharCount">0</span> / 500 caracteres
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn-cancel" onclick="closeEditModal()">Cancelar</button>
                    <button type="submit" class="btn-submit">Guardar cambios</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- JavaScript para el modal -->
<script src="js/modal.js"></script>
</body>
</html>
