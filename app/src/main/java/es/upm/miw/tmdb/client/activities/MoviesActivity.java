package es.upm.miw.tmdb.client.activities;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static es.upm.miw.tmdb.client.models.EntityContract.movieTable;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.adapters.MovieAdapter;
import es.upm.miw.tmdb.client.models.EntityContract;
import es.upm.miw.tmdb.client.models.Movie;

public class MoviesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private MovieAdapter adapter;
    private String cursorFilter;
    private TextView tvEmpty, tvQueryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        tvEmpty = (TextView) findViewById(R.id.empty);
        ListView listView = (ListView) findViewById(R.id.movieList);
        listView.setEmptyView(tvEmpty);
        tvQueryTitle = (TextView) findViewById(R.id.queryTitle);
        getLoaderManager().initLoader(1, null, this);
        adapter = new MovieAdapter(this, null, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                Integer id = cursor.getInt(cursor.getColumnIndex(movieTable.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(movieTable.COL_TITLE));
                String overview = cursor.getString(cursor.getColumnIndex(movieTable.COL_OVERVIEW));
                String directing = cursor.getString(cursor.getColumnIndex(movieTable.COL_DIRECTING));
                String writing = cursor.getString(cursor.getColumnIndex(movieTable.COL_WRITING));
                String releaseDate = cursor.getString(cursor.getColumnIndex(movieTable.COL_RELEASEDATE));
                Double popularity = cursor.getDouble(cursor.getColumnIndex(movieTable.COL_POPULARITY));
                String posterImage = cursor.getString(cursor.getColumnIndex(movieTable.COL_POSTERIMAGE));
                String backdropImage = cursor.getString(cursor.getColumnIndex(movieTable.COL_BACKDROPIMAGE));
                Intent intent = new Intent(getApplicationContext(), InfoMovieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.MOVIE_KEY), new Movie(id, title, overview, directing, writing, releaseDate, popularity, posterImage, backdropImage));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        if (cursorFilter == null) {
            tvQueryTitle.setText(R.string.mostPopularMovies);
            return new CursorLoader(this, EntityContract.URI_MOVIES, EntityContract.MOVIE_COLUMNS, null, null, movieTable.COL_POPULARITY + " DESC LIMIT 20");
        } else {
            tvQueryTitle.setText(R.string.searchResult);
            return new CursorLoader(this, EntityContract.URI_MOVIES, EntityContract.MOVIE_COLUMNS, movieTable.COL_TITLE + " LIKE '%" + cursorFilter + "%'", null, movieTable.COL_POPULARITY + " DESC LIMIT 20");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        adapter.swapCursor(cursor);
        if (adapter.getCount() == 0) {
            findViewById(R.id.columns).setVisibility(View.INVISIBLE);
            if (cursorFilter == null) {
                tvEmpty.setText(R.string.noMovies);
            } else {
                tvEmpty.setText(R.string.noMoviesWithTitle);
            }
        } else {
            findViewById(R.id.columns).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint(R.string.searchMovieHint);
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    cursorFilter = query;
                    refresh();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                cursorFilter = null;
                refresh();
                break;
        }
        return true;
    }

    public void refresh() {
        getLoaderManager().restartLoader(1, null, this);
    }

}
