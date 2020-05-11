package net.jaumebalmes.grincon17.futchamp.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLeagueListInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.League;
import java.util.List;

public class MyLeagueRecyclerViewAdapter extends RecyclerView.Adapter<MyLeagueRecyclerViewAdapter.ViewHolder> {

    private final List<League> mValues;
    private final OnLeagueListInteractionListener mListener;
    private final Context mContent;

    public MyLeagueRecyclerViewAdapter(Context context, List<League> items, OnLeagueListInteractionListener listener) {
        mContent = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_league, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.getName());
        Glide.with(mContent).load(holder.mItem.getLogo()).into(holder.mLogoView);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final ImageView mLogoView;
        public League mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.textViewLeagueName);
            mLogoView = view.findViewById(R.id.imageViewLogo);
        }

    }
}
