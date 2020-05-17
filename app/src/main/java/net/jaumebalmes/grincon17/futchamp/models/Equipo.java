package net.jaumebalmes.grincon17.futchamp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Equipo implements Serializable {

    @SerializedName("name")
    private String name;

    private String logo; // url de la imagen de logo del equipo

    private League id_league;


    // Constructores
    public Equipo() {
    }

    public Equipo(String name, String logo, League id_league) {
        this.name = name;
        this.logo = logo;
        this.id_league = id_league;
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

    public League getId_league() {
        return id_league;
    }

    public void setId_league(League id_league) {
        this.id_league = id_league;
    }
}
