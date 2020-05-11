package net.jaumebalmes.grincon17.futchamp.models;

import com.google.gson.annotations.SerializedName;

public class League {

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    public League() {
    }

    public League(String name, String logo) {
        this.name = name;
        this.logo = logo;
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
