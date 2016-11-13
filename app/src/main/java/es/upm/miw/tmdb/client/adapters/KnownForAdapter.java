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

import static es.upm.miw.tmdb.client.models.EntityContract.knownForTable;

public class KnownForAdapter extends CursorAdapter {


    public KnownForAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.known_for_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView tvImage = (ImageView) view.findViewById(R.id.image);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIETITLE)));
        TextView tvPopularity = (TextView) view.findViewById(R.id.popularity);
        tvPopularity.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(knownForTable.COL_MOVIEPOPULARITY))));
        String imageLink = cursor.getString(cursor.getColumnIndex(knownForTable.COL_MOVIEPOSTERIMAGE));
        Picasso.with(context).load(imageLink).into(tvImage);
    }

}