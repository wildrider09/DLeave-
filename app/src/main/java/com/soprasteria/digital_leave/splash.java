package com.soprasteria.digital_leave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splash extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if(!connected) {
            Toast.makeText(getApplicationContext(), "CHECK YOUR INTERNET CONNECTION",
                    Toast.LENGTH_LONG).show();
        }


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

                if(sp.contains("status") && sp.getString("status","").equals("1")) {
                    Intent i = new Intent(splash.this, Main4Activity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(splash.this, Register.class);
                    startActivity(i);
                    finish();
                }

                // close this activity

            }
        }, SPLASH_TIME_OUT);
    }

}