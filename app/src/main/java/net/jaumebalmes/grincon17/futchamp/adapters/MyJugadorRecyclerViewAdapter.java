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
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJugadorInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;

import java.util.List;

/**
 * Adaptador para la vista de la lista de jugadores
 * @author guillermo
 */
public class MyJugadorRecyclerViewAdapter extends RecyclerView.Adapter<MyJugadorRecyclerViewAdapter.ViewHolder> {

    private final List<Jugador> mValues;
    private final OnListJugadorInteractionListener mListener;
    private final Context mContent;

    public MyJugadorRecyclerViewAdapter(Context context, List<Jugador> items, OnListJugadorInteractionListener listener) {
        mContent = context;
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_jugador, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSurnameView.setText(holder.mItem.getApellidos());
        holder.mNameView.setText(holder.mItem.getNombre());
        holder.mDorsalView.setText(holder.mItem.getDorsal());

        loadImg(holder.mItem.getImagen(), holder.mJugadorImg);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onJugadorClickListener(holder.mItem);
                }
            }
        });
    }

    /**
     *
     * @param url de la imagen
     * @param imageView la vista para poner la imagen
     */
    private void loadImg(String url, ImageView imageView) {
        Glide.with(mContent)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .centerInside() //
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mJugadorImg;
        final TextView mNameView;
        final TextView mSurnameView;
        final TextView mDorsalView;
        Jugador mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mJugadorImg = view.findViewById(R.id.imageViewJugadorImg);
            mNameView = view.findViewById(R.id.text_jugador_name);
            mSurnameView = view.findViewById(R.id.text_jugador_surname);
            mDorsalView = view.findViewById(R.id.text_jugador_dorsal);

        }
    }
}
