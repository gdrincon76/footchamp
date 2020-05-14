package net.jaumebalmes.grincon17.futchamp.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Jugador {

    private Long id;
    @SerializedName("name")
    private String nombre;
    private String apellidos;
    private String dni;
    private Character genero; // Sexo del jugador 'M' o 'H'
    private LocalDate fNac;  // Fecha de nacimiento
    private String email;
    private String telefono;
    private String imagen; // url de ubicacion de la imagen
    private String ocupacion; // campo para saber si es jugador o coordinador

    // Constructor
    public Jugador(String nombre, String apellidos, String dni, Character genero, LocalDate fNac, String email, String telefono, String imagen, String ocupacion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.genero = genero;
        this.fNac = fNac;
        this.email = email;
        this.telefono = telefono;
        this.imagen = imagen;
        this.ocupacion = ocupacion;
    }

    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public LocalDate getfNac() {
        return fNac;
    }

    public void setfNac(LocalDate fNac) {
        this.fNac = fNac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}
