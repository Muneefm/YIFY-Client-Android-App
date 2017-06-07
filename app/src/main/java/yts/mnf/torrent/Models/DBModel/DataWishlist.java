package yts.mnf.torrent.Models.DBModel;

import java.util.Date;

import yts.mnf.torrent.Models.Movie;

/**
 * Created by muneef on 02/06/17.
 */

public class DataWishlist {
    private String id;
    private String movieId;
    private Movie movie;
    private Date date;

    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public void setMovieId(String movieId){
        this.movieId = movieId;
    }

    public void setDate(){
        this.date = new Date();
    }
}
