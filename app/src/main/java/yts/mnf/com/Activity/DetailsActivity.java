package yts.mnf.com.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.robertlevonyan.views.chip.Chip;
import com.ruslankishai.unmaterialtab.tabs.RoundTabLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import yts.mnf.com.Adapter.RecycleAdapter;
import yts.mnf.com.Adapter.SuggestionsAdapter;
import yts.mnf.com.AppController;
import yts.mnf.com.Fragment.QualityFragment;
import yts.mnf.com.GridSpacingItemDecoration;
import yts.mnf.com.Models.ListModel;
import yts.mnf.com.Models.Movie;
import yts.mnf.com.Models.Torrent;
import yts.mnf.com.R;
import yts.mnf.com.Tools.Config;
import yts.mnf.com.Tools.Url;

public class DetailsActivity extends AppCompatActivity {

    Chip chip;
    private SuggestionsAdapter adapter;
    private List<Movie> mModels;
    Context c;
    Gson gson = new Gson();
    ListModel listMode;

    @BindView(R.id.recycler_view_suggestion)
    RecyclerView recyclerViewSuggestion;

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

    @BindView(R.id.sugg_head)
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


    @BindView(R.id.scroll_nest)
    NestedScrollView scrollView;

    @BindView(R.id.container_quality)
    LinearLayout downloadDetails;

    @BindView(R.id.download_torrent)
    Button downloadTorrent;
    //ten_download


   /* @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;*/

