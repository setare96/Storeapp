package com.setare.setare_96.store.Buy_BOX.Address;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amirhossein on 12/8/17.
 */

public class Add_New_Address extends AppCompatActivity {

    private EditText ED_receiver_name, ED_receiver_phone_number, ED_receiver_code_number, ED_receiver_home_number, ED_receiver_state,
             ED_receiver_city, ED_receiver_post_number, ED_receiver_post_address;
    private TextView BTN_final_buy_box;

    private String name, phone_number, code_number, home_number, state, city, post_number, post_address;
    private String username;
    private String Total_price;

    private SessionManager session;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_address);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle True_bl = getIntent().getExtras();
        Total_price = True_bl.getString("Total_price");

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        init();
        setNewAddrss();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void init() {

        ED_receiver_name = (EditText) findViewById(R.id.ED_receiver_name);
        ED_receiver_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_name.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ED_receiver_phone_number = (EditText) findViewById(R.id.ED_receiver_phone_number);
        ED_receiver_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_phone_number.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ED_receiver_code_number = (EditText) findViewById(R.id.ED_receiver_code_number);
        ED_receiver_code_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_code_number.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ED_receiver_home_number = (EditText) findViewById(R.id.ED_receiver_home_number);
        ED_receiver_home_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_home_number.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ED_receiver_state = (EditText) findViewById(R.id.ED_receiver_state);
        ED_receiver_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_state.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ED_receiver_city = (EditText) findViewById(R.id.ED_receiver_city);
        ED_receiver_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_city.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ED_receiver_post_number = (EditText) findViewById(R.id.ED_receiver_post_number);
        ED_receiver_post_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_post_number.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ED_receiver_post_address = (EditText) findViewById(R.id.ED_receiver_post_address);
        ED_receiver_post_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ED_receiver_post_address.setBackgroundTintList(Add_New_Address.this.getResources().getColorStateList(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        BTN_final_buy_box = (TextView) findViewById(R.id.BTN_final_buy_box);

    }

    public void getdata() {

        name = ED_receiver_name.getText().toString();
        phone_number = ED_receiver_phone_number.getText().toString();
        code_number = ED_receiver_code_number.getText().toString();
        home_number = ED_receiver_home_number.getText().toString();
        state = ED_receiver_state.getText().toString();
        city = ED_receiver_city.getText().toString();
        post_number = ED_receiver_post_number.getText().toString();
        post_address = ED_receiver_post_address.getText().toString();

    }

    public void setNewAddrss() {

        BTN_final_buy_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getdata();

                if (name.matches("")) {
                    Toast.makeText(Add_New_Address.this , "نام گیرنده را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (phone_number.matches("")) {
                    Toast.makeText(Add_New_Address.this , "شماره تلفن همراه گیرنده را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (state.matches("")) {
                    Toast.makeText(Add_New_Address.this , "استان گیرنده را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (city.matches("")) {
                    Toast.makeText(Add_New_Address.this , "شهر گیرنده را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (post_number.matches("")) {
                    Toast.makeText(Add_New_Address.this , "کد پستی را وارد کنید" , Toast.LENGTH_LONG).show();
                }else if (post_address.matches("")) {
                    Toast.makeText(Add_New_Address.this , "آدرس پستی را وارد کنید" , Toast.LENGTH_LONG).show();
                }else {

                    String dbName = "store";
                    String collectionName = username + "_address";
                    JSONObject json = new JSONObject();
                    try {
                        json.put("name",name);
                        json.put("phone_number",phone_number);
                        json.put("code_number",code_number);
                        json.put("home_number",home_number);
                        json.put("state",state);
                        json.put("city",city);
                        json.put("post_number",post_number);
                        json.put("post_address",post_address);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    App42API.initialize(getBaseContext(),
                            "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                            "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
                    StorageService storageService = App42API.buildStorageService();
                    storageService.insertJSONDocument(dbName, collectionName, json, new App42CallBack() {
                        public void onSuccess(Object response)
                        {
                            Storage storage  = (Storage )response;
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

                    Bundle Price_Data = new Bundle();
                    Price_Data.putString("Total_price",Total_price);

                    Intent go_home = new Intent(Add_New_Address.this , AddressActivity.class);
                    go_home.putExtras(Price_Data);
                    startActivity(go_home);

                }

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
