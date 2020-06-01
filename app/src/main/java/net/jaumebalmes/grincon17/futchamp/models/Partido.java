package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Partido implements Serializable {

    private Long id;
    private String fecha; // Fecha del partido
    private String hora; // Hora de inicio
    private int jornada; // sera el numero de la jornada en donde se encuentra el partido
    private Equipo local;  // id de equipo y otros datos
    private Equipo visitante; // id de equipo y otros datos
    private Calendario calendario; // id calendario que genero los partidos

    // FALTAN HACER 1 CAMPO ==>> Marcador ==> Falta terminar

    //Constructores
    public Partido() {
    }


    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    public Equipo getLocal() {
        return local;
    }

    public void setLocal(Equipo local) {
        this.local = local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public void setVisitante(Equipo visitante) {
        this.visitante = visitante;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
}
