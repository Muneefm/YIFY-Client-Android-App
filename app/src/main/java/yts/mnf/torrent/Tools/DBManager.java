package yts.mnf.torrent.Tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import yts.mnf.torrent.Models.DBModel.WishListMovieModel;
import yts.mnf.torrent.Models.DBModel.WishlistModel;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.Models.Popcorn.PopcornModel;
import yts.mnf.torrent.Models.Torrent;

/**
 * Created by muneef on 16/05/17.
 */

public class DBManager {
    private static String TAG = "DBManager";

    public boolean addWishlist(Movie movieModel, String provider){

       /* if(!checkIdExist(movieModel.getId())) {
            Log.e("TAG", "wishlist add call movie id = " + movieId + " \n json string = " + movieModel.getId());

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();


            String uuid = UUID.randomUUID().toString();
            WishListMovieModel wModel = realm.createObject(WishListMovieModel.class, uuid);
            wModel.setAdded_on(new Date());
            wModel.setMovie_id(movieModel.getId());
            wModel.setProvider(provider);
            wModel.setRating(movieModel.getRating().toString());
            wModel.setTitle(movieModel.getTitle());
            wModel.setQuality(qualitiesList);

            realm.commitTransaction();

            return true;
        }else{
            Log.e("TAG", "wishlist add Duplicate entry  movie id = " + movieId + " \n json string = " + json);
            return false;
        }*/
       return true;
    }

    public boolean addDataYify(Movie movieModel){
        if(!checkIdExist(movieModel.getId().toString(),"yify")) {
            String qua1="",qua2="",qua3="";
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            List<String> qualitiesList = new ArrayList<>();
            for(int i=0;i<movieModel.getTorrents().size();i++){
                switch (i){
                    case 0:qua1 = movieModel.getTorrents().get(i).getQuality();break;
                    case 1:qua1 = movieModel.getTorrents().get(i).getQuality();break;
                    case 2:qua1 = movieModel.getTorrents().get(i).getQuality();break;

                }
            }

            String uuid = UUID.randomUUID().toString();
            WishListMovieModel wModel = realm.createObject(WishListMovieModel.class, uuid);
            wModel.setAdded_on(new Date());
            wModel.setMovie_id(movieModel.getId().toString());
            wModel.setProvider("yify");
            wModel.setRating(movieModel.getRating().toString());
            wModel.setTitle(movieModel.getTitle());
            wModel.setQuality_one(qua1);
            wModel.setQuality_two(qua2);
            wModel.setQuality_three(qua3);
            wModel.setImage_url(movieModel.getMediumCoverImage());
            realm.commitTransaction();
            return true;
        }else{
            return false;
        }
    }

    public boolean addDataPopcorn(PopcornModel movieModel){
        if(!checkIdExist(movieModel.getId().toString(),"pop")) {
            String qua1="",qua2="",qua3="";

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            List<String> qualitiesList = new ArrayList<>();
            if(movieModel.getTorrents().getEn().get720p()!=null){
                qua1 = "720p";
            }
            if(movieModel.getTorrents().getEn().get1080p()!=null){
                qua2 = "1080p";
            }


                String uuid = UUID.randomUUID().toString();
            WishListMovieModel wModel = realm.createObject(WishListMovieModel.class, uuid);
            wModel.setAdded_on(new Date());
            wModel.setMovie_id(movieModel.getId().toString());
            wModel.setProvider("pop");
            wModel.setRating(movieModel.getRating().toString());
            wModel.setTitle(movieModel.getTitle());
            wModel.setQuality_one(qua1);
            wModel.setQuality_two(qua2);
            wModel.setQuality_three(qua3);
            realm.commitTransaction();
            return true;
        }else{
            return false;
        }
    }


    public RealmResults<WishListMovieModel> getAllWishlist(){
        RealmResults<WishListMovieModel> wishModel =   Realm.getDefaultInstance().where(WishListMovieModel.class).findAll();
        Log.e(TAG,"getWishList size = "+wishModel.size());
        return wishModel;
    }

    public WishlistModel getWishListSingle(int id){
       WishlistModel wishModel =  Realm.getDefaultInstance().where(WishlistModel.class).equalTo("id",id).findFirst();
        return wishModel;
    }

    public boolean checkIdExist(String id,String provider){
           if(Realm.getDefaultInstance().where(WishListMovieModel.class).equalTo("provider",provider).equalTo("movie_id",id).findFirst()!=null){
               return true;
           }else {
               return false;
           }
    }
    public void deleteItemFromWishlist(String movieId, String provider){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<WishListMovieModel> results = realm.where(WishListMovieModel.class).equalTo("provider",provider).equalTo("movie_id",movieId).findAll();
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
