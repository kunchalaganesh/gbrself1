package com.futureproducts.gbrself.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.adapter.ListViewAdapter;
import com.futureproducts.gbrself.adapter.placeorderadapter;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.models.itemdetailsmodel;
import com.futureproducts.gbrself.models.shoplistmodel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class salesorder extends AppCompatActivity implements SearchView.OnQueryTextListener, ListViewAdapter.AdapterCallback {
    Apiinterface apiinterface;
    List<shoplistmodel> list;
    List<itemdetailsmodel> itemlist;
    String id, user_unique_id, name, email, mobile, user_type, password, country_id, country_name, governate_id, governate,
            city, locality, locality_ar, locality_id, time, address, login_id, login_otp, outlet_name, login_time, profile_update,
            signup_type, social_auth_token, img_url, seller_id, seller_name, backend_register, backend_verify, user_profile_verified,
            last_login, active_status, device_token;

    ListView listview;
    ListViewAdapter adapter;
    SearchView editsearch;
    TextView tshopname, tcode, taddress, model, imei1, imei2;
    Button gotoorder, addphone, submit;
    LinearLayout scan;
    SessionManage sessionManage;
    String distributor_id;
    placeorderadapter padapter;
    RecyclerView recyclerView;
    JsonObject orderPlace = new JsonObject();
    JsonArray OPjsonArray = new JsonArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorder);


        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        list = new ArrayList<>();
        itemlist = new ArrayList<>();
        listview = findViewById(R.id.listview);
        editsearch = findViewById(R.id.search);
        tshopname = findViewById(R.id.shopname);
        tcode = findViewById(R.id.code);
        taddress = findViewById(R.id.address);
        gotoorder = findViewById(R.id.gotoshopbtn);
        scan = findViewById(R.id.scan);
        sessionManage = SessionManage.getInstance(this);
        model = findViewById(R.id.model);
        imei1 = findViewById(R.id.imei1);
        imei2 = findViewById(R.id.imei2);
        addphone = findViewById(R.id.addphone);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        submit = findViewById(R.id.submit);

        distributor_id = sessionManage.getUserDetails().get("DISTRIBUTOR_ID");

        editsearch.setFocusable(false);
        gotoorder.setEnabled(false);
        scan.setEnabled(false);
        addphone.setFocusable(false);
        scan.setBackgroundColor(Color.parseColor("#EDEDED"));

