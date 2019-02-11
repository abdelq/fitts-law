package ca.umontreal.iro.dift2905.fitts;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Chronometer chrono;
    private Button startButton, button;
    private ConstraintLayout myLayout;

    private int screenWidth, screenHeight;
    private int smallerDimension;

    private Random rand = new Random();

    private int count=-1;
    private int maxTry;
    private float[] timeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLayout = findViewById(R.id.myLayout);
        chrono = new Chronometer(this);

        maxTry = 20;
        timeResult = new float[maxTry];

        startButton = findViewById(R.id.startButton);
        startButton.setOnTouchListener(startButtonOnTouchListener);
    }

    //TODO: Lier la fonction reset au bouton reset de la dernière page
    private void reset() {
        //TODO: faire disparaitre ce qui est dans la dernière page
        count = -1;
        startButton.setVisibility(View.VISIBLE);
    }

    View.OnTouchListener startButtonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){
                getScreenDimensions();
                startButton.setVisibility(View.GONE);
                button = new Button(MainActivity.this);

                button.setOnTouchListener(mainButtonOnTouchListener);
                button.setBackgroundColor(getResources().getColor(R.color.purple));
                myLayout.addView(button);

                onNextButton((int) event.getX(), (int) event.getY());
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
            }
            return false;
        }
    };


    View.OnTouchListener mainButtonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    chrono.stop();
                    timeResult[count] = SystemClock.elapsedRealtime()-chrono.getBase();
                    break;
                case MotionEvent.ACTION_UP:
                    onNextButton((int) event.getX(), (int) event.getY());

                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.start();
            }
            return false;
        }
    };

    public void getScreenDimensions(){
        screenHeight = myLayout.getHeight();
        screenWidth = myLayout.getWidth();

        if(screenHeight > screenWidth)
            smallerDimension = screenWidth;
        else
            smallerDimension = screenHeight;
    }

    public int adaptDimension(int position, int buttonDimension, int screenDimension){
        int diff = screenDimension-(position+buttonDimension);

        if(diff > 0) {
            if (position < 0)
                return position * -1;
            return 0;
        }
        else
            return diff;
    }


    public void onNextButton(int x, int y){
        if(++count >= maxTry){
            button.setVisibility(View.GONE);
            //TODO: Afficher liste de résultat
        }

        //FIXME: Fonctionne si je met *0.25+0.25. Mais dans notre cas on veut *0.46+0.04, mais parfois on obtient un rectangle avec ses valeurs... Est-ce qu'il faudrait mettre un mini délai?
        int buttonDimension = (int) Math.floor(smallerDimension*(rand.nextDouble()*0.46+0.04));

        button.setWidth(buttonDimension);
        button.setHeight(buttonDimension);

        int touchX = (int) button.getX()+x;
        int touchY = (int) button.getY()+y;

        button.setX(touchX+adaptDimension(touchX, buttonDimension, screenWidth));
        button.setY(touchY+adaptDimension(touchY, buttonDimension, screenHeight));
    }
}
