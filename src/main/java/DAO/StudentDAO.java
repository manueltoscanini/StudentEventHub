package DAO;

import ConectionDB.ConnectionDB;
import Models.Student;
import Models.Hashed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    // Crear nuevo estudiante con contraseña encriptada
    public void crearStudent(String name, String email, String password) {
        String sql = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String passwordHashed = Hashed.encriptarContra(password);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, passwordHashed);

            int filas = ps.executeUpdate();
            System.out.println("✅ Estudiante creado. Filas insertadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear estudiante", e);
        }
    }

    // Autenticar estudiante
    public Student autenticarStudent(String email, String password) {
        String sql = "SELECT * FROM students WHERE email = ?";
        try (Connection conn = ConnectionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String passwordDB = rs.getString("password");

                    // Verificar contraseña usando Hashed
                    if (Hashed.verificarContra(password, passwordDB)) {
                        return new Student(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al autenticar estudiante", e);
        }
        return null;
    }
}
