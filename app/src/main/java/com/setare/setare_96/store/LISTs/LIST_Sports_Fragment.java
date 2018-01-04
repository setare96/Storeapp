package com.setare.setare_96.store.LISTs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.setare.setare_96.store.Albums.AlbumsAdapterVertical;
import com.setare.setare_96.store.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by amirhossein on 11/3/17.
 */

public class LIST_Sports_Fragment extends Fragment {

    ArrayList<LIST_Product> arrayList;
    ListView lv;
    Intent switch_activity;
    String collectionName;
    String dbName;
    Bundle Album_Data;

    public LIST_Sports_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);

    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        arrayList = new ArrayList<>();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("https://api.myjson.com/bins/t05pr");
            }
        });

        View v = inflater.inflate(R.layout.list_sports_fragment, container,false);
        lv = (ListView) v.findViewById(R.id.listView_sports);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + position);
                switch (position){
                    case 0:
                        Album_Data = new Bundle();

                        dbName = "store";
                        collectionName = "sports-wear";

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","پوشاک ورزشی");

                        switch_activity = new Intent(getActivity() , AlbumsAdapterVertical.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);
                        break;
                    case 1:
                        Album_Data = new Bundle();

                        dbName = "store";
                        collectionName = "sporting-goods";

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","لوازم ورزشی");

                        switch_activity = new Intent(getActivity() , AlbumsAdapterVertical.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);
                        break;
                    case 2:
                        Album_Data = new Bundle();

                        dbName = "store";
                        collectionName = "bike";

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","دوچرخه و لوازم جانبی");

                        switch_activity = new Intent(getActivity() , AlbumsAdapterVertical.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);
                        break;
                    case 3:
                        Album_Data = new Bundle();

                        dbName = "store";
                        collectionName = "travel-equipment";

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","تجهیزات سفر");

                        switch_activity = new Intent(getActivity() , AlbumsAdapterVertical.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);
                        break;
                    case 4:
                        Album_Data = new Bundle();

                        dbName = "store";
                        collectionName = "toy";

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","اسباب بازی");

                        switch_activity = new Intent(getActivity() , AlbumsAdapterVertical.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);
                        break;
                    case 5:
                        Album_Data = new Bundle();

                        dbName = "store";
                        collectionName = "pets";

                        Album_Data.putString("dbName",dbName);
                        Album_Data.putString("collectionName",collectionName);
                        Album_Data.putString("sub_album","حیوانات خانگی");

                        switch_activity = new Intent(getActivity() , AlbumsAdapterVertical.class);
                        switch_activity.putExtras(Album_Data);
                        startActivity(switch_activity);
                        break;
                }
            }

        });

        return v;
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("products");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new LIST_Product(
                            productObject.getString("image"),
                            productObject.getString("name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LIST_CustomListAdapter adapter = new LIST_CustomListAdapter(
                    getActivity().getApplicationContext(), R.layout.list_item, arrayList
            );
            lv.setAdapter(adapter);
        }
    }


    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
