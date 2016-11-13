package es.upm.miw.tmdb.client.activities;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.adapters.PersonAdapter;
import es.upm.miw.tmdb.client.models.EntityContract;
import es.upm.miw.tmdb.client.models.Person;

import static es.upm.miw.tmdb.client.models.EntityContract.personTable;

public class PersonsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private PersonAdapter adapter;
    private String cursorFilter;
    private TextView tvEmpty, tvQueryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);
        tvQueryTitle = (TextView) findViewById(R.id.queryTitle);
        tvEmpty = (TextView) findViewById(R.id.empty);
        ListView listView = (ListView) findViewById(R.id.personList);
        adapter = new PersonAdapter(this, null, 0);
        getLoaderManager().initLoader(1, null, this);
        listView.setAdapter(adapter);
        listView.setEmptyView(tvEmpty);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                Integer id = cursor.getInt(cursor.getColumnIndex(personTable.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(personTable.COL_NAME));
                String biography = cursor.getString(cursor.getColumnIndex(personTable.COL_BIOGRAPHY));
                String birthday = cursor.getString(cursor.getColumnIndex(personTable.COL_BIRTHDAY));
                String deathday = cursor.getString(cursor.getColumnIndex(personTable.COL_DEATHDAY));
                String placeOfBirth = cursor.getString(cursor.getColumnIndex(personTable.COL_PLACEOFBIRTH));
                Double popularity = cursor.getDouble(cursor.getColumnIndex(personTable.COL_POPULARITY));
                String bigImage = cursor.getString(cursor.getColumnIndex(personTable.COL_BIGIMAGE));
                String smallImage = cursor.getString(cursor.getColumnIndex(personTable.COL_SMALLIMAGE));
                Intent intent = new Intent(getApplicationContext(), InfoPersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.PERSON_KEY), new Person(id, name, biography, birthday, deathday, placeOfBirth, popularity, bigImage, smallImage));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        if (cursorFilter == null) {
            tvQueryTitle.setText(R.string.mostPopularPersons);
            return new CursorLoader(this, EntityContract.URI_PERSON, EntityContract.PERSON_COLUMNS, null, null, "CAST(" + personTable.COL_POPULARITY + " as REAL) DESC LIMIT 20");
        } else {
            tvQueryTitle.setText(R.string.searchResult);
            return new CursorLoader(this, EntityContract.URI_PERSON, EntityContract.PERSON_COLUMNS, personTable.COL_NAME + " LIKE '%" + cursorFilter + "%'", null, "CAST(" + personTable.COL_POPULARITY + " as REAL) DESC LIMIT 20");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        adapter.swapCursor(cursor);
        if (adapter.getCount() == 0) {
            findViewById(R.id.columns).setVisibility(View.INVISIBLE);
            if (cursorFilter == null) {
                tvEmpty.setText(R.string.noPersons);
            } else {
                tvEmpty.setText(R.string.noPersonsWithName);
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
            searchPlate.setHint(R.string.searchPersonHint);
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
