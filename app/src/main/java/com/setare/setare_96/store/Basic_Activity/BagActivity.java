package com.setare.setare_96.store.Basic_Activity;

import android.app.Activity;
import android.os.Bundle;

import com.setare.setare_96.store.R;

/**
 * Created by amirhossein on 10/17/17.
 */

public class BagActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
