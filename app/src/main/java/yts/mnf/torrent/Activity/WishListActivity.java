package yts.mnf.torrent.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import yts.mnf.torrent.Adapter.WishlistAdapter;
import yts.mnf.torrent.GridSpacingItemDecoration;
import yts.mnf.torrent.Models.DBModel.DBmodelRoot;
import yts.mnf.torrent.Models.DBModel.WishlistModel;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.Config;
import yts.mnf.torrent.Tools.DBManager;

public class WishListActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_w)
    RecyclerView recyclerView;
    List<Movie> moviesModel;
    DBmodelRoot modelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        ButterKnife.bind(this);

      /*  recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
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
            Log.e("TAG","json obj  = "+modelData.getData());
        }


        moviesModel = new ArrayList<>();
        recyclerView.setAdapter(new WishlistAdapter(getApplicationContext(),modelData.getData()));*/
    }
}
