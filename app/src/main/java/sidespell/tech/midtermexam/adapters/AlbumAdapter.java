package sidespell.tech.midtermexam.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sidespell.tech.midtermexam.R;
import sidespell.tech.midtermexam.entities.Album;

public class AlbumAdapter extends ArrayAdapter<Album> {

    private Context     mContext;
    private int         mLayoutId;
    private List<Album> mMovies;

    public AlbumAdapter(Context context, int resource, List<Album> movies) {
        super(context, resource, movies);

        mContext = context;
        mLayoutId = resource;
        mMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);

            // create the view holder
            viewHolder = new ViewHolder();
            viewHolder.imgMovie = (ImageView) convertView.findViewById(R.id.imgAlbum);
            viewHolder.tvAlbum = (TextView) convertView.findViewById(R.id.txtAlbumName);
            viewHolder.tvArtist = (TextView) convertView.findViewById(R.id.txtAlbumArtist);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the movie data
        Album album = mMovies.get(position);

        if (album != null) {
            if (viewHolder.imgMovie != null) {
                viewHolder.imgMovie.setImageResource(album.getImageId());
            }
            if (viewHolder.tvAlbum != null) {
                viewHolder.tvAlbum.setText(album.getAlbum());
            }
            if (viewHolder.tvArtist != null) {
                viewHolder.tvArtist.setText(album.getArtist());
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imgMovie;
        public TextView  tvAlbum;
        public TextView  tvArtist;
    }
}