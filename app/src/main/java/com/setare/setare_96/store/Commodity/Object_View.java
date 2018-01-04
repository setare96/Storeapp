package com.setare.setare_96.store.Commodity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mindorks.placeholderview.PlaceHolderView;
import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amirhossein on 11/28/17.
 */

public class Object_View extends Activity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private  int f = 0;

    //Object_Value :
    private String name;
    private String price;
    private String warranty;
    private String seller;
    private String description;
    private String image;
    private String type;
    private String brand;
    private int number;
    private int view_num;
    private long upload_date;
    private int sell_number;
    private JSONArray images;
    private JSONArray ditail;

    private String dbName;
    private String collectionName;
    private String key1;
    private String value1;

    private boolean favorite;
    private boolean buy;

    private JSONObject jsonObject_private;
    private SessionManager session;

    private PlaceHolderView mGalleryView;
    private SliderLayout mDemoSlider;
    private Toolbar toolbar;

    private Bundle Ditail_Data;

    TextView TV_name_object_view;
    TextView TV_price_object_view;
    TextView TV_warranty_object_view;
    TextView TV_seller_object_view;
    TextView TV_description_object_view;

    TextView BTN_description_object_view;
    TextView BTN_add_to_box;

    Button BTN_dital_object_view;

    ImageButton BTN_favorite_object_view;



    private String dbName_private = "store";
    private String collectionName_private = "phone";
    private String Name_private = "Hoda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_view);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider_object_view);

        Bundle True_bl = getIntent().getExtras();
        Name_private = True_bl.getString("Name");
        collectionName_private = True_bl.getString("collectionName");
        dbName_private = True_bl.getString("dbName");

        Data();
        Check_Object_In_Buy_List();
        Check_Object_In_Favorite_List();
        setBTN_description_object_view();
        setBTN_favorite_object_view();
        setBTN_add_to_box();

    }


    public void Data () {
        dbName = dbName_private;
        collectionName = collectionName_private;
        key1 = "name";
        value1 = Name_private;

        Query q1 = QueryBuilder.build(key1, value1, QueryBuilder.Operator.EQUALS);
        // Build query q1 for key1 equal to name and value1 equal to Nick

        // Pass aggregated query to finder method below.
        // Similarly you can aggregate more conditions in querying object.
        App42API.initialize(Object_View.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findDocumentsByQuery(dbName, collectionName, q1, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {

                    String myData;
                    JSONObject jsonObject_local = null;
                    JSONArray Ditail = null;
                    JSONArray Images = null;
                    String Name = null;
                    String Price = null;
                    String Warranty = null;
                    String Seller = null;
                    String Description = null;
                    String Image = null;
                    String Type = null;
                    String Brand = null;
                    int Number = 0;
                    int View_num = 0;
                    long Upload_date = 0;
                    int Sell_number = 0;
                    myData = jsonDocList.get(i).getJsonDoc();

                    try {
                        JSONObject jsonObject = new JSONObject(myData);
                        jsonObject_local = jsonObject;
                        Ditail = jsonObject.getJSONArray("ditail");
                        Images = jsonObject.getJSONArray("images");
                        Name = jsonObject.getString("name");
                        Price = jsonObject.getString("price");
                        Warranty = jsonObject.getString("warranty");
                        Seller = jsonObject.getString("seller");
                        Description = jsonObject.getString("description");
                        Image = jsonObject.getString("image");
                        Type = jsonObject.getString("type");
                        Brand = jsonObject.getString("brand");
                        Number = jsonObject.getInt("number");
                        View_num = jsonObject.getInt("view_num");
                        Upload_date = jsonObject.getLong("upload-date");
                        Sell_number = jsonObject.getInt("sell_number");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jsonObject_private = jsonObject_local;
                    ditail = Ditail;
                    images = Images;
                    name = Name;
                    price = Price;
                    warranty = Warranty;
                    seller = Seller;
                    description = Description;
                    image = Image;
                    type = Type;
                    brand = Brand;
                    number = Number;
                    view_num = View_num;
                    upload_date = Upload_date;
                    sell_number = Sell_number;

                    Update_View_num(ditail ,images ,name ,price ,warranty ,seller ,description
                                    ,image ,type ,brand ,number ,view_num ,upload_date ,sell_number);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Set_Data();
                        }
                    });
                }
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void Set_Data() {

        TV_name_object_view = (TextView) findViewById(R.id.TV_name_object_view);
        TV_price_object_view = (TextView) findViewById(R.id.TV_price_object_view);
        TV_warranty_object_view = (TextView) findViewById(R.id.TV_warranty_object_view);
        TV_seller_object_view = (TextView) findViewById(R.id.TV_seller_object_view);
        TV_description_object_view = (TextView) findViewById(R.id.TV_description_object_view);

        TV_name_object_view.setText(name);
        TV_price_object_view.setText(price);
        TV_warranty_object_view.setText(warranty);
        TV_seller_object_view.setText(seller);
        TV_description_object_view.setText(description);

        Galary();
        ditail1();
    }

    public void Galary() {

        //add_picture:
        HashMap<String,String> url_maps = new HashMap<String, String>();
        for (int i=0; i<=images.length(); i++){

            try {
                url_maps.put(Integer.toString(i), images.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView_object_view);


        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView.description(name).image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) Object_View.this);
    }

    public void ditail1() {

        BTN_dital_object_view = (Button) findViewById(R.id.BTN_dital_object_view);
        BTN_dital_object_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ditail_Data = new Bundle();
                Ditail_Data.putString("Name",value1);
                Ditail_Data.putString("dbName",dbName);
                Ditail_Data.putString("collectionName",collectionName_private);

                Intent switch_activity = new Intent(Object_View.this , Ditail_Table.class);
                switch_activity.putExtras(Ditail_Data);
                startActivity(switch_activity);
            }
        });
    }

    public void setBTN_description_object_view() {

        BTN_description_object_view = (TextView) findViewById(R.id.BTN_description_object_view);
        BTN_description_object_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f <= 4){
                    f = (description.length())/40;
                    TV_description_object_view.setMaxLines(f);
                    BTN_description_object_view.setText("بستن");
                }
                else if (f > 4){
                    f = 4;
                    TV_description_object_view.setMaxLines(f);
                    BTN_description_object_view.setText("ادامه مطلب");
                }
            }
        });

    }

    public void setBTN_add_to_box() {

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        final String username_fav_collectionName = username + "_buy";

        BTN_add_to_box = (TextView) findViewById(R.id.BTN_add_to_box);
        BTN_add_to_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buy == false){
                    BTN_add_to_box.setBackgroundColor(Color.parseColor("#3498DB"));
                    buy = true;
                    Add_to_OBJECT_list(jsonObject_private ,username_fav_collectionName);
                }else if (buy == true){
                    BTN_add_to_box.setBackgroundColor(Color.parseColor("#00AB49"));
                    buy = false;
                    Delete_OBJECT_from_list(username_fav_collectionName , name);
                }
            }
        });

    }

    public void setBTN_favorite_object_view() {

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        final String username_fav_collectionName = username + "_fav";

        BTN_favorite_object_view = (ImageButton) findViewById(R.id.BTN_favorite_object_view);
        BTN_favorite_object_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite == false){
                    BTN_favorite_object_view.setImageResource(R.drawable.ic_favorite_red_24dp);
                    favorite = true;
                    Add_to_OBJECT_list(jsonObject_private ,username_fav_collectionName);
                }else if (favorite == true){
                    BTN_favorite_object_view.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favorite = false;
                    Delete_OBJECT_from_list(username_fav_collectionName , name);
                }
            }
        });

    }

    private void Add_to_OBJECT_list(JSONObject fav_JSONObject , String collectionName_fav) {

        String dbName = dbName_private;
        String collectionName = collectionName_fav;
        JSONObject json = fav_JSONObject;
        App42API.initialize(Object_View.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
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

    private void Delete_OBJECT_from_list(String collectionName_fav , String fav_value){

        String dbName = dbName_private;
        String collectionName = collectionName_fav;
        String key = "name";
        String value = fav_value;
        App42API.initialize(Object_View.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.deleteDocumentsByKeyValue(dbName, collectionName, key, value, new App42CallBack() {
            public void onSuccess(Object response)
            {
                App42Response app42response = (App42Response)response;
                System.out.println("response is " + app42response) ;
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });

    }

    private void Check_Object_In_Buy_List(){

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        String username_fav_collectionName = username + "_buy";

        dbName = dbName_private;
        collectionName = username_fav_collectionName;
        String key = "name";
        String value = Name_private;
        App42API.initialize(Object_View.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findDocumentByKeyValue(dbName, collectionName, key, value, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage  storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BTN_add_to_box = (TextView) findViewById(R.id.BTN_add_to_box);
                            BTN_add_to_box.setBackgroundColor(Color.parseColor("#3498DB"));
                            buy = true;
                        }
                    });

                }
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    private void Check_Object_In_Favorite_List(){

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        String username_fav_collectionName = username + "_fav";

        dbName = dbName_private;
        collectionName = username_fav_collectionName;
        String key = "name";
        String value = Name_private;
        App42API.initialize(Object_View.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findDocumentByKeyValue(dbName, collectionName, key, value, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage  storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BTN_favorite_object_view = (ImageButton) findViewById(R.id.BTN_favorite_object_view);
                            BTN_favorite_object_view.setImageResource(R.drawable.ic_favorite_red_24dp);
                            favorite = true;
                        }
                    });

                }
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void Update_View_num(JSONArray Ditail, JSONArray Images, String Name, String Price, String Warranty, String Seller, String Description,
                                String Image, String Type, String Brand, int Number, int View_num, long Upload_date, int Sell_number) {

        dbName = dbName_private;
        collectionName = collectionName_private;
        String key = "name";
        String value = Name_private;

        JSONObject jsonDoc = new JSONObject();

        try {
            jsonDoc.put("ditail",Ditail);
            jsonDoc.put("images",Images);
            jsonDoc.put("name",Name);
            jsonDoc.put("price",Price);
            jsonDoc.put("warranty",Warranty);
            jsonDoc.put("seller",Seller);
            jsonDoc.put("description",Description);
            jsonDoc.put("image",Image);
            jsonDoc.put("type",Type);
            jsonDoc.put("brand",Brand);
            jsonDoc.put("number",Number);
            jsonDoc.put("view_num",View_num+1);
            jsonDoc.put("upload-date",Upload_date);
            jsonDoc.put("sell_number",Sell_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        App42API.initialize(Object_View.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.updateDocumentByKeyValue(dbName, collectionName, key, value, jsonDoc, new App42CallBack() {
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


    //OTHER METHOD

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

}