//        scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(salesorder.this);
////                builder.setTitle("Please choose 1 option");
//                builder.setMessage("Please choose 1 option");
//
//                // add a button
//                builder.setPositiveButton("SCAN", null);
//                builder.setNegativeButton("Enter IMEI manually", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AlertDialog.Builder alert = new AlertDialog.Builder(salesorder.this);
//                        final EditText edittext = new EditText(salesorder.this);
//                        alert.setMessage("Enter IMEI Number");
////                        alert.setTitle("Enter Your Title");
//
//                        alert.setView(edittext);
//
//                        alert.setPositiveButton("enter", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                //What ever you want to do with the value
//                                Editable YouEditTextValue = edittext.getText();
//                                //OR
//                                String imeivalue = edittext.getText().toString();
//                                if (imeivalue.matches("")) {
//                                    editsearch.setFocusable(false);
//                                    Toast.makeText(salesorder.this, "Please enter imei number", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    editsearch.setFocusable(false);
//                                    Call call = apiinterface.checkimei(imeivalue, distributor_id, country_id);
//                                    call.enqueue(new Callback() {
//                                        @Override
//                                        public void onResponse(Call call, Response response) {
//                                            Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
//                                            JSONObject jsonObject = null;
//
//                                            try {
//                                                jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                                                String error = jsonObject.getString("error");
//                                                String message = jsonObject.getString("message");
//
//                                                if (error.matches("true")) {
//                                                    addphone.setFocusable(true);
//
//                                                    String user_detail = jsonObject.getString("list");
//                                                    Toast.makeText(salesorder.this, "t" + user_detail, Toast.LENGTH_SHORT).show();
//
//                                                    JSONObject jsonObject1 = new JSONObject(user_detail);
//                                                    String smodel = jsonObject1.optString("model");
//                                                    String scolor = jsonObject1.optString("marketing_name");
//                                                    String simei1 = jsonObject1.optString("imei");
//                                                    String simei2 = jsonObject1.optString("imei2");
//                                                    String item_price = jsonObject1.optString("item_price");
//                                                    String srno = jsonObject1.optString("srno");
//                                                    String sku = jsonObject1.optString("sku");
//                                                    String brand = jsonObject1.optString("brand");
//                                                    String seconday_direct = jsonObject1.optString("seconday_direct");
//                                                    String tertialry_direct = jsonObject1.optString("tertialry_direct");
//
//                                                    addphone.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View view) {
//
////                                                            if(itemlist.size()>0){
////                                                                for(int i = 0; i<itemlist.size(); i++){
////                                                                    if(itemlist.get(i).getSmodel().matches(smodel)){
////
////                                                                    }
////                                                                }
////
////
////                                                            }else {
//                                                            itemlist.add(new itemdetailsmodel(smodel, scolor, simei1, simei2,
//                                                                    item_price, srno, sku, brand, seconday_direct, tertialry_direct));
//                                                            model.setText("");
////                                                    color.setText(scolor);
//                                                            imei1.setText("");
//                                                            imei2.setText("");
//                                                            addphone.setFocusable(false);
////                                                            }
//                                                            Log.d("tag", "checklist1" + itemlist.size());
//                                                            padapter = new placeorderadapter(itemlist, getApplication());
//                                                            recyclerView.setAdapter(padapter);
//                                                            recyclerView.setLayoutManager(new LinearLayoutManager(salesorder.this));
//
//                                                        }
//                                                    });
//
//                                                    submit.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View view) {
//                                                            orderPlace.addProperty("ret_id", id);
//                                                            orderPlace.addProperty("ret_name", name);
//                                                            orderPlace.addProperty("country_id", country_id);
//                                                            orderPlace.addProperty("country_name", country_name);
//                                                            orderPlace.addProperty("locality_id", locality_id);
//                                                            orderPlace.addProperty("locality_name", locality);
//                                                            orderPlace.addProperty("ret_mobile", mobile);
//                                                            orderPlace.addProperty("address", address);
//                                                            orderPlace.addProperty("pretotal", "2");
//                                                            orderPlace.addProperty("order_total", "2");
//                                                            orderPlace.addProperty("discount", "2");
//                                                            orderPlace.add("items", OPjsonArray);
////                                                            String json = sessionManage.getUserDetails().get("CARD_DATA");
//
//                                                            orderPlace.addProperty("dis_id", distributor_id);
//                                                            orderPlace.addProperty("dis_name", seller_name);
//                                                            orderPlace.addProperty("product_ivt_id", scolor);
//                                                            orderPlace.addProperty("product_id", smodel);
//                                                            orderPlace.addProperty("product_name", brand);
//                                                            orderPlace.addProperty("imei", String.valueOf(imei1));
//                                                            orderPlace.addProperty("qty", "2");
//                                                            orderPlace.addProperty("actual_price", item_price);
//                                                            orderPlace.addProperty("discount_price", "0");
//                                                            orderPlace.addProperty("item_total", "2");
//
//
//                                                            Call call = apiinterface.buyproduct(orderPlace);
//                                                            call.enqueue(new Callback() {
//                                                                @Override
//                                                                public void onResponse(Call call, Response response) {
//
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call call, Throwable t) {
//
//                                                                }
//                                                            });
//                                                        }
//                                                    });
//
//
//                                                    Log.d("tag", "checklist" + itemlist.size());
//
//                                                    model.setText(scolor);
////                                                    color.setText(scolor);
//                                                    imei1.setText(simei1);
//                                                    imei2.setText(simei2);
//
//
//                                                }
//
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                                Toast.makeText(salesorder.this, "error" + e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call call, Throwable t) {
//
//                                        }
//                                    });
//
//                                }
//                            }
//                        });
//
//                        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                // what ever you want to do with No option.
//                            }
//                        });
//
//                        alert.show();
//                    }
//                });
//
//                // create and show the alert dialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            }
//        });
//        gotoorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editsearch.setFocusable(false);
//                scan.setEnabled(true);
//                scan.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.search));
//                Toast.makeText(salesorder.this, "clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        Call call = apiinterface.Shoplist("2");
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
//
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                    String error = jsonObject.getString("error");
//                    String message = jsonObject.getString("message");
//
//                    if (error.equals("false")) {
//
////                        String all_shop_list = jsonObject.getString("all_shop_list");
//                        JSONArray jsonArray = jsonObject.getJSONArray("all_shop_list");
////                        JSONObject jsonObject1 = new JSONObject(all_shop_list);
//
//                        if (jsonArray.length() > 0) {
//
//                            list.clear();
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                id = object.optString("id");
//                                user_unique_id = object.optString("user_unique_id");
//                                name = object.optString("name");
//                                email = object.optString("email");
//                                mobile = object.optString("mobile");
//                                user_type = object.optString("user_type");
//                                password = object.optString("password");
//                                country_id = object.optString("country_id");
//                                country_name = object.optString("country_name");
//                                governate_id = object.optString("governate_id");
//                                governate = object.optString("governate");
//                                city = object.optString("city");
//                                locality = object.optString("locality");
//                                locality_ar = object.optString("locality_ar");
//                                locality_id = object.optString("locality_id");
//                                time = object.optString("time");
//                                address = object.optString("address");
//                                login_id = object.optString("login_id");
//                                login_otp = object.optString("login_otp");
//                                outlet_name = object.optString("outlet_name");
//                                login_time = object.optString("login_time");
//                                profile_update = object.optString("profile_update");
//                                signup_type = object.optString("signup_type");
//                                social_auth_token = object.optString("social_auth_token");
//                                img_url = object.optString("img_url");
//                                seller_id = object.optString("seller_id");
//                                seller_name = object.optString("seller_name");
//                                backend_register = object.optString("backend_register");
//                                backend_verify = object.optString("backend_verify");
//                                user_profile_verified = object.optString("user_profile_verified");
//                                last_login = object.optString("last_login");
//                                active_status = object.optString("active_status");
//                                device_token = object.optString("device_token");
//
//
//                                list.add(new shoplistmodel(id,
//                                        user_unique_id, name, email, mobile, user_type, password, country_id, country_name,
//                                        governate_id, governate, city, locality, locality_ar, locality_id, time, address,
//                                        login_id, login_otp, outlet_name, login_time, profile_update, signup_type,
//                                        social_auth_token, img_url, seller_id, seller_name, backend_register, backend_verify,
//                                        user_profile_verified, last_login, active_status, device_token));
//                            }
//                            adapter = new ListViewAdapter(getApplicationContext(), salesorder.this, list);
//                            listview.setAdapter(adapter);
//                        }
//                        editsearch = (SearchView) findViewById(R.id.search);
//                        editsearch.setOnQueryTextListener(salesorder.this);
////
////                        String id = jsonObject1.getString("id");
//
//                        Log.d("tag", "all_shop_list11" + list.get(1));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//
//            }
//        });


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        adapter.filter(text);
        listview.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onItemClicked(int position) {

        editsearch.setQuery("", false);
//        editsearch.setFocusable(false);
        listview.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        Toast.makeText(this, "ppos" + position, Toast.LENGTH_SHORT).show();
        tshopname.setText(list.get(position).getName());
        tcode.setText(list.get(position).getCountry_id());
        taddress.setText(list.get(position).getAddress());
        gotoorder.setEnabled(true);

    }

    public void hideKeyboard(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editsearch.setFocusable(false);
    }
}