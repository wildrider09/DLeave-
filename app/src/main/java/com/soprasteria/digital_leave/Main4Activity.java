package com.soprasteria.digital_leave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
public class Main4Activity extends AppCompatActivity {

    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Home");
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

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);


        Fragment f=null ;

        if(sp.getString("type","").equals("Employee"))
            f = new Home();
        else
            f = new ManagerData();

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.frame_container,f).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_old:
                    toolbar.setTitle("Past Records");
                    Old o = new Old();
                    FragmentManager fm = getSupportFragmentManager();

                    fm.beginTransaction().replace(R.id.frame_container,o).commit();
                    return true;
                case R.id.navigation_home:
                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

                    toolbar.setTitle("Home Page");

                    Fragment f=null ;

                    if(sp.getString("type","").equals("Employee"))
                    f = new Home();
                    else
                    f = new ManagerData();


                    FragmentManager ol = getSupportFragmentManager();

                    ol.beginTransaction().replace(R.id.frame_container,f).commit();
                    return true;
                case R.id.navigation_new:
                    toolbar.setTitle("Apply for Leave");
                    New  n = new New();
                    FragmentManager ne = getSupportFragmentManager();

                    ne.beginTransaction().replace(R.id.frame_container,n).commit();
                    return true;

                case R.id.logout:

//
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                    //Uncomment the below code to Set the message and title from the strings.xml file
                    //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage("Do you want to Logout ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences sp1 = getSharedPreferences("login", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor et = sp1.edit();

                                    et.putString("status", "");
                                    et.commit();

                                    Intent i = new Intent(Main4Activity.this, Register.class);

                                    startActivity(i);


                                    finish();
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Dleave");
                    alert.show();






                    return true;
            }
            return false;
        }
    };
}