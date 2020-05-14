package net.jaumebalmes.grincon17.futchamp.models;

import com.google.gson.annotations.SerializedName;

public class Jornada {

    @SerializedName("name")
    private String name;


    public Jornada() {
    }

    public Jornada(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
