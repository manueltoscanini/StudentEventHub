--la contraseña de estos usuarios no funciona pq las hashee
CREATE DATABASE IF NOT EXISTS student_eventhub;
USE student_eventhub;

CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  password VARCHAR(200) NOT NULL
);

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  title VARCHAR(150) NOT NULL,
  description TEXT,
  event_type VARCHAR(50),
  event_date DATE NOT NULL,
  start_time TIME,
  end_time TIME,
  FOREIGN KEY (student_id) REFERENCES students(id) //  si se elimina un estudiante, se eliminan sus eventos automáticamente.
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO students (name, email, password) VALUES
('Ana Pereira', 'ana.pereira@example.com', '1234'),
('Lucas Gómez', 'lucas.gomez@example.com', 'abcd'),
('María Torres', 'maria.torres@example.com', 'pass2024');

INSERT INTO events (student_id, title, description, event_type, event_date, start_time, end_time)
VALUES
(1, 'Parcial de Matemática', 'Capítulos 1 al 4', 'Parcial', '2025-11-10', '10:00:00', '12:00:00'),
(1, 'Entrega de Proyecto de Programación', 'Versión final del TP grupal', 'Entrega', '2025-11-18', '18:00:00', '20:00:00'),
(2, 'Exposición de Física', 'Tema: Energía y Movimiento', 'Exposición', '2025-11-25', '09:00:00', '09:30:00');
