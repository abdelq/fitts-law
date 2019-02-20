package ca.umontreal.iro.dift2905.fitts;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    private int layoutWidth, layoutHeight;
    private int minButtonSize, maxButtonSize;

    private long lastTime;
    private float lastPosX, lastPosY;

    private class Trial {
        private double difficulty;
        private long duration;

        private Trial(long currTime, float currPosX, float currPosY) {
            duration = currTime - lastTime;
            difficulty = calcDifficulty(lastPosX, lastPosY, currPosX, currPosY);
        }

        private double calcDifficulty(float x1, float y1, float x2, float y2) {
            double dist = Math.sqrt(Math.pow(x2 - x1, 2) - Math.pow(y2 - y1, 2));
            double dim = findViewById(R.id.trial_button).getWidth();

            return Math.log(dist/dim + 1) / Math.log(2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_button).setOnTouchListener(startButtonListener);
        findViewById(R.id.trial_button).setOnTouchListener(trialButtonListener);

        Resources res = getResources();
        float smallestScreenSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                res.getConfiguration().smallestScreenWidthDp,
                res.getDisplayMetrics()
        );

        minButtonSize = (int) (smallestScreenSize / res.getInteger(R.integer.min_ratio));
        maxButtonSize = (int) (smallestScreenSize / res.getInteger(R.integer.max_ratio));
    }

    View.OnTouchListener startButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                layoutWidth = v.getWidth();
                layoutHeight = v.getHeight();
            } else if (event.getAction() == MotionEvent.ACTION_UP) { // XXX Order of operations
                findViewById(R.id.start_button).setVisibility(View.GONE);
                findViewById(R.id.trial_button).setVisibility(View.VISIBLE);

                // XXX Refactor
                lastTime = currentTimeMillis();
                lastPosX = event.getX();
                lastPosY = event.getY();

                newTrial();

                v.performClick(); // XXX
            }

            return true; // XXX
        }
    };

    View.OnTouchListener trialButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                new Trial(currentTimeMillis(), event.getX(), event.getY()); // TODO
            } else if (event.getAction() == MotionEvent.ACTION_UP) { // XXX Refactor
                lastTime = currentTimeMillis();
                lastPosX = event.getX();
                lastPosY = event.getY();

                newTrial();

                v.performClick(); // XXX
            }

            return true; // XXX
        }
    };

    private void newTrial() {
        // TODO Gestion de la fin

        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int buttonSize = rand.nextInt(minButtonSize, maxButtonSize + 1);
        float buttonPosX = rand.nextFloat() * (layoutWidth - buttonSize);
        float buttonPosY = rand.nextFloat() * (layoutHeight - buttonSize);

        Button button = findViewById(R.id.trial_button);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(buttonSize, buttonSize));
        button.setX(buttonPosX);
        button.setY(buttonPosY);
    }
}
