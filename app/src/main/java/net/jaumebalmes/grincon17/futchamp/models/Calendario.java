package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Calendario implements Serializable {

    private Long id;
    private String league;  // Nombre de la league que generara los partidos
    private LocalDate fecha; // Fecha de inicio de esta league y del primer partido
    private LocalTime hora; // Hora de inicio del primer partido generado en este calendario.


    //Constructores
    public Calendario() {
    }

    public Calendario(Long id, String league, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.league = league;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getter y Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Calendario{" +
                "league='" + league + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}



