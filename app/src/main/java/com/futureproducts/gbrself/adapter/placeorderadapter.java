package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.holder.holder;
import com.futureproducts.gbrself.homefragments.salesorderfragment;
import com.futureproducts.gbrself.models.itemdetailsmodel;

import java.util.List;

public class placeorderadapter extends RecyclerView.Adapter<holder> {

    List<itemdetailsmodel> list;

    Context context;
    PAdapterCallback callback;

    public placeorderadapter(List<itemdetailsmodel> itemlist, FragmentActivity application, salesorderfragment salesorderfragment) {
        this.list = itemlist;
        this.context = application;
        this.callback = (PAdapterCallback) salesorderfragment;
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
                .inflate(R.layout.placeorderlayout,
                        parent, false);

        holder viewHolder
                = new holder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final int index = holder.getAdapterPosition();

        if (list.size() > 0) {
            holder.ocolor.setText(list.get(index).getScolor());
            holder.oqty.setText(list.get(index).getQty());
            holder.oprice.setText(list.get(index).getItem_price());
            holder.ovalue.setText(list.get(index).getValue());
            holder.oimei.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(holder.getAdapterPosition());
                }
            });
        }else {
            holder.ocolor.setText("");
            holder.oqty.setText("");
            holder.oprice.setText("");
            holder.ovalue.setText("");

        }
//        holder.oimei.setText(list.get(index).getSimei1());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface PAdapterCallback {
        void onClick(int position);
    }
}
