package com.example.codebreaker.weatherforecast.Activities;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.codebreaker.weatherforecast.Fragment.ShowWeather;
import com.example.codebreaker.weatherforecast.R;



public class MainActivity extends AppCompatActivity  {

    android.support.v4.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(this,"Please Grant The Location Permission",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Location Permission is needed for fetching the your location",Toast.LENGTH_SHORT).show();

            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {

            startFragment();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               startFragment();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 1)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted\nIf not showing anything try restarting the app",Toast.LENGTH_SHORT).show();

                /** startFragment() is not working here **/
                    //startFragment();
                /** App crashes for the first time however it works fine after that
                 * Can't find what the reason is**/


                  /** So here i am triggring this same activity which will run the fragment ShowWeather
                   * through else clause in onCreate method**/
                     startActivity(new Intent(MainActivity.this,MainActivity.class));
                     finish();

            } else {
                Toast.makeText(this,"Permission was not granted\nApp needs to access location in order to show weather details",Toast.LENGTH_LONG).show();

                finish();
            }

            return;
        }
    }

    public void startFragment()
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new ShowWeather()).commit();
    }

}


