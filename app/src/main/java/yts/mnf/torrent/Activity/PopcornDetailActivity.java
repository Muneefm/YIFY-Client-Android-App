package yts.mnf.torrent.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.robertlevonyan.views.chip.Chip;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import yts.mnf.torrent.Adapter.SuggestionsAdapter;
import yts.mnf.torrent.AppController;
import yts.mnf.torrent.GridSpacingItemDecoration;
import yts.mnf.torrent.Models.ListModel;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.Models.Popcorn.PopcornModel;
import yts.mnf.torrent.Models.Popcorn._720p;
import yts.mnf.torrent.Models.Torrent;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.Config;
import yts.mnf.torrent.Tools.Url;

public class PopcornDetailActivity extends AppCompatActivity {

    Chip chip;
    private SuggestionsAdapter adapter;
    private List<Movie> mModels;
    Context c;
    Gson gson = new Gson();
    ListModel listMode;

   // @BindView(R.id.recycler_view_suggestion)
   // RecyclerView recyclerViewSuggestion;

    @BindView(R.id.title_movie)
    TextView tvMovie;

    @BindView(R.id.rate_tv)
    TextView tvRate;

    @BindView(R.id.runtime_tv)
    TextView tvTime;

    @BindView(R.id.directed_tv)
    TextView tvDirected;

    @BindView(R.id.desc_tv)
    TextView tvDesc;

    @BindView(R.id.movie_poster_main)
    ImageView posterMain;

    @BindView(R.id.gener_container)
    LinearLayout generContainer;

   /* @BindView(R.id.sugg_head)
    TextView suggestionTag;


    //720p
    @BindView(R.id.sev_container)
    LinearLayout llContainer7;


    @BindView(R.id.sev_quality)
    TextView tvQuality7;

    @BindView(R.id.sev_seed)
    TextView tvSeeds7;

    @BindView(R.id.sev_leech)
    TextView tvLeech7;

    @BindView(R.id.sev_size)
    TextView tvSize7;
    @BindView(R.id.sev_download)
    FloatingTextButton tvDown7;

    @BindView(R.id.sev_magnet)
    FloatingTextButton tvMagnet7;



    //1080p
    @BindView(R.id.ten_container)
    LinearLayout llContainer1;

    @BindView(R.id.ten_quality)
    TextView tvQuality1;

    @BindView(R.id.ten_seeds)
    TextView tvSeeds1;

    @BindView(R.id.ten_leech)
    TextView tvLeech1;

    @BindView(R.id.ten_size)
    TextView tvSize1;

    @BindView(R.id.ten_download)
    FloatingTextButton tvDown1;

    @BindView(R.id.ten_magnet)
    FloatingTextButton tvMagnet1;


    //T version
    @BindView(R.id.ten_containert)
    LinearLayout llContainerT;

    @BindView(R.id.ten_qualityt)
    TextView tvQualityT;

    @BindView(R.id.ten_seedst)
    TextView tvSeedsT;

    @BindView(R.id.ten_leecht)
    TextView tvLeechT;

    @BindView(R.id.ten_sizet)
    TextView tvSizeT;

    @BindView(R.id.ten_downloadt)
    FloatingTextButton tvDownT;

    @BindView(R.id.ten_magnett)
    FloatingTextButton tvMagnetT;

*/




    @BindView(R.id.scroll_nest)
    NestedScrollView scrollView;

  /*  @BindView(R.id.container_quality)
    LinearLayout downloadDetails;
*/


    @BindView(R.id.scroll_view_quality)
    HorizontalScrollView qualityScrollView;

    @BindView(R.id.quality_linear)
    LinearLayout qualityLinear;
    //@BindView(R.id.download_torrent)
    // Button downloadTorrent;
    //ten_download


   /* @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;*/

    PopcornModel movieModel;
    String movieName;
    static String TAG = "PopcornDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
// set an enter transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide());
            getWindow().setExitTransition(new Slide());

        }
        setContentView(R.layout.activity_popcorn_detail);

// set an exit transition
        ButterKnife.bind(this);
        c =this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if(getIntent().hasExtra("movie_json")) {
            Log.e(TAG,"activity has extra ");
            String target = getIntent().getStringExtra("movie_json");
            Log.e(TAG,"activity has extra json =  "+target);
            movieModel = new Gson().fromJson(target, PopcornModel.class);
        }else{
            Log.e(TAG,"activity has no extra ");

        }

