package yts.mnf.torrent.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yts.mnf.torrent.Adapter.WishlistAdapter;
import yts.mnf.torrent.GridSpacingItemDecoration;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.Config;

public class WishListActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_w)
    RecyclerView recyclerView;
    List<Movie> moviesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        moviesModel = new ArrayList<>();
        recyclerView.setAdapter(new WishlistAdapter(getApplicationContext(),moviesModel));
    }
}
