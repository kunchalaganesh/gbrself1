package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.homefragments.salesorderfragment;
import com.futureproducts.gbrself.models.shoplistmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<shoplistmodel> shoplist = null;

    private ArrayList<shoplistmodel> ushop, nshop;
    AdapterCallback callback;

    public ListViewAdapter(Context context, salesorderfragment salesorder, List<shoplistmodel> shoplist) {
        mContext = context;
        this.shoplist = shoplist;
        inflater = LayoutInflater.from(mContext);
        this.ushop = new ArrayList<shoplistmodel>();
        this.ushop.addAll(shoplist);
        this.nshop = new ArrayList<>();
        this.callback = (AdapterCallback) salesorder;
    }


    public class ViewHolder {
        TextView name;
        RelativeLayout placeorder;
    }

    @Override
    public int getCount() {
        return shoplist.size();
    }

    @Override
    public Object getItem(int i) {
        return shoplist.get(i);
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
        holder.name.setText(shoplist.get(i).getName());
        holder.placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "checklistview1"+shoplist.get(i).getName());
                callback.onItemClicked(i);
            }
        });
        return view;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        nshop.clear();
        shoplist.clear();
        if (charText.length() == 0) {
            shoplist.addAll(ushop);
        } else {
//            shoplist.clear();
            for (shoplistmodel sp : ushop) {
                if (sp.getUser_unique_id().toLowerCase(Locale.getDefault()).contains(charText) ||
                        sp.getName().toLowerCase(Locale.getDefault()).contains(charText) ||
                sp.getMobile().contains(charText)) {
                    nshop.add(sp);
                }
            }
            for(shoplistmodel sp : nshop){
                if (sp.getUser_unique_id().toLowerCase(Locale.ROOT).matches(charText)) {
                    shoplist.add(0,sp);
                }else if(sp.getName().contains(charText) || sp.getMobile().contains(charText))
                {
                    shoplist.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }


    public interface AdapterCallback{
        void onItemClicked(int position);
    }
}
