package yts.mnf.me.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;

import java.util.List;

import yts.mnf.me.Activity.DetailsActivity;
import yts.mnf.me.Models.Movie;
import yts.mnf.me.R;

/**
 * Created by muneef on 11/03/17.
 */

public class SuggestionsAdapter  extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mModels;
    FragmentManager fm;



    public SuggestionsAdapter(Context mContext, List<Movie> models, FragmentManager fm) {
        this.mContext = mContext;
        this.mModels = models;
        this.fm = fm;
    }

    public void addItems(List<Movie> models){
        this.mModels.addAll(models);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle, movieYear,tvRating;
        public ImageView moviePoster, overflow;
        public RelativeLayout relativeLayout;
        public CardView cv;
        // TagView tagGroup;
        public ViewHolder(View view) {
            super(view);
            movieTitle = (TextView) view.findViewById(R.id.movie_title);
            moviePoster = (ImageView) view.findViewById(R.id.poster);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.name_relative);
            cv = (CardView) view.findViewById(R.id.card_view);
            movieYear = (TextView) view.findViewById(R.id.movie_year);
            tvRating = (TextView) view.findViewById(R.id.rate_tv_adapter);
            //  tagGroup = (TagView) view.findViewById(R.id.tag_group);

            // count = (TextView) view.findViewById(R.id.count);
            //thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //  overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggestion, parent, false);

        return new SuggestionsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Movie movie = mModels.get(position);

        holder.movieTitle.setText(movie.getTitle());
        //holder.tagGroup.removeAll();
        if(movie.getYear()!=null){
            holder.movieYear.setText(movie.getYear().toString());
        }
        holder.tvRating.setText(movie.getRating()+"");
        Glide.clear(holder.moviePoster);


        if (movie.getMediumCoverImage() != null) { // simulate an optional url from the data item
            holder.moviePoster.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(movie.getMediumCoverImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.moviePoster);
            Glide
                    .with(mContext)
                    .load(movie.getMediumCoverImage())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    // .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))

                    .into(new SimpleTarget<Bitmap>(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            //holder.moviePoster.setImageBitmap(resource); // Possibly runOnUiThread()
                            if(holder.relativeLayout!=null){
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette p) {
                                        // Use generated instance
                                        holder.relativeLayout.setBackgroundColor(p.getDarkVibrantColor(mContext.getResources().getColor(R.color.grey700)));
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


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gS = new Gson();
                String movieJson = gS.toJson(movie);
                Intent detailAct = new Intent(mContext, DetailsActivity.class);
                detailAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Pair<View, String> p1 = Pair.create((View)holder.moviePoster, "poster");
                Pair<View, String> p2 = Pair.create((View)holder.movieTitle, "title");

                detailAct.putExtra("movie_json", movieJson);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, p1,p2);
                mContext.startActivity(detailAct,options.toBundle());

            }
        });



    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }



}
