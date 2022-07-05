package com.futureproducts.gbrself.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.homefragments.salesorderfragment;

import java.util.ArrayList;
import java.util.List;

public class cartimeisAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    private List<String> imeilist;
    cartcallback callback;
    int finp;
//    private ArrayList<shoplistmodel> ushop;
//    ListViewAdapter.AdapterCallback callback;

    public cartimeisAdapter(ArrayList<String> ss, Context activity, salesorderfragment salesorderfragment, int position) {
        mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.callback = (cartcallback) salesorderfragment;
        imeilist = ss;
        finp = position;
    }
    public class ViewHolder {
        TextView name;
        ImageView rimei;
//        RelativeLayout placeorder;
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
            holder.rimei = view.findViewById(R.id.rimei);
            holder.rimei.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onimeicross(finp, i);
                }
            });
//            holder.placeorder = (RelativeLayout) view.findViewById(R.id.places);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText((getItemId(i)+1)+"    "+    imeilist.get(i));
        return view;
    }

    public interface cartcallback{
        void onimeicross(int position, int ipos);
    }
}
