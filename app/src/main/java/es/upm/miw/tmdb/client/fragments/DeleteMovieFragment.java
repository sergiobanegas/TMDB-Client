package es.upm.miw.tmdb.client.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.activities.InfoMovieActivity;

public class DeleteMovieFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final InfoMovieActivity infoMovie = (InfoMovieActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(infoMovie);
        builder
                .setTitle(R.string.deleteMovie)
                .setMessage(R.string.txtDeleteMovieQuestion)
                .setPositiveButton(
                        getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                infoMovie.delete();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
        return builder.create();
    }
}