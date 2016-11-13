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

import static es.upm.miw.tmdb.client.models.EntityContract.castTable;

public class CastAdapter extends CursorAdapter {


    public CastAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cast_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView tvImage = (ImageView) view.findViewById(R.id.image);
        tvImage.setMaxWidth(100);
        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvCharacter = (TextView) view.findViewById(R.id.character);
        tvName.setText(cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONNAME)));
        tvCharacter.setText(String.valueOf(cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONCHARACTER))));
        String imageLink = cursor.getString(cursor.getColumnIndex(castTable.COL_PERSONSMALLIMAGE));
        Picasso.with(context).load(imageLink).into(tvImage);
    }

}