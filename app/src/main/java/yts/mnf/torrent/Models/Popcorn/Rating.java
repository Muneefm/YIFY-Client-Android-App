
package yts.mnf.torrent.Models.Popcorn;


public class Rating {

    private Integer percentage;
    private Integer watching;
    private Integer votes;
    private Integer loved;
    private Integer hated;

    public float getPercentage() {

        float rating = (float)percentage/10;
        return rating;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getWatching() {
        return watching;
    }

    public void setWatching(Integer watching) {
        this.watching = watching;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getLoved() {
        return loved;
    }

    public void setLoved(Integer loved) {
        this.loved = loved;
    }

    public Integer getHated() {
        return hated;
    }

    public void setHated(Integer hated) {
        this.hated = hated;
    }

}
