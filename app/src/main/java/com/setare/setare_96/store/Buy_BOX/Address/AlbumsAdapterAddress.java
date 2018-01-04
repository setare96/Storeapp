package com.setare.setare_96.store.Buy_BOX.Address;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by amirhossein on 12/8/17.
 */

public class AlbumsAdapterAddress extends RecyclerView.Adapter<AlbumsAdapterAddress.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    private Context mContext;
    private List<Album_Address> albumList;

    private String username;

    private SessionManager session;

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

@Override
public void onNothingSelected(AdapterView<?> parent) {

        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView TV_receiver_name , TV_receiver_phone_number , TV_receiver_state ,
                    TV_receiver_city , TV_receiver_post_number , TV_receiver_post_address;

    public ImageView BTN_delete_address;

    public MyViewHolder(View view) {
        super(view);
        TV_receiver_name = (TextView) view.findViewById(R.id.TV_receiver_name);
        TV_receiver_phone_number = (TextView) view.findViewById(R.id.TV_receiver_phone_number);
        TV_receiver_state = (TextView) view.findViewById(R.id.TV_receiver_state);
        TV_receiver_city = (TextView) view.findViewById(R.id.TV_receiver_city);
        TV_receiver_post_number = (TextView) view.findViewById(R.id.TV_receiver_post_number);
        TV_receiver_post_address = (TextView) view.findViewById(R.id.TV_receiver_post_address);

        BTN_delete_address = (ImageView) view.findViewById(R.id.BTN_delete_address);
    }
}


    public AlbumsAdapterAddress(Context mContext, List<Album_Address> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public AlbumsAdapterAddress.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card_address , parent, false);

        return new AlbumsAdapterAddress.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AlbumsAdapterAddress.MyViewHolder holder, int position) {
        final Album_Address album = albumList.get(position);

        holder.TV_receiver_name.setText(album.getNameRes());
        holder.TV_receiver_phone_number.setText(album.getPhone_Number());
        holder.TV_receiver_state.setText(album.getState());
        holder.TV_receiver_city.setText(album.getCity());
        holder.TV_receiver_post_number.setText(album.getPost_number());
        holder.TV_receiver_post_address.setText(album.getPost_address());

        holder.BTN_delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.BTN_delete_address.setBackgroundColor(Color.parseColor("#E74C3C"));

                session = new SessionManager(mContext);
                HashMap<String, String> user = session.getUserDetails();
                username = user.get(SessionManager.KEY_NAME);

                String dbName = "store";
                String collectionName = username + "_address";
                String key = "post_number";
                String value = album.getPost_number();
                App42API.initialize(mContext,
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
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

}
