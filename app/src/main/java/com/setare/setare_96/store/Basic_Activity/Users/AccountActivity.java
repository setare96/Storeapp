package com.setare.setare_96.store.Basic_Activity.Users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.setare.setare_96.store.Basic_Activity.Main_Activity.HomeActivity;
import com.setare.setare_96.store.R;

import java.util.HashMap;


/**
 * Created by amirhossein on 10/17/17.
 */

public class AccountActivity extends Activity {

    private SessionManager session;

    private String username = "Hoda :)";
    private String email = "aforootan75@gmail.com";

    TextView TV_username_account, TV_email_account;
    Button BTN_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);

        TV_username_account = (TextView) findViewById(R.id.TV_username_account);
        TV_email_account = (TextView) findViewById(R.id.TV_email_account);
        BTN_logout = (Button) findViewById(R.id.BTN_logout);

        TV_username_account.setText(username);
        TV_email_account.setText(email);

        BTN_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();

                Toast.makeText(AccountActivity.this , "خروج با موفقیت انجام شد :)" , Toast.LENGTH_LONG).show();

                Intent go_home = new Intent(AccountActivity.this , HomeActivity.class);
                startActivity(go_home);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