    Movie movieModel;
    static String TAG = "DetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
// set an enter transition
        getWindow().setEnterTransition(new Slide());
// set an exit transition
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_details);
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
            movieModel = new Gson().fromJson(target, Movie.class);
        }else{
            Log.e(TAG,"activity has no extra ");

        }


        Typeface face=Typeface.createFromAsset(getAssets(), "fonts/FjallaOne-Regular.ttf");
        tvMovie.setTypeface(face);

        Typeface faceRate=Typeface.createFromAsset(getAssets(), "fonts/Righteous-Regular.ttf");
        tvRate.setTypeface(faceRate);

        Typeface faceTime=Typeface.createFromAsset(getAssets(), "fonts/QuattrocentoSans-Regular.ttf");
        tvTime.setTypeface(faceTime);
        tvDirected.setTypeface(faceTime);
        suggestionTag.setTypeface(faceTime);

        Typeface faceDesc=Typeface.createFromAsset(getAssets(), "fonts/Abel-Regular.ttf");
        tvDesc.setTypeface(faceDesc);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        if(movieModel!=null){
            tvMovie.setText(movieModel.getTitle());
            Config.loadImage(posterMain,movieModel.getLargeCoverImage());
            tvDesc.setText(movieModel.getDescriptionFull());
            tvRate.setText(movieModel.getRating().toString());
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
                    Intent detailAct = new Intent(DetailsActivity.this, ImageViewActivity.class);
                    Pair<View, String> p1 = Pair.create((View)posterMain, "image");
                   // Pair<View, String> p2 = Pair.create((View)holder.movieTitle, "title");

                    detailAct.putExtra("img_url", movieModel.getLargeCoverImage());
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(DetailsActivity.this, p1);
                    startActivity(detailAct,options.toBundle());

                }
            });


            mModels = new ArrayList<>();
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            recyclerViewSuggestion.setLayoutManager(mLayoutManager);
            recyclerViewSuggestion.addItemDecoration(new GridSpacingItemDecoration(1, Config.dpToPx(1,getApplicationContext()), true));
            //recyclerView.addItemDecoration(new MarginDecoration(this));
            recyclerViewSuggestion.setHasFixedSize(true);



            adapter = new SuggestionsAdapter (c, mModels,getSupportFragmentManager());
            recyclerViewSuggestion.setAdapter(adapter);

            recyclerViewSuggestion.setNestedScrollingEnabled(false);
            startSuggestionRequest(Url.SuggestionUrl+"?movie_id="+movieModel.getId());

                if(movieModel.getTorrents()!=null) {
                    final List<Torrent> listTorModel = movieModel.getTorrents();
                    if (listTorModel.size() > 0) {
                        for (int i = 0; i < listTorModel.size(); i++){
                            if(i==0){
                                final Torrent model = listTorModel.get(i);
                                tvQuality7.setText(model.getQuality());
                                tvSeeds7.setText(model.getSeeds()+" Seeds");
                                tvLeech7.setText(model.getPeers()+" Peers");
                                tvSize7.setText(model.getSize()+"");
                                llContainer7.setVisibility(View.VISIBLE);
                                tvDown7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       // if(checkPermission()) {
                                        new AppController().startBrowser(model.getUrl(),c);
                                       // }else{
                                        //    reqPermission();
                                       // }
                                    }
                                });
                            }
                            else if(i==1){
                                final Torrent model = listTorModel.get(i);
                                tvQuality1.setText(model.getQuality());
                                tvSeeds1.setText(model.getSeeds()+" Seeds");
                                tvLeech1.setText(model.getPeers()+" Peers");
                                tvSize1.setText(model.getSize()+"");
                                llContainer1.setVisibility(View.VISIBLE);
                                tvDown1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       // if(checkPermission()) {
                                        new AppController().startBrowser(model.getUrl(),c);

                                        //  }else{
                                         //   reqPermission();
                                       // }
                                    }
                                });
                            }else if(i==2){
                                final Torrent model = listTorModel.get(i);
                                tvQualityT.setText(model.getQuality());
                                tvSeedsT.setText(model.getSeeds()+" Seeds");
                                tvLeechT.setText(model.getPeers()+" Peers");
                                tvSizeT.setText(model.getSize()+"");
                                llContainerT.setVisibility(View.VISIBLE);
                                tvDownT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // if(checkPermission()) {
                                        new AppController().startBrowser(model.getUrl(),c);

                                        //  }else{
                                        //   reqPermission();
                                        // }
                                    }
                                });
                            }
                        }



                    }
                    if(listTorModel.size()<3){
                        Log.e("tag","less 3");
                        ((ViewGroup)llContainerT.getParent()).removeView(llContainerT);
                        llContainer7.setPadding(30,5,30,5);
                        llContainer1.setPadding(30,5,30,5);
                    }
                    if(listTorModel.size()<2){
                        Log.e("tag","less 2");

                        ((ViewGroup)llContainer1.getParent()).removeView(llContainer1);
                        llContainer7.setPadding(100,5,100,5);
                    }
                    if(listTorModel.size()<1){
                        Log.e("tag","less 1");

                        ((ViewGroup)llContainer7.getParent()).removeView(llContainer7);
                    }
                    downloadTorrent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  focusOnView();
                            showDialogue(listTorModel);
                        }
                    });
                }


               // reqPermission();

          //  SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
           // viewPager.setAdapter(mSectionsPagerAdapter);
         //   tabLayout.setViewPager(viewPager);







        }




    }

    public void showDialogue(final List<Torrent> torrents){
        String[] title =new String[torrents.size()];
        for (int i=0;i<torrents.size();i++) {
            title[i] = torrents.get(i).getQuality()+" "+torrents.get(i).getSize();

        }
        new MaterialDialog.Builder(this)
                .title("Download")
                .items(title)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //Toast.makeText(DetailsActivity.this, which + ": " + text + ", ID = " , Toast.LENGTH_SHORT).show();
                        new AppController().startBrowser(torrents.get(which).getUrl(),c);
                        Log.e("TAG","onSelction download which = "+which);

                    }
                })
                .show();
    }

    public void startSuggestionRequest(String url){

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());

                        listMode = gson.fromJson(response.toString(),ListModel.class);
                        Log.e("tag","response suggestions "+listMode.getStatus());
                        suggestionTag.setVisibility(View.VISIBLE);
                        mModels = listMode.getData().getMovies();

                      //  Log.e("MainActivity","movieCount = "+movieCount+" movieLimit = "+movieLimit+" totalPage = "+totalPages);
                        adapter.addItems(mModels);
                        recyclerViewSuggestion.setVisibility(View.VISIBLE);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                // hide the progress dialog

            }
        });
        queue.add(jsonObjReq);


    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private  final String[] TITLES = new String[]{"720p", "1080p"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return QualityFragment.newInstance(position+"","");
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return TITLES[position];
        }
    }




    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();

        super.onBackPressed();

    }

    public boolean checkPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("tag","checkPermission  true");

            return true;
        }else{
            Log.e("tag","checkPermission  false ");

            return false;
        }
    }

    public void reqPermission(){
        Log.e("tag","reqPermission   ");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.e("tag","reqPermission  shouldShowRequestPermissionRationale ");

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                Log.e("tag","reqPermission  No explanation needed, we can request the permission. ");

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.e("tag","permission granted   ");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Log.e("tag","permission not granted   ");
                    showSnackBar("Permission needed for writing to storage");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showSnackBar(String msg) {
        if(tvMovie!=null)
        Snackbar.make(tvMovie,""+msg,Snackbar.LENGTH_LONG).show();
    }

    private final void focusOnView(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, downloadDetails.getBottom());
            }
        });
    }
}
