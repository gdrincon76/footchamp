package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class League implements Serializable {

    private Long id;
    private String name;
    private String logo;

    // Constructores
    public League() {
    }

    public League(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
