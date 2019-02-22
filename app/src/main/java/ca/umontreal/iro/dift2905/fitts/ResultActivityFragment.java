package ca.umontreal.iro.dift2905.fitts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ca.umontreal.iro.dift2905.fitts.trial.LinearRegression;

public class ResultActivityFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        LinearRegression reg = new LinearRegression();
        ((TextView) view.findViewById(R.id.intercept)).setText(reg.getIntercept());
        ((TextView) view.findViewById(R.id.slope)).setText(reg.getSlope());
        ((TextView) view.findViewById(R.id.coefficient)).setText(reg.getCoefficient());

        return view;
    }
}
