package net.jaumebalmes.grincon17.futchamp.models;

import java.io.Serializable;

public class League implements Serializable {

<<<<<<< HEAD
    private long id;
=======
    private Long id;
>>>>>>> toEstrada
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

<<<<<<< HEAD
    public long getId() {
        return id;
    }

=======
    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

>>>>>>> toEstrada
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
