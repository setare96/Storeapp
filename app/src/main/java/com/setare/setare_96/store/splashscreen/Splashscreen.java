package com.setare.setare_96.store.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.setare.setare_96.store.Basic_Activity.Main_Activity.HomeActivity;
import com.setare.setare_96.store.R;

/**
 * Created by amirhossein on 11/9/17.
 */

public class Splashscreen extends Activity {

    boolean connected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        Thread time_control = new Thread(){

          public void run(){

              try {
                  sleep(2000);
              }catch (InterruptedException e){
                  e.printStackTrace();
              }finally {
                    if (connected == true) {
                        Intent open_activity = new Intent(Splashscreen.this , HomeActivity.class);
                        startActivity(open_activity);
                    }else {
                        Intent open_activity = new Intent(Splashscreen.this , Erorr_Conction.class);
                        startActivity(open_activity);
                    }
              }

          }

        };
        time_control.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
