
package yts.mnf.torrent.Models;

import java.util.List;

public class Movie {

    private Integer id;
    private String url;
    private String imdbCode;
    private String title;
    private String titleEnglish;
    private String titleLong;
    private String slug;
    private Integer year;
    private Double rating;
    private Integer runtime;
    private List<String> genres = null;
    private String summary;
    private String description_full;
    private String synopsis;
    private String yt_trailer_code;
    private String language;
    private String mpaRating;
    private String backgroundImage;
    private String backgroundImageOriginal;
    private String smallCoverImage;
    private String medium_cover_image;
    private String large_cover_image;
    private String state;
    private List<Torrent> torrents = null;
    private String dateUploaded;
    private Integer dateUploadedUnix;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImdbCode() {
        return imdbCode;
    }

    public void setImdbCode(String imdbCode) {
        this.imdbCode = imdbCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleLong() {
        return titleLong;
    }

    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescriptionFull() {
        return description_full;
    }

    public void setDescriptionFull(String description_full) {
        this.description_full = description_full;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getYtTrailerCode() {
        return yt_trailer_code;
    }

    public void setYtTrailerCode(String yt_trailer_code) {
        this.yt_trailer_code = yt_trailer_code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImageOriginal() {
        return backgroundImageOriginal;
    }

    public void setBackgroundImageOriginal(String backgroundImageOriginal) {
        this.backgroundImageOriginal = backgroundImageOriginal;
    }

    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    public void setSmallCoverImage(String smallCoverImage) {
        this.smallCoverImage = smallCoverImage;
    }

    public String getMediumCoverImage() {
        return medium_cover_image;
    }

    public void setMediumCoverImage(String medium_cover_image) {
        this.medium_cover_image = medium_cover_image;
    }

    public String getLargeCoverImage() {
        return large_cover_image;
    }

    public void setLargeCoverImage(String large_cover_image) {
        this.large_cover_image = large_cover_image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Torrent> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<Torrent> torrents) {
        this.torrents = torrents;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Integer getDateUploadedUnix() {
        return dateUploadedUnix;
    }

    public void setDateUploadedUnix(Integer dateUploadedUnix) {
        this.dateUploadedUnix = dateUploadedUnix;
    }

}
