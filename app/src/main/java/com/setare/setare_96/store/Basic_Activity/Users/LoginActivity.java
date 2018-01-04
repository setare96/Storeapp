package com.setare.setare_96.store.Basic_Activity.Users;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.setare.setare_96.store.Basic_Activity.Main_Activity.HomeActivity;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

/**
 * Created by amirhossein on 12/4/17.
 */

public class LoginActivity extends Activity {

    private EditText ED_username_login , ED_password_login;
    private Button BTN_login;
    private TextView BTN_logup;

    private Intent start_activity;

    private String username_login , password_login;
    private String username , email , password;

    private SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BTN_logup = (TextView) findViewById(R.id.BTN_logup);
        ED_username_login = (EditText) findViewById(R.id.ED_username_login);
        ED_password_login = (EditText) findViewById(R.id.ED_password_login);
        BTN_login = (Button) findViewById(R.id.BTN_login);

        BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username_login = ED_username_login.getText().toString();
                password_login = ED_password_login.getText().toString();

                if (username_login.matches("")) {
                    Toast.makeText(LoginActivity.this , "نام کاربری را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (password_login.matches("")) {
                    Toast.makeText(LoginActivity.this , "رمز را وارد کنید" , Toast.LENGTH_LONG).show();
                }else {

                    String userName = username_login;
                    String pwd = password_login;
                    App42API.initialize(getBaseContext(),
                            "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                            "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
                    UserService userService = App42API.buildUserService();
                    userService.authenticate(userName , pwd, new App42CallBack() {
                        public void onSuccess(Object response)
                        {
                            User user = (User)response;
                            username = user.getUserName();
                            email = user.getEmail();
                            password = user.getPassword();

                            session = new SessionManager(getApplicationContext());
                            session.createLoginSession(username, email, password);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this , "ورود با موفقیت انجام شد :)" , Toast.LENGTH_LONG).show();

                                    Intent go_home = new Intent(LoginActivity.this , HomeActivity.class);
                                    startActivity(go_home);
                                }
                            });

                        }
                        public void onException(Exception ex)
                        {
                            System.out.println("Exception Message : "+ex.getMessage());
                        }
                    });

                }

            }
        });

        BTN_logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_activity = new Intent(LoginActivity.this , LogupActivity.class);
                startActivity(start_activity);
            }
        });
    }
}
