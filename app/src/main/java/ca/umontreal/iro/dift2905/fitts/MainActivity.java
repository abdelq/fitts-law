package ca.umontreal.iro.dift2905.fitts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import ca.umontreal.iro.dift2905.fitts.trial.TrialContent;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    private int numTrials;
    private long lastTime;
    private float lastPosX, lastPosY;

    private int maxTrials;
    private int minButtonSize, maxButtonSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        float smallestScreenSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                res.getConfiguration().smallestScreenWidthDp,
                res.getDisplayMetrics()
        );

        maxTrials = res.getInteger(R.integer.max_trials);
        minButtonSize = (int) (smallestScreenSize / res.getInteger(R.integer.min_ratio));
        maxButtonSize = (int) (smallestScreenSize / res.getInteger(R.integer.max_ratio));

        TrialContent.clear();
        findViewById(R.id.trial_button).setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));

        findViewById(R.id.start_button).setOnTouchListener(startButtonListener);
        findViewById(R.id.trial_button).setOnTouchListener(trialButtonListener);
    }

    OnTouchListener startButtonListener = new OnTouchListener() {
        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lastTime = currentTimeMillis();
                lastPosX = event.getX();
                lastPosY = event.getY();

                newTrial();

                findViewById(R.id.start_button).setVisibility(View.GONE);
                findViewById(R.id.trial_button).setVisibility(View.VISIBLE);
            }

            return false;
        }
    };

    OnTouchListener trialButtonListener = new OnTouchListener() {
        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                TrialContent.add(
                        currentTimeMillis() - lastTime,
                        event.getX() - lastPosX, event.getY() - lastPosY,
                        v.getWidth()
                );
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                lastTime = currentTimeMillis();
                lastPosX = event.getX();
                lastPosY = event.getY();

                newTrial();
            }

            return false;
        }
    };

    private void newTrial() {
        if (++numTrials > maxTrials) {
            startActivity(new Intent(this, ResultActivity.class));
            return;
        }

        ThreadLocalRandom rand = ThreadLocalRandom.current(); // XXX
        int buttonSize = rand.nextInt(minButtonSize, maxButtonSize + 1);

        View layout = findViewById(android.R.id.content);
        float buttonPosX = rand.nextFloat() * (layout.getWidth() - buttonSize);
        float buttonPosY = rand.nextFloat() * (layout.getHeight() - buttonSize);

        View button = findViewById(R.id.trial_button);
        setPosition(button, buttonPosX, buttonPosY);
        setDimensions(button, buttonSize, buttonSize);
    }

    private void setPosition(View view, float x, float y) {
        view.setX(x);
        view.setY(y);
    }

    private void setDimensions(View view, int width, int height) {
        LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
}
