package yts.mnf.torrent.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yts.mnf.torrent.Adapter.WishlistAdapter;
import yts.mnf.torrent.Models.DBModel.DBmodelRoot;
import yts.mnf.torrent.Models.DBModel.WishlistModel;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.DBManager;

public class WishListActivityTwo extends AppCompatActivity {
    @BindView(R.id.recycler_view_w)
    RecyclerView recyclerView;
    Context c;
    DBmodelRoot modelData;
    WishlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_two);
         c = this;
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        adapter =  new WishlistAdapter(c,getMovieData());
        recyclerView.setAdapter(adapter);
    }

    private List<Movie> getMovieData(){
        String jsonString=null;

        for (WishlistModel result: new DBManager().getAllWishlist() ) {
            Log.e("TAG","id  = "+result.getMovieId());
            if(jsonString==null){
                jsonString = result.getMovie();
            }else {
                jsonString = jsonString+","+result.getMovie();
            }
        }
        Log.e("TAG","after loop  = "+jsonString);
        jsonString = "{data:["+jsonString+"]}";
        Log.e("TAG","after con  = "+jsonString);

        String json = new DBManager().getAllWishlist().toString();

        Log.e("TAG","string json   = "+json);
        json = "{data:"+json+"}";
        Log.e("TAG","string json after  = "+json);
        modelData = new Gson().fromJson(jsonString, DBmodelRoot.class);
        if(modelData != null){
            Log.e("TAG","json obj  = "+modelData.getData().size());
        }


        Collections.reverse(modelData.getData());
        return modelData.getData();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView.getAdapter()!=null){
            Log.e("TAG","recycle view has adapter");
            adapter.resetData(getMovieData());

        }
    }
}
