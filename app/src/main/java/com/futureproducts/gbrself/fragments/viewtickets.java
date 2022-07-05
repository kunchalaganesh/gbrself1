package com.futureproducts.gbrself.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.adapter.OrdersAdapter;
import com.futureproducts.gbrself.adapter.myticketsadapter;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.models.njorder1;
import com.futureproducts.gbrself.viewtickets.SupportTicket;
import com.futureproducts.gbrself.viewtickets.myticketmodel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewtickets extends Fragment {


    View v;
    RecyclerView recyclerView;
    Apiinterface apiinterface;
    SessionManage sessionManage;
    //    ArrayList<List<likes>> orderlist;
    ArrayList<List<njorder1>> orderlist;
    OrdersAdapter ordersAdapter;
    ProgressDialog dialog;
    ArrayList<List<SupportTicket>> nlist;
    myticketsadapter myticketadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_viewtickets, container, false);
        recyclerView = v.findViewById(R.id.orderrecycler);
        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        sessionManage = SessionManage.getInstance(getActivity());
//        orderlist = new ArrayList<List<likes>>();
        orderlist = new ArrayList<List<njorder1>>();
        dialog = new ProgressDialog(getActivity());
        nlist = new ArrayList<List<SupportTicket>>();
        dialog.setTitle("Loading Order Please wait");

        sessionManage.getUserDetails().get("DISTRIBUTOR_ID");
        readtickets(sessionManage.getUserDetails().get("DISTRIBUTOR_ID"));
        // Inflate the layout for this fragment
        return v;
    }

    private void readtickets(String dse_id) {

        Call<myticketmodel> call = apiinterface.gettickets(dse_id);
        call.enqueue(new Callback<myticketmodel>() {
            @Override
            public void onResponse(Call<myticketmodel> call, Response<myticketmodel> response) {
                Log.d("tag", "checkrepo   "+response.body().getSupport_tickets());

                for(int i=0; i<response.body().getSupport_tickets().size(); i++){
                    Log.d("tag", "checkviewticket1 "+response.body().getSupport_tickets().get(i).getTime());
                }
                myticketadapter = new myticketsadapter(getActivity(), response.body().getSupport_tickets(), viewtickets.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(myticketadapter );
                nlist.add(response.body().getSupport_tickets());
            }

            @Override
            public void onFailure(Call<myticketmodel> call, Throwable t) {

            }
        });

    }


}