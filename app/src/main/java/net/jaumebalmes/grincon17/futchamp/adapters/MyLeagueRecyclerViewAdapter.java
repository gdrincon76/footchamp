package net.jaumebalmes.grincon17.futchamp.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListLeagueInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.League;

import java.util.List;


/**
 * Adaptador para la vista de la lista de ligas
 * @author guillermo
 */
public class MyLeagueRecyclerViewAdapter extends RecyclerView.Adapter<MyLeagueRecyclerViewAdapter.ViewHolder> {

    private final List<League> mValues;
    private final OnListLeagueInteractionListener mListener;
    private final Context mContent;

    public MyLeagueRecyclerViewAdapter(Context context, List<League> items, OnListLeagueInteractionListener listener) {
        mContent = context;
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_league, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.getName()); // Asigna el nombre de la liga

        // Para obtener las imagenes por medio su url
        Glide.with(mContent)
                .load(holder.mItem.getLogo()) // Ruta de la imagen en la web
                .error(R.mipmap.ic_launcher) //Muestra imagen por defecto si no carga la imagen de red
                .centerCrop() // La imagen ocupara todo el espacion disponible
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mLogoView); // Hay que pasarle el contexto

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onLeagueClickListener(holder.mItem);
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
        final ImageView mLogoView;
        League mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.textViewLeagueName);
            mLogoView = view.findViewById(R.id.imageViewLogo);
        }

    }
}
