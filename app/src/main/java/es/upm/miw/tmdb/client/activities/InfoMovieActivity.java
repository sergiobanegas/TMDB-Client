package es.upm.miw.tmdb.client.activities;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.adapters.CastAdapter;
import es.upm.miw.tmdb.client.fragments.DeleteMovieFragment;
import es.upm.miw.tmdb.client.fragments.EditMovieOverviewFragment;
import es.upm.miw.tmdb.client.models.EntityContract;
import es.upm.miw.tmdb.client.models.Movie;
import es.upm.miw.tmdb.client.models.Person;

import static es.upm.miw.tmdb.client.models.EntityContract.movieTable;
import static es.upm.miw.tmdb.client.models.EntityContract.castTable;
import static es.upm.miw.tmdb.client.models.EntityContract.knownForTable;

public class InfoMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CastAdapter adapter;
    private Movie movie;
    private TextView tvOverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_movie);
        movie = getIntent().getExtras().getParcelable(getString(R.string.MOVIE_KEY));
        tvOverView = (TextView) findViewById(R.id.overview);
        tvOverView.setText(movie.getOverview());
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(movie.getTitle());
        TextView tvReleaseDate = (TextView) findViewById(R.id.releaseDate);
        tvReleaseDate.setText(movie.getReleaseDate());
        TextView tvPopularity = (TextView) findViewById(R.id.popularity);
        tvPopularity.setText(String.valueOf(movie.getPopularity()));
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(movie.getBackdropImage()).into(imageView);
        TextView tvDirecting = (TextView) findViewById(R.id.directing);
        TextView tvWriting = (TextView) findViewById(R.id.writing);
        tvDirecting.setText(movie.getDirecting());
        tvWriting.setText(movie.getWriting());
        ListView lvCast = (ListView) findViewById(R.id.castList);
        adapter = new CastAdapter(this, null, 0);
        lvCast.setAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);
        lvCast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                Integer id = cursor.getInt(cursor.getColumnIndex(castTable.COL_PERSONID));
                String name = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONNAME));
                String biography = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONBIOGRAPHY));
                String birthday = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONBIRTHDAY));
                String deathday = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONDEATHDAY));
                String placeOfBirth = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONPLACEOFBIRTH));
                Double popularity = cursor.getDouble(cursor.getColumnIndex(castTable.COL_PERSONPOPULARITY));
                String bigImage = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONBIGIMAGE));
                String smallImage = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONSMALLIMAGE));
                ContentResolver cr = getContentResolver();
                Cursor cursorPerson = cr.query(EntityContract.URI_PERSON, null, castTable.COL_ID + "=" + id, null, null);
                if (cursorPerson == null || cursorPerson.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.saveIsNotSaved), Toast.LENGTH_LONG).show();
                } else {
                    cursorPerson.close();
                    Intent intent = new Intent(getApplicationContext(), InfoPersonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(getString(R.string.PERSON_KEY), new Person(id, name, biography, birthday, deathday, placeOfBirth, popularity, bigImage, smallImage));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteMovie:
                new DeleteMovieFragment().show(getFragmentManager(), "DELETE MOVIE DIALOG");
                break;
            case R.id.editMovieOverView:
                new EditMovieOverviewFragment().show(getFragmentManager(), "EDIT MOVIE OVERVIEW DIALOG");
                break;
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(this, EntityContract.URI_CAST, EntityContract.CAST_COLUMNS, castTable.COL_MOVIEID + "=" + movie.getId() + "", null, "CAST(" + castTable.COL_PERSONPOPULARITY + " as REAL) DESC LIMIT 10");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void delete() {
        ContentResolver cr = getContentResolver();
        String[] args = new String[]{String.valueOf(movie.getId())};
        cr.delete(EntityContract.URI_CAST, castTable.COL_MOVIEID + "=", args);
        cr.delete(EntityContract.URI_MOVIES, castTable.COL_ID + "=", args);
        Toast.makeText(this, R.string.movieDeleted, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void editMovieOverview(String overview) {
        ContentResolver cr = getContentResolver();
        String[] args = new String[]{String.valueOf(movie.getId())};
        ContentValues valuesMovie = new ContentValues();
        valuesMovie.put(movieTable.COL_ID, movie.getId());
        valuesMovie.put(movieTable.COL_TITLE, movie.getTitle());
        valuesMovie.put(movieTable.COL_OVERVIEW, overview);
        valuesMovie.put(movieTable.COL_DIRECTING, movie.getDirecting());
        valuesMovie.put(movieTable.COL_WRITING, movie.getWriting());
        valuesMovie.put(movieTable.COL_RELEASEDATE, movie.getReleaseDate());
        valuesMovie.put(movieTable.COL_POPULARITY, movie.getPopularity());
        valuesMovie.put(movieTable.COL_POSTERIMAGE, movie.getPosterImage());
        valuesMovie.put(movieTable.COL_BACKDROPIMAGE, movie.getBackdropImage());
        cr.update(EntityContract.URI_MOVIES, valuesMovie, movieTable.COL_ID + "=", args);
        Cursor cursorKnownFor = cr.query(EntityContract.URI_KNOWNFOR, null, castTable.COL_MOVIEID + "=" + movie.getId(), null, null);
        if (cursorKnownFor != null) {
            ContentValues valuesCast;
            if (cursorKnownFor.moveToFirst()) {
                do {
                    valuesCast = new ContentValues();
                    valuesCast.put(knownForTable.COL_PERSONID, cursorKnownFor.getString(cursorKnownFor.getColumnIndex(knownForTable.COL_PERSONID)));
                    valuesCast.put(knownForTable.COL_PERSONPOPULARITY, cursorKnownFor.getString(cursorKnownFor.getColumnIndex(knownForTable.COL_PERSONPOPULARITY)));
                    valuesCast.put(knownForTable.COL_MOVIEID, movie.getId());
                    valuesCast.put(knownForTable.COL_MOVIETITLE, movie.getTitle());
                    valuesCast.put(knownForTable.COL_MOVIEOVERVIEW, movie.getOverview());
                    valuesCast.put(knownForTable.COL_MOVIEDIRECTING, movie.getDirecting());
                    valuesCast.put(knownForTable.COL_MOVIEWRITING, movie.getWriting());
                    valuesCast.put(knownForTable.COL_MOVIERELEASEDATE, movie.getReleaseDate());
                    valuesCast.put(knownForTable.COL_MOVIEPOPULARITY, movie.getPopularity());
                    valuesCast.put(knownForTable.COL_MOVIEPOSTERIMAGE, movie.getPosterImage());
                    valuesCast.put(knownForTable.COL_MOVIEBACKDROPIMAGE, movie.getBackdropImage());
                    cr.update(EntityContract.URI_KNOWNFOR, valuesCast, knownForTable.COL_MOVIEID + "=", args);
                } while (cursorKnownFor.moveToNext());
            }
            cursorKnownFor.close();
        }
        tvOverView.setText(overview);
        Toast.makeText(this, R.string.movieOverviewUpdated, Toast.LENGTH_SHORT).show();
    }
}
