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
import android.widget.Toast;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.adapter.OrdersAdapter;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.models.likes;
import com.futureproducts.gbrself.models.newOrder;
import com.futureproducts.gbrself.models.njorder;
import com.futureproducts.gbrself.models.njorder1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.sql.StatementEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class orders extends Fragment {
    View v;
    RecyclerView recyclerView;
    Apiinterface apiinterface;
    SessionManage sessionManage;
//    ArrayList<List<likes>> orderlist;
    ArrayList<List<njorder1>> orderlist;
    OrdersAdapter ordersAdapter;
    ProgressDialog dialog;
    ArrayList<likes> nlist;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = v.findViewById(R.id.orderrecycler);
        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        sessionManage = SessionManage.getInstance(getActivity());
//        orderlist = new ArrayList<List<likes>>();
        orderlist = new ArrayList<List<njorder1>>();
        dialog = new ProgressDialog(getActivity());
        nlist = new ArrayList<>();
        dialog.setTitle("Loading Order Please wait");

        sessionManage.getUserDetails().get("DISTRIBUTOR_NAME");
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        long m = TimeUnit.DAYS.toMillis(90);
        long m1 = System.currentTimeMillis();
        long m2 = m1-m;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String bdate = formatter.format(m2);
        Log.d("tag", "checkdate"+ currentDate+"  "+bdate);

        readorders(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME"), bdate, currentDate);
        // Inflate the layout for this fragment
        //what is the base url ?
//        ok i will make demookay
        return v;
    }

    private void readorders(String dse, String s, String s1) {

        Log.d("tag", "myorders1"+dse+" "+s+" "+s);
        dialog.show();
        Call<njorder> call = apiinterface.getOrders(dse, s, s1);
        call.enqueue(new Callback<njorder>() {
            @Override
            public void onResponse(Call<njorder> call, Response<njorder> response) {
                Log.e("tag", "checkreponse123"+response.body());//.getList());

                dialog.dismiss();
                for(int i=0; i<response.body().list.size(); i++){
                    for(int j = i+1; j<response.body().list.size(); j++){

                        if(response.body().list.get(i).getOrder_no().matches(response.body().list.get(j).getOrder_no())){
                            Log.d("tag", "checkingorderno   "+  response.body().list.get(i).getOrder_no()+"   "+ response.body().list.get(i).imei);
                            String m = response.body().list.get(i).getProduct_name();
                            String mm = response.body().list.get(j).getProduct_name();
                            String mq = response.body().list.get(i).getQty();
                            String mmq = response.body().list.get(j).getQty();
                            String m11 = m+" ("+mq+")";
                            String m2 = mm+" ("+mmq+")";

                            String m1 = m11+"\n"+m2;
                            response.body().list.get(i).setProduct_name(m1);
//                            j--;
                            response.body().list.remove(j);
                            j--;
//                            response.body().getList().get(i).

                        }

                    }
//                orderlist.add(response.body().getList());
                }
                ordersAdapter = new OrdersAdapter(getActivity(), response.body().list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(ordersAdapter);
//                for(int j=0; j<orderlist.size(); j++) {
//                    Log.d("tag", "myoutput"+orderlist.get(j).get(j).getDis_name());
//                }


            }

            @Override
            public void onFailure(Call<njorder> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.d("tag", "checkjson resopnse"+ new Gson().toJson(response.body()));
//                new myordersss(new Gson().toJson(response.body().)
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//
//            }
//        });

//        Log.d("tag", "checkorderresponse"+dse+" "+s+" "+s1);
//        Call call = apiinterface.myorders(dse, s, s1);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
//                JSONArray jsonObject = null;
//                try {
//                    jsonObject = new JSONArray(new Gson().toJson(response.body()));
//                    String error = jsonObject.getString("error");
//                    String message = jsonObject.getString("message");
//
//                    if(error.matches("false")) {
//
//                        JSONArray jsonarray = new JSONArray(jsonObject);
//
//                        for(int i=0; i < jsonarray.length(); i++) {
//                            JSONArray jsonobject = jsonarray.getJSONArray(i);
//                            Log.d("tag", "checkingorderid"+jsonobject.getString(i));
//
//                        }
////                        JSONObject jsonObject1 = new JSONObject(order_details);
////                        String orderno = jsonObject1.optString("order_no");
////                        String ordertotal = jsonObject1.optString("order_total");
////                        String ret_name = jsonObject1.optString("ret_name");
////                        String pname = jsonObject1.optString("product_name");
////                        String sku = jsonObject1.optString("sku");
////                        String brand = jsonObject1.optString("brand");
////                        String qty = jsonObject1.optString("qty");
////                        String price = jsonObject1.optString("price");
////                        String itemtotal = jsonObject1.optString("item_total");
////
////                        orderlist.add(new myordersmodel(orderno, ordertotal, ret_name, pname, sku,
////                                brand, qty, price, itemtotal));
////                        ordersAdapter = new OrdersAdapter(getActivity(), orderlist);
////                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////                        recyclerView.setAdapter(ordersAdapter);
//
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//
//            }
//        });


    }
}