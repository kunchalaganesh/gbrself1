package com.futureproducts.gbrself.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.activities.salesorder;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.homefragments.salesorderfragment;

public class home extends Fragment {

    View vv;
    RelativeLayout salesorder, complaint;
    TextView name;
    SessionManage sessionManage;
    FrameLayout layout;
    RelativeLayout reportlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vv = inflater.inflate(R.layout.fragment_home, container, false);


        sessionManage = SessionManage.getInstance(getActivity());
        layout = vv.findViewById(R.id.childfragment);
        complaint = vv.findViewById(R.id.hcomplaint);

        salesorder = vv.findViewById(R.id.salesorder);
        reportlay = vv.findViewById(R.id.reportlay);
        name = vv.findViewById(R.id.uname);


        Log.d("tag", "checkname" + sessionManage.getUserDetails().get("UNAME"));
        name.setText(sessionManage.getUserDetails().get("UNAME"));

        salesorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new salesorderfragment(),"salesOrder").addToBackStack(null).commit();

            }
        });
        reportlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new reports()).addToBackStack(null).commit();
            }
        });
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new complaintfragment()).addToBackStack(null).commit();
            }
        });


        // Inflate the layout for this fragment
        return vv;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}