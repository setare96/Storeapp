package com.setare.setare_96.store.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.setare.setare_96.store.Basic_Activity.Main_Activity.HomeActivity;
import com.setare.setare_96.store.R;

/**
 * Created by amirhossein on 11/30/17.
 */

public class Erorr_Conction extends Activity {

    private Button BTN_try_again;
    private boolean connected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erorr_conctoin);

        BTN_try_again = (Button) findViewById(R.id.BTN_try_again);

        BTN_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else
                    connected = false;


                if (connected == true){
                    Intent open_activity = new Intent(Erorr_Conction.this , HomeActivity.class);
                    startActivity(open_activity);
                }
            }
        });

    }
}
