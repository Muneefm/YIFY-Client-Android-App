
package yts.mnf.torrent.Models;

import java.util.List;

public class Data {

    private Integer movie_count;
    private Integer limit;
    private Integer page_number;
    private List<Movie> movies = null;

    public Integer getMovieCount() {
        return movie_count;
    }

    public void setMovieCount(Integer movie_count) {
        this.movie_count = movie_count;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPageNumber() {
        return page_number;
    }

    public void setPageNumber(Integer page_number) {
        this.page_number = page_number;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
