package ca.umontreal.iro.dift2905.fitts;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ca.umontreal.iro.dift2905.fitts.dummy.DummyContent;

import android.view.View;

public class ResultActivity extends AppCompatActivity implements TrialFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        // FIXME
    }
}
