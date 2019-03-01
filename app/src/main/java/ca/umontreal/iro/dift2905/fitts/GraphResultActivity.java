package ca.umontreal.iro.dift2905.fitts;

import android.os.Bundle;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import ca.umontreal.iro.dift2905.fitts.trial.TrialContent.TrialItem;

import static ca.umontreal.iro.dift2905.fitts.ResultActivity.reg;
import static ca.umontreal.iro.dift2905.fitts.trial.TrialContent.ITEMS;

/**
 * La classe GraphResultActivity fournit des méthodes pour l'activité qui
 * affiche un graphique de type nuage de points, sur lequel sont représentés
 * les points de données et de la droite de régression linéaire.
 *
 * La librairie utilisée pour le graphique est MPAndroidChart.
 */
public class GraphResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_result);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        createGraph();
    }

    /*
     * Création du graphique
     */
    public void createGraph() {
        CombinedChart chart = findViewById(R.id.chart);

        ScatterData scatterData = getScatterData();
        LineData lineData = getLineData(scatterData.getXMax());

        CombinedData combinedData = new CombinedData();
        combinedData.setData(scatterData);
        combinedData.setData(lineData);
        setAxis(chart, combinedData);

        chart.setData(combinedData);
        chart.getDescription().setText("");
        chart.getLegend().setEnabled(false);
        chart.invalidate(); // Refresh
    }

    /*
     * Initiation des axes
     *
     * @param chart graphique
     * @param data données du graphique
     */
    private void setAxis(CombinedChart chart, CombinedData data) {
        // Initialiser l'axe des X
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(data.getXMax() + .25f);
        xAxis.setAxisMinimum(Math.max(data.getXMin() - .25f, 0));

        // Initialiser l'axe des Y
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(10);
        chart.getAxisLeft().setAxisMinimum(Math.max(data.getYMin() - 5, 0));
    }

    /*
     * @param xMax plus grande valeur de x
     * @return la droite de régression linéaire
     */
    private LineData getLineData(float xMax) {
        List<Entry> entries = new ArrayList<>();

        float intercept = (float) reg.getIntercept();
        float slope = (float) reg.getSlope();

        entries.add(new Entry(0, intercept));
        entries.add(new Entry(xMax, intercept + slope * xMax));

        LineDataSet set = new LineDataSet(entries, "");
        set.setColor(getColor(R.color.colorAccent));
        set.setDrawValues(false);
        set.setDrawCircles(false);

        return new LineData(set);
    }

    /*
     * @return les points de données
     */
    public ScatterData getScatterData() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (TrialItem trial : ITEMS) {
            entries.add(new Entry((float) trial.difficulty, (float) trial.duration));
        }
        Collections.sort(entries, new EntryXComparator());

        ScatterDataSet set = new ScatterDataSet(entries, "");
        set.setColor(getColor(R.color.colorAccent));
        set.setDrawValues(false);

        return new ScatterData(set);
    }
}
