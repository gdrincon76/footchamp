package net.jaumebalmes.grincon17.futchamp.conexion;

import java.util.HashMap;

/**
 * Esta clase se encargara de guardar los enlaces que ubicaran los metodos de las clases controller
 * para poder realizar el CRUD a la base de datos.
 */

public class Enlace {
    private final String LOCALHOST = "http://192.168.1.23:";  // Direccion Ip del Pc(puede variar)
    private final String PORT = "8080";  // Puerto de conexion, depende de la configuracion del Pc
    private final String ROUTE = "/api/futchamp/";  // Ruta a la API.
    // Enlace de ubicacion de metodos CRUD de
    private final String LINK_JUGADOR = LOCALHOST + PORT + ROUTE + "jugador/";
    private final String LINK_COORDINADOR = LOCALHOST + PORT + ROUTE + "coordinador/";
    private final String LINK_EQUIPO = LOCALHOST + PORT + ROUTE + "equipo/";
    private final String LINK_JORNADA = LOCALHOST + PORT + ROUTE + "jornada/";
    private final String LINK_LIGA = LOCALHOST + PORT + ROUTE + "liga/";
    private final String LINK_PARTIDO = LOCALHOST + PORT + ROUTE + "partido/";
    // Claves para busqueda de link en el hashmap
    public final String JUGADOR = "jugador";
    public final String COORDINADOR = "coordinador";
    public final String EQUIPO = "equipo";
    public final String JORNADA = "jornada";
    public final String LIGA = "liga";
    public final String PARTIDO = "partido";

    private HashMap<String, String> enlaceUrlApi; // Almacenara los enlaces o url para accedera a la api

    // Builder
    public Enlace() {
        // Inicializa el hashMap para tener disponibli los enlaces
        enlaceUrlApi = new HashMap<>();
        enlaceUrlApi.put("jugador", LINK_JUGADOR);
        enlaceUrlApi.put("coordinador", LINK_COORDINADOR);
        enlaceUrlApi.put("equipo", LINK_EQUIPO);
        enlaceUrlApi.put("jornada", LINK_JORNADA);
        enlaceUrlApi.put("liga", LINK_LIGA);
        enlaceUrlApi.put("partido", LINK_PARTIDO);
    }


    //Method

    /**
     * Este metodo devolvera un string que sera la la direccion enlace donde se llama al metodo.
     * para realizar el CRUD a la base de datos.
     *
     * @param keyLink Sera la clave de tipo string para obtener el valor del enlace.
     * @return Devuelve una cadena de texto que sera el link para realizar llamara a la api.
     */
    public String getLink(String keyLink) {
        return enlaceUrlApi.get(keyLink);
    }

}
