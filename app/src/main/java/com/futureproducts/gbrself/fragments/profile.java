package com.futureproducts.gbrself.fragments;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class profile extends Fragment {


    View v;
    ImageView profile;
    EditText name, phone, mail, address, dse;
    Apiinterface apiinterface;
    SessionManage sessionManage;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_profile, container, false);

        profile = v.findViewById(R.id.profile_image);
        name = v.findViewById(R.id.et_name);
        phone = v.findViewById(R.id.et_phone);
        mail = v.findViewById(R.id.et_email);
        address = v.findViewById(R.id.et_address);
        dse = v.findViewById(R.id.et_dse);
        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        sessionManage = SessionManage.getInstance(getActivity());
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading Profile Please wait");
        dialog.show();

        readprofile(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME"));

        // Inflate the layout for this fragment
        return v;
    }

    private void readprofile(String loginid) {

        Call call = apiinterface.myprofile(loginid);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                JSONObject jsonObject = null;

                    try {
                        jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String error = jsonObject.getString("error");
                        String message = jsonObject.getString("message");

                        if(error.matches("false")) {
                            String profile = jsonObject.getString("user_detail");
                            JSONObject jsonObject1 = new JSONObject(profile);
                            String sname = jsonObject1.optString("name");
                            String sphone = jsonObject1.optString("mobile");
                            String semail = jsonObject1.optString("email");
                            String saddress = jsonObject1.optString("address");
                            String sdse = jsonObject1.optString("dis_name");

                            name.setText(sname);
                            phone.setText(sphone);
                            mail.setText(semail);
                            address.setText(saddress);
                            dse.setText(sdse);
                            dialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


    }
}