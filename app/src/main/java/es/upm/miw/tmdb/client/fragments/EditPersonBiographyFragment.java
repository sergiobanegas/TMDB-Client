package es.upm.miw.tmdb.client.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.upm.miw.tmdb.client.R;
import es.upm.miw.tmdb.client.activities.InfoPersonActivity;

public class EditPersonBiographyFragment extends DialogFragment {

    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.edit_biography, container, false);
        Button button = (Button) v.findViewById(R.id.editButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMovieOverview();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        assert window != null;
        window.setLayout(1000, 1000);
        window.setGravity(Gravity.CENTER);
    }

    public void editMovieOverview() {
        EditText biographyForm = (EditText) v.findViewById(R.id.biographyForm);
        String biography = biographyForm.getText().toString();
        if (!biography.equals("")) {
            ((InfoPersonActivity) getActivity()).editPersonBiography(biography);
            biographyForm.setText("");
        } else {
            Toast.makeText(getActivity(), R.string.voidBiographyError, Toast.LENGTH_LONG).show();
        }
    }

}