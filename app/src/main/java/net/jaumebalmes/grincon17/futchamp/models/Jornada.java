package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class Jornada implements Serializable {

    private Long id;
    private String name;

    // Contructor
    public Jornada() {
    }

    //Setter y Getter
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
