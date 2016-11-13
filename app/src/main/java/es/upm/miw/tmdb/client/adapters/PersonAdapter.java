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

import static es.upm.miw.tmdb.client.models.EntityContract.personTable;

public class PersonAdapter extends CursorAdapter {


    public PersonAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.person_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView tvImage = (ImageView) view.findViewById(R.id.image);
        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvPopularity = (TextView) view.findViewById(R.id.popularity);
        tvName.setText(cursor.getString(cursor.getColumnIndex(personTable.COL_NAME)));
        tvPopularity.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(personTable.COL_POPULARITY))));
        String imageLink = cursor.getString(cursor.getColumnIndex(personTable.COL_SMALLIMAGE));
        Picasso.with(context).load(imageLink).into(tvImage);
    }

}