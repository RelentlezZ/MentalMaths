package org.relentlezz.mentalmaths.app;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

public class SplashScreenActivity extends Activity {

    SharedPreferences notificationToggle;
    boolean notificationsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        notificationToggle = getSharedPreferences("settings", MODE_PRIVATE);
        notificationsEnabled = notificationToggle.getBoolean(StartScreenActivity.NOTIFICATIONS_ENABLED, true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, StartScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    private void createNotification(){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SplashScreenActivity.this);
            mBuilder.setSmallIcon(R.drawable.ic_launcher);
            mBuilder.setContentTitle(getResources().getString(R.string.action_fun));
            mBuilder.setContentText(getResources().getString(R.string.even_more_fun));
            mBuilder.setAutoCancel(true);

        Intent nIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.physik-lk.widukindgymnasium.de"));
        PendingIntent nPendingIntent = PendingIntent.getActivity(SplashScreenActivity.this, 0, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(nPendingIntent);

        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nManager.notify(1, mBuilder.build());

    }

    @Override
    public void onDestroy(){
        if(notificationsEnabled){
            createNotification();
        }
        super.onDestroy();
    }
}


