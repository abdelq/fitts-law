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

    // valeurs de la position (x, y) du bouton
    private double random_x, random_y;
    
    // valeurs du positionnement du clique courrant et précédent
    private double current_x, current_y, past_x, past_y;
    
    // largeur du bouton
    private int buttonDimension;

    private int count= -1;
    private int maxTry = 20;
    
    // tableau des temps
    private float[] timeResult;
    // tableau des indices de difficulté
    private double[] difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLayout = findViewById(R.id.myLayout);
        chrono = new Chronometer(this);

        difficulty = new double[maxtry];
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
                
                past_x = event.getX();
                past_y = event.getY();

                changeButton();
                
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
                    break;
                case MotionEvent.ACTION_UP:
                    if (++count < maxTry) {
                        timeResult[count] = SystemClock.elapsedRealtime() - 
                            chrono.getBase();
                        
                        current_x = event.getX();
                        current_y = event.getY();
                        difficulty[count] = getDifficulty(past_x, past_y,
                                                          current_x, current_y);
                        past_x = current_x;
                        past_y = current_y;
                        
                        changeButton();
                        chrono.setBase(SystemClock.elapsedRealtime());
                        chrono.start();
                    } else {
                        button.setVisibility(View.GONE);
                    }
            }
            return false;
        }
    };

    public void getScreenDimensions(){
        screenHeight = myLayout.getHeight();
        screenWidth = myLayout.getWidth();

        if(screenHeight < screenWidth) {
            int temp = screenHeight;
            screenHeight = screenWidth;
            screenWidth = temp;
        }
        // Je dois utiliser ces données car sinon ce n'est
        // pas les bonne valeurs trouvées par la fonction
        // à corriger
        screenWidth = 915;
        screenHeight = 1420;    
    }
    
    // fonction qui s'occupe de changer la taille et la position du bouton
    public void changeButton() {
        Random rand = new Random();
        
        buttonDimension = (int) (screenWidth * (rand.nextDouble()*0.5 + 0.04));
        
        //je n'ai pas été capable de changer le fait
        //que cela donne des rectangles
        // à corriger
        button.setWidth(buttonDimension);
        button.setHeight(buttonDimension);
        
        random_x = Math.random() * (screenWidth - buttonDimension);
        random_y = Math.random() * (screenHeight - buttonDimension);
        
        button.setX((float) random_x);
        button.setY((float) random_y);
    }
    
    // fonction qui détermine l'indice de difficulté
    public double getDifficulty(double x1, double y1,
                                double x2, double y2) {
        double D = Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
        double S = buttonDimension;
        
        return (Math.log(D/s + 1)/Math.log(2));
    }
    
}
