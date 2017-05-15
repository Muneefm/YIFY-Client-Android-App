package yts.mnf.torrent.Models.DBModel;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by muneef on 16/05/17.
 */

public class WishlistModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String movie;
    private Date date;

    public void setMovie(String movie){
        this.movie = movie;
    }

}
