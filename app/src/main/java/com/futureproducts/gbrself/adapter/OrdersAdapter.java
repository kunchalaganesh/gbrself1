package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.holder.holder;
import com.futureproducts.gbrself.models.likes;
import com.futureproducts.gbrself.models.myordersmodel;
import com.futureproducts.gbrself.models.njorder;
import com.futureproducts.gbrself.models.njorder1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<holder>{

    ArrayList<njorder1> list;

    Context context;
    public OrdersAdapter(Context context, ArrayList<njorder1> itemlist) {
        this.list = itemlist;
        this.context = context;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.item_order_list,
                        parent, false);

        holder viewHolder
                = new holder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final int p = holder.getAdapterPosition();
        holder.myousername.setText(list.get(p).getRet_name());
        holder.myostatus.setText(list.get(p).getStatus());

        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "ddMMMyyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(list.get(p).getTime());
            String str = outputFormat.format(date);
            holder.myodate.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.orederno.setText("Order no  " +list.get(p).getOrder_no());

        holder.myototal.setText(list.get(p).getOrder_total());
        holder.orderlist.setText(list.get(p).getProduct_name());
//        cartitemAdapter cadapter;
//        cadapter = new cartitemAdapter(list.get(p).getProduct_name(), context);
//        holder.orderlist.setAdapter((cartitemAdapter) cadapter);


        Log.d("tag", "checkorderno"+list.get(p).getProduct_name());
//        holder.myostatus.setText(list.get(p).get);
    }

    @Override
    public int getItemCount() {
         return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
