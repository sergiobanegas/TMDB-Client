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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.upm.miw.tmdb.client.NonScrollListView;
import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.adapters.KnownForAdapter;

import es.upm.miw.tmdb.client.fragments.DeletePersonFragment;
import es.upm.miw.tmdb.client.fragments.EditPersonBiographyFragment;
import es.upm.miw.tmdb.client.models.EntityContract;
import es.upm.miw.tmdb.client.models.Movie;
import es.upm.miw.tmdb.client.models.Person;

import static es.upm.miw.tmdb.client.models.EntityContract.knownForTable;
import static es.upm.miw.tmdb.client.models.EntityContract.personTable;
import static es.upm.miw.tmdb.client.models.EntityContract.castTable;

public class InfoPersonActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private KnownForAdapter adapter;
    private Person person;
    private TextView tvBiography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_person);
        person = getIntent().getExtras().getParcelable(getString(R.string.PERSON_KEY));
        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText(person.getName());
        tvBiography = (TextView) findViewById(R.id.biography);
        tvBiography.setText(person.getBiography());
        TextView tvBirthday = (TextView) findViewById(R.id.birthday);
        tvBirthday.setText(person.getBirthday());
        TextView tvDeathday = (TextView) findViewById(R.id.deathday);
        tvDeathday.setText(person.getDeathday());
        TextView tvPlaceOfBirth = (TextView) findViewById(R.id.placeOfBirth);
        tvPlaceOfBirth.setText(person.getPlaceOfBirth());
        TextView tvPopularity = (TextView) findViewById(R.id.popularity);
        tvPopularity.setText(String.valueOf(person.getPopularity()));
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(person.getBigImage()).into(imageView);
        NonScrollListView lvKnownFor = (NonScrollListView) findViewById(R.id.knownForList);
        adapter = new KnownForAdapter(this, null, 0);
        getLoaderManager().initLoader(1, null, this);
        lvKnownFor.setAdapter(adapter);
        lvKnownFor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                Integer id = cursor.getInt(cursor.getColumnIndex(knownForTable.COL_MOVIEID));
                String title = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIETITLE));
                String overview = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIEOVERVIEW));
                String directing = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIEDIRECTING));
                String writing = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIEWRITING));
                String releaseDate = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIERELEASEDATE));
                Double popularity = cursor.getDouble(cursor.getColumnIndex(knownForTable.COL_MOVIEPOPULARITY));
                String posterImage = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIEPOSTERIMAGE));
                String backdropImage = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIEBACKDROPIMAGE));
                ContentResolver cr = getContentResolver();
                Cursor cursorMovie = cr.query(EntityContract.URI_MOVIES, null, knownForTable.COL_ID + "=" + id, null, null);
                if (cursorMovie == null || cursorMovie.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.movieIsNotSaved), Toast.LENGTH_LONG).show();
                } else {
                    cursorMovie.close();
                    Intent intent = new Intent(getApplicationContext(), InfoMovieActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(getString(R.string.MOVIE_KEY), new Movie(id, title, overview, directing, writing, releaseDate, popularity, posterImage, backdropImage));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.person_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deletePerson:
                new DeletePersonFragment().show(getFragmentManager(), "DELETE PERSON DIALOG");
                break;
            case R.id.editPersonBiography:
                new EditPersonBiographyFragment().show(getFragmentManager(), "EDIT PERSON BIOGRAPHY DIALOG");
                break;
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(this, EntityContract.URI_KNOWNFOR, EntityContract.KNOWNFOR_COLUMNS, knownForTable.COL_PERSONID + "=" + person.getId(), null, knownForTable.COL_PERSONPOPULARITY + " DESC");
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
        String[] args = new String[]{String.valueOf(person.getId())};
        cr.delete(EntityContract.URI_KNOWNFOR, knownForTable.COL_PERSONID + "=", args);
        cr.delete(EntityContract.URI_PERSON, knownForTable.COL_ID + "=", args);
        Toast.makeText(this, R.string.personDeleted, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void editPersonBiography(String biography) {
        ContentResolver cr = getContentResolver();
        String[] args = new String[]{String.valueOf(person.getId())};
        ContentValues valuesPerson = new ContentValues();
        valuesPerson.put(personTable.COL_ID, person.getId());
        valuesPerson.put(personTable.COL_NAME, person.getName());
        valuesPerson.put(personTable.COL_BIOGRAPHY, biography);
        valuesPerson.put(personTable.COL_BIRTHDAY, person.getBirthday());
        valuesPerson.put(personTable.COL_DEATHDAY, person.getDeathday());
        valuesPerson.put(personTable.COL_PLACEOFBIRTH, person.getPlaceOfBirth());
        valuesPerson.put(personTable.COL_POPULARITY, person.getPopularity());
        valuesPerson.put(personTable.COL_BIGIMAGE, person.getBigImage());
        valuesPerson.put(personTable.COL_SMALLIMAGE, person.getSmallImage());
        cr.update(EntityContract.URI_PERSON, valuesPerson, personTable.COL_ID + "=", args);
        Cursor cursorCast = cr.query(EntityContract.URI_CAST, null, castTable.COL_PERSONID + "=" + person.getId(), null, null);
        if (cursorCast != null) {
            ContentValues valuesCast;
            if (cursorCast.moveToFirst()) {
                do {
                    valuesCast = new ContentValues();
                    valuesCast.put(castTable.COL_MOVIEID, cursorCast.getString(cursorCast.getColumnIndex(castTable.COL_MOVIEID)));
                    valuesCast.put(castTable.COL_PERSONID, person.getId());
                    valuesCast.put(castTable.COL_PERSONNAME, person.getName());
                    valuesCast.put(castTable.COL_PERSONCHARACTER, cursorCast.getString(cursorCast.getColumnIndex(castTable.COL_PERSONCHARACTER)));
                    valuesCast.put(castTable.COL_PERSONBIOGRAPHY, person.getBiography());
                    valuesCast.put(castTable.COL_PERSONBIRTHDAY, person.getBirthday());
                    valuesCast.put(castTable.COL_PERSONDEATHDAY, person.getDeathday());
                    valuesCast.put(castTable.COL_PERSONPLACEOFBIRTH, person.getPlaceOfBirth());
                    valuesCast.put(castTable.COL_PERSONPOPULARITY, person.getPopularity());
                    valuesCast.put(castTable.COL_PERSONSMALLIMAGE, person.getSmallImage());
                    valuesCast.put(castTable.COL_PERSONBIGIMAGE, person.getBigImage());
                    cr.update(EntityContract.URI_CAST, valuesCast, castTable.COL_PERSONID + "=", args);
                } while (cursorCast.moveToNext());
            }
            cursorCast.close();
        }
        tvBiography.setText(biography);
        Toast.makeText(this, R.string.personBiographyUdpdated, Toast.LENGTH_SHORT).show();
    }

}
