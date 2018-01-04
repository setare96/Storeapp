package com.setare.setare_96.store.Basic_Activity.Main_Activity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mindorks.placeholderview.PlaceHolderView;
import com.setare.setare_96.store.Albums.Album;
import com.setare.setare_96.store.Albums.AlbumsAdapterHorizontal;
import com.setare.setare_96.store.Albums.AlbumsAdapterSearch;
import com.setare.setare_96.store.Albums.AlbumsAdapterVertical;
import com.setare.setare_96.store.Albums.RecyclerItemClickListener;
import com.setare.setare_96.store.Basic_Activity.AboutUs;
import com.setare.setare_96.store.Basic_Activity.Users.AccountActivity;
import com.setare.setare_96.store.Basic_Activity.LIST_ListActivity;
import com.setare.setare_96.store.Basic_Activity.SettingsActivity;
import com.setare.setare_96.store.Basic_Activity.Users.LoginActivity;
import com.setare.setare_96.store.Basic_Activity.Users.LogupActivity;
import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.Buy_BOX.Buy_BOX_album;
import com.setare.setare_96.store.Commodity.Object_View;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends ActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private PlaceHolderView mGalleryView;
    private SliderLayout mDemoSlider;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private SessionManager session;
    private String username = "Hoda :)";
    private String email = "aforootan75@gmail.com";

    private Intent start_activity;

    private AlbumsAdapterHorizontal adapter1 ,adapter2 ,adapter3 ,adapter4 ,adapter5 ,adapter6 ,adapter7;
    private List<Album> albumList1 ,albumList2 ,albumList3 ,albumList4 ,albumList5 ,albumList6 ,albumList7;

    private String buy_box_counter;
    private String buy_box_counter_toolbar;

    private Bundle Album_Data;

    private String phone_name;
    private String phone_price;
    private String phone_image_url;
    private int view_num;
    private int sell_number;
    private long upload_date;

    private String dbName;
    private String collectionName;

    private int imageID;
    private String imageUrl[] = new String[8];
    private String imageCollection[] = new String[8];

    private int recycleID;
    private String recycleName[] = new String[7];
    private String recycleCollection[] = new String[7];



    private String dbName_Laptop = "store";
    private String collectionName_Laptop = "laptop";

    private String Name_album;
    private String Url_album;

    private TextView TV_buy_box_counter, TV_buy_box_counter_toolbar;

    private ImageView Home_Image_1 ,Home_Image_2 ,Home_Image_3 ,Home_Image_4 ,
            Home_Image_5 ,Home_Image_6 ,Home_Image_7 ,Home_Image_8;

    private TextView TV_recycler_view1 , TV_recycler_view2 , TV_recycler_view3
                    ,TV_recycler_view4 ,TV_recycler_view5 ,TV_recycler_view6 ,TV_recycler_view7;

    private HashMap<String,String> url_maps = new HashMap<String, String>();

    private ImageView iv;
    private TextView text;
    private AnimatedVectorDrawable searchToBar;
    private AnimatedVectorDrawable barToSearch;
    private float offset;
    private Interpolator interp;
    private int duration;
    private boolean expanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buy_box_counter();

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        setRecyclers();
        setHome_image_url();
        set_TV_buy_box_counter_toolbar();
        Search();

    }

    public void Search() {

        iv = (ImageView) findViewById(R.id.search);
        text = (TextView) findViewById(R.id.text);

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String search_data =  text.getText().toString();
                    Album_Data = new Bundle();

                    dbName = "store";
                    collectionName = "ALL";

                    Album_Data.putString("dbName",dbName);
                    Album_Data.putString("collectionName",collectionName);
                    Album_Data.putString("sub_album","جست و جو");
                    Album_Data.putString("value",search_data);

                    start_activity = new Intent(HomeActivity.this , AlbumsAdapterSearch.class);
                    start_activity.putExtras(Album_Data);
                    startActivity(start_activity);

                    return true;
                }
                return false;
            }
        });

        searchToBar = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_search_to_bar);
        barToSearch = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_to_search);
        interp = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        duration = getResources().getInteger(R.integer.duration_bar);
        // iv is sized to hold the search+bar so when only showing the search icon, translate the
        // whole view left by half the difference to keep it centered
        offset = -71f * (int) getResources().getDisplayMetrics().scaledDensity;
        iv.setTranslationX(offset);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animate(View view) {

        if (!expanded) {
            iv.setImageDrawable(searchToBar);
            searchToBar.start();
            iv.animate().translationX(0f).setDuration(duration).setInterpolator(interp);
            text.animate().alpha(1f).setStartDelay(duration - 100).setDuration(100).setInterpolator(interp);
        } else {
            iv.setImageDrawable(barToSearch);
            barToSearch.start();
            iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
            text.setAlpha(0f);
        }
        expanded = !expanded;
    }

    @Override
    protected void onResume() {
        buy_box_counter();

        //update header
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        TextView tv_name = (TextView)header.findViewById(R.id.nameTxt);
        tv_name.setText(username);

        TextView tv_email = (TextView)header.findViewById(R.id.emailTxt);
        tv_email.setText(email);

        super.onResume();
    }

    public void set_TV_buy_box_counter_toolbar() {

        TV_buy_box_counter_toolbar = (TextView) findViewById(R.id.TV_buy_box_counter_toolbar);
        TV_buy_box_counter_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Album_Data = new Bundle();

                String username_buy_collectionName = username + "_buy";

                dbName = "store";
                collectionName = username_buy_collectionName;

                Album_Data.putString("dbName",dbName);
                Album_Data.putString("collectionName",collectionName);
                Album_Data.putString("sub_album","سبد خرید");

                start_activity = new Intent(HomeActivity.this , Buy_BOX_album.class);
                start_activity.putExtras(Album_Data);
                drawerLayout.closeDrawers();
                startActivity(start_activity);

            }
        });

    }

    public void initNavigationDrawer() {

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.list_things:
                        start_activity = new Intent(HomeActivity.this , LIST_ListActivity.class);
                        drawerLayout.closeDrawers();
                        startActivity(start_activity);
                        break;
                    case R.id.settings:
                        start_activity = new Intent(HomeActivity.this , SettingsActivity.class);
                        drawerLayout.closeDrawers();
                        startActivity(start_activity);
                        break;
                    case R.id.buy_box:
                        Album_Data = new Bundle();

                        String username_buy_collectionName = username + "_buy";

                        dbName = "store";
                        collectionName = username_buy_collectionName;

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","سبد خرید");

                        start_activity = new Intent(HomeActivity.this , Buy_BOX_album.class);
                        start_activity.putExtras(Album_Data);
                        drawerLayout.closeDrawers();
                        startActivity(start_activity);
                        break;
                    case R.id.about_us:
                        start_activity = new Intent(HomeActivity.this , AboutUs.class);
                        drawerLayout.closeDrawers();
                        startActivity(start_activity);
                        break;
                    case R.id.account:
                        if (username != null){
                            start_activity = new Intent(HomeActivity.this , AccountActivity.class);
                        }else {
                            start_activity = new Intent(HomeActivity.this , LoginActivity.class);
                        }
                        drawerLayout.closeDrawers();
                        startActivity(start_activity);
                        break;
                    case R.id.favorite:
                        Album_Data = new Bundle();

                        String username_fav_collectionName = username + "_fav";

                        dbName = "store";
                        collectionName = username_fav_collectionName;

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","لیست علاقه‌مندی ها");

                        start_activity = new Intent(HomeActivity.this , AlbumsAdapterVertical.class);
                        start_activity.putExtras(Album_Data);
                        drawerLayout.closeDrawers();
                        startActivity(start_activity);
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);

        TextView tv_name = (TextView)header.findViewById(R.id.nameTxt);
        tv_name.setText(username);

        TextView tv_email = (TextView)header.findViewById(R.id.emailTxt);
        tv_email.setText(email);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        String dbName = "store";
        String collectionName = "home-album-galary";
        App42API.initialize(getBaseContext(),
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findAllDocuments(dbName, collectionName, new App42CallBack() {
            public void onSuccess(final Object response)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Storage  storage  = (Storage )response;
                        System.out.println("dbName is " + storage.getDbName());
                        System.out.println("collection Name is " + storage.getCollectionName());
                        ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                        for(int i=0;i<jsonDocList.size();i++)
                        {
                            String myData;
                            String album_name = "11";
                            String album_url = "11";
                            myData = jsonDocList.get(i).getJsonDoc();

                            try {
                                JSONObject jsonObject = new JSONObject(myData);
                                album_name = jsonObject.getString("name");
                                album_url = jsonObject.getString("url");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Name_album = album_name;
                            Url_album = album_url;

                            url_maps.put(Name_album , Url_album);
                        }

                        mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView);


                        for(String name : url_maps.keySet()){
                            TextSliderView textSliderView = new TextSliderView(HomeActivity.this);
                            // initialize a SliderLayout
                            textSliderView.description(name).image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(HomeActivity.this);

                            //add your extra information
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle().putString("extra",name);

                            mDemoSlider.addSlider(textSliderView);
                        }
                        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                        mDemoSlider.setDuration(4000);
                        mDemoSlider.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) HomeActivity.this);

                    }
                });
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void RecyclerView1 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList1.add(a);
                            adapter1.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList1 = new ArrayList<>();
        adapter1 = new AlbumsAdapterHorizontal(this, albumList1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter1);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList1.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void RecyclerView2 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList2.add(a);
                            adapter2.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList2 = new ArrayList<>();
        adapter2 = new AlbumsAdapterHorizontal(this, albumList2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter2);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList2.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void RecyclerView3 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList3.add(a);
                            adapter3.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList3 = new ArrayList<>();
        adapter3 = new AlbumsAdapterHorizontal(this, albumList3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter3);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList3.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void RecyclerView4 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList4.add(a);
                            adapter4.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList4 = new ArrayList<>();
        adapter4 = new AlbumsAdapterHorizontal(this, albumList4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter4);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList4.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void RecyclerView5 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList5.add(a);
                            adapter5.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList5 = new ArrayList<>();
        adapter5 = new AlbumsAdapterHorizontal(this, albumList5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter5);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList5.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void RecyclerView6 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList6.add(a);
                            adapter6.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList6 = new ArrayList<>();
        adapter6 = new AlbumsAdapterHorizontal(this, albumList6);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter6);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList6.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void RecyclerView7 (final String dbName_par , final String collectionName_par ,final int R_V) {

        String dbName = dbName_par;
        String collectionName = collectionName_par;

        App42API.initialize(getBaseContext(),
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
                            int View_num = 0;
                            int Sell_number = 0;
                            long Upload_date = 0;
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

                            Album a = new Album(phone_name, phone_price,phone_image_url,view_num,sell_number,upload_date);
                            albumList7.add(a);
                            adapter7.notifyDataSetChanged();
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R_V);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        albumList7 = new ArrayList<>();
        adapter7 = new AlbumsAdapterHorizontal(this, albumList7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter7);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext() ,
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String Name = albumList7.get(position).getName();
                        String dbName_Click = dbName_par;
                        String collectionName_Click = collectionName_par;

                        Album_Data = new Bundle();
                        Album_Data.putString("Name",Name);
                        Album_Data.putString("dbName",dbName_Click);
                        Album_Data.putString("collectionName",collectionName_Click);

                        Intent switch_activity = new Intent(getBaseContext() , Object_View.class);
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

    public void buy_box_counter() {

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        String username_fav_collectionName = username + "_buy";

        dbName = "store";
        collectionName = username_fav_collectionName;
        App42API.initialize(HomeActivity.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findAllDocumentsCount(dbName, collectionName, new App42CallBack() {
            public void onSuccess(Object response)
            {
                App42Response app42response = (App42Response)response;
                buy_box_counter = Integer.toString(app42response.getTotalRecords());
                buy_box_counter_toolbar = Integer.toString(app42response.getTotalRecords());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TV_buy_box_counter = (TextView) findViewById(R.id.TV_buy_box_counter);
                        TV_buy_box_counter.setText(buy_box_counter);

                        if (Integer.parseInt(buy_box_counter_toolbar) > 0){
                            TV_buy_box_counter_toolbar = (TextView) findViewById(R.id.TV_buy_box_counter_toolbar);
                            TV_buy_box_counter_toolbar.setText(buy_box_counter_toolbar);
                        }
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void setRecyclers() {

        String dbName1 = "store";
        String collectionName1 = "home-recyclers";
        App42API.initialize(HomeActivity.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findAllDocuments(dbName1, collectionName1, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage  storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {

                    String myData;
                    int RecycleID = 11;
                    String RecycleName = "11";
                    String RecycleCollection = "11";
                    myData = jsonDocList.get(i).getJsonDoc();

                    try {
                        JSONObject jsonObject = new JSONObject(myData);
                        RecycleID = jsonObject.getInt("id");
                        RecycleName = jsonObject.getString("Recycle_Name");
                        RecycleCollection = jsonObject.getString("Recycle_collection");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recycleID = RecycleID;
                    recycleName[recycleID] = RecycleName;
                    recycleCollection[recycleID] = RecycleCollection;

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TV_recycler_view1 = (TextView) findViewById(R.id.TV_recycler_view1);
                        TV_recycler_view2 = (TextView) findViewById(R.id.TV_recycler_view2);
                        TV_recycler_view3 = (TextView) findViewById(R.id.TV_recycler_view3);
                        TV_recycler_view4 = (TextView) findViewById(R.id.TV_recycler_view4);
                        TV_recycler_view5 = (TextView) findViewById(R.id.TV_recycler_view5);
                        TV_recycler_view6 = (TextView) findViewById(R.id.TV_recycler_view6);
                        TV_recycler_view7 = (TextView) findViewById(R.id.TV_recycler_view7);

                        TV_recycler_view1.setText(recycleName[0]);
                        TV_recycler_view2.setText(recycleName[1]);
                        TV_recycler_view3.setText(recycleName[2]);
                        TV_recycler_view4.setText(recycleName[3]);
                        TV_recycler_view5.setText(recycleName[4]);
                        TV_recycler_view6.setText(recycleName[5]);
                        TV_recycler_view7.setText(recycleName[6]);

                        RecyclerView1(dbName_Laptop , recycleCollection[0] ,R.id.recycler_view1);
                        RecyclerView2(dbName_Laptop , recycleCollection[1] ,R.id.recycler_view2);
                        RecyclerView3(dbName_Laptop , recycleCollection[2] ,R.id.recycler_view3);
                        RecyclerView4(dbName_Laptop , recycleCollection[3] ,R.id.recycler_view4);
                        RecyclerView5(dbName_Laptop , recycleCollection[4] ,R.id.recycler_view5);
                        RecyclerView6(dbName_Laptop , recycleCollection[5] ,R.id.recycler_view6);
                        RecyclerView7(dbName_Laptop , recycleCollection[6] ,R.id.recycler_view7);
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

    }

    public void setHome_image_url() {

        String dbName1 = "store";
        String collectionName1 = "home-images";
        App42API.initialize(HomeActivity.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findAllDocuments(dbName1, collectionName1, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage  storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {

                    String myData;
                    int ImageID = 11;
                    String ImageUrl = "11";
                    String ImageCollection = "11";
                    myData = jsonDocList.get(i).getJsonDoc();

                    try {
                        JSONObject jsonObject = new JSONObject(myData);
                        ImageID = jsonObject.getInt("id");
                        ImageUrl = jsonObject.getString("image_url");
                        ImageCollection = jsonObject.getString("Recycle_collection");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    imageID = ImageID;
                    imageUrl [imageID] = ImageUrl;
                    imageCollection[imageID] = ImageCollection;

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Home_Image_1 = (ImageView) findViewById(R.id.Home_Image_1);
                        Home_Image_2 = (ImageView) findViewById(R.id.Home_Image_2);
                        Home_Image_3 = (ImageView) findViewById(R.id.Home_Image_3);
                        Home_Image_4 = (ImageView) findViewById(R.id.Home_Image_4);
                        Home_Image_5 = (ImageView) findViewById(R.id.Home_Image_5);
                        Home_Image_6 = (ImageView) findViewById(R.id.Home_Image_6);
                        Home_Image_7 = (ImageView) findViewById(R.id.Home_Image_7);
                        Home_Image_8 = (ImageView) findViewById(R.id.Home_Image_8);

                        Picasso.with(HomeActivity.this).load(imageUrl[0]).into(Home_Image_1);
                        Picasso.with(HomeActivity.this).load(imageUrl[1]).into(Home_Image_2);
                        Picasso.with(HomeActivity.this).load(imageUrl[2]).into(Home_Image_3);
                        Picasso.with(HomeActivity.this).load(imageUrl[3]).into(Home_Image_4);
                        Picasso.with(HomeActivity.this).load(imageUrl[4]).into(Home_Image_5);
                        Picasso.with(HomeActivity.this).load(imageUrl[5]).into(Home_Image_6);
                        Picasso.with(HomeActivity.this).load(imageUrl[6]).into(Home_Image_7);
                        Picasso.with(HomeActivity.this).load(imageUrl[7]).into(Home_Image_8);

                        setOnClickForHome_image (Home_Image_1  , imageCollection[0] , 0 );
                        setOnClickForHome_image (Home_Image_2  , imageCollection[1] , 1 );
                        setOnClickForHome_image (Home_Image_3  , imageCollection[2] , 2 );
                        setOnClickForHome_image (Home_Image_4  , imageCollection[3] , 3 );
                        setOnClickForHome_image (Home_Image_5  , imageCollection[4] , 4 );
                        setOnClickForHome_image (Home_Image_6  , imageCollection[5] , 5 );
                        setOnClickForHome_image (Home_Image_7  , imageCollection[6] , 6 );
                        setOnClickForHome_image (Home_Image_8  , imageCollection[7] , 7 );
                    }
                });

            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

    }

    public void setOnClickForHome_image (ImageView image , final String imageCollection , final int ID) {

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album_Data = new Bundle();

                dbName = "store";
                collectionName = imageCollection;

                Album_Data.putString("dbName",dbName);
                Album_Data.putString("collectionName",collectionName);
                Album_Data.putString("sub_album","IMAGE ALBUM" + Integer.toString(ID+1));

                start_activity = new Intent(HomeActivity.this , AlbumsAdapterVertical.class);
                start_activity.putExtras(Album_Data);
                startActivity(start_activity);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
