package yts.mnf.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import yts.mnf.com.Activity.DetailsActivity;
import yts.mnf.com.Activity.SearchActivity;
import yts.mnf.com.Tools.Config;
import yts.mnf.com.Tools.Url;
import yts.mnf.com.Adapter.RecycleAdapter;
import yts.mnf.com.Models.ListModel;
import yts.mnf.com.Models.Movie;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Gson gson = new Gson();
    ListModel listMode;

    @BindView(R.id.main_activty_loading)
     CircularProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshSwipe;

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
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
// set an enter transition
        getWindow().setEnterTransition(new Slide());
// set an exit transition
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        c = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = (AdView) findViewById(R.id.ad_home);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent act = new Intent(MainActivity.this, DetailsActivity.class);
        //startActivity(act);


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
                            makeNetwoekRequest(Url.ListUrl+"?page="+page,false);
                            //  RequestData(Url + "?page=" + page);
                            //  showProgg();
                        }

                    }
                }

            }
        });


        adapter = new RecycleAdapter(c, mModels,getSupportFragmentManager());
        recyclerView.setAdapter(adapter);



        makeNetwoekRequest(Url.ListUrl,false);


        refreshSwipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("TAG", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        makeNetwoekRequest(Url.ListUrl,true);
                        refreshSwipe.setRefreshing(true);

                    }
                }
        );

    }



public void startLoading(){
    progressBar.setVisibility(View.VISIBLE);
    ((CircularProgressDrawable)progressBar.getIndeterminateDrawable()).start();
}
    public void stopLoading(){
        ((CircularProgressDrawable)progressBar.getIndeterminateDrawable()).progressiveStop();
        progressBar.setVisibility(View.INVISIBLE);
    }



    public void makeNetwoekRequest(String url, final boolean swipeRefresh){
       // RequestQueue queue = Volley.newRequestQueue(this);
        if(!swipeRefresh)
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
                        int movieCount = listMode.getData().getMovieCount();
                        int movieLimit = listMode.getData().getLimit();
                        totalPages = movieCount/movieLimit;
                        if(movieCount%movieLimit != 0){
                            totalPages++;
                        }
                        Log.e("MainActivity","movieCount = "+movieCount+" movieLimit = "+movieLimit+" totalPage = "+totalPages);
                        if(mModels!=null) {
                            if (swipeRefresh) {
                                refreshSwipe.setRefreshing(false);

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

            }
        });
AppController.getInstance().addToRequestQueue(jsonObjReq,"movie_list");
        AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);

    }










    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent search = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(search);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent home = new Intent(MainActivity.this, MainActivity.class);
            startActivity(home);
            finish();
            // Handle the camera action
        } else if (id == R.id.search) {
        Intent search = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(search);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
