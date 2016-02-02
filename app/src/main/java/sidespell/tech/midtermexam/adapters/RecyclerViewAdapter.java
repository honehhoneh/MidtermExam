package sidespell.tech.midtermexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sidespell.tech.midtermexam.AlbumDetailActivity;
import sidespell.tech.midtermexam.R;
import sidespell.tech.midtermexam.constants.Constants;
import sidespell.tech.midtermexam.entities.Album;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AlbumViewHolder> {

    private Context     mContext;
    private int         mLayoutId;
    private List<Album> mAlbum;

    public RecyclerViewAdapter(Context context, int layoutId, List<Album> album) {
        mContext = context;
        mLayoutId = layoutId;
        mAlbum = album;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album album = mAlbum.get(position);

        if (album != null) {
            if (holder.imgAlbum != null) {
                holder.imgAlbum.setImageResource(album.getImageId());
            }
            if (holder.tvAlbum != null) {
                holder.tvAlbum.setText(album.getAlbum());
            }
            if (holder.tvArtist != null) {
                holder.tvArtist.setText(album.getArtist());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAlbum.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView  cardView;
        public ImageView imgAlbum;
        public TextView tvAlbum;
        public TextView tvArtist;

        public AlbumViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            imgAlbum = (ImageView) view.findViewById(R.id.imgAlbum);
            tvAlbum = (TextView) view.findViewById(R.id.txtAlbumName);
            tvArtist = (TextView) view.findViewById(R.id.txtAlbumArtist);

            // once an item view (list item or grid item) is clicked
            view.setOnClickListener(this);
        }

       @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, AlbumDetailActivity.class);

            intent.putExtra(Constants.EXTRA_POSITION, getAdapterPosition());
            context.startActivity(intent);
        }

    }
}