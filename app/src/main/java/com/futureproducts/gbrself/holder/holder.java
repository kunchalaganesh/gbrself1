package com.futureproducts.gbrself.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.futureproducts.gbrself.R;

public class holder extends RecyclerView.ViewHolder {
    public TextView  ocolor, oqty, oprice, ovalue, oimei, llmarket, llimei1, llimei2;
    public ImageView llclose;
    public RelativeLayout llinearLayout;
    public TextView myousername, myostatus, myodate, myototal, mytname, mytdate, mytstatus, mytdesp;
    public TextView orderlist, orederno;
    public ImageView mytdownload;


    View view;


    public holder(@NonNull View itemView) {
        super(itemView);

        ocolor = itemView.findViewById(R.id.ocolor);
        oqty = itemView.findViewById(R.id.oqty);
        oprice = itemView.findViewById(R.id.oprice);
        ovalue = itemView.findViewById(R.id.ovalue);
        oimei = itemView.findViewById(R.id.oimei);

        llmarket = itemView.findViewById(R.id.limeimname);
        llimei1 = itemView.findViewById(R.id.limei1);
        llclose = itemView.findViewById(R.id.limeiclose);
        llinearLayout = itemView.findViewById(R.id.lmain);

        myousername = itemView.findViewById(R.id.order_username);
        myostatus = itemView.findViewById(R.id.order_status);
        myodate = itemView.findViewById(R.id.placed_on);
        myototal = itemView.findViewById(R.id.order_amnt);
        orderlist = itemView.findViewById(R.id.orderlist);
        orederno = itemView.findViewById(R.id.item_orderno);

        mytname = itemView.findViewById(R.id.mytname);
        mytdate = itemView.findViewById(R.id.item_orderno1);
        mytstatus = itemView.findViewById(R.id.order_status1);
        mytdownload = itemView.findViewById(R.id.order_amnt1);
        mytdesp = itemView.findViewById(R.id.orderlist1);




        view = itemView;
    }
}
