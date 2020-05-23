package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class Equipo implements Serializable {

    private Long id;
    private String name;
    private String logo; // url de la imagen de logo del equipo
    private League league;

    // Constructores
    public Equipo() {
    }

    public Equipo(Long id, String name, String logo, League league) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.league = league;
    }

    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
