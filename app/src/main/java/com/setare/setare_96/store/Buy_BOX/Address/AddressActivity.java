package com.setare.setare_96.store.Buy_BOX.Address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.setare.setare_96.store.Albums.RecyclerItemClickListener;
import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.Buy_BOX.Album_Buy_BOX;
import com.setare.setare_96.store.Buy_BOX.AlbumsAdapterBuyBox;
import com.setare.setare_96.store.Buy_BOX.Buy_BOX_album;
import com.setare.setare_96.store.Buy_BOX.Send_OrderActivity;
import com.setare.setare_96.store.Commodity.Object_View;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by amirhossein on 12/8/17.
 */

public class AddressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapterAddress adapter;
    private List<Album_Address> albumList;

    private String name;
    private String phone_number;
    private String code_number;
    private String home_number;
    private String state;
    private String city;
    private String post_number;
    private String post_address;

    private TextView TV_empty_address;
    private Button BTN_add_address;

    private String dbName;
    private String collectionName;
    private String Total_price;

    private SessionManager session;
    private String username = "Hoda :)";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TV_empty_address = (TextView) findViewById(R.id.TV_empty_address);

        Bundle True_bl = getIntent().getExtras();
        Total_price = True_bl.getString("Total_price");

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        dbName = "store";
        collectionName = username + "_address";

        Data();
        RecyclerView_method();

        BTN_add_address = (Button) findViewById(R.id.BTN_add_address);
        BTN_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle Price_Data = new Bundle();
                Price_Data.putString("Total_price",Total_price);

                Intent switch_activity = new Intent(AddressActivity.this , Add_New_Address.class);
                switch_activity.putExtras(Price_Data);
                startActivity(switch_activity);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Data() {

        App42API.initialize(AddressActivity.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findAllDocuments(dbName, collectionName, new App42CallBack() {
            public void onSuccess(final Object response)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Storage storage  = (Storage )response;
                        System.out.println("dbName is " + storage.getDbName());
                        System.out.println("collection Name is " + storage.getCollectionName());
                        ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();

                        for(int i=0;i<jsonDocList.size();i++)
                        {
                            String myData;
                            String Name = "11";
                            String PhoneNumber = "11";
                            String CodeNumber = "11";
                            String HomeNumber = "11";
                            String State = "11";
                            String City = "11";
                            String PostNumber = "11";
                            String PostAddress = "11";

                            myData = jsonDocList.get(i).getJsonDoc();

                            try {
                                JSONObject jsonObject = new JSONObject(myData);
                                Name = jsonObject.getString("name");
                                PhoneNumber = jsonObject.getString("phone_number");
                                CodeNumber = jsonObject.getString("code_number");
                                HomeNumber = jsonObject.getString("home_number");
                                State = jsonObject.getString("state");
                                City = jsonObject.getString("city");
                                PostNumber = jsonObject.getString("post_number");
                                PostAddress = jsonObject.getString("post_address");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            name = Name;
                            phone_number = PhoneNumber;
                            code_number = CodeNumber;
                            home_number = HomeNumber;
                            state = State;
                            city = City;
                            post_number = PostNumber;
                            post_address = PostAddress;

                            Album_Address a = new Album_Address(name, phone_number, code_number, home_number, state, city, post_number, post_address);
                            albumList.add(a);
                            adapter.notifyDataSetChanged();

                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TV_empty_address.setText("هنوز آدرسی در این بخش نیست ... !!!");
                    }
                });
            }
        });

    }

    public void RecyclerView_method() {

        recyclerView = (RecyclerView) findViewById(R.id.address_recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapterAddress(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AddressActivity.this , recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String name = albumList.get(position).getNameRes();
                        String phone_number = albumList.get(position).getPhone_Number();
                        String code_number = albumList.get(position).getCode_number();
                        String home_number = albumList.get(position).getHome_Number();
                        String state = albumList.get(position).getState();
                        String city = albumList.get(position).getCity();
                        String post_number = albumList.get(position).getPost_number();
                        String post_address = albumList.get(position).getPost_address();


                        Bundle Album_Data = new Bundle();
                        Album_Data.putString("name",name);
                        Album_Data.putString("phone_number",phone_number);
                        Album_Data.putString("code_number",code_number);
                        Album_Data.putString("home_number",home_number);
                        Album_Data.putString("state",state);
                        Album_Data.putString("city",city);
                        Album_Data.putString("post_number",post_number);
                        Album_Data.putString("post_address",post_address);
                        Album_Data.putString("Total_price",Total_price);

                        Intent switch_activity = new Intent(AddressActivity.this , Send_OrderActivity.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        try {
            Glide.with(this).load(R.drawable.ic_library_music_black_24dp).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
