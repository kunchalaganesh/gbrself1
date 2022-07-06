package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.holder.holder;
import com.futureproducts.gbrself.homefragments.salesorderfragment;
import com.futureproducts.gbrself.models.itemdetailsmodel;

import java.util.List;

public class imeiselectAdapter extends RecyclerView.Adapter<imeiselectAdapter.VH>{



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
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imeiselectlayout,
                        null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class VH extends RecyclerView.ViewHolder{

        private final TextView limeimname,limei1;
        private final ImageView limeiclose;

        public VH(@NonNull View itemView) {
            super(itemView);

            limeimname = itemView.findViewById(R.id.limeimname);
            limei1 = itemView.findViewById(R.id.limei1);
            limeiclose = itemView.findViewById(R.id.limeiclose);

        }

        void bind(itemdetailsmodel item){


            limeimname.setText(item.getScolor());
            limei1.setText(item.getSimei1());
            Log.d("ttt","binding: "+item.getSimei1());
            limeiclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.checked(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
    }

    public interface IAdapterCallback{
        void checked(int position);
    }
}
