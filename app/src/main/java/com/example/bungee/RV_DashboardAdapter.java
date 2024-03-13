package com.example.bungee;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RV_DashboardAdapter extends BaseAdapter {

    Context c;
    List<ProductData> pdata;
    LayoutInflater inflater;
    Dialog d;

    public RV_DashboardAdapter(Context c, List<ProductData> pdata) {
        this.c = c;
        this.pdata = pdata;
    }

    @Override
    public int getCount() {
        return pdata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(c).inflate(R.layout.dashboard_container, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView price = convertView.findViewById(R.id.price);
        TextView sold = convertView.findViewById(R.id.sold);
        ImageView iv = convertView.findViewById(R.id.iv);

        ProductData productData = pdata.get(position);

        name.setText(productData.getName());
        price.setText(String.valueOf(productData.getPrice()));
        sold.setText(String.valueOf(productData.getSold()));
        iv.setImageURI(productData.getImage());

        convertView.setOnClickListener(v->{
            ProductData productDataCopy = pdata.get(position);
            int[] currcount = {0};
            d = new Dialog(c);
            d.setContentView(R.layout.card_dialog);
            d.setCancelable(true);

            TextView dgname = d.findViewById(R.id.itemName);
            TextView dgprice = d.findViewById(R.id.itemPrice);
            TextView dgstock = d.findViewById(R.id.itemStock);
            ImageView pos = d.findViewById(R.id.add);
            ImageView minus = d.findViewById(R.id.minus);
            TextView count = d.findViewById(R.id.stocknum);
            ImageView img = d.findViewById(R.id.ivcard);

            img.setImageURI(productDataCopy.getImage());
            dgname.setText(productDataCopy.getName());
            dgprice.setText(String.valueOf(productDataCopy.getPrice()));
            dgstock.setText(String.valueOf(productDataCopy.getSold()));

            pos.setOnClickListener(vi -> {
                if (currcount[0] < productDataCopy.getQuantity()) {
                    count.setText(String.valueOf(++currcount[0]));
                }
            });
            minus.setOnClickListener(vi->{
                if (currcount[0] > 0){
                    count.setText(String.valueOf(--currcount[0]));
                }
            });

            d.show();
        });

        return convertView;
    }
}
