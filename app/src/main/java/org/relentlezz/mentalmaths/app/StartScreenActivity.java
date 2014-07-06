package org.relentlezz.mentalmaths.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class StartScreenActivity extends Activity{

    public final static String NOTIFICATIONS_ENABLED = "org.relentlezz.mentalmaths.app.NOTIFICATIONS_ENABLED";
    public final static String MUSIC_ENABLED = "org.relentlezz.mentalmaths.app.MUSIC_ENABLED";

    Intent startIntent;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Boolean music;
    Boolean notifs;
    Boolean doubleBack;

    Button playButton;
    Button showHighscoresButton;
    Button extraFunButton;
    Button creditsButton;
    CheckBox musicCheckBox;
    CheckBox notifCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        settings = getSharedPreferences("settings", MODE_PRIVATE);

        music = settings.getBoolean(MUSIC_ENABLED, true);
        notifs = settings.getBoolean(NOTIFICATIONS_ENABLED, true);
        doubleBack = false;

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(MainActivity.MAIN_MUSIC, music);
        editor.apply();

        playButton = (Button) findViewById(R.id.playButton);
        showHighscoresButton = (Button) findViewById(R.id.showHighscoresButton);
        extraFunButton = (Button) findViewById(R.id.extraFunButton);
        creditsButton = (Button) findViewById(R.id.creditsButton);
        musicCheckBox = (CheckBox) findViewById(R.id.musicCheckBox);
        notifCheckBox = (CheckBox) findViewById(R.id.notifCheckBox);

        musicCheckBox.setChecked(music);
        notifCheckBox.setChecked(notifs);

        musicCheckBox.setText(music?getResources().getString(R.string.music_toggle_on):getResources().getString(R.string.music_toggle_off));
        notifCheckBox.setText(notifs?getResources().getString(R.string.notif_toggle_on):getResources().getString(R.string.notif_toggle_off));


        playButton.setOnClickListener(onClick);
        showHighscoresButton.setOnClickListener(onClick);
        extraFunButton.setOnClickListener(onClick);
        creditsButton.setOnClickListener(onClick);
        musicCheckBox.setOnClickListener(onClick);
        notifCheckBox.setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            editor = settings.edit();

            switch (view.getId()) {

                case R.id.playButton:
                    startIntent = new Intent(StartScreenActivity.this, MainActivity.class);
                    startActivity(startIntent);
                    break;

                case R.id.showHighscoresButton:
                    startIntent = new Intent(StartScreenActivity.this, HighScoreActivity.class);
                    startActivity(startIntent);
                    break;

                case R.id.extraFunButton:
                    if(getConnectivity()) {
                        startIntent = new Intent(StartScreenActivity.this, ExtraFunActivity.class);
                        startActivity(startIntent);
                    }else {
                        Toast.makeText(StartScreenActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT);
                    }
                    break;

                case R.id.creditsButton:
                    Toast.makeText(StartScreenActivity.this, getResources().getString(R.string.credits_toast), Toast.LENGTH_SHORT).show();
                    break;

                case R.id.musicCheckBox:
                    musicCheckBox.setText(musicCheckBox.isChecked()?getResources().getString(R.string.music_toggle_on):getResources().getString(R.string.music_toggle_off));
                    editor.putBoolean(MUSIC_ENABLED, musicCheckBox.isChecked());
                    editor.putBoolean(MainActivity.MAIN_MUSIC, musicCheckBox.isChecked());
                    editor.apply();
                    break;

                case R.id.notifCheckBox:
                    notifCheckBox.setText(notifCheckBox.isChecked()?getResources().getString(R.string.notif_toggle_on):getResources().getString(R.string.notif_toggle_off));
                    editor.putBoolean(NOTIFICATIONS_ENABLED, notifCheckBox.isChecked());
                    editor.apply();
                    break;
            }
        }
    };

    private boolean getConnectivity(){

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cManager.getActiveNetworkInfo() != null && cManager.getActiveNetworkInfo().isConnected();

    }

    @Override
    public void onBackPressed(){
        if(doubleBack) {
            super.onBackPressed();
        }
        doubleBack = true;
        Toast.makeText(StartScreenActivity.this, getResources().getString(R.string.double_press_back), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

}
