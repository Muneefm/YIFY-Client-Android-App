
package yts.mnf.torrent.Models.Popcorn;


import com.google.gson.annotations.SerializedName;

public class En {

    @SerializedName("720p")
    private _720p _720p;

    @SerializedName("1080p")
    private _1080p _1080p;


    public _720p get720p() {
        return _720p;
    }

    public void set720p(_720p _720p) {
        this._720p = _720p;
    }

    public _1080p get1080p() {
        return _1080p;
    }

    public void set1080p(_1080p _1080p) {
        this._1080p = _1080p;
    }

}
