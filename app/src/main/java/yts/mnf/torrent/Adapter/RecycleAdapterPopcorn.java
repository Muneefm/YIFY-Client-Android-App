package yts.mnf.torrent.Adapter;

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

import yts.mnf.torrent.Activity.DetailsActivity;
import yts.mnf.torrent.Activity.PopcornDetailActivity;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.Models.Popcorn.PopcornModel;
import yts.mnf.torrent.R;

/**
 * Created by muneef on 22/01/17.
 */

public class RecycleAdapterPopcorn extends RecyclerView.Adapter<RecycleAdapterPopcorn.ViewHolder> {


    private Context mContext;
    private List<PopcornModel> mModels;
    FragmentManager fm;

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public RecycleAdapterPopcorn(Context mContext, List<PopcornModel> models, FragmentManager fm) {
        this.mContext = mContext;
        this.mModels = models;
        this.fm = fm;
    }
    public void replaceItems(List<PopcornModel> models){
        this.mModels = models;
        notifyDataSetChanged();
    }
    public void addItems(List<PopcornModel> models){
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
                .inflate(R.layout.item_grid, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PopcornModel popModel = mModels.get(position);

        holder.movieTitle.setText(popModel.getTitle());
        if(popModel.getYear()!=null){
            holder.movieYear.setText(popModel.getYear().toString());
        }
        holder.tvRating.setText(popModel.getRating().getPercentage()+"");

        //holder.tagGroup.removeAll();
        Glide.clear(holder.moviePoster);


        if (popModel.getImages().getPoster() != null) { // simulate an optional url from the data item
            holder.moviePoster.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(popModel.getImages().getPoster())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.moviePoster);
            Glide
                    .with(mContext)
                    .load(popModel.getImages().getPoster())
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

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gS = new Gson();
                String movieJson = gS.toJson(popModel);
                Intent detailAct = new Intent(mContext, PopcornDetailActivity.class);
                detailAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Pair<View, String> p1 = Pair.create((View)holder.moviePoster, "poster");
                Pair<View, String> p2 = Pair.create((View)holder.movieTitle, "title");
                Pair<View, String> p3 = Pair.create((View)holder.tvRating, "rating");


                detailAct.putExtra("movie_json", movieJson);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, p1,p2,p3);
                mContext.startActivity(detailAct,options.toBundle());

            }
        });



    }


}
