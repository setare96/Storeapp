package com.setare.setare_96.store.Buy_BOX;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.setare.setare_96.store.Basic_Activity.Users.LogupActivity;
import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.Commodity.Object_View;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by amirhossein on 12/6/17.
 */

public class AlbumsAdapterBuyBox extends RecyclerView.Adapter<AlbumsAdapterBuyBox.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    //Object_Value :
    private String name_p;
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
    private JSONArray images_p;
    private JSONArray ditail;

    private Context mContext;
    private List<Album_Buy_BOX> albumList;
    int con;
    private String name;
    private SessionManager session;
    private String username = "Hoda :)";
    private Buy_BOX_album buy_box_album;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Data(position+1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title , count , TV_warranty_object_view , TV_seller_object_view;
        public ImageView thumbnail;
        public Spinner spinner;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            TV_warranty_object_view = (TextView) view.findViewById(R.id.TV_warranty_object_view);
            TV_seller_object_view = (TextView) view.findViewById(R.id.TV_seller_object_view);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            spinner = (Spinner) view.findViewById(R.id.spinner);
        }
    }


    public AlbumsAdapterBuyBox(Context mContext, List<Album_Buy_BOX> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public AlbumsAdapterBuyBox.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card_buy_box , parent, false);

        return new AlbumsAdapterBuyBox.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album_Buy_BOX album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getPrice() + "  تومان");
        holder.TV_warranty_object_view.setText(album.getWarranty());
        holder.TV_seller_object_view.setText(album.getSeller());
        Picasso.with(mContext).load(album.getImages()).into(holder.thumbnail);

        name = album.getName();

        holder.spinner.setOnItemSelectedListener(this);
        con = Integer.parseInt(album.getNumber());
        String[] items = new String[con];
        for (int i=1; i<=items.length; i++){
            items[i-1] = Integer.toString(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mContext , android.R.layout.simple_spinner_dropdown_item, items);
        holder.spinner.setAdapter(adapter);
        holder.spinner.setSelection(album.getBuy_number());
    }

    public void Update_View_num(JSONArray Ditail, JSONArray Images, String Name, String Price, String Warranty, String Seller, String Description,
                                String Image, String Type, String Brand, int Number, int View_num, long Upload_date, int Sell_number, int Buy_number) {


        session = new SessionManager(mContext);
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        String dbName = "store";
        String collectionName = username + "_buy";
        String key = "name";
        String value = name;

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
            jsonDoc.put("buy_number",Buy_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        App42API.initialize(mContext,
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

    public void Data (final int buy_number_p) {
        session = new SessionManager(mContext);
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        String dbName = "store";
        String collectionName = username + "_buy";
        String key = "name";
        String value = name;

        Query q1 = QueryBuilder.build(key, value, QueryBuilder.Operator.EQUALS);
        // Build query q1 for key1 equal to name and value1 equal to Nick

        // Pass aggregated query to finder method below.
        // Similarly you can aggregate more conditions in querying object.
        App42API.initialize(mContext,
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

                    ditail = Ditail;
                    images_p = Images;
                    name_p = Name;
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

                    Update_View_num(ditail ,images_p ,name_p ,price ,warranty ,seller ,description
                            ,image ,type ,brand ,number ,view_num ,upload_date ,sell_number ,buy_number_p);

                }
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

}
