package es.upm.miw.tmdb.client.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private int id;
    private String name;
    private String biography;
    private String birthday;
    private String deathday;
    private String placeOfBirth;
    private Double popularity;
    private String bigImage;
    private String smallImage;

    public Person() {

    }

    public Person(int id, String name, String biography, String birthday, String deathday, String placeOfBirth, Double popularity, String bigImage, String smallImage) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.placeOfBirth = placeOfBirth;
        this.popularity = popularity;
        this.bigImage = bigImage;
        this.smallImage = smallImage;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getBigImage() {
        return bigImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.biography);
        dest.writeString(this.birthday);
        dest.writeString(this.deathday);
        dest.writeString(this.placeOfBirth);
        dest.writeValue(this.popularity);
        dest.writeString(this.bigImage);
        dest.writeString(this.smallImage);
    }

    protected Person(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.biography = in.readString();
        this.birthday = in.readString();
        this.deathday = in.readString();
        this.placeOfBirth = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.bigImage = in.readString();
        this.smallImage = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}