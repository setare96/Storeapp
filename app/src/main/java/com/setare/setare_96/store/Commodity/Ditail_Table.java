package com.setare.setare_96.store.Commodity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ditail_Table extends AppCompatActivity {

    private TableLayout tableLayout;
    private TextView TV_topic;
    private TextView TV_description;
    private JSONArray ditail;
    private String topic;
    private String description;
    private String sub_index = "sub";



    private String dbName_private = "store";
    private String collectionName_private = "test";
    private String Name_private = "s8";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ditail_table);
        tableLayout=(TableLayout)findViewById(R.id.tableLayout);

        Bundle True_bl = getIntent().getExtras();
        Name_private = True_bl.getString("Name");
        collectionName_private = True_bl.getString("collectionName");
        dbName_private = True_bl.getString("dbName");

        TextView tt55 = (TextView) findViewById(R.id.tt55);
        tt55.setText(Name_private);

        Data();

    }

    public void Data () {
        String dbName = dbName_private;
        String collectionName = collectionName_private;
        String key1 = "name";
        String value1 = Name_private;

        Query q1 = QueryBuilder.build(key1, value1, QueryBuilder.Operator.EQUALS); // Build query q1 for key1 equal to name and value1 equal to Nick


        // Pass aggregated query to finder method below. Similarly you can aggregate more conditions in querying object.
        App42API.initialize(Ditail_Table.this,
                "ab01dcf124672f6451829d38aaae23fb7f8308b988a67135941d539d2df2201d",
                "94fb39c0db8d31d1ce8b39b22135e8bb82f1e3c624617b682da444bdc674c7bd");
        StorageService storageService = App42API.buildStorageService();
        storageService.findDocumentsByQuery(dbName, collectionName, q1, new App42CallBack() {
            public void onSuccess(Object response)
            {
                Storage  storage  = (Storage )response;
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
                for(int i=0;i<jsonDocList.size();i++)
                {

                    String myData;
                    JSONArray Ditail = null;
                    myData = jsonDocList.get(i).getJsonDoc();

                    try {
                        JSONObject jsonObject = new JSONObject(myData);
                        Ditail = jsonObject.getJSONArray("ditail");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ditail = Ditail;
                    try {
                        topic = ditail.getString(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Table();
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

    public void Table (){

        for (int i=0;i<(ditail.length()/2);i++){
            View tableRow = LayoutInflater.from(Ditail_Table.this).inflate(R.layout.table_item,null,false);
            TV_topic  = (TextView) tableRow.findViewById(R.id.topic);
            TV_description  = (TextView) tableRow.findViewById(R.id.description);

            try {
                topic = ditail.getString(2*i);
                description = ditail.getString((2*i)+1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if  (description.equals(sub_index)){
                TV_topic.setBackgroundColor(Color.parseColor("#AEB6BF"));
                TV_description.setBackgroundColor(Color.parseColor("#AEB6BF"));

                TV_description.setTextColor(Color.parseColor("#AEB6BF"));

                TV_topic.setText(topic);
                TV_description.setText(description);
            }else {
                TV_topic.setText(topic);
                TV_description.setText(description);
            }

            tableLayout.addView(tableRow);
        }

    }

}
