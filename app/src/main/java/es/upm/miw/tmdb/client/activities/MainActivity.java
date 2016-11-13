package es.upm.miw.tmdb.client.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.models.EntityContract;
import es.upm.miw.tmdb.client.models.Movie;

import static es.upm.miw.tmdb.client.models.EntityContract.movieTable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView appInfo = (TextView) findViewById(R.id.appInfo);
        appInfo.setText(Html.fromHtml(getString(R.string.appInfo)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContentResolver cr = getContentResolver();
        Cursor cursorMovies = cr.query(EntityContract.URI_MOVIES, null, null, null, "CAST(" + movieTable.COL_POPULARITY + " as REAL) DESC LIMIT 1");
        if (cursorMovies != null) {
            TextView tvEmpty = (TextView) findViewById(R.id.empty);
            if (cursorMovies.moveToFirst()) {
                int mostPopularMovieId = cursorMovies.getInt(cursorMovies.getColumnIndex(movieTable.COL_ID));
                String mostPopularMovieTitle = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_TITLE));
                String mostPopularMovieOverview = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_OVERVIEW));
                String mostPopularMovieDirecting = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_DIRECTING));
                String mostPopularMovieWriting = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_WRITING));
                String mostPopularMovieReleaseDate = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_RELEASEDATE));
                Double mostPopularMoviePopularity = cursorMovies.getDouble(cursorMovies.getColumnIndex(movieTable.COL_POPULARITY));
                String mostPopularMoviePosterImage = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_POSTERIMAGE));
                String mostPopularMovieBackdropImage = cursorMovies.getString(cursorMovies.getColumnIndex(movieTable.COL_BACKDROPIMAGE));
                final Movie mostPopularMovie = new Movie(mostPopularMovieId, mostPopularMovieTitle, mostPopularMovieOverview, mostPopularMovieDirecting, mostPopularMovieWriting, mostPopularMovieReleaseDate, mostPopularMoviePopularity, mostPopularMoviePosterImage, mostPopularMovieBackdropImage);
                TextView tvTitle = (TextView) findViewById(R.id.mostPopularMovieTitle);
                tvTitle.setText(mostPopularMovieTitle);
                TextView tvOverview = (TextView) findViewById(R.id.mostPopularMovieOverview);
                if (mostPopularMovieOverview.length() > 99) {
                    String shortOverview = mostPopularMovieOverview.substring(0, 100) + "...";
                    tvOverview.setText(shortOverview);
                } else {
                    tvOverview.setText(mostPopularMovieOverview);
                }
                ImageView ivImage = (ImageView) findViewById(R.id.mostPopularMovieImage);
                Picasso.with(getApplicationContext()).load(mostPopularMoviePosterImage).into(ivImage);
                LinearLayout tvInfo = (LinearLayout) findViewById(R.id.mostPopularMovieInfo);
                tvInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), InfoMovieActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(getString(R.string.MOVIE_KEY), mostPopularMovie);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                tvEmpty.setVisibility(View.INVISIBLE);
            }else{
                findViewById(R.id.mostPopularMovieInfo).setVisibility(View.INVISIBLE);
                tvEmpty.setVisibility(View.VISIBLE);

            }
            cursorMovies.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMovies:
                startActivity(new Intent(this, MoviesActivity.class));
                break;
            case R.id.searchPersons:
                startActivity(new Intent(this, PersonsActivity.class));
                break;
        }
        return true;
    }

}






