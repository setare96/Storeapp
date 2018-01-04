package com.setare.setare_96.store.Buy_BOX;

import android.content.Intent;
import android.net.Uri;
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

import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.R;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by amirhossein on 12/13/17.
 */

public class Pay_Order_Activity extends AppCompatActivity{

    private String Total_price;
    private String dbName;
    private String collectionName;
    private int phone_price;
    private String phone_price_p;
    private int phone_buynumber = 1;
    private int total_price = 0;

    private Number number;
    private Number numbert;

    private SessionManager session;
    private String username = "Hoda :)";

    private Toolbar toolbar;
    private RadioGroup radioGroup;

    private TextView TV_total_price_pay , BTN_send_pay;

    private boolean option_pay = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_order_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        dbName = "store";
        collectionName = username + "_buy";

//        getData();
        setTotal_price();
        Option_Pay();
        Cheack_Payment();

        BTN_send_pay = (TextView) findViewById(R.id.BTN_send_pay);
        BTN_send_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option_pay) {
                    myPayment(numbert.longValue());
                }
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

//    public void getData() {
//        Bundle True_bl = getIntent().getExtras();
//        Total_price = True_bl.getString("Total_price");
//    }
    public void setTotal_price() {

        App42API.initialize(Pay_Order_Activity.this,
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

                            phone_price_p = phonePrice;
                            phone_buynumber = phonebuyNumber;

                            NumberFormat format = NumberFormat.getInstance(Locale.US);
                            try {
                                number = format.parse(phone_price_p);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            total_price += phone_buynumber * number.intValue();
                            Total_price = NumberFormat.getNumberInstance(Locale.US).format(total_price) + " تومان ";
                            setData();

                        }
                    }
                });

            }
            public void onException(Exception ex) {}
        });

    }

    public void setData() {
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        try {
            numbert = format.parse(Total_price);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TV_total_price_pay = (TextView) findViewById(R.id.TV_total_price_pay);
        TV_total_price_pay.setText(Total_price);

    }

    public void Option_Pay() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getId() == R.id.Radio_BTN_ZarinPal){
                        Toast.makeText(Pay_Order_Activity.this, "پرداخت با زرین پال", Toast.LENGTH_SHORT).show();
                        option_pay = true;
                    }
                    else{
                        Toast.makeText(Pay_Order_Activity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                        option_pay = false;
                    }
                }

            }
        });

    }

    private void myPayment(Long amount) {

        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment = ZarinPal.getPaymentRequest();

        payment.setMerchantID("dce61b88-e01a-11e7-b5a1-005056a205be");
        payment.setAmount(amount);
        payment.setDescription("خرید از استور");
        payment.setCallbackURL("return://zarinpalpayment");

        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {

                if(status == 100)
                    startActivity(intent);
                else
                    Toast.makeText(Pay_Order_Activity.this , "خطا در ارتباط" , Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void Cheack_Payment() {

        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
                if(isPaymentSuccess)
                    Toast.makeText(Pay_Order_Activity.this ,  "پرداخت موفق" + refID , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Pay_Order_Activity.this , "پرداخت ناموفق" , Toast.LENGTH_SHORT).show();
            }
        });

    }

}