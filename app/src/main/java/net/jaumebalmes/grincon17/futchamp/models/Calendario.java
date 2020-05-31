package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class Calendario implements Serializable {

    private Long id;
    private String league;  // Nombre de la league que generara los partidos
    private String fecha; // Fecha de inicio de esta league y del primer partido
    private String hora; // Hora de inicio del primer partido generado en este calendario.

    //Constructores
    public Calendario() {
    }

    public Calendario(Long id, String league, String fecha, String hora) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
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



