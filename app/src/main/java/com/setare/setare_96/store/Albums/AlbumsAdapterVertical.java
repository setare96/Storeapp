package com.setare.setare_96.store.Albums;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.List;
import java.util.Locale;

public class AlbumsAdapterVertical extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    private String phone_name;
    private String phone_price;
    private String phone_image_url;
    private int view_num;
    private int sell_number;
    private long upload_date;

    private Bundle  Album_Data;

    private TextView TV_SUB_album;
    private TextView TV_empty;

    private String dbName;
    private String collectionName;
    private String sub_album;

    private TextView BTN_sort;

    private Album[] album;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_phones);

        TV_SUB_album = (TextView) findViewById(R.id.TV_SUB_album);
        TV_empty = (TextView) findViewById(R.id.TV_empty);


        Bundle True_bl = getIntent().getExtras();
        dbName = True_bl.getString("dbName");
        collectionName = True_bl.getString("collectionName");
        sub_album = True_bl.getString("sub_album");

        TV_SUB_album.setText(sub_album);
        recyclerView = (RecyclerView) findViewById(R.id.phone_recycler_view);

        getData();
        RecyclerView_OnClick();
        Spinner();

    }

    public void Spinner() {
        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("مرتب سازی");
        categories.add("بیشترین بازدید");
        categories.add("قیمت از زیاد به کم");
        categories.add("قیمت از کم به زیاد");
        categories.add("جدید ترین");
        categories.add("پر فروش ترین");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void getData() {

        App42API.initialize(AlbumsAdapterVertical.this,
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

                        Album album_data[] = new Album[jsonDocList.size()];

                        for(int i=0;i<jsonDocList.size();i++)
                        {
                            String myData;
                            String phoneName = "11";
                            String phonePrice = "11";
                            String phoneImage = "11";
                            int Sell_number = 0;
                            long Upload_date = 0;
                            int View_num = 0;
                            myData = jsonDocList.get(i).getJsonDoc();

                            try {
                                JSONObject jsonObject = new JSONObject(myData);
                                phoneName = jsonObject.getString("name");
                                phonePrice = jsonObject.getString("price");
                                phoneImage = jsonObject.getString("image");
                                View_num = jsonObject.getInt("view_num");
                                Sell_number = jsonObject.getInt("sell_number");
                                Upload_date = jsonObject.getLong("upload-date");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            phone_name = phoneName;
                            phone_price = phonePrice;
                            phone_image_url = phoneImage;
                            view_num = View_num;
                            sell_number = Sell_number;
                            upload_date = Upload_date;

                            album_data[i] = new Album();
                            album_data[i].setName(phoneName);
                            album_data[i].setPrice(phonePrice);
                            album_data[i].setImages(phoneImage);
                            album_data[i].setView_num(View_num);
                            album_data[i].setSell_number(Sell_number);
                            album_data[i].setUpload_date(Upload_date);

                            album = album_data;

                            albumList.add(album_data[i]);
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
                        TV_empty.setText("هنوز کالایی در این بخش نیست ... !!!");
                    }
                });
            }
        });

    }

    public void RecyclerView_OnClick() {

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AlbumsAdapterVertical.this , recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList.get(position).getName();

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);

                        Intent switch_activity = new Intent(AlbumsAdapterVertical.this , Object_View.class);
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

    public void sort_view_num(Album[] values) {
        // check for empty or null array
        if (values == null || values.length==0){
            return;
        }
        this.album = values;
        number = values.length;
        quicksort_view_num(0, number - 1);
    }

    private void quicksort_view_num(int low, int high) {
        int i = low, j = high;
        int pivot = album[low + (high-low)/2].view_num;

        while (i <= j) {

            while (album[i].view_num < pivot) {
                i++;
            }

            while (album[j].view_num > pivot) {
                j--;
            }

            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort_view_num(low, j);
        if (i < high)
            quicksort_view_num(i, high);
    }

    public void sort_price(Album[] values) {
        // check for empty or null array
        if (values == null || values.length==0){
            return;
        }
        this.album = values;
        number = values.length;
        quicksort_price(0, number - 1);
    }
    private void quicksort_price(int low, int high) {
        int i = low, j = high;

        NumberFormat format = NumberFormat.getInstance(Locale.US);
        int pivot = 0;
        try {
            pivot = format.parse(album[low + (high-low)/2].price).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (i <= j) {

            try {
                while (format.parse(album[i].price).intValue() < pivot) {
                    i++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                while (format.parse(album[j].price).intValue() > pivot) {
                    j--;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort_view_num(low, j);
        if (i < high)
            quicksort_view_num(i, high);
    }

    public void sort_sell_number(Album[] values) {
        // check for empty or null array
        if (values == null || values.length==0){
            return;
        }
        this.album = values;
        number = values.length;
        quicksort_sell_number(0, number - 1);
    }
    private void quicksort_sell_number(int low, int high) {
        int i = low, j = high;
        int pivot = album[low + (high-low)/2].sell_number;

        while (i <= j) {

            while (album[i].sell_number < pivot) {
                i++;
            }

            while (album[j].sell_number > pivot) {
                j--;
            }

            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort_sell_number(low, j);
        if (i < high)
            quicksort_sell_number(i, high);
    }

    public void sort_upload_date(Album[] values) {
        // check for empty or null array
        if (values == null || values.length==0){
            return;
        }
        this.album = values;
        number = values.length;
        quicksort_upload_date(0, number - 1);
    }
    private void quicksort_upload_date(int low, int high) {
        int i = low, j = high;
        long pivot = album[low + (high-low)/2].upload_date;

        while (i <= j) {

            while (album[i].upload_date < pivot) {
                i++;
            }

            while (album[j].upload_date > pivot) {
                j--;
            }

            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort_upload_date(low, j);
        if (i < high)
            quicksort_upload_date(i, high);
    }

    private void exchange(int i, int j) {
        Album temp = album[i];
        album[i] = album[j];
        album[j] = temp;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        recyclerView = (RecyclerView) findViewById(R.id.phone_recycler_view);

        switch(position) {

            case 1:
                albumList.clear();
                adapter.notifyDataSetChanged();
                sort_view_num(album);
                for (int i=album.length-1 ; i>=0 ; i--){
                    albumList.add(album[i]);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 2:
                albumList.clear();
                adapter.notifyDataSetChanged();
                sort_price(album);
                for (int i=album.length-1 ; i>=0 ; i--){
                    albumList.add(album[i]);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 3:
                albumList.clear();
                adapter.notifyDataSetChanged();
                sort_price(album);
                for (int i=0 ; i<album.length ; i++){
                    albumList.add(album[i]);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 4:
                albumList.clear();
                adapter.notifyDataSetChanged();
                sort_upload_date(album);
                for (int i=0 ; i<album.length ; i++){
                    albumList.add(album[i]);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 5:
                albumList.clear();
                adapter.notifyDataSetChanged();
                sort_sell_number(album);
                for (int i=album.length-1 ; i>=0 ; i--){
                    albumList.add(album[i]);
                    adapter.notifyDataSetChanged();
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}