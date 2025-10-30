// Student.java
package Models;

public class Student {
    private int id;
    private String nombre;
    private String email;
    private String contrasenia;

    // Constructor completo sin id (para registro)
    public Student(String nombre, String email, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    // Constructor con id (por si se recupera de BD)
    public Student(int id, String nombre, String email, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    // Constructor vacío
    public Student() {
        this.id = 0;
        this.nombre = null;
        this.email = null;
        this.contrasenia = null;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public String getContrasenia() {
        return this.contrasenia;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    // Método auxiliar para mostrar info
    public void mostrarInfo() {
        System.out.println("-------------------------");
        System.out.println("ID: " + this.id);
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Email: " + this.email);
    }
}
