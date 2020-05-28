package net.jaumebalmes.grincon17.futchamp.interfaces;

import net.jaumebalmes.grincon17.futchamp.models.Equipo;

public interface OnListEquipoInteractionListener {
    void onEquipoClickListener(Equipo equipo);

    void onEquipoLongClickListener(Equipo equipo);
}
