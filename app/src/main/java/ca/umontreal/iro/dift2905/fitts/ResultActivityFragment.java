package ca.umontreal.iro.dift2905.fitts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ca.umontreal.iro.dift2905.fitts.trial.LinearRegression;

/**
 * La classe ResultActivityFragment s'occupe d'afficher
 * les valeurs de l'intersection avec l'ordonnée, la pente,
 * et le coéficient de corrélation linéaire.
 */
public class ResultActivityFragment extends Fragment {

    public static LinearRegression reg;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        reg = new LinearRegression();
        ((TextView) view.findViewById(R.id.intercept)).setText(reg.getIntercept());
        ((TextView) view.findViewById(R.id.slope)).setText(reg.getSlope());
        ((TextView) view.findViewById(R.id.coefficient)).setText(reg.getCoefficient());

        return view;
    }
}
