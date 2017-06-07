package yts.mnf.torrent.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.R;

/**
 * Created by muneef on 28/05/17.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {


    private Context mContext;

    private List<Movie> mModels;

    public WishlistAdapter(Context mContext, List<Movie> models) {

        this.mContext = mContext;
        this.mModels = models;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle, movieYear,tvRating;
        public ImageView moviePoster, overflow;
        public RelativeLayout relativeLayout;
        public CardView cv;
        public View viewColor;
        public ViewHolder(View view) {
            super(view);
            movieTitle = (TextView) view.findViewById(R.id.m_title);
            moviePoster = (ImageView) view.findViewById(R.id.m_poster);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.name_relative);
            cv = (CardView) view.findViewById(R.id.card_view);
            movieYear = (TextView) view.findViewById(R.id.movie_year);
            tvRating = (TextView) view.findViewById(R.id.m_rate);
            viewColor =  view.findViewById(R.id.m_color);
           }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item, parent, false);

        return new WishlistAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Movie item = mModels.get(position);
        Typeface face=Typeface.createFromAsset(mContext.getAssets(), "fonts/FjallaOne-Regular.ttf");
        holder.movieTitle.setTypeface(face);
        holder.movieTitle.setText(item.getTitle());
        holder.tvRating.setText(item.getRating().toString());
        if (item.getMediumCoverImage() != null) { // simulate an optional url from the data item
            holder.moviePoster.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(item.getMediumCoverImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.moviePoster);
            Glide
                    .with(mContext)
                    .load(item.getMediumCoverImage())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    // .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))

                    .into(new SimpleTarget<Bitmap>(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            Log.e("WishlistAdapter","onResourceReady ");
                            //holder.moviePoster.setImageBitmap(resource); // Possibly runOnUiThread()
                            if(holder.viewColor!=null){
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette p) {
                                        // Use generated instance
                                        holder.viewColor.setBackgroundColor(p.getDarkVibrantColor(mContext.getResources().getColor(R.color.grey700)));
                                        //holder.tvRating.setTextColor(Config.manipulateColor(p.getDarkVibrantColor(mContext.getResources().getColor(R.color.white)),0.9f));
                                    }
                                });
                            }
                        }
                    });

        } else {
            // clear when no image is shown, don't use holder.imageView.setImageDrawable(null) to do the same
            Glide.clear(holder.moviePoster);
            holder.moviePoster.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }



}
