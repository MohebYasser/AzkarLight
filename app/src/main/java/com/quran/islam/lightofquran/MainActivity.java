package com.quran.islam.lightofquran;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    boolean run=false;
    boolean isKosrsy = false;
    TextView txt;
    Button btnAzkar ;
    Button btnKorsy ;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=(TextView)findViewById(R.id.msg);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.al_kursy);
        btnAzkar =  (Button)findViewById(R.id.button);
        btnKorsy = (Button)findViewById(R.id.button2);

        btnAzkar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying())
                {
                    if(isKosrsy)
                    {
                        isKosrsy=false;
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.azkarmassa);
                    }
                    run = true;
                    mediaPlayer.start();
                }
                else
                {
                    mediaPlayer.pause();
                    run = false;
                }
            }
        });

        btnKorsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying())
                {
                    if(!isKosrsy)
                    {
                        isKosrsy=true;
                        mediaPlayer.stop();
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.al_kursy);
                    }
                    run = true;
                    mediaPlayer.start();
                }
                else
                {
                    mediaPlayer.pause();
                    run = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        String x="";
        // Isolate the force of gravity with the low-pass filter.
        x= String.valueOf(event.values[0]);// light value
        txt.setText(x);
        // run wher we chnage light
        if((event.values[0]<40))
        {
            if(isKosrsy)
            {
                isKosrsy = true ;
            }
            if(!mediaPlayer.isPlaying())
            {
                mediaPlayer.start();
                run = true;
            }
        }
        else
        {
            run=false;
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
