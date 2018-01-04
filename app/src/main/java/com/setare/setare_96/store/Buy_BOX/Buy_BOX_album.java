package com.setare.setare_96.store.Buy_BOX;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.setare.setare_96.store.Albums.RecyclerItemClickListener;
import com.setare.setare_96.store.Basic_Activity.Main_Activity.HomeActivity;
import com.setare.setare_96.store.Buy_BOX.Address.Add_New_Address;
import com.setare.setare_96.store.Buy_BOX.Address.AddressActivity;
import com.setare.setare_96.store.Commodity.Object_View;
import com.setare.setare_96.store.R;
import com.setare.setare_96.store.splashscreen.Erorr_Conction;
import com.setare.setare_96.store.splashscreen.Splashscreen;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class Buy_BOX_album extends AppCompatActivity{

    private RecyclerView recyclerView;
    private AlbumsAdapterBuyBox adapter;
    private List<Album_Buy_BOX> albumList;

    private String phone_name;
    private String phone_price;
    private String phone_image_url;
    private String phone_warranty;
    private String phone_seller;
    private String phone_number;
    private int phone_buynumber;

    private String Total_price;
    private int total_price = 0;

    private Number number;

    private Bundle Album_Data;

    private TextView TV_SUB_album;
    private TextView TV_empty;
    private TextView BTN_final_buy_box;

    private String dbName;
    private String collectionName;
    private String sub_album;

    private Toolbar toolbar;
    private View view_R;
    public Inflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_buy_box);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TV_SUB_album = (TextView) findViewById(R.id.TV_SUB_album);
        TV_empty = (TextView) findViewById(R.id.TV_empty);

        Bundle True_bl = getIntent().getExtras();
        dbName = True_bl.getString("dbName");
        collectionName = True_bl.getString("collectionName");
        sub_album = True_bl.getString("sub_album");

        TV_SUB_album.setText(sub_album);

        Data();
        RecyclerView_method();

        BTN_final_buy_box = (TextView) findViewById(R.id.BTN_final_buy_box);
        BTN_final_buy_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Buy_BOX_album.this,"لطفا شکیبا باشید" , Toast.LENGTH_SHORT).show();
                Thread time_control = new Thread(){

                    public void run(){

                        try {
                            sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }finally {
                            total_price = 0;
                            phone_buynumber = 1;
                            setTotal_price();

                            Bundle Price_Data = new Bundle();
                            Price_Data.putString("Total_price",Total_price);

                            Intent switch_activity = new Intent(Buy_BOX_album.this , AddressActivity.class);
                            switch_activity.putExtras(Price_Data);
                            startActivity(switch_activity);
                        }

                    }

                };
                time_control.start();

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

    public void setTotal_price() {

        App42API.initialize(Buy_BOX_album.this,
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
                            String phonePrice = "11";
                            int phonebuyNumber = 1;
                            myData = jsonDocList.get(i).getJsonDoc();

                            try {
                                JSONObject jsonObject = new JSONObject(myData);
                                phonePrice = jsonObject.getString("price");
                                phonebuyNumber = jsonObject.getInt("buy_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            phone_price = phonePrice;
                            phone_buynumber = phonebuyNumber;

                            NumberFormat format = NumberFormat.getInstance(Locale.US);
                            try {
                                number = format.parse(phone_price);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            total_price += phone_buynumber * number.intValue();
                            Total_price = NumberFormat.getNumberInstance(Locale.US).format(total_price) + " تومان ";

                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TV_empty.setText("هنوز کالایی در این بخش نیست ... !!!");
                    }
                });
            }
        });

    }

    public void Data() {

        App42API.initialize(Buy_BOX_album.this,
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
                            int phonebuyNumber = 2;
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

                            total_price += phone_buynumber * number.intValue();
                            Total_price = NumberFormat.getNumberInstance(Locale.US).format(total_price) + " تومان ";

                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TV_empty.setText("هنوز کالایی در این بخش نیست ... !!!");
                    }
                });
            }
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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(Buy_BOX_album.this , recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList.get(position).getName();

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);

                        Intent switch_activity = new Intent(Buy_BOX_album.this , Object_View.class);
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
    }

}