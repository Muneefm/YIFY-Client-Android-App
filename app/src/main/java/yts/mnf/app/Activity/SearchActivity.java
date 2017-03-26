package yts.mnf.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yts.mnf.app.Adapter.RecycleAdapter;
import yts.mnf.app.AppController;
import yts.mnf.app.GridSpacingItemDecoration;
import yts.mnf.app.Models.ListModel;
import yts.mnf.app.Models.Movie;
import yts.mnf.app.R;
import yts.mnf.app.Tools.Config;
import yts.mnf.app.Tools.Url;

public class SearchActivity extends AppCompatActivity {

    Gson gson = new Gson();
    ListModel listMode;

    @BindView(R.id.spinner_quality)
    Spinner spinnerQuality;

    @BindView(R.id.spinner_genre)
    Spinner spinnerGenre;

    @BindView(R.id.spinner_rating)
    Spinner spinnerRating;

    @BindView(R.id.spinner_sort_by)
    Spinner spinnerSort;

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefreshSearch)
    SwipeRefreshLayout refreshSwipe;

    @BindView(R.id.search_edt)
    EditText searchEdt;



    @BindView(R.id.search_btn)
    Button searchBtn;


    @BindView(R.id.container_error)
    RelativeLayout containerError;


    @BindView(R.id.tag_msg)
    TextView tvErrorMsg;


    String qualityString="",genreString="",ratingString="",sortString="latest",queryString="";

    private RecycleAdapter adapter;
    private List<Movie> mModels;
    Context c;

    int totalPages=1;
    int page =1;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        c=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchRequest(generateSearchUrl(),true);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRequest(generateSearchUrl(),true);

            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adSearch);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        ArrayAdapter<CharSequence> adapterQuality = ArrayAdapter.createFromResource(this,
                R.array.quality, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterQuality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerQuality.setAdapter(adapterQuality);

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                queryString = String.valueOf(s);
                Log.e("TAG","afterTextChanged string "+queryString );

            }
        });

        spinnerQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        qualityString ="";
                        break;
                    case 1:
                        qualityString ="720p";
                        break;
                    case 2:
                        qualityString = "1080p";
                        break;
                    case 3:
                        qualityString = "3D";
                }
                Log.e("TAG","onItemClick position "+position+" qualityString = "+qualityString );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(this,
                R.array.genre, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerGenre.setAdapter(adapterGenre);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        genreString ="";
                        break;
                    case 1:
                        genreString ="action";
                        break;
                    case 2:
                        genreString = "animation";
                        break;
                    case 3:
                        genreString = "comedy";
                        break;
                    case 4:
                        genreString ="documentary";
                        break;
                    case 5:
                        genreString = "family";
                        break;
                    case 6:
                        genreString = "film-noir";
                        break;
                    case 7:
                        genreString ="horror";
                        break;
                    case 8:
                        genreString = "musical";
                        break;
                    case 9:
                        genreString = "romance";
                        break;
                    case 10:
                        genreString ="sport";
                        break;
                    case 11:
                        genreString = "war";
                        break;
                    case 12:
                        genreString = "adventure";
                        break;
                    case 13:
                        genreString = "biography";
                        break;
                    case 14:
                        genreString = "crime";
                        break;
                    case 15:
                        genreString = "drama";
                        break;
                    case 16:
                        genreString = "fantasy";
                        break;
                    case 17:
                        genreString = "history";
                        break;
                    case 18:
                        genreString = "music";
                        break;
                    case 19:
                        genreString = "mystery";
                        break;

                    case 20:
                        genreString = "Sci-Fi";
                        break;
                    case 21:
                        genreString = "thriller";
                        break;
                    case 22:
                        genreString = "western";
                        break;
                }
                Log.e("TAG","onItemClick position "+position+" genreString = "+genreString );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ArrayAdapter<CharSequence> adapterRating = ArrayAdapter.createFromResource(this,
                R.array.rating, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterRating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerRating.setAdapter(adapterRating);



        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        ratingString ="";
                        break;
                    case 1:
                        ratingString = "9";
                        break;
                    case 2:
                        ratingString = "8";
                        break;
                    case 3:
                        ratingString ="7";
                        break;
                    case 4:
                        ratingString = "6";
                        break;
                    case 5:
                        ratingString = "5";
                        break;
                    case 6:
                        ratingString ="4";
                        break;
                    case 7:
                        ratingString = "3";
                        break;
                    case 8:
                        ratingString = "2";
                        break;
                    case 9:
                        ratingString ="1";
                        break;

                }

                Log.e("TAG","onItemClick position "+position+" ratingString = "+ratingString );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        ArrayAdapter<CharSequence> adapterSort = ArrayAdapter.createFromResource(this,
                R.array.sort, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerSort.setAdapter(adapterSort);



        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        sortString ="date_added";
                        break;
                    case 1:
                        sortString = "seeds";
                        break;
                    case 2:
                        sortString = "peers";
                        break;
                    case 3:
                        sortString ="year";
                        break;
                    case 4:
                        sortString = "rating";
                        break;
                    case 5:
                        sortString = "like_count";
                        break;
                    case 6:
                        sortString ="title";
                        break;
                    case 7:
                        sortString = "download_count";
                        break;


                }

                Log.e("TAG","onItemClick position "+position+" sortString = "+sortString );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        mModels = new ArrayList<>();
        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Config.dpToPx(1,getApplicationContext()), true));
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("TAGl", "inside onScolled totpages = " + totalPages);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.e("TAGl", "inside loading tot & page = " + totalPages + " " + page);
                        if (page < totalPages) {
                            Log.e("TAGl", "inside page<=totalPage");
                            Log.e("TAGl", "last inside tot & page = " + totalPages + " " + page);
                            page++;
                            //  paramsModel.add(new ParamsModel("page", page + ""));
                           // makeNetwoekRequest(Url.ListUrl+"?page="+page,false);
                            //  RequestData(Url + "?page=" + page);
                            //  showProgg();
                            searchRequest(generateSearchUrl()+"&page="+page,false);
                        }

                    }
                }

            }
        });


        adapter = new RecycleAdapter(c, mModels,getSupportFragmentManager());
        recyclerView.setAdapter(adapter);





    }


    public String generateSearchUrl(){
        String params = "?quality="+qualityString+"&minimum_rating="+ratingString+"&genre="+genreString+"&sort_by="+sortString+"&query_term="+queryString;
        Log.e("TAG","paramsString = "+params);
        String url = Url.ListUrl+params;
        Log.e("TAG","final url  = "+url);
        return url;
    }


    public void searchRequest(String url, final boolean newSearch){
        // RequestQueue queue = Volley.newRequestQueue(this);
        Log.e("TAG","searchRequest final url  = "+url);


        startLoading();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());
                        stopLoading();

                        listMode = gson.fromJson(response.toString(),ListModel.class);
                        Log.e("tag","response "+listMode.getStatus());
                        loading = true;

                        mModels = listMode.getData().getMovies();
                        if(mModels==null){
                            tvErrorMsg.setText("No Result Found !!");
                            containerError.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                        }else{
                            containerError.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        int movieCount = listMode.getData().getMovieCount();
                        int movieLimit = listMode.getData().getLimit();
                        totalPages = movieCount/movieLimit;
                        if(movieCount%movieLimit != 0){
                            totalPages++;
                        }
                        Log.e("MainActivity","movieCount = "+movieCount+" movieLimit = "+movieLimit+" totalPage = "+totalPages);
                            refreshSwipe.setRefreshing(false);
                        if(mModels!=null) {

                            if (newSearch) {
                                adapter.replaceItems(mModels);
                            } else {
                                adapter.addItems(mModels);
                            }

                            recyclerView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                // hide the progress dialog
                loading = true;
                stopLoading();
                recyclerView.setVisibility(View.INVISIBLE);

                tvErrorMsg.setText("Network Issue, Please try again !!");
                    containerError.setVisibility(View.VISIBLE);


            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq,"movie_list_search");
        AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);

    }




    public void startLoading(){
        containerError.setVisibility(View.INVISIBLE);

        refreshSwipe.setEnabled( true );
        refreshSwipe.setRefreshing( true );

    }
    public void stopLoading(){
        refreshSwipe.setRefreshing( false );
        refreshSwipe.setEnabled( false );


    }

}
