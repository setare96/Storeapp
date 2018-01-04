package com.setare.setare_96.store.Buy_BOX;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.setare.setare_96.store.Buy_BOX.Address.AddressActivity;
import com.setare.setare_96.store.Commodity.Object_View;
import com.setare.setare_96.store.R;

/**
 * Created by amirhossein on 12/9/17.
 */

public class Send_OrderActivity extends AppCompatActivity{

    private String name, phone_number, code_number, home_number, state, city, post_number, post_address;
    private TextView TV_receiver_state, TV_receiver_city, TV_receiver_post_address, TV_receiver_post_number,
                     TV_receiver_phone_number, TV_receiver_home_number, TV_change_address;
    private TextView BTN_send_order;

    private RadioButton Radio_BTN_post;
    private RadioGroup radioGroup;
    private boolean FACTOR = true;
    private String Total_price;
    private String Place_Sentence;
    private String Delivery_Sentence;
    private String Factor_Sentence = "فاکتور برای این سفارش ارسال خواهد شد.";

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_order_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
        init();
        Factor();
        Change_Address();
        Sentence();

        BTN_send_order = (TextView) findViewById(R.id.BTN_send_order);
        BTN_send_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle Price_Data = new Bundle();
                Price_Data.putString("Total_price",Total_price);
                Price_Data.putString("Place_Sentence",Place_Sentence);
                Price_Data.putString("Delivery_Sentence",Delivery_Sentence);
                Price_Data.putString("Factor_Sentence",Factor_Sentence);

                Intent switch_activity = new Intent(Send_OrderActivity.this , Final_OrderActivity.class);
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


    public void getData() {
        Bundle True_bl = getIntent().getExtras();
        name = True_bl.getString("name");
        phone_number = True_bl.getString("phone_number");
        code_number = True_bl.getString("code_number");
        home_number = True_bl.getString("home_number");
        state = True_bl.getString("state");
        city = True_bl.getString("city");
        post_number = True_bl.getString("post_number");
        post_address = True_bl.getString("post_address");
        Total_price = True_bl.getString("Total_price");
    }

    public void init() {

        TV_receiver_state = (TextView) findViewById(R.id.TV_receiver_state);
        TV_receiver_state.setText(state);

        TV_receiver_city = (TextView) findViewById(R.id.TV_receiver_city);
        TV_receiver_city.setText(city);

        TV_receiver_post_address = (TextView) findViewById(R.id.TV_receiver_post_address);
        TV_receiver_post_address.setText(post_address);

        TV_receiver_post_number = (TextView) findViewById(R.id.TV_receiver_post_number);
        TV_receiver_post_number.setText(post_number);

        TV_receiver_phone_number = (TextView) findViewById(R.id.TV_receiver_phone_number);
        TV_receiver_phone_number.setText(phone_number);

        TV_receiver_home_number = (TextView) findViewById(R.id.TV_receiver_home_number);
        TV_receiver_home_number.setText(code_number + "-" + home_number);

    }

    public void Factor() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().equals("بله")){
                        Toast.makeText(Send_OrderActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                        FACTOR = true;
                        Factor_Sentence = "فاکتور برای این سفارش ارسال خواهد شد.";
                    }
                    else{
                        Toast.makeText(Send_OrderActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                        FACTOR = false;
                        Factor_Sentence = "فاکتور برای این سفارش ارسال نخواهد شد.";
                    }
                }

            }
        });

    }

    public void Change_Address() {

        TV_change_address = (TextView) findViewById(R.id.TV_change_address);
        TV_change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle Price_Data = new Bundle();
                Price_Data.putString("Total_price",Total_price);

                Intent switch_activity = new Intent(Send_OrderActivity.this , AddressActivity.class);
                switch_activity.putExtras(Price_Data);
                startActivity(switch_activity);
            }
        });

    }

    public void Sentence() {

        Place_Sentence = "این کالا به " + name + " به آدرس " + post_address + " و شماره تماس " +
                         phone_number + " تحویل میگردد.";

        Delivery_Sentence = "این سفارش از طریق پست پیشتاز با هزینه 0 تومان به شما تحویل داده میشود.";

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
