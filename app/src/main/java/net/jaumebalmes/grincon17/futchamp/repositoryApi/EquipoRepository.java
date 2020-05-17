package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.Equipo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EquipoRepository {

    // Obtiene la lista de equipos de la BBDD por medio de la API
    @GET("mostrar")
    Call<ArrayList<Equipo>> obtenerListaequipos();
}
