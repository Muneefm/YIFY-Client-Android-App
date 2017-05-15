package yts.mnf.torrent.Tools;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import yts.mnf.torrent.Models.DBModel.WishlistModel;

/**
 * Created by muneef on 16/05/17.
 */

public class DBManager {
    private static String TAG = "DBManager";

    public void addWishlist(String json){
        new WishlistModel().setMovie(json);
    }
    public RealmResults<WishlistModel> getAllWishlist(){
        RealmResults<WishlistModel> wishModel =   Realm.getDefaultInstance().where(WishlistModel.class).findAll();
        Log.e(TAG,"getWishList size = "+wishModel.size());
        return wishModel;
    }

    public WishlistModel getWishListSingle(int id){
       WishlistModel wishModel =  Realm.getDefaultInstance().where(WishlistModel.class).equalTo("id",id).findFirst();
        return wishModel;
    }



}
