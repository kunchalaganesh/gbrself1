package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.util.Log;
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

public class imeiselectAdapter extends RecyclerView.Adapter<holder>{



    List<itemdetailsmodel> list;

    Context context;
    IAdapterCallback callback;

    public imeiselectAdapter(List<itemdetailsmodel> itemlist, FragmentActivity application, salesorderfragment salesorderfragment) {
        this.list = itemlist;
        this.context = application;
        this.callback = (IAdapterCallback) salesorderfragment;
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
                .inflate(R.layout.imeiselectlayout,
                        null, false);

        holder viewHolder
                = new holder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final int index = holder.getAdapterPosition();

        Log.d("tag", "checkimeis"+list.get(position).getSimei1());
//        holder.limei2.setText(list.get(position).getSimei1());
        holder.llimei1.setText(list.get(position).getSimei1());
//        holder.llimei2.setText(list.get(position).getSimei2());
        holder.llmarket.setText(list.get(position).getScolor());
        holder.llclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.checked(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface IAdapterCallback{
        void checked(int position);
    }
}
