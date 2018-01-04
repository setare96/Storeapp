package com.setare.setare_96.store.Albums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.setare.setare_96.store.Commodity.Object_View;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlbumsAdapterSearch extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    private String phone_name;
    private String phone_price;
    private String phone_image_url;
    private int view_num;

    private Bundle Album_Data;

    private TextView TV_SUB_album;
    private TextView TV_empty;

    private String dbName;
    private String collectionName;
    private String sub_album;
    private String value;

    private TextView BTN_sort;

    private Album[] album;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);

        TV_SUB_album = (TextView) findViewById(R.id.TV_SUB_album);
        TV_empty = (TextView) findViewById(R.id.TV_empty);


        Bundle True_bl = getIntent().getExtras();
        dbName = True_bl.getString("dbName");
        collectionName = True_bl.getString("collectionName");
        sub_album = True_bl.getString("sub_album");
        value = True_bl.getString("value");

        TV_SUB_album.setText(sub_album);
        recyclerView = (RecyclerView) findViewById(R.id.phone_recycler_view);

        getData();
        RecyclerView_OnClick();

    }

    public void getData() {

        String key = "name";

        Query query = QueryBuilder.build(key, value, QueryBuilder.Operator.LIKE);
        App42API.initialize(AlbumsAdapterSearch.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findDocumentsByQuery(dbName, collectionName, query, new App42CallBack() {
            public void onSuccess(final Object response)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Storage storage = (Storage) response;
                        System.out.println("dbName is " + storage.getDbName());
                        System.out.println("collection Name is " + storage.getCollectionName());
                        ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();

                        Album album_data[] = new Album[jsonDocList.size()];

                        for (int i = 0; i < jsonDocList.size(); i++) {
                            String myData;
                            String phoneName = "11";
                            String phonePrice = "11";
                            String phoneImage = "11";
                            int View_num = 0;
                            myData = jsonDocList.get(i).getJsonDoc();

                            try {
                                JSONObject jsonObject = new JSONObject(myData);
                                phoneName = jsonObject.getString("name");
                                phonePrice = jsonObject.getString("price");
                                phoneImage = jsonObject.getString("image");
                                View_num = jsonObject.getInt("view_num");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            phone_name = phoneName;
                            phone_price = phonePrice;
                            phone_image_url = phoneImage;
                            view_num = View_num;

                            album_data[i] = new Album();
                            album_data[i].setName(phoneName);
                            album_data[i].setPrice(phonePrice);
                            album_data[i].setImages(phoneImage);
                            album_data[i].setView_num(View_num);

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
                        TV_empty.setText("کالایی یافت نشد");
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

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AlbumsAdapterSearch.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String Name = albumList.get(position).getName();

                Album_Data = new Bundle();
                Album_Data.putString("Name", Name);
                Album_Data.putString("dbName", dbName);
                Album_Data.putString("collectionName", collectionName);

                Intent switch_activity = new Intent(AlbumsAdapterSearch.this, Object_View.class);
                switch_activity.putExtras(Album_Data);
                startActivity(switch_activity);

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

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