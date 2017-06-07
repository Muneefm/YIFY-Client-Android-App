package yts.mnf.torrent.Models.DBModel;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by muneef on 16/05/17.
 */

public class WishlistModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String movieId;
    private String movie;
    private Date date;

    public void setMovie(String movie){
        this.movie = movie;
    }

    public void setMovieId(String movieId){
        this.movieId = movieId;
    }

    public void setDate(){
        this.date = new Date();
    }


    public String getMovie(){
        return movie;
    }

    public String getMovieId(){
        return movieId;
    }

    public Date getDate(){
        return date;
    }
}
