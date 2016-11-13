package es.upm.miw.tmdb.client.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.activities.InfoPersonActivity;

public class DeletePersonFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final InfoPersonActivity infoPerson = (InfoPersonActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(infoPerson);
        builder
                .setTitle(R.string.deletePerson)
                .setMessage(R.string.txDeletePersonQuestion)
                .setPositiveButton(
                        getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                infoPerson.delete();
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