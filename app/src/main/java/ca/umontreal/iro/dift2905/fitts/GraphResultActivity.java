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
import ca.umontreal.iro.dift2905.fitts.trial.TrialContent;

import static androidx.core.app.NavUtils.navigateUpFromSameTask;
import static ca.umontreal.iro.dift2905.fitts.trial.TrialContent.ITEMS;


/**
 * La librairie utilisée pour faire le graphique est la librarie MPAndroidChart.
 * Pour utilisation, cloner le git: https://github.com/PhilJay/MPAndroidChart.git
 * dans le repo du projet.
 *
 * Pour plus d'information:
 * - Lien vers le git
 * https://github.com/PhilJay/MPAndroidChart
 *
 * - Documentation
 * https://github.com/PhilJay/MPAndroidChart/wiki
 *
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

    @Override
    public void onBackPressed() {
        navigateUpFromSameTask(this);
    }

    public void createGraph(){

        CombinedChart chart = findViewById(R.id.chart);
        CombinedData combinedData = new CombinedData();

        ScatterData scatterData = getScatterData();
        LineData lineData = getLineData(scatterData.getXMax());

        combinedData.setData(scatterData);
        combinedData.setData(lineData);
        setAxis(chart, combinedData);

        chart.setData(combinedData);
        chart.getDescription().setText("");
        chart.getLegend().setEnabled(false);
        chart.invalidate(); // refresh
    }

    private void setAxis(CombinedChart chart, CombinedData data){
        //Set x Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(data.getXMax()+0.25f);
        xAxis.setAxisMinimum(Math.max(data.getXMin()-0.25f, 0));

        //Set y Axis
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(10);
        chart.getAxisLeft().setAxisMinimum(Math.max(data.getYMin()-5f, 0));
    }

    private LineData getLineData(float xmax) {
        List<Entry> entry = new ArrayList<>();

        float origine = (float) ResultActivityFragment.reg.getInterceptValue();
        float pente = (float) ResultActivityFragment.reg.getSlopeValue();

        float extremePoint = origine+(xmax*pente);

        entry.add(new Entry(0, origine));
        entry.add(new Entry(xmax, extremePoint));

        Collections.sort(entry, new EntryXComparator());
        LineDataSet set = new LineDataSet(entry, "");

        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setColor(getColor(R.color.colorAccent));

        return new LineData(set);
    }

    public ScatterData getScatterData(){
        ArrayList<Entry> entry = new ArrayList<>();

        for (TrialContent.TrialItem trial : ITEMS) {
            entry.add(new Entry((float) trial.difficulty, (float) trial.duration));
        }

        Collections.sort(entry, new EntryXComparator());
        ScatterDataSet set = new ScatterDataSet(entry,"");

        set.setColor(getColor(R.color.colorAccent));
        set.setDrawValues(false);

        return new ScatterData(set);
    }
}