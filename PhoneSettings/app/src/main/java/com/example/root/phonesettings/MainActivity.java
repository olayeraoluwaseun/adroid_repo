package com.example.root.phonesettings;

import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Button general = (Button) findViewById(R.id.genral);
            Button silent = (Button) findViewById(R.id.silent);
            Button vibrate = (Button) findViewById(R.id.vibrate);


            final AudioManager manager =(AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);

                    general.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                        }
                    });

            silent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                }
            });

            vibrate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                }
            });
        }



}
