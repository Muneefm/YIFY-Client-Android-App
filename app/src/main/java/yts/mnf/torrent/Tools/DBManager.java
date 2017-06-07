package yts.mnf.torrent.Tools;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import yts.mnf.torrent.Models.DBModel.WishlistModel;

/**
 * Created by muneef on 16/05/17.
 */

public class DBManager {
    private static String TAG = "DBManager";

    public boolean addWishlist(String json,String movieId){

        if(!checkIdExist(movieId)) {
            Log.e("TAG", "wishlist add call movie id = " + movieId + " \n json string = " + json);

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            String uuid = UUID.randomUUID().toString();
            WishlistModel wModel = realm.createObject(WishlistModel.class, uuid);
            wModel.setMovie(json);
            wModel.setMovieId(movieId);
            wModel.setDate();

            realm.commitTransaction();

            return true;
        }else{
            Log.e("TAG", "wishlist add Duplicate entry  movie id = " + movieId + " \n json string = " + json);
            return false;
        }
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
        final RealmResults<WishlistModel> results = realm.where(WishlistModel.class).equalTo("movieId",movieId).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(results.size()>0)
               Log.e(TAG,"delete item method call result[0] = "+results.get(0));
                results.deleteAllFromRealm();
            }
        });



    }


}
