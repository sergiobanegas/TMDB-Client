package es.upm.miw.tmdb.client.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String directing;
    private String writing;
    private String releaseDate;
    private Double popularity;
    private String posterImage;
    private String backdropImage;

    public Movie() {

    }

    public Movie(int id, String title, String overview, String directing, String writing, String releaseDate, Double popularity, String posterImage, String backdropImage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.directing = directing;
        this.writing = writing;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.posterImage = posterImage;
        this.backdropImage = backdropImage;

    }

    public int getId() {
        return id;
    }

    public String getBackdropImage() {
        return backdropImage;
    }

    public String getDirecting() {
        return directing;
    }

    public String getOverview() {
        return overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWriting() {
        return writing;
    }

    public void setBackdropImage(String backdropImage) {
        this.backdropImage = backdropImage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDirecting(String directing) {
        this.directing = directing;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.directing);
        dest.writeString(this.writing);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterImage);
        dest.writeString(this.backdropImage);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.directing = in.readString();
        this.writing = in.readString();
        this.releaseDate = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.posterImage = in.readString();
        this.backdropImage = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
