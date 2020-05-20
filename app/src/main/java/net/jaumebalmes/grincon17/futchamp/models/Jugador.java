package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class Jugador implements Serializable {

    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String imagen; // url de ubicacion de la imagen
    private String posicion;
    private String dorsal; // Numero de camiseta del jugador
    private Equipo equipo;

    // Constructor
    public Jugador(Long id, String nombre, String apellidos, String dni, String email, String imagen, String posicion, String dorsal, Equipo equipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.imagen = imagen;
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.equipo = equipo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", posicion='" + posicion + '\'' +
                ", dorsal='" + dorsal + '\'' +
                ", id_equipo=" + equipo +
                '}';
    }
}
