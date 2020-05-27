package net.jaumebalmes.grincon17.futchamp.interfaces;

import net.jaumebalmes.grincon17.futchamp.models.Jugador;

public interface OnListJugadorInteractionListener {
    void onJugadorClickListener(Jugador jugador);
    void onJugadorLongClickListener(Jugador jugador);
}
