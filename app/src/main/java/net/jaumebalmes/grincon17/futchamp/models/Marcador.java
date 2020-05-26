package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class Marcador implements Serializable {

    private Long id;
    private int gLocal; // Goles del equipo local
    private int gVisitante; // Goles del equipo visitante
    private Partido partido; // id del partido donde fue el enfrentamiento.

    // Constructores
    public Marcador() {
    }

    public Marcador(Long id, int gLocal, int gVisitante, Partido partido) {
        this.id = id;
        this.gLocal = gLocal;
        this.gVisitante = gVisitante;
        this.partido = partido;
    }

    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getgLocal() {
        return gLocal;
    }

    public void setgLocal(int gLocal) {
        this.gLocal = gLocal;
    }

    public int getgVisitante() {
        return gVisitante;
    }

    public void setgVisitante(int gVisitante) {
        this.gVisitante = gVisitante;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
