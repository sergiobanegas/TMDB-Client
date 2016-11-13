package es.upm.miw.tmdb.client.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.upm.miw.tmdb.client.R;

import static es.upm.miw.tmdb.client.models.EntityContract.movieTable;

public class MovieAdapter extends CursorAdapter {


    public MovieAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView tvImage = (ImageView) view.findViewById(R.id.image);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvPopularity = (TextView) view.findViewById(R.id.popularity);
        TextView tvReleaseDate = (TextView) view.findViewById(R.id.releaseDate);
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(movieTable.COL_TITLE)));
        tvPopularity.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(movieTable.COL_POPULARITY))));
        String imageLink = cursor.getString(cursor.getColumnIndex(movieTable.COL_POSTERIMAGE));
        Picasso.with(context).load(imageLink).into(tvImage);
        tvReleaseDate.setText(cursor.getString(cursor.getColumnIndex(movieTable.COL_RELEASEDATE)));
    }

}