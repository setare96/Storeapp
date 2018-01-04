package com.setare.setare_96.store.Basic_Activity.Users;

import android.app.Activity;
import android.content.Intent;
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
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amirhossein on 12/1/17.
 */

public class LogupActivity extends Activity {

    private EditText ED_username ,ED_password ,ED_email ,ED_phone_number ,ED_date_of_birth;
    private Button BTN_logup;

    private String username = null ,password = null ,email = null ,phone_number = null ,date_of_birth = null;

    private SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        session = new SessionManager(getApplicationContext());

        init();

        BTN_logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ED_username.getText().toString();
                password = ED_password.getText().toString();
                email = ED_email.getText().toString();
                phone_number = ED_phone_number.getText().toString();
                date_of_birth = ED_date_of_birth.getText().toString();

                if (username.matches("")) {
                    Toast.makeText(LogupActivity.this , "نام کاربری را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (password.matches("")) {
                    Toast.makeText(LogupActivity.this , "رمز را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (email.matches("")) {
                    Toast.makeText(LogupActivity.this , "ایمیل را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (phone_number.matches("")) {
                    Toast.makeText(LogupActivity.this , "شماره همراه را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (date_of_birth.matches("")) {
                    Toast.makeText(LogupActivity.this , "تاریخ تولد را وارد کنید" , Toast.LENGTH_LONG).show();
                }else {
                    session.createLoginSession(username, email, password);
                    CreateUser();
                    CreateUserCollection();
                    Toast.makeText(LogupActivity.this , "ثبت نام با موفقیت انجام شد :)" , Toast.LENGTH_LONG).show();

                    Intent go_home = new Intent(LogupActivity.this , HomeActivity.class);
                    startActivity(go_home);
                }

            }
        });

    }

    public void init() {
        ED_username = (EditText) findViewById(R.id.ED_username);
        ED_password = (EditText) findViewById(R.id.ED_password);
        ED_email = (EditText) findViewById(R.id.ED_email);
        ED_phone_number = (EditText) findViewById(R.id.ED_phone_number);
        ED_date_of_birth = (EditText) findViewById(R.id.ED_date_of_birth);

        BTN_logup = (Button) findViewById(R.id.BTN_logup);
    }

    public void CreateUser() {

        String userName = username;
        String pwd = password;
        String emailId = email;
        App42API.initialize(getBaseContext(),
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        UserService userService = App42API.buildUserService();
        userService.createUser( userName, pwd, emailId, new App42CallBack() {
            public void onSuccess(Object response)
            {
                User user = (User)response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("emailId is " + user.getEmail());
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

    }

    public void CreateUserCollection() {

        String dbName = "store";
        String collectionName = username;

        App42API.initialize(getBaseContext(),
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");

        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("email", email);
            json.put("phone-number", phone_number);
            json.put("date-of-birth", date_of_birth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StorageService storageService = App42API.buildStorageService();
        storageService.insertJSONDocument(dbName, collectionName, json, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage  storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {
                    System.out.println("objectId is " + jsonDocList.get(i).getDocId());
                    System.out.println("CreatedAt is " + jsonDocList.get(i).getCreatedAt());
                    System.out.println("UpdatedAtis " + jsonDocList.get(i).getUpdatedAt());
                    System.out.println("Jsondoc is " + jsonDocList.get(i).getJsonDoc());
                }
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

    }

}
