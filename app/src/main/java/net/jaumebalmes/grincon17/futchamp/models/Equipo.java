package net.jaumebalmes.grincon17.futchamp.models;

import com.google.gson.annotations.SerializedName;

public class Equipo {

    @SerializedName("name")
    private String name;

    public Equipo() {
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
}
