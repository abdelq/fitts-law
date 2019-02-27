package ca.umontreal.iro.dift2905.fitts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ca.umontreal.iro.dift2905.fitts.trial.TrialContent;

import static androidx.core.app.NavUtils.navigateUpFromSameTask;

/**
 * La classe ResultActivity fournit des méthodes pour l'activité qui
 * permet d'exporter les résultats ou de consulter le graphique qui
 * présente les résultats.
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        navigateUpFromSameTask(this);
    }

    /*
     * Exporter les résultats
     *
     * @param v
     */
    public void export(View v) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        // XXX Text or File?
        sendIntent.setType("text/csv");
        sendIntent.putExtra(Intent.EXTRA_TEXT, TrialContent.toCSV());

        startActivity(sendIntent);
    }

    /*
     * Consulter le graphique
     *
     * @param v
     */
    public void graphResult(View v){
        startActivity(new Intent(this, GraphResultActivity.class));
        return;

    }
}
