package yts.mnf.torrent.Models.DBModel;

import java.util.List;

import yts.mnf.torrent.Models.Movie;

/**
 * Created by muneef on 02/06/17.
 */

public class DBmodelRoot {
    private List<Movie> data;

    public void setData(List<Movie> data){
        this.data = data;
    }
    public List<Movie> getData(){
        return this.data;
    }
}
