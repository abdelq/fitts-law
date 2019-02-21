package ca.umontreal.iro.dift2905.fitts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static java.lang.String.format;

public class ResultActivityFragment extends Fragment {

    @Override
    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // TODO
        ((TextView) view.findViewById(R.id.intercept)).setText(format("a = %f", 6.9));
        ((TextView) view.findViewById(R.id.slope)).setText(format("b = %f", 6.9));
        ((TextView) view.findViewById(R.id.coefficient)).setText(format("R² = %f", 6.9));

        return view;
    }
}