//adDetailPage
        AdView mAdView = (AdView) findViewById(R.id.adDetailPage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Typeface face=Typeface.createFromAsset(getAssets(), "fonts/FjallaOne-Regular.ttf");
        tvMovie.setTypeface(face);

        Typeface faceRate=Typeface.createFromAsset(getAssets(), "fonts/Righteous-Regular.ttf");
        tvRate.setTypeface(faceRate);

        Typeface faceTime=Typeface.createFromAsset(getAssets(), "fonts/QuattrocentoSans-Regular.ttf");
        tvTime.setTypeface(faceTime);
        tvDirected.setTypeface(faceTime);
        //suggestionTag.setTypeface(faceRate);

        Typeface faceDesc=Typeface.createFromAsset(getAssets(), "fonts/Abel-Regular.ttf");
        tvDesc.setTypeface(faceDesc);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        if(movieModel!=null){
            tvMovie.setText(movieModel.getTitle());
            if(movieModel.getTitle()!=null) {
                movieName = movieModel.getTitle();
            }
            Log.e("TAG","detail large cover url = "+movieModel.getImages().getBanner());
            if(movieModel.getImages().getBanner()==null){
                Config.loadImage(posterMain,movieModel.getImages().getBanner());
            }else {
                Config.loadImage(posterMain, movieModel.getImages().getPoster());
            }
            tvDesc.setText(movieModel.getSynopsis());
            tvRate.setText(movieModel.getRating().getPercentage().toString());
            tvTime.setText(movieModel.getRuntime()+"ms");
            tvDirected.setText(movieModel.getYear().toString());
            for (int i = 0;i<movieModel.getGenres().size();i++){
                chip = new Chip(getApplication());
                chip.setChipText(movieModel.getGenres().get(i));

                //  chip.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,9));

                generContainer.addView(chip);
            }
            posterMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailAct = new Intent(PopcornDetailActivity.this, ImageViewActivity.class);
                    Pair<View, String> p1 = Pair.create((View)posterMain, "image");
                    // Pair<View, String> p2 = Pair.create((View)holder.movieTitle, "title");

                    detailAct.putExtra("img_url", movieModel.getImages().getBanner());
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(PopcornDetailActivity.this, p1);
                    startActivity(detailAct,options.toBundle());

                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + movieModel.getTrailer()));
                    startActivity(intent);

                }
            });


          /*  mModels = new ArrayList<>();
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            recyclerViewSuggestion.setLayoutManager(mLayoutManager);
            recyclerViewSuggestion.addItemDecoration(new GridSpacingItemDecoration(1, Config.dpToPx(1,getApplicationContext()), true));
            //recyclerView.addItemDecoration(new MarginDecoration(this));
            recyclerViewSuggestion.setHasFixedSize(true);



            adapter = new SuggestionsAdapter (c, mModels,getSupportFragmentManager());
            recyclerViewSuggestion.setAdapter(adapter);

            recyclerViewSuggestion.setNestedScrollingEnabled(false);
          //  startSuggestionRequest(Url.SuggestionUrl+"?movie_id="+movieModel.getId());

            if(movieModel.getTorrents()!=null) {

            }

*/
            // reqPermission();

            //  SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            // viewPager.setAdapter(mSectionsPagerAdapter);
            //   tabLayout.setViewPager(viewPager);

            LayoutInflater factory = LayoutInflater.from(this);
            View qualityView = factory.inflate(R.layout.single_quality, null);
            qualityView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
          /*  View qualityViewOne = factory.inflate(R.layout.single_quality, null);
            View qualityViewTwo = factory.inflate(R.layout.single_quality, null);
            View qualityViewThree = factory.inflate(R.layout.single_quality, null);
            View qualityViewFour = factory.inflate(R.layout.single_quality, null);*/


            qualityLinear.addView(qualityView);
        /*    qualityLinear.addView(qualityViewOne);
            qualityLinear.addView(qualityViewTwo);
            qualityLinear.addView(qualityViewThree);
            qualityLinear.addView(qualityViewFour);
*/

        if(movieModel.getTorrents().getEn().get720p()!=null){
                Log.e("TAG","not null 720");
            }else{
            Log.e("TAG","null 720");
        }



            Gson gson = new Gson();
            String jsonStringTorrent = gson.toJson(movieModel.getTorrents().getEn());
            JsonObject jsonObjTorrent = gson.fromJson(jsonStringTorrent,JsonObject.class);
          //  jsonObjTorrent.getAsJsonArray().get

            if(jsonObjTorrent.get("720p")!=null) {
                _720p json720p = gson.fromJson(jsonStringTorrent, _720p.class);

            }


        }
    }

    public void copyText(String mUrl){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("yify", mUrl);
        clipboard.setPrimaryClip(clip);
    }

    public String generateMagneticUrl(String hash,String movieName) throws UnsupportedEncodingException {
        //magnet:?xt=urn:btih:TORRENT_HASH&dn=Url+Encoded+Movie+Name&tr=http://track.one:1234/announce&tr=udp://track.two:80

        String baseString = "magnet:?xt=urn:btih:"+hash+"&dn=";
        String encodedMovieName = URLEncoder.encode(movieName, "utf-8").replace("+", "%20");
        baseString+=encodedMovieName;
        Log.e("TAG","after mName encode = "+baseString);

        String tracker1 = "udp://open.demonii.com:1337/announce";
        String tracker2 = "udp://tracker.openbittorrent.com:80";
        String tracker3 = "udp://tracker.coppersurfer.tk:6969";
        String tracker4 = "udp://glotorrents.pw:6969/announce";
        String tracker5 = "udp://tracker.opentrackr.org:1337/announce";
        String tracker6 = "udp://torrent.gresille.org:80/announce";
        String tracker7 = "udp://p4p.arenabg.com:1337";
        String tracker8 = "udp://tracker.leechers-paradise.org:6969";

        String[] trackerArray = {tracker1,tracker2,tracker3,tracker4,tracker5,tracker6,tracker7,tracker8};
        String trackers ="";


        for (String trackerItem: trackerArray) {
            trackers+="&tr="+URLEncoder.encode(trackerItem, "utf-8").replace("+", "%20");
        }
        Log.e("TAG","after tracker encode = "+trackers);
        Log.e("TAG","final magnetic url  = "+baseString+trackers);

        return baseString+trackers;

    }
}
