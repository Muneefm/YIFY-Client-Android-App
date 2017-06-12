package yts.mnf.torrent.Models.DBModel;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by muneef on 10/06/17.
 */

public class WishListMovieModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String movie_id;
    private String provider;
    private String title;
    private String rating;
    private String quality_one;
    private String quality_two;
    private String quality_three;
    private Date added_on;
    private String image_url;
    private String json_string;


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



    public Date getAdded_on() {
        return added_on;
    }

    public void setAdded_on(Date added_on) {
        this.added_on = added_on;
    }


    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getQuality_one() {
        return quality_one;
    }

    public void setQuality_one(String quality_one) {
        this.quality_one = quality_one;
    }

    public String getQuality_two() {
        return quality_two;
    }

    public void setQuality_two(String quality_two) {
        this.quality_two = quality_two;
    }

    public String getQuality_three() {
        return quality_three;
    }

    public void setQuality_three(String quality_three) {
        this.quality_three = quality_three;
    }

    public String getJson_string() {
        return json_string;
    }

    public void setJson_string(String json_string) {
        this.json_string = json_string;
    }
}
