package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.holder.holder;

import java.util.List;

public class cartitemAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<String> imeilist;
    String lists;

    public cartitemAdapter(String product_name, Context context) {
        lists = product_name;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        String[] sub = lists.split("\n");
        for (String s : sub) {
            imeilist.add(s);
        }

    }

    public class ViewHolder {
        TextView name;
        RelativeLayout placeorder;
    }

    @Override
    public int getCount() {
        return imeilist.size();
    }

    @Override
    public Object getItem(int i) {
        return imeilist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.placeorder = (RelativeLayout) view.findViewById(R.id.places);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(imeilist.get(i));

        return view;
    }
}