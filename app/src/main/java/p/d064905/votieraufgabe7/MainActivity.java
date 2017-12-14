package p.d064905.votieraufgabe7;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    TextView score;
    int iScore;
    TextView aufgabe;
    TextView min;
    TextView max;
    ProgressBar progressBar;
    Button next;
    int höchsterWert;
    int Ziel;
    final String[] achse = {"X", "Y", "Z"};
    final String[] vorzeichen = {"+", "-"};
    String sAufgabe;
    int zahl;
    int zahl2;
    int iteration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = findViewById(R.id.textViewScore);
        aufgabe = findViewById(R.id.textViewAufgabe);
        min = findViewById(R.id.textViewMinValue);
        max = findViewById(R.id.textViewMaxValue);
        progressBar = findViewById(R.id.progressBar);
        next = findViewById(R.id.buttonNext);
        iScore = 0;
        höchsterWert = 0;
        iteration = 1;
        Ziel = 100;
        setAufgabe();

        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerator = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sm.registerListener(this, accelerator, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int i = (int) (sensorEvent.values[zahl] * 100);
        progressBar.setProgress(i);
        if (zahl2 == 0){
            if (i > höchsterWert) {
                höchsterWert = Math.abs(i);
                progressBar.setSecondaryProgress(höchsterWert);
            }
            if (i > Ziel){
                next.setEnabled(true);
            }
        }else {
            if (i < höchsterWert * (-1)) {
                höchsterWert = Math.abs(i);
                progressBar.setSecondaryProgress(höchsterWert);
            }
            if (i < Ziel * (-1)){
                next.setEnabled(true);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void setAufgabe(){
        zahl = (int)((Math.random()) * 3);
        zahl2 = (int)((Math.random()) * 2);
        sAufgabe = "Aufgabe: Beschleunige dein Telefon mit " + vorzeichen[zahl2] + (Ziel/100) + " m/s*s entlang der " + achse[zahl] + "-Achse!";
        aufgabe.setText(sAufgabe);
        max.setText(vorzeichen[zahl2] + (Ziel/100));
    }

    public void onNext(View v){
        iScore = iScore + Ziel;
        score.setText("Score: " + iScore);
        Ziel = Ziel + 100 * iteration;
        progressBar.setMax(Ziel);
        iteration++;
        next.setEnabled(false);
        höchsterWert = 0;
        progressBar.setSecondaryProgress(höchsterWert);
        setAufgabe();
    }

    public void onInfo(View v){
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
    }
}
