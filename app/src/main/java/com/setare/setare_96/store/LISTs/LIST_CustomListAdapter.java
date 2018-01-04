package com.setare.setare_96.store.LISTs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.setare.setare_96.store.LISTs.LIST_Product;
import com.setare.setare_96.store.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LIST_CustomListAdapter extends ArrayAdapter<LIST_Product> {
    ArrayList<LIST_Product> products;
    Context context;
    int resource;

    public LIST_CustomListAdapter(Context context, int resource, ArrayList<LIST_Product> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null, true);

        }
        LIST_Product product = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
        Picasso.with(context).load(product.getImage()).into(imageView);

        TextView txtName = (TextView) convertView.findViewById(R.id.list_item_name);
        txtName.setText(product.getName());

        return convertView;
    }

}
