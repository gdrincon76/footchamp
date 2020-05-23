package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class League implements Serializable {


    private long id;
    private String name;
    private String logo;

    // Constructores
    public League() {
    }

    public League(long id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    public long getId() {
        return id;
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
}
