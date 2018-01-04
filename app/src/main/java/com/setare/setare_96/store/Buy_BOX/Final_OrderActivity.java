package com.setare.setare_96.store.Buy_BOX;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.setare.setare_96.store.Albums.RecyclerItemClickListener;
import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.Buy_BOX.Address.AddressActivity;
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
 * Created by amirhossein on 12/9/17.
 */

public class Final_OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapterBuyBox adapter;
    private List<Album_Buy_BOX> albumList;

    private String phone_name;
    private String phone_price;
    private String phone_image_url;
    private String phone_warranty;
    private String phone_seller;
    private String phone_count;
    private String phone_number;
    private int phone_buynumber;

    private String Total_price;
    private String Place_Sentence;
    private String Delivery_Sentence;
    private String Factor_Sentence;

    private Number number;

    private Bundle Album_Data;

    private SessionManager session;
    private String username = "Hoda :)";

    private TextView BTN_final_buy_box, TV_receiver_address, TV_post_delivery, TV_factor;

    private String dbName;
    private String collectionName;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_order_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        dbName = "store";
        collectionName = username + "_buy";

        Data();
        RecyclerView_method();
        getData();
        setData();

        BTN_final_buy_box = (TextView) findViewById(R.id.BTN_final_buy_box);
        BTN_final_buy_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle Price_Data = new Bundle();
                Price_Data.putString("Total_price",Total_price);

                Intent switch_activity = new Intent(Final_OrderActivity.this , Pay_Order_Activity.class);
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

        App42API.initialize(Final_OrderActivity.this,
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
                            String phoneName = "11";
                            String phonePrice = "11";
                            String phoneImage = "11";
                            String phoneWarranty = "11";
                            String phoneSeller = "11";
                            int phoneNumber = 0;
                            int phonebuyNumber = 1;
                            myData = jsonDocList.get(i).getJsonDoc();

                            try {
                                JSONObject jsonObject = new JSONObject(myData);
                                phoneName = jsonObject.getString("name");
                                phonePrice = jsonObject.getString("price");
                                phoneImage = jsonObject.getString("image");
                                phoneWarranty = jsonObject.getString("warranty");
                                phoneSeller = jsonObject.getString("seller");
                                phoneNumber = jsonObject.getInt("number");
                                phonebuyNumber = jsonObject.getInt("buy_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            phone_name = phoneName;
                            phone_price = phonePrice;
                            phone_image_url = phoneImage;
                            phone_warranty = phoneWarranty;
                            phone_seller = phoneSeller;
                            phone_number = Integer.toString(phoneNumber);
                            phone_buynumber = phonebuyNumber;

                            NumberFormat format = NumberFormat.getInstance(Locale.US);
                            try {
                                number = format.parse(phone_price);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Album_Buy_BOX a = new Album_Buy_BOX(phone_name, phone_price,phone_image_url ,phone_warranty ,phone_seller ,phone_number ,phone_buynumber-1);
                            albumList.add(a);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex) { }
        });

    }

    public void RecyclerView_method() {

        recyclerView = (RecyclerView) findViewById(R.id.phone_recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapterBuyBox(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try {
            Glide.with(this).load(R.drawable.ic_library_music_black_24dp).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getData() {

        Bundle True_bl = getIntent().getExtras();
        Total_price = True_bl.getString("Total_price");
        Place_Sentence = True_bl.getString("Place_Sentence");
        Delivery_Sentence = True_bl.getString("Delivery_Sentence");
        Factor_Sentence = True_bl.getString("Factor_Sentence");

    }

    public void setData() {

        TV_receiver_address = (TextView) findViewById(R.id.TV_receiver_address);
        TV_receiver_address.setText(Place_Sentence);

        TV_post_delivery = (TextView) findViewById(R.id.TV_post_delivery);
        TV_post_delivery.setText(Delivery_Sentence);

        TV_factor = (TextView) findViewById(R.id.TV_factor);
        TV_factor.setText(Factor_Sentence);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
