package es.upm.miw.tmdb.client.models;

import android.net.Uri;
import android.provider.BaseColumns;

public class EntityContract {

    public static final Uri URI_MOVIES = Uri.parse("content://es.upm.miw.tmdb.manager.services.movies.movie.provider/movie");
    public static final Uri URI_PERSON = Uri.parse("content://es.upm.miw.tmdb.manager.services.persons.provider/person");
    public static final Uri URI_CAST = Uri.parse("content://es.upm.miw.tmdb.manager.services.movies.cast.provider/cast");
    public static final Uri URI_KNOWNFOR = Uri.parse("content://es.upm.miw.tmdb.manager.services.persons.knownfor.provider/knownfor");
    public static final String[] MOVIE_COLUMNS = new String[]{movieTable.COL_ID, movieTable.COL_TITLE, movieTable.COL_OVERVIEW, movieTable.COL_DIRECTING, movieTable.COL_WRITING, movieTable.COL_RELEASEDATE, movieTable.COL_POPULARITY, movieTable.COL_POSTERIMAGE, movieTable.COL_BACKDROPIMAGE};
    public static final String[] PERSON_COLUMNS = new String[]{personTable.COL_ID, personTable.COL_NAME, personTable.COL_BIOGRAPHY, personTable.COL_BIRTHDAY, personTable.COL_DEATHDAY, personTable.COL_PLACEOFBIRTH, personTable.COL_POPULARITY, personTable.COL_BIGIMAGE, personTable.COL_SMALLIMAGE};
    public static final String[] CAST_COLUMNS = new String[]{castTable.COL_ID, castTable.COL_MOVIEID, castTable.COL_PERSONID, castTable.COL_PERSONNAME, castTable.COL_PERSONCHARACTER, castTable.COL_PERSONBIOGRAPHY, castTable.COL_PERSONBIRTHDAY, castTable.COL_PERSONDEATHDAY, castTable.COL_PERSONPLACEOFBIRTH, castTable.COL_PERSONPOPULARITY, castTable.COL_PERSONBIGIMAGE, castTable.COL_PERSONSMALLIMAGE};
    public static final String[] KNOWNFOR_COLUMNS = new String[]{knownForTable.COL_ID, knownForTable.COL_PERSONID, knownForTable.COL_MOVIEID, knownForTable.COL_MOVIETITLE, knownForTable.COL_MOVIEOVERVIEW, knownForTable.COL_MOVIEDIRECTING, knownForTable.COL_MOVIEWRITING, knownForTable.COL_MOVIERELEASEDATE, knownForTable.COL_MOVIEPOPULARITY, knownForTable.COL_MOVIEPOSTERIMAGE, knownForTable.COL_MOVIEBACKDROPIMAGE};

    private EntityContract() {
    }

    public static abstract class movieTable implements BaseColumns {
        public static final String COL_ID = "_id";
        public static final String COL_TITLE = "title";
        public static final String COL_OVERVIEW = "overview";
        public static final String COL_POPULARITY = "popularity";
        public static final String COL_POSTERIMAGE = "poster_image";
        public static final String COL_BACKDROPIMAGE = "backdrop_image";
        public static final String COL_DIRECTING = "directing";
        public static final String COL_WRITING = "writing";
        public static final String COL_RELEASEDATE = "release_date";
    }

    public static abstract class personTable implements BaseColumns {
        public static final String COL_ID = "_id";
        public static final String COL_NAME = "name";
        public static final String COL_BIOGRAPHY = "biography";
        public static final String COL_BIRTHDAY = "birthday";
        public static final String COL_DEATHDAY = "deathday";
        public static final String COL_PLACEOFBIRTH = "place_of_birth";
        public static final String COL_POPULARITY = "popularity";
        public static final String COL_BIGIMAGE = "big_image";
        public static final String COL_SMALLIMAGE = "small_image";
    }

    public static abstract class knownForTable implements BaseColumns {
        public static final String COL_ID = "_id";
        public static final String COL_PERSONID = "person_id";
        public static final String COL_PERSONPOPULARITY = "person_popularity";
        public static final String COL_MOVIEID = "movie_id";
        public static final String COL_MOVIETITLE = "movie_title";
        public static final String COL_MOVIEOVERVIEW = "movie_overview";
        public static final String COL_MOVIEPOPULARITY = "movie_popularity";
        public static final String COL_MOVIEPOSTERIMAGE = "movie_poster_image";
        public static final String COL_MOVIEBACKDROPIMAGE = "movie_backdrop_image";
        public static final String COL_MOVIEDIRECTING = "movie_directing";
        public static final String COL_MOVIEWRITING = "movie_writing";
        public static final String COL_MOVIERELEASEDATE = "movie_release_date";
    }

    public static abstract class castTable implements BaseColumns {
        public static final String COL_ID = "_id";
        public static final String COL_MOVIEID = "movie_id";
        public static final String COL_PERSONID = "person_id";
        public static final String COL_PERSONNAME = "person_name";
        public static final String COL_PERSONCHARACTER = "person_character";
        public static final String COL_PERSONBIOGRAPHY = "person_biography";
        public static final String COL_PERSONBIRTHDAY = "person_birthday";
        public static final String COL_PERSONDEATHDAY = "person_deathday";
        public static final String COL_PERSONPLACEOFBIRTH = "person_place_of_birth";
        public static final String COL_PERSONPOPULARITY = "person_popularity";
        public static final String COL_PERSONBIGIMAGE = "big_image";
        public static final String COL_PERSONSMALLIMAGE = "small_image";
    }
}
