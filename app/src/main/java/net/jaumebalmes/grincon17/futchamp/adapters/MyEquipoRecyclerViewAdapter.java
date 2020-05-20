package net.jaumebalmes.grincon17.futchamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListEquipoInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJugadorInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import java.util.List;

/**
 * Adaptador para la vista de la lista de equipos
 * @author guillermo
 */
public class MyEquipoRecyclerViewAdapter extends RecyclerView.Adapter<MyEquipoRecyclerViewAdapter.ViewHolder> {

    private final List<Equipo> mValues;
    private final OnListEquipoInteractionListener mListener;
    private final Context mContent;

    public MyEquipoRecyclerViewAdapter(Context context, List<Equipo> items, OnListEquipoInteractionListener listener) {
        mContent = context;
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equipo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.getName());
        Glide.with(mContent)
                .load(holder.mItem.getLogo()) // Ruta de la imagen en la web
                .error(R.mipmap.ic_launcher) //Muestra imagen por defecto si no carga la imagen de red
                .centerCrop() // La imagen ocupara todo el espacion disponible
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView); // Hay que pasarle el contexto

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onEquipoClickListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final ImageView mImageView;
        Equipo mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.text_equipo_name);
            mImageView = view.findViewById(R.id.imageViewEquipoLogo);
        }

    }
}