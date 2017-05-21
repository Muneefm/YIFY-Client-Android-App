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

    public boolean addWishlist(String json,String movieId){
        Log.e("TAG","wishlist add call movie id = "+movieId+" \n json string = "+json);
        WishlistModel wModel = new WishlistModel();
        wModel.setMovie(json);
        wModel.setMovieId(movieId);
        return true;
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

    public boolean checkIdExist(String id){
           if(Realm.getDefaultInstance().where(WishlistModel.class).equalTo("movieId",id).findFirst()!=null){
               return true;
           }else {
               return false;
           }
    }
    public void deleteItemFromWishlist(String movieId){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<WishlistModel> results = realm.where(WishlistModel.class).equalTo("id",movieId).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               Log.e(TAG,"delete item method call result[0] = "+results.get(0));
                results.deleteAllFromRealm();
            }
        });



    }


}
