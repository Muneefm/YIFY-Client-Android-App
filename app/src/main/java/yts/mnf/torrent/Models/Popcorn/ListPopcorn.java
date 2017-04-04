package yts.mnf.torrent.Models.Popcorn;

import java.util.List;

import yts.mnf.torrent.Models.Data;
import yts.mnf.torrent.Models.Movie;

/**
 * Created by muneef on 04/04/17.
 */

public class ListPopcorn {


    private List<PopcornModel> popcornmodel;
    public List<PopcornModel> getPopcornModel() {
        return popcornmodel;
    }

    public void setPopcornModel(List<PopcornModel> popcornmodel) {
        this.popcornmodel = popcornmodel;
    }
}
