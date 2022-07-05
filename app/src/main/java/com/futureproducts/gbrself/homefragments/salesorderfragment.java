package com.futureproducts.gbrself.homefragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.adapter.ListViewAdapter;
import com.futureproducts.gbrself.adapter.cartimeisAdapter;
import com.futureproducts.gbrself.adapter.imeiselectAdapter;
import com.futureproducts.gbrself.adapter.placeorderadapter;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.apisupport.BackButtonHandlerInterface;
import com.futureproducts.gbrself.apisupport.OnBackClickListener;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.barcode.BarCodeScannerViewModel;
import com.futureproducts.gbrself.models.ItemXX;
import com.futureproducts.gbrself.models.itemdetailsmodel;
import com.futureproducts.gbrself.models.ordermodel1;
import com.futureproducts.gbrself.models.ordermodel2;
import com.futureproducts.gbrself.models.shoplistmodel;
import com.futureproducts.gbrself.ui.ScannerFragment;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class salesorderfragment extends Fragment implements SearchView.OnQueryTextListener, ListViewAdapter.AdapterCallback,
        placeorderadapter.PAdapterCallback, imeiselectAdapter.IAdapterCallback, cartimeisAdapter.cartcallback, OnBackClickListener {
    Apiinterface apiinterface;
    List<shoplistmodel> list;
    ArrayList<itemdetailsmodel> finallist;
    ArrayList<itemdetailsmodel> scanedlist, nscanedlist, n1scanedlist;
    String id, user_unique_id, name, email, mobile, user_type, password, country_id, country_name, governate_id, governate,
            city, locality, locality_ar, locality_id, time, address, login_id, login_otp, outlet_name, login_time, profile_update,
            signup_type, social_auth_token, img_url, seller_id, seller_name, backend_register, backend_verify, user_profile_verified,
            last_login, active_status, device_token, wallet_amount;

    ListView listview;
    ListViewAdapter adapter;
    imeiselectAdapter imeiselectAdapter;
    SearchView editsearch;
    TextView tshopname, tcode, taddress, model, imei1, imei2;
    TextView gotoorder, addphone, submit;
    LinearLayout scan, imeihold, orderl;
    SessionManage sessionManage;
    String distributor_id, totalamount;
    placeorderadapter padapter;
    RecyclerView recyclerView, imeilistrecyclerview;
    RelativeLayout search_bar;
    JsonObject orderPlace = new JsonObject();
    JSONObject MainjsonObject = new JSONObject();
    JsonArray OPjsonArray = new JsonArray();
    private final String[] neededPermissions = new String[]{Manifest.permission.CAMERA};
    public static final int REQUEST_CODE = 100;
    ScannerFragment scannerFragment;
    View customLayout;
    private BarCodeScannerViewModel mViewModel;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    private static final String TAG = "BarCodeScannerFragment";
    String intentData = "";
    SurfaceView surfaceView;
    TextView barcodeValue, salesname, carttotal, searchresult, addreset;
    View v;
    ProgressDialog imeiscandialog, submitdialog;
    ImageView shopclose;
    TextView ordertitle;
    String sku, scolor, stotal;
    ProgressDialog tdialog;
    cartimeisAdapter cartadapter;
    ArrayList<String> ss;
    int totalcartamount;
    final Calendar myCalendar = Calendar.getInstance();
    String sduedate = "";
    int a = 0;
    String spinnerid;
    AlertDialog cadialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_salesorderfragment, container, false);

        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        list = new ArrayList<>();
        scanedlist = new ArrayList<>();
        listview = v.findViewById(R.id.listview);
        editsearch = v.findViewById(R.id.search);
        tshopname = v.findViewById(R.id.shopname);
        tcode = v.findViewById(R.id.code);
        taddress = v.findViewById(R.id.address);
        gotoorder = v.findViewById(R.id.gotoshopbtn);
        scan = v.findViewById(R.id.scan_btn);
        sessionManage = SessionManage.getInstance(getActivity());
        model = v.findViewById(R.id.model);
        imei1 = v.findViewById(R.id.imei1);
        imei2 = v.findViewById(R.id.imei2);
        addphone = v.findViewById(R.id.addphone);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        imeilistrecyclerview = v.findViewById(R.id.imeilistrecycler);
        submit = v.findViewById(R.id.submit);
        finallist = new ArrayList<>();
        search_bar = v.findViewById(R.id.search_bar);
        salesname = v.findViewById(R.id.salesname);
        carttotal = v.findViewById(R.id.carttotal);
        searchresult = v.findViewById(R.id.searchresult);
        imeihold = v.findViewById(R.id.scan_result_contaienr);
        orderl = v.findViewById(R.id.product_item_contaienr);
        ordertitle = v.findViewById(R.id.ordertitle);
        salesname.setText(sessionManage.getUserDetails().get("UNAME"));
//        firstimeilist = new ArrayList<>();
        imeiscandialog = new ProgressDialog(getActivity());
        imeiscandialog.setTitle("scanning IMEI Please wait");
        submitdialog = new ProgressDialog(getActivity());
        submitdialog.setTitle("Placing order please wait");
        tdialog = new ProgressDialog(getActivity());
        nscanedlist = new ArrayList<>();
        n1scanedlist = new ArrayList<>();
        addreset = v.findViewById(R.id.addreset);
        ss = new ArrayList<>();


        myCalendar.clear();


        ProgressDialog odialog = new ProgressDialog(getActivity());
        odialog.setMessage("Loading Outlets Please wait..");


        distributor_id = sessionManage.getUserDetails().get("DISTRIBUTOR_ID");


        editsearch.setFocusable(false);
        gotoorder.setEnabled(false);
        gotoorder.setBackgroundColor(Color.parseColor("#FBFBFB"));
        scan.setEnabled(false);
        imeihold.setVisibility(View.GONE);
        orderl.setVisibility(View.GONE);
        addphone.setFocusable(false);
        scan.setBackgroundColor(Color.parseColor("#EDEDED"));
        scan.setVisibility(View.GONE);

        if (scanedlist.size() <= 0) {
            addphone.setEnabled(false);
            addphone.setBackgroundColor(Color.parseColor("#FBFBFB"));
        } else {
            addphone.setEnabled(true);

        }

        odialog.show();
        odialog.setCanceledOnTouchOutside(false);
        Call call = apiinterface.Shoplist("2");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("TAG", "onResponseshop: " + new Gson().toJson(response.body()));

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String error = jsonObject.getString("error");
                    String message = jsonObject.getString("message");

                    if (error.equals("false")) {

//                        String all_shop_list = jsonObject.getString("all_shop_list");
                        JSONArray jsonArray = jsonObject.getJSONArray("all_shop_list");
//                        JSONObject jsonObject1 = new JSONObject(all_shop_list);

                        if (jsonArray.length() > 0) {

                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                id = object.optString("id");
                                user_unique_id = object.optString("user_unique_id");
                                name = object.optString("name");
                                email = object.optString("email");
                                mobile = object.optString("mobile");
                                user_type = object.optString("user_type");
                                password = object.optString("password");
                                country_id = object.optString("country_id");
                                country_name = object.optString("country_name");
                                governate_id = object.optString("governate_id");
                                governate = object.optString("governate");
                                city = object.optString("city");
                                locality = object.optString("locality");
                                locality_ar = object.optString("locality_ar");
                                locality_id = object.optString("locality_id");
                                time = object.optString("time");
                                address = object.optString("address");
                                login_id = object.optString("login_id");
                                login_otp = object.optString("login_otp");
                                outlet_name = object.optString("outlet_name");
                                login_time = object.optString("login_time");
                                profile_update = object.optString("profile_update");
                                signup_type = object.optString("signup_type");
                                social_auth_token = object.optString("social_auth_token");
                                img_url = object.optString("img_url");
                                seller_id = object.optString("seller_id");
                                seller_name = object.optString("seller_name");
                                backend_register = object.optString("backend_register");
                                backend_verify = object.optString("backend_verify");
                                user_profile_verified = object.optString("user_profile_verified");
                                last_login = object.optString("last_login");
                                active_status = object.optString("active_status");
                                device_token = object.optString("device_token");
                                wallet_amount = object.optString("wallet_amount");


                                list.add(new shoplistmodel(id,
                                        user_unique_id, name, email, mobile, user_type, password, country_id, country_name,
                                        governate_id, governate, city, locality, locality_ar, locality_id, time, address,
                                        login_id, login_otp, outlet_name, login_time, profile_update, signup_type,
                                        social_auth_token, img_url, seller_id, seller_name, backend_register, backend_verify,
                                        user_profile_verified, last_login, active_status, device_token));
                            }
                            adapter = new ListViewAdapter(getContext(), salesorderfragment.this, list);
                            listview.setAdapter(adapter);


                            odialog.dismiss();

                        }

//
//                        String id = jsonObject1.getString("id");

//                                             Log.d("tag", "all_shop_list11" + list.get(1));


                    } else {
                        odialog.dismiss();
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    odialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to load outlets" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                odialog.dismiss();
                Toast.makeText(getActivity(), "Failed to load outlets" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();


            }
        });

        addreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

//                builder.setTitle("Remove IMEI");
                builder.setMessage("Are you sure? want to add imei or reset outlet");

                builder.setPositiveButton("Add IMEI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        scanedlist.clear();
                        finallist.clear();
                        totalcartamount =0;
                        nscanedlist.clear();
                        nscanedlist.addAll(n1scanedlist);

                        Log.d("tag", "checkallsize  "+scanedlist.size()+"  "+finallist.size()+" "+totalcartamount+"  "+nscanedlist.size()+" "+ n1scanedlist);

                        for(int i =0; i<n1scanedlist.size(); i++){
                            Log.d("tag", "addqtycheck  "+n1scanedlist.get(i).getQty()+ "  "+n1scanedlist.get(i).getSimei1());
                        }


                        padapter = new placeorderadapter(finallist, getActivity(), salesorderfragment.this);
                        padapter.notifyDataSetChanged();

                        imeiselectAdapter = new imeiselectAdapter(nscanedlist, getActivity(), salesorderfragment.this);
                        imeiselectAdapter.notifyDataSetChanged();
                        imeilistrecyclerview.setAdapter(imeiselectAdapter);
                        imeilistrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


                        orderl.setVisibility(View.GONE);
                        ordertitle.setVisibility(View.GONE);

                        editsearch.setVisibility(View.GONE);
                        searchresult.setVisibility(View.VISIBLE);
                        editsearch.setEnabled(false);
                        scan.setVisibility(View.VISIBLE);
                        imeihold.setVisibility(View.VISIBLE);
                        gotoorder.setVisibility(View.GONE);
                        gotoorder.setEnabled(false);
                        gotoorder.setBackgroundColor(Color.parseColor("#FBFBFB"));
                        editsearch.setFocusable(false);
                        scan.setEnabled(true);
                        scan.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.search));
                        addphone.setEnabled(true);


                    }
                });

                builder.setNegativeButton("Change Outlet", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();


                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
//                builder.setTitle("Please choose 1 option");
                builder.setMessage("Please choose 1 option");

                // add a button
                builder.setPositiveButton("SCAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkPermission()) {

                            AlertDialog.Builder builder
                                    = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Scan IMEI");


                            // set the custom layout
                            customLayout = getLayoutInflater().inflate(R.layout.barcodelayout,
                                    null);
                            builder.setView(customLayout);

                            // add a button
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {


                                }
                            });

                            // create and show
                            // the alert dialog
                            AlertDialog dialog
                                    = builder.create();
                            dialog.getWindow().setLayout(100, 100);
                            dialog.show();
                            scancode();


                        }
                    }
                });
                builder.setNegativeButton("Enter IMEI manually", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        final EditText edittext = new EditText(getActivity());
                        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        alert.setMessage("Enter IMEI Number");
//                        alert.setTitle("Enter Your Title");

                        alert.setView(edittext);


                        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.setPositiveButton("enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                Editable YouEditTextValue = edittext.getText();
                                //OR
                                String imeivalue = edittext.getText().toString();
                                if (imeivalue.matches("") || imeivalue.length() != 15) {
                                    editsearch.setFocusable(false);
                                    Toast.makeText(getActivity(), "Please enter valid imei number", Toast.LENGTH_SHORT).show();
                                } else {
                                    imeiscandialog.show();
                                    if (scanedlist.size() > 0) {
                                        Log.d("tag", "checkpoint");
                                        for (int i = 0; i < scanedlist.size(); i++) {
                                            if (scanedlist.get(i).getSimei1().matches(imeivalue) ||
                                                    scanedlist.get(i).getSimei2().matches(imeivalue)) {
                                                imeiscandialog.dismiss();
                                                Toast.makeText(getActivity(), "IMEI Already Added", Toast.LENGTH_SHORT).show();
                                            } else {
                                                edittext.setText("");
                                                edittext.clearFocus();
                                                Log.d("tag", "checkpoint2");
                                                editsearch.setFocusable(false);
//                                                firstimeilist.add(scanedlist.size(), imeivalue);
//                                            showrecycler();
                                                checkimei(imeivalue, distributor_id, country_id);
                                            }

                                        }
                                    } else {
                                        edittext.setText("");
                                        edittext.clearFocus();
                                        Log.d("tag", "checkpoint1");
                                        editsearch.setFocusable(false);
//                                        firstimeilist.add(imeivalue);
//                                    showrecycler();
                                        checkimei(imeivalue, distributor_id, country_id);
                                    }
                                }
                            }
                        });
                        AlertDialog dialog = alert.create();
                        dialog.show();
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);

                        edittext.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                String imeivalue = editable.toString();
                                if (imeivalue.length() == 15) {
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);


                                } else {
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);

                                }
                            }
                        });
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        addphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "imeicheck", Toast.LENGTH_SHORT).show();

                scanedlist.clear();
                finallist.clear();
                ss.clear();
                totalcartamount = 0;
                scanedlist.addAll(nscanedlist);
                finallist.addAll(scanedlist);

                for (int i = 0; i < finallist.size(); i++) {
                    Log.d("tag", "checkimei13  " + finallist.get(i).getQty() + "  " + finallist.size());
                }

                if (scanedlist.size() <= 0) {
                    Toast.makeText(getActivity(), "Please add imei", Toast.LENGTH_SHORT).show();
                    return;
                }
                orderl.setVisibility(View.VISIBLE);
                scan.setVisibility(View.GONE);
                imeihold.setVisibility(View.GONE);
                ordertitle.setVisibility(View.VISIBLE);


                for (int k = 0; k < finallist.size(); k++) {
                    Log.d("tag", "checkpointf3");
                    for (int j = k + 1; j < finallist.size(); j++) {
                        Log.d("tag", "checkpointf4");
                        // compare list.get(i) and list.get(j)
                        if (finallist.get(k).getScolor().equals(finallist.get(j).getScolor())) {

                            int a = Integer.parseInt(finallist.get(k).getQty());
                            Log.d("tag", "checkingqty  "+String.valueOf(a));
                            int b = a + 1;
                            int c = Integer.parseInt(finallist.get(k).getItem_price());
                            int d = Integer.parseInt(finallist.get(k).getValue());
                            int e = c + d;
                            finallist.get(k).setQty(String.valueOf(b));
                            finallist.get(k).setValue(String.valueOf(e));
//                            finallist.add(finallist.get(j).getSimei1());
                            String im = finallist.get(k).getSimei1();
                            String im1 = finallist.get(j).getSimei1();
                            Log.d("tag", "checkpointf1  " + e + "  " + finallist.get(k).getItem_price());

                            for (int s = 0; s < scanedlist.size(); s++) {
                                String imm = im + ", " + im1;
                                finallist.get(k).setSimei1(imm);
                            }


//                                    finallist.add()
                            finallist.remove(j);
                            j--;

                        } else {
                            Log.d("tag", "checkpointf2   " + finallist.size());
                        }
                    }
                }

                if (finallist.size() < 1) {
                    Log.d("tag", "checkfinal imei1  ");
                } else if (finallist.size() == 1) {

                    for (int h = 0; h < finallist.size(); h++) {
                        Log.d("tag", "checkfinal imei2  " + finallist.get(h).getSimei1());
                    }
                } else {
                    for (int h = 0; h < finallist.size(); h++) {
                        String m = finallist.get(h).getSimei1();
                        String mm = m + ", ";
                        finallist.get(h).setSimei1(mm);
                        Log.d("tag", "checkfinal imei  " + mm);
                    }
                }


                for (int g = 0; g < finallist.size(); g++) {
                    String v = finallist.get(g).getValue();
                    int v1 = Integer.parseInt(v);
                    totalcartamount = totalcartamount + v1;

                }

                Log.d("tag", "finalprice" + totalcartamount);
                totalamount = String.valueOf(totalcartamount);
                carttotal.setText("Order total \n" + String.valueOf(totalcartamount));
                tdialog.setMessage("Your Order total " + totalcartamount + " Rs");


                padapter = new placeorderadapter(finallist, getActivity(), salesorderfragment.this);
                padapter.notifyDataSetChanged();
                recyclerView.setAdapter(padapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                addphone.setEnabled(false);
                addphone.setBackgroundColor(Color.parseColor("#FBFBFB"));
                scan.setEnabled(false);
                scan.setBackgroundColor(Color.parseColor("#EDEDED"));


            }
        });


        gotoorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show warning

                editsearch.setVisibility(View.GONE);
                searchresult.setVisibility(View.VISIBLE);
                editsearch.setEnabled(false);
                scan.setVisibility(View.VISIBLE);
                imeihold.setVisibility(View.VISIBLE);
                gotoorder.setVisibility(View.GONE);
                gotoorder.setEnabled(false);
                gotoorder.setBackgroundColor(Color.parseColor("#FBFBFB"));
                editsearch.setFocusable(false);
                scan.setEnabled(true);
                scan.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.search));

            }
        });

        editsearch = (SearchView) v.findViewById(R.id.search);
        editsearch.setOnQueryTextListener((SearchView.OnQueryTextListener) salesorderfragment.this);


//        here is submitbtn
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (finallist.size() <= 0) {
                    Toast.makeText(getActivity(), "add items in cart", Toast.LENGTH_SHORT).show();
                } else {

                    final Dialog dialog = new Dialog(getActivity(), R.style.MaterialAlertDialog_rounded);
                    dialog.setContentView(R.layout.paymentmodelayout);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    EditText ddesp = dialog.findViewById(R.id.dialogdesp);
                    Spinner dspinner = dialog.findViewById(R.id.dialogspinner);
                    TextView dtotal = dialog.findViewById(R.id.dialogtotal);
                    TextView wallet = dialog.findViewById(R.id.submitwalletbal);
                    TextView rbal = dialog.findViewById(R.id.remainingbal);
                    TextView damount = dialog.findViewById(R.id.dialogamount);
                    TextView dsubmit = (TextView) dialog.findViewById(R.id.dialogsubmit);
                    TextView payamount = dialog.findViewById(R.id.payamount);
                    TextView duedate = dialog.findViewById(R.id.duedate);
                    EditText chequeno = dialog.findViewById(R.id.chequeno);
                    TextView onlinetxt = dialog.findViewById(R.id.onlinetxt);
                    //layouts
                    RelativeLayout bline, sline, pline, dueline, chequeline;
                    LinearLayout payblel, creditamountl, ddatel, chequel;

                    creditamountl = dialog.findViewById(R.id.creditamountl);
                    payblel = dialog.findViewById(R.id.payblel);
                    bline = dialog.findViewById(R.id.bline);
                    sline = dialog.findViewById(R.id.sline);
                    pline = dialog.findViewById(R.id.pline);
                    ddatel = dialog.findViewById(R.id.cdatel);
                    dueline = dialog.findViewById(R.id.dueline);
                    dtotal.setText(String.valueOf(totalcartamount));
                    chequel = dialog.findViewById(R.id.chequel);
                    chequeline = dialog.findViewById(R.id.chequeline);
                    wallet.setText(wallet_amount);


                    int wbal = Integer.parseInt(wallet_amount);
                    int tbal = totalcartamount;

                    dialog.show();

                    if (tbal <= wbal) {
                        rbal.setText("0");
                        ddatel.setVisibility(View.GONE);
                        bline.setVisibility(View.GONE);
                        sline.setVisibility(View.GONE);
                        pline.setVisibility(View.GONE);
                        dspinner.setVisibility(View.GONE);
                        payblel.setVisibility(View.GONE);
                        ddesp.setVisibility(View.GONE);
                        dueline.setVisibility(View.GONE);
                        chequel.setVisibility(View.GONE);
                        chequeline.setVisibility(View.GONE);
                        creditamountl.setVisibility(View.GONE);
                        Log.d("tag", "checkcame here");
                    } else {
                        rbal.setText(String.valueOf(tbal - wbal));
                        dspinner.setVisibility(View.VISIBLE);

                        // if button is clicked, close the custom dialog
                        String pmode;
                        String[] items = new String[]{"cash", "credit", "online", "cheque"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                        dspinner.setAdapter(adapter);
                    }

                    dspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            spinnerid = dspinner.getSelectedItem().toString();
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

                            if (spinnerid.matches("cash")) {
                                ddesp.setVisibility(View.GONE);
                                creditamountl.setVisibility(View.GONE);
                                dueline.setVisibility(View.GONE);
                                ddatel.setVisibility(View.GONE);
                                payamount.setText(String.valueOf(tbal - wbal));
                                chequel.setVisibility(View.GONE);
                                chequeline.setVisibility(View.GONE);
                                sduedate = "";
                                myCalendar.clear();
                                payamount.setEnabled(false);


                            } else if (spinnerid.matches("credit")) {

                                payamount.setText("");
                                damount.setText(String.valueOf((tbal - wbal)));
                                ddesp.setVisibility(View.GONE);
                                ddatel.setVisibility(View.VISIBLE);
                                dueline.setVisibility(View.VISIBLE);
                                creditamountl.setVisibility(View.VISIBLE);
                                payamount.setEnabled(true);
                                chequel.setVisibility(View.GONE);
                                chequeline.setVisibility(View.GONE);
                                pline.setVisibility(View.VISIBLE);
                                sduedate = "";
                                myCalendar.clear();

                                payamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                        String camount = editable.toString();
                                        if (!camount.matches("")) {
                                            int a = Integer.parseInt(camount);
                                            if (a > (tbal - wbal)) {
                                                Toast.makeText(getActivity(), "Please enter equal or less then " + (tbal - wbal), Toast.LENGTH_SHORT).show();
                                                payamount.setText(String.valueOf(tbal - wbal));
                                            } else {
                                                damount.setText(String.valueOf((tbal - wbal) - a));
                                            }
                                        }

                                    }
                                });


                            } else if (spinnerid.matches("online")) {

                                chequeno.setText("");
                                duedate.setText("");
                                onlinetxt.setText("Trasaction No");
                                chequel.setVisibility(View.VISIBLE);
                                chequeline.setVisibility(View.VISIBLE);
                                pline.setVisibility(View.GONE);
                                ddatel.setVisibility(View.VISIBLE);
                                payamount.setText(String.valueOf(tbal - wbal));
                                creditamountl.setVisibility(View.GONE);
                                payamount.setEnabled(false);
                                dueline.setVisibility(View.VISIBLE);
                                chequeno.setHint("Enter Trasaction No");
                                sduedate = "";
                                myCalendar.clear();


                            } else if (spinnerid.matches("cheque")) {
                                chequel.setVisibility(View.VISIBLE);
                                chequeline.setVisibility(View.VISIBLE);
                                pline.setVisibility(View.GONE);
                                ddatel.setVisibility(View.VISIBLE);
                                payamount.setText(String.valueOf(tbal - wbal));
                                creditamountl.setVisibility(View.GONE);
                                payamount.setEnabled(false);
                                chequeno.setText("");
                                duedate.setText("");
                                dueline.setVisibility(View.VISIBLE);
                                chequeline.setVisibility(View.VISIBLE);
                                chequeno.setHint("Enter Cheque No");
                                onlinetxt.setText("Cheque No");
                                sduedate = "";
                                myCalendar.clear();


                            } else {
                                Toast.makeText(getActivity(), "please choose payment mode", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    duedate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (spinnerid.matches("credit")) {
                                if (!payamount.getText().toString().matches("")) {

                                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                            myCalendar.set(Calendar.YEAR, year);
                                            myCalendar.set(Calendar.MONTH, month);
                                            myCalendar.set(Calendar.DAY_OF_MONTH, day);

                                            if (System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
                                                String myFormat = "dd/MM/yy";
                                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                                                duedate.setText(dateFormat.format(myCalendar.getTime()));
                                                sduedate = dateFormat.format(myCalendar.getTime());
                                            } else {
                                                Toast.makeText(getActivity(), "Please choose upcoming date", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    };
                                    new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                                } else {
                                    Toast.makeText(getActivity(), "Please enter payable amount", Toast.LENGTH_SHORT).show();
                                }
                            } else if (spinnerid.matches("online")) {
                                if (!chequeno.getText().toString().matches("")) {

                                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                            myCalendar.set(Calendar.YEAR, year);
                                            myCalendar.set(Calendar.MONTH, month);
                                            myCalendar.set(Calendar.DAY_OF_MONTH, day);

                                            if (System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
                                                String myFormat = "dd/MM/yy";
                                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                                                duedate.setText(dateFormat.format(myCalendar.getTime()));
                                                sduedate = dateFormat.format(myCalendar.getTime());
                                            } else {
                                                Toast.makeText(getActivity(), "Please choose upcoming date", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    };
                                    new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                                } else {
                                    Toast.makeText(getActivity(), "Please enter transaction number", Toast.LENGTH_SHORT).show();
                                }
                            } else if (spinnerid.matches("cheque")) {
                                if (!chequeno.getText().toString().matches("")) {

                                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                            myCalendar.set(Calendar.YEAR, year);
                                            myCalendar.set(Calendar.MONTH, month);
                                            myCalendar.set(Calendar.DAY_OF_MONTH, day);

                                            if (System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
                                                String myFormat = "dd/MM/yy";
                                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                                                duedate.setText(dateFormat.format(myCalendar.getTime()));
                                                sduedate = dateFormat.format(myCalendar.getTime());
                                            } else {
                                                Toast.makeText(getActivity(), "Please choose upcoming date", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    };
                                    new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                                } else {
                                    Toast.makeText(getActivity(), "Please enter cheque number", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });


                    dsubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (tbal <= wbal) {

                                ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
                                for (int i = 0; i <= finallist.size() - 1; i++) {
                                    itemdetailsmodel model2 = new itemdetailsmodel();
                                    model2 = finallist.get(i);

                                    String m = model2.getSimei1();
                                    String mm = m.replace(", ", ", ");
                                    Log.d("tag", "checkingimeis,   " + m + "   " + mm);

                                    ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
                                            model2.getQty(), model2.getSku());
                                    items.add(item);
                                }
                                Log.d("tag", "checkempty" + address + login_id + user_unique_id);
                                ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), totalamount, "Online", "Online", "Paid Complete order amount using wallet balance");


                                Call<ordermodel2> respons = apiinterface.buyproduct(obj);

                                respons.enqueue(new Callback<ordermodel2>() {
                                    @Override
                                    public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                            submitdialog.dismiss();
                                            dialog.dismiss();
                                            getFragmentManager().popBackStack();
                                            Log.d("DEEP", response.body().getMessage());
                                            response.body().getData().getBrand_id();
                                            Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
                                        } else {
                                            submitdialog.dismiss();
//                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();

                                            Log.d("DEEP1", String.valueOf(response.errorBody()));
                                            Log.d("DEEP2", "Failer");
                                            Log.d("DEEP3", String.valueOf(response.raw()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ordermodel2> call, Throwable t) {
                                        Log.d("DEEP", t.getMessage().toString());
                                        submitdialog.dismiss();
                                        Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            } else {
                                if (spinnerid.matches("cash")) {

                                    ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
                                    for (int i = 0; i <= finallist.size() - 1; i++) {
                                        itemdetailsmodel model2 = new itemdetailsmodel();
                                        model2 = finallist.get(i);

                                        String m = model2.getSimei1();
                                        String mm = m.replace(", ", ", ");
                                        Log.d("tag", "checkingimeis,   " + m + "   " + mm);

                                        ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
                                                model2.getQty(), model2.getSku());
                                        items.add(item);
                                    }

                                    String udesp = "Paid complete order amount using Cash \nPayable amount " + totalamount;
                                    Log.d("tag", "checkempty" + address + login_id + user_unique_id + " " + udesp);
                                    ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), String.valueOf(tbal - wbal), "Cash", "Cash", udesp);


                                    Call<ordermodel2> respons = apiinterface.buyproduct(obj);

                                    respons.enqueue(new Callback<ordermodel2>() {
                                        @Override
                                        public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                submitdialog.dismiss();
                                                dialog.dismiss();
                                                getFragmentManager().popBackStack();
                                                Log.d("DEEP", response.body().getMessage());
                                                response.body().getData().getBrand_id();
                                                Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
                                            } else {
                                                submitdialog.dismiss();
//                                            dialog.dismiss();
                                                Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();

                                                Log.d("DEEP1", String.valueOf(response.errorBody()));
                                                Log.d("DEEP2", "Failer");
                                                Log.d("DEEP3", String.valueOf(response.raw()));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ordermodel2> call, Throwable t) {
                                            Log.d("DEEP", t.getMessage().toString());
                                            submitdialog.dismiss();
                                            Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                } else if (spinnerid.matches("credit")) {
                                    if (payamount.getText().toString().matches("")) {
                                        Toast.makeText(getActivity(), "Please enter payable amount", Toast.LENGTH_SHORT).show();
                                    } else if (sduedate.matches("")) {
                                        Toast.makeText(getActivity(), "Please choose due date", Toast.LENGTH_SHORT).show();
                                    } else {

                                        ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
                                        for (int i = 0; i <= finallist.size() - 1; i++) {
                                            itemdetailsmodel model2 = new itemdetailsmodel();
                                            model2 = finallist.get(i);

                                            String m = model2.getSimei1();
                                            String mm = m.replace(", ", ", ");
                                            Log.d("tag", "checkingimeis,   " + m + "   " + mm);

                                            ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
                                                    model2.getQty(), model2.getSku());
                                            items.add(item);
                                        }
                                        Log.d("tag", "checkmycredit  " + address + login_id + user_unique_id + "   " + sduedate);
                                        ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), payamount.getText().toString(), "Credit", "Credit", "Paid order amount using Credit mode \nCredit amount " + String.valueOf((tbal - wbal) - a) + "\nDue Date " + sduedate);


                                        Call<ordermodel2> respons = apiinterface.buyproduct(obj);

                                        respons.enqueue(new Callback<ordermodel2>() {
                                            @Override
                                            public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                    submitdialog.dismiss();
                                                    dialog.dismiss();
                                                    getFragmentManager().popBackStack();
                                                    Log.d("DEEP", response.body().getMessage());
                                                    response.body().getData().getBrand_id();
                                                    Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
                                                } else {
                                                    submitdialog.dismiss();
//                                            dialog.dismiss();
                                                    Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();

                                                    Log.d("DEEP1", String.valueOf(response.errorBody()));
                                                    Log.d("DEEP2", "Failer");
                                                    Log.d("DEEP3", String.valueOf(response.raw()));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ordermodel2> call, Throwable t) {
                                                Log.d("DEEP", t.getMessage().toString());
                                                submitdialog.dismiss();
                                                Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }

                                } else if (spinnerid.matches("online")) {
                                    if (chequeno.getText().toString().matches("")) {
                                        Toast.makeText(getActivity(), "Please enter transaction number", Toast.LENGTH_SHORT).show();
                                    } else if (sduedate.matches("")) {
                                        Toast.makeText(getActivity(), "Please choose due date", Toast.LENGTH_SHORT).show();
                                    } else {

                                        ArrayList<ItemXX> items = new ArrayList<ItemXX>();

                                        for (int i = 0; i <= finallist.size() - 1; i++) {
                                            itemdetailsmodel model2 = new itemdetailsmodel();
                                            model2 = finallist.get(i);

                                            String m = model2.getSimei1();
                                            String mm = m.replace(", ", ", ");
                                            Log.d("tag", "checkingimeis,   " + m + "   " + mm);

                                            ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
                                                    model2.getQty(), model2.getSku());
                                            items.add(item);
                                        }
                                        Log.d("tag", "checkempty" + address + login_id + user_unique_id);
                                        ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), payamount.getText().toString(), "Online", "Online", "Paid Complete order amount using Online \nTransaction number " + chequeno.getText().toString() + "\nDue Date " + sduedate);


                                        Call<ordermodel2> respons = apiinterface.buyproduct(obj);

                                        respons.enqueue(new Callback<ordermodel2>() {
                                            @Override
                                            public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                    submitdialog.dismiss();
                                                    dialog.dismiss();
                                                    getFragmentManager().popBackStack();
                                                    Log.d("DEEP", response.body().getMessage());
                                                    response.body().getData().getBrand_id();
                                                    Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
                                                } else {
                                                    submitdialog.dismiss();
//                                            dialog.dismiss();
                                                    Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();

                                                    Log.d("DEEP1", String.valueOf(response.errorBody()));
                                                    Log.d("DEEP2", "Failer");
                                                    Log.d("DEEP3", String.valueOf(response.raw()));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ordermodel2> call, Throwable t) {
                                                Log.d("DEEP", t.getMessage().toString());
                                                submitdialog.dismiss();
                                                Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }


                                } else if (spinnerid.matches("cheque")) {

                                    if (chequeno.getText().toString().matches("")) {
                                        Toast.makeText(getActivity(), "Please enter cheque number", Toast.LENGTH_SHORT).show();
                                    } else if (sduedate.matches("")) {
                                        Toast.makeText(getActivity(), "Please choose due date", Toast.LENGTH_SHORT).show();
                                    } else {

                                        ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
                                        for (int i = 0; i <= finallist.size() - 1; i++) {
                                            itemdetailsmodel model2 = new itemdetailsmodel();
                                            model2 = finallist.get(i);

                                            String m = model2.getSimei1();
                                            String mm = m.replace(", ", ", ");
                                            Log.d("tag", "checkingimeis,   " + m + "   " + mm);

                                            ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
                                                    model2.getQty(), model2.getSku());
                                            items.add(item);
                                        }
                                        Log.d("tag", "chequecredit  " + address + login_id + user_unique_id + "  " + sduedate);
                                        ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), payamount.getText().toString(), "Cheque", "Cheque", "Paid Complete order amount using Cheque \nCheque number " + chequeno.getText().toString() + "\nDue Date " + sduedate);


                                        Call<ordermodel2> respons = apiinterface.buyproduct(obj);

                                        respons.enqueue(new Callback<ordermodel2>() {
                                            @Override
                                            public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                    submitdialog.dismiss();
                                                    dialog.dismiss();
                                                    getFragmentManager().popBackStack();
                                                    Log.d("DEEP", response.body().getMessage());
                                                    response.body().getData().getBrand_id();
                                                    Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
                                                } else {
                                                    submitdialog.dismiss();
                                                    //                                            dialog.dismiss();
                                                    Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();

                                                    Log.d("DEEP1", String.valueOf(response.errorBody()));
                                                    Log.d("DEEP2", "Failer");
                                                    Log.d("DEEP3", String.valueOf(response.raw()));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ordermodel2> call, Throwable t) {
                                                Log.d("DEEP", t.getMessage().toString());
                                                submitdialog.dismiss();
                                                Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }


                                }

                            }


                        }
                    });


//                    if (tbal <= wbal) {
//                        rbal.setText("0");
//                        ddatel.setVisibility(View.GONE);
//                        bline.setVisibility(View.GONE);
//                        sline.setVisibility(View.GONE);
//                        pline.setVisibility(View.GONE);
//                        dspinner.setVisibility(View.GONE);
//                        payblel.setVisibility(View.GONE);
//                        ddesp.setVisibility(View.GONE);
//                        dueline.setVisibility(View.GONE);
//                        chequel.setVisibility(View.GONE);
//                        chequeline.setVisibility(View.GONE);
//                        creditamountl.setVisibility(View.GONE);
//                        Log.d("tag", "checkcame here");
//
//                        dsubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
//                                for (int i = 0; i <= finallist.size() - 1; i++) {
//                                    itemdetailsmodel model2 = new itemdetailsmodel();
//                                    model2 = finallist.get(i);
//
//                                    String m = model2.getSimei1();
//                                    String mm = m.replace(", ", ", ");
//                                    Log.d("tag", "checkingimeis,   " + m + "   " + mm);
//
//                                    ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
//                                            model2.getQty(), model2.getSku());
//                                    items.add(item);
//                                }
//                                Log.d("tag", "checkempty" + address + login_id + user_unique_id);
//                                ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), totalamount, "Online", "Online", "Paid Complete order amount using wallet balance");
//
//
//                                Call<ordermodel2> respons = apiinterface.buyproduct(obj);
//
//                                respons.enqueue(new Callback<ordermodel2>() {
//                                    @Override
//                                    public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
//                                        if (response.isSuccessful()) {
//                                            Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//                                            submitdialog.dismiss();
//                                            dialog.dismiss();
//                                            getFragmentManager().popBackStack();
//                                            Log.d("DEEP", response.body().getMessage());
//                                            response.body().getData().getBrand_id();
//                                            Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
//                                        } else {
//                                            submitdialog.dismiss();
////                                            dialog.dismiss();
//                                            Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
//
//                                            Log.d("DEEP1", String.valueOf(response.errorBody()));
//                                            Log.d("DEEP2", "Failer");
//                                            Log.d("DEEP3", String.valueOf(response.raw()));
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<ordermodel2> call, Throwable t) {
//                                        Log.d("DEEP", t.getMessage().toString());
//                                        submitdialog.dismiss();
//                                        Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                            }
//                        });


//                    } else if (wbal < tbal) {
//                        rbal.setText(String.valueOf(tbal - wbal));
//                        dspinner.setVisibility(View.VISIBLE);
//
//                        // if button is clicked, close the custom dialog
//                        String pmode;
//                        String[] items = new String[]{"cash", "credit", "online", "cheque"};
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
//                        dspinner.setAdapter(adapter);
//
//
//                        dspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                String id = dspinner.getSelectedItem().toString();
//                                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
////                            pmode = dspinner.getSelectedItem().toString();
//                                if (id.matches("credit")) {
//                                    payamount.setText("");
//                                    damount.setText(String.valueOf((tbal - wbal)));
//                                    ddesp.setVisibility(View.GONE);
//                                    ddatel.setVisibility(View.VISIBLE);
//                                    dueline.setVisibility(View.VISIBLE);
//                                    creditamountl.setVisibility(View.VISIBLE);
//                                    payamount.setEnabled(true);
//                                    chequel.setVisibility(View.GONE);
//                                    chequeline.setVisibility(View.GONE);
//                                    pline.setVisibility(View.VISIBLE);
//                                    sduedate = "";
//                                    myCalendar.clear();
//
//
//                                    duedate.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            if (payamount.getText().toString().matches("")) {
//                                                Toast.makeText(getActivity(), "Please choose payable amount", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//
//                                    payamount.addTextChangedListener(new TextWatcher() {
//                                        @Override
//                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                        }
//
//                                        @Override
//                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                        }
//
//                                        @Override
//                                        public void afterTextChanged(Editable editable) {
//
//                                            String camount = editable.toString();
//                                            if (!camount.matches("")) {
//                                                int a = Integer.parseInt(camount);
//                                                if (a > (tbal - wbal)) {
//                                                    Toast.makeText(getActivity(), "Please enter equal or less then " + (tbal - wbal), Toast.LENGTH_SHORT).show();
//                                                } else {
//                                                    damount.setText(String.valueOf((tbal - wbal) - a));
//                                                    duedate.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View view) {
//                                                            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//                                                                @Override
//                                                                public void onDateSet(DatePicker view, int year, int month, int day) {
//                                                                    myCalendar.set(Calendar.YEAR, year);
//                                                                    myCalendar.set(Calendar.MONTH, month);
//                                                                    myCalendar.set(Calendar.DAY_OF_MONTH, day);
//
//                                                                    if (System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
//                                                                        String myFormat = "MM/dd/yy";
//                                                                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                                                                        duedate.setText(dateFormat.format(myCalendar.getTime()));
//                                                                        sduedate = dateFormat.format(myCalendar.getTime());
//                                                                    } else {
//                                                                        Toast.makeText(getActivity(), "Please choose upcoming date", Toast.LENGTH_SHORT).show();
//                                                                    }
//
//
//                                                                }
//                                                            };
//                                                            new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//
//                                                        }
//                                                    });
//                                                    dsubmit.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View view) {
//                                                            String myFormat = "MM/dd/yy";
//                                                            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//
//                                                            if (payamount.getText().toString().matches("")) {
//                                                                Toast.makeText(getActivity(), "Please enter payable amount", Toast.LENGTH_SHORT).show();
//                                                            } else if (myCalendar.getTime() == null || System.currentTimeMillis() > myCalendar.getTimeInMillis()) {
//                                                                Toast.makeText(getActivity(), "Please choose other date", Toast.LENGTH_SHORT).show();
//                                                            } else {
//                                                                ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
//                                                                for (int i = 0; i <= finallist.size() - 1; i++) {
//                                                                    itemdetailsmodel model2 = new itemdetailsmodel();
//                                                                    model2 = finallist.get(i);
//
//                                                                    String m = model2.getSimei1();
//                                                                    String mm = m.replace(", ", ", ");
//                                                                    Log.d("tag", "checkingimeis,   " + m + "   " + mm);
//
//                                                                    ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
//                                                                            model2.getQty(), model2.getSku());
//                                                                    items.add(item);
//                                                                }
//                                                                Log.d("tag", "checkmycredit  " + address + login_id + user_unique_id + "   " + sduedate);
//                                                                ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), payamount.getText().toString(), "Credit", "Credit", "Paid order amount using Credit mode \nCredit amount " + String.valueOf((tbal - wbal) - a) + "\nDue Date " + sduedate);
//
//
//                                                                Call<ordermodel2> respons = apiinterface.buyproduct(obj);
//
//                                                                respons.enqueue(new Callback<ordermodel2>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
//                                                                        if (response.isSuccessful()) {
//                                                                            Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//                                                                            submitdialog.dismiss();
//                                                                            dialog.dismiss();
//                                                                            getFragmentManager().popBackStack();
//                                                                            Log.d("DEEP", response.body().getMessage());
//                                                                            response.body().getData().getBrand_id();
//                                                                            Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
//                                                                        } else {
//                                                                            submitdialog.dismiss();
////                                            dialog.dismiss();
//                                                                            Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
//
//                                                                            Log.d("DEEP1", String.valueOf(response.errorBody()));
//                                                                            Log.d("DEEP2", "Failer");
//                                                                            Log.d("DEEP3", String.valueOf(response.raw()));
//                                                                        }
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ordermodel2> call, Throwable t) {
//                                                                        Log.d("DEEP", t.getMessage().toString());
//                                                                        submitdialog.dismiss();
//                                                                        Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            }
//
//
//                                                        }
//                                                    });
//                                                }
//                                            } else {
//                                                Toast.makeText(getActivity(), "Please enter Payable amount", Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        }
//                                    });
//
//
//                                } else if (id.matches("cash")) {
//                                    ddesp.setVisibility(View.GONE);
//                                    creditamountl.setVisibility(View.GONE);
//                                    dueline.setVisibility(View.GONE);
//                                    ddatel.setVisibility(View.GONE);
//                                    payamount.setText(String.valueOf(tbal - wbal));
//                                    chequel.setVisibility(View.GONE);
//                                    chequeline.setVisibility(View.GONE);
//
//
//                                    dsubmit.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//
//                                            ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
//                                            for (int i = 0; i <= finallist.size() - 1; i++) {
//                                                itemdetailsmodel model2 = new itemdetailsmodel();
//                                                model2 = finallist.get(i);
//
//                                                String m = model2.getSimei1();
//                                                String mm = m.replace(", ", ", ");
//                                                Log.d("tag", "checkingimeis,   " + m + "   " + mm);
//
//                                                ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
//                                                        model2.getQty(), model2.getSku());
//                                                items.add(item);
//                                            }
//                                            Log.d("tag", "checkempty" + address + login_id + user_unique_id);
//                                            ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), String.valueOf(tbal - wbal), "Cash", "Cash", "Paid complete order amount using Cash \nPayable amount " + totalamount);
//
//
//                                            Call<ordermodel2> respons = apiinterface.buyproduct(obj);
//
//                                            respons.enqueue(new Callback<ordermodel2>() {
//                                                @Override
//                                                public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
//                                                    if (response.isSuccessful()) {
//                                                        Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//                                                        submitdialog.dismiss();
//                                                        dialog.dismiss();
//                                                        getFragmentManager().popBackStack();
//                                                        Log.d("DEEP", response.body().getMessage());
//                                                        response.body().getData().getBrand_id();
//                                                        Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
//                                                    } else {
//                                                        submitdialog.dismiss();
////                                            dialog.dismiss();
//                                                        Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
//
//                                                        Log.d("DEEP1", String.valueOf(response.errorBody()));
//                                                        Log.d("DEEP2", "Failer");
//                                                        Log.d("DEEP3", String.valueOf(response.raw()));
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<ordermodel2> call, Throwable t) {
//                                                    Log.d("DEEP", t.getMessage().toString());
//                                                    submitdialog.dismiss();
//                                                    Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                        }
//                                    });
//
//
//                                } else if (id.matches("cheque")) {
//                                    Toast.makeText(getActivity(), "cheque", Toast.LENGTH_SHORT).show();
//                                    chequel.setVisibility(View.VISIBLE);
//                                    chequeline.setVisibility(View.VISIBLE);
//                                    pline.setVisibility(View.GONE);
//                                    ddatel.setVisibility(View.VISIBLE);
//                                    payamount.setText(String.valueOf(tbal - wbal));
//                                    creditamountl.setVisibility(View.GONE);
//                                    payamount.setEnabled(false);
//                                    chequeno.setText("");
//                                    duedate.setText("");
//                                    chequeno.setHint("Enter Cheque No");
//                                    onlinetxt.setText("Cheque No");
//
//                                    sduedate = "";
//                                    myCalendar.clear();
//
//
//                                    dueline.setVisibility(View.VISIBLE);
//
//                                    duedate.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            if (chequeno.getText().toString().matches("")) {
//                                                Toast.makeText(getActivity(), "Please enter cheque number", Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        }
//                                    });
//
//                                    chequeno.addTextChangedListener(new TextWatcher() {
//                                        @Override
//                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                        }
//
//                                        @Override
//                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                        }
//
//                                        @Override
//                                        public void afterTextChanged(Editable editable) {
//
//                                            String scheque = editable.toString();
//                                            if (scheque.matches("")) {
//                                                Toast.makeText(getActivity(), "Please enter cheque number", Toast.LENGTH_SHORT).show();
//                                            } else {
//
//                                                duedate.setOnClickListener(new View.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(View view) {
//                                                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//                                                            @Override
//                                                            public void onDateSet(DatePicker view, int year, int month, int day) {
//                                                                myCalendar.set(Calendar.YEAR, year);
//                                                                myCalendar.set(Calendar.MONTH, month);
//                                                                myCalendar.set(Calendar.DAY_OF_MONTH, day);
//
//                                                                if (System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
//                                                                    String myFormat = "MM/dd/yy";
//                                                                    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                                                                    duedate.setText(dateFormat.format(myCalendar.getTime()));
//
//                                                                    sduedate = dateFormat.format(myCalendar.getTime());
//                                                                } else {
//                                                                    Toast.makeText(getActivity(), "Please choose upcoming date", Toast.LENGTH_SHORT).show();
//                                                                }
//
//
//                                                            }
//                                                        };
//                                                        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//
//                                                    }
//                                                });
//
//
//                                            }
//
//                                            dsubmit.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View view) {
//
//                                                    String myFormat = "MM/dd/yy";
//                                                    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//
//                                                    if (chequeno.getText().toString().matches("")) {
//                                                        Toast.makeText(getActivity(), "Please enter cheque number", Toast.LENGTH_SHORT).show();
//                                                    } else {
//                                                        myCalendar.getTime();
//                                                        if (System.currentTimeMillis() > myCalendar.getTimeInMillis()) {
//                                                            Toast.makeText(getActivity(), "Please choose other date", Toast.LENGTH_SHORT).show();
//                                                        } else {
//
//                                                            ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
//                                                            for (int i = 0; i <= finallist.size() - 1; i++) {
//                                                                itemdetailsmodel model2 = new itemdetailsmodel();
//                                                                model2 = finallist.get(i);
//
//                                                                String m = model2.getSimei1();
//                                                                String mm = m.replace(", ", ", ");
//                                                                Log.d("tag", "checkingimeis,   " + m + "   " + mm);
//
//                                                                ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
//                                                                        model2.getQty(), model2.getSku());
//                                                                items.add(item);
//                                                            }
//                                                            Log.d("tag", "chequecredit  " + address + login_id + user_unique_id + "  " + sduedate);
//                                                            ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), payamount.getText().toString(), "Cheque", "Cheque", "Paid Complete order amount using Cheque \nCheque number " + chequeno.getText().toString() + "\nDue Date " + sduedate);
//
//
//                                                            Call<ordermodel2> respons = apiinterface.buyproduct(obj);
//
//                                                            respons.enqueue(new Callback<ordermodel2>() {
//                                                                @Override
//                                                                public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
//                                                                    if (response.isSuccessful()) {
//                                                                        Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//                                                                        submitdialog.dismiss();
//                                                                        dialog.dismiss();
//                                                                        getFragmentManager().popBackStack();
//                                                                        Log.d("DEEP", response.body().getMessage());
//                                                                        response.body().getData().getBrand_id();
//                                                                        Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
//                                                                    } else {
//                                                                        submitdialog.dismiss();
//                                                                        //                                            dialog.dismiss();
//                                                                        Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
//
//                                                                        Log.d("DEEP1", String.valueOf(response.errorBody()));
//                                                                        Log.d("DEEP2", "Failer");
//                                                                        Log.d("DEEP3", String.valueOf(response.raw()));
//                                                                    }
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<ordermodel2> call, Throwable t) {
//                                                                    Log.d("DEEP", t.getMessage().toString());
//                                                                    submitdialog.dismiss();
//                                                                    Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            });
//
//                                                        }
//                                                    }
//
//
//                                                }
//                                            });
//
//                                        }
//                                    });
//
//
//                                } else if (id.matches("online")) {
//                                    chequeno.setText("");
//                                    duedate.setText("");
//                                    onlinetxt.setText("Trasaction No");
//                                    chequel.setVisibility(View.VISIBLE);
//                                    chequeline.setVisibility(View.VISIBLE);
//                                    pline.setVisibility(View.GONE);
//                                    ddatel.setVisibility(View.VISIBLE);
//                                    payamount.setText(String.valueOf(tbal - wbal));
//                                    creditamountl.setVisibility(View.GONE);
//                                    payamount.setEnabled(false);
//                                    dueline.setVisibility(View.VISIBLE);
//                                    chequeno.setHint("Enter Trasaction No");
//
//                                    sduedate = "";
//                                    myCalendar.clear();
//
//                                    duedate.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            if (chequeno.getText().toString().matches("")) {
//                                                Toast.makeText(getActivity(), "Please enter Trasaction number", Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        }
//                                    });
//
//                                    chequeno.addTextChangedListener(new TextWatcher() {
//                                        @Override
//                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                        }
//
//                                        @Override
//                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                        }
//
//                                        @Override
//                                        public void afterTextChanged(Editable editable) {
//
//                                            String scheque = editable.toString();
//                                            if (scheque.matches("")) {
//                                                Toast.makeText(getActivity(), "Please enter Trasaction number", Toast.LENGTH_SHORT).show();
//                                            } else {
//
//                                                duedate.setOnClickListener(new View.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(View view) {
//                                                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//                                                            @Override
//                                                            public void onDateSet(DatePicker view, int year, int month, int day) {
//                                                                myCalendar.set(Calendar.YEAR, year);
//                                                                myCalendar.set(Calendar.MONTH, month);
//                                                                myCalendar.set(Calendar.DAY_OF_MONTH, day);
//
//                                                                if (System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
//                                                                    String myFormat = "MM/dd/yy";
//                                                                    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                                                                    duedate.setText(dateFormat.format(myCalendar.getTime()));
//                                                                    sduedate = dateFormat.format(myCalendar.getTime());
//                                                                } else {
//                                                                    Toast.makeText(getActivity(), "Please choose upcoming date", Toast.LENGTH_SHORT).show();
//                                                                }
//
//
//                                                            }
//                                                        };
//                                                        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//
//                                                    }
//                                                });
//
//
//                                            }
//
//                                        }
//                                    });
//
//                                    dsubmit.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//
//                                            String myFormat = "MM/dd/yy";
//                                            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//
//                                            if (chequeno.getText().toString().matches("")) {
//                                                Toast.makeText(getActivity(), "Please enter transaction number", Toast.LENGTH_SHORT).show();
//                                            } else if (myCalendar.getTime() == null || System.currentTimeMillis() < myCalendar.getTimeInMillis()) {
//                                                Toast.makeText(getActivity(), "Please choose other date", Toast.LENGTH_SHORT).show();
//                                            } else {
//
//                                                ArrayList<ItemXX> items = new ArrayList<ItemXX>();
//
//                                                for (int i = 0; i <= finallist.size() - 1; i++) {
//                                                    itemdetailsmodel model2 = new itemdetailsmodel();
//                                                    model2 = finallist.get(i);
//
//                                                    String m = model2.getSimei1();
//                                                    String mm = m.replace(", ", ", ");
//                                                    Log.d("tag", "checkingimeis,   " + m + "   " + mm);
//
//                                                    ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
//                                                            model2.getQty(), model2.getSku());
//                                                    items.add(item);
//                                                }
//                                                Log.d("tag", "checkempty" + address + login_id + user_unique_id);
//                                                ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), payamount.getText().toString(), "Online", "Online", "Paid Complete order amount using Online \nTransaction number " + chequeno.getText().toString() + "\nDue Date " + sduedate);
//
//
//                                                Call<ordermodel2> respons = apiinterface.buyproduct(obj);
//
//                                                respons.enqueue(new Callback<ordermodel2>() {
//                                                    @Override
//                                                    public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
//                                                        if (response.isSuccessful()) {
//                                                            Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//                                                            submitdialog.dismiss();
//                                                            dialog.dismiss();
//                                                            getFragmentManager().popBackStack();
//                                                            Log.d("DEEP", response.body().getMessage());
//                                                            response.body().getData().getBrand_id();
//                                                            Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
//                                                        } else {
//                                                            submitdialog.dismiss();
////                                            dialog.dismiss();
//                                                            Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
//
//                                                            Log.d("DEEP1", String.valueOf(response.errorBody()));
//                                                            Log.d("DEEP2", "Failer");
//                                                            Log.d("DEEP3", String.valueOf(response.raw()));
//                                                        }
//                                                    }
//
//                                                    @Override
//                                                    public void onFailure(Call<ordermodel2> call, Throwable t) {
//                                                        Log.d("DEEP", t.getMessage().toString());
//                                                        submitdialog.dismiss();
//                                                        Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                });
//
//
//                                            }
//
//
//                                        }
//                                    });
//
//
//                                }
////                                dsubmit.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View view) {
////                                        String sdesp = ddesp.getText().toString();
////                                        String smode = id;
////                                        String samount = damount.getText().toString();
////
////                                        if (id.matches("credit")) {
////                                            if (samount.matches("") || sdesp.matches("")) {
////                                                Toast.makeText(getActivity(), "Please enter amount and description", Toast.LENGTH_SHORT).show();
////                                            } else {
////                                                ArrayList<ItemXX> items = new ArrayList<ItemXX>();
////
////                                                for (int i = 0; i <= finallist.size() - 1; i++) {
////                                                    itemdetailsmodel model2 = new itemdetailsmodel();
////                                                    model2 = finallist.get(i);
////
////                                                    String m = model2.getSimei1();
////                                                    String mm = m.replace(", ", ", ");
////                                                    Log.d("tag", "checkingimeis,   " + m + "   " + mm);
////
////                                                    ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
////                                                            model2.getQty(), model2.getSku());
////                                                    items.add(item);
////                                                }
////                                                Log.d("tag", "checkempty" + address + login_id + user_unique_id);
////                                                ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), "", "", "", "");
////
////
////                                                Call<ordermodel2> respons = apiinterface.buyproduct(obj);
////
////                                                respons.enqueue(new Callback<ordermodel2>() {
////                                                    @Override
////                                                    public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
////                                                        if (response.isSuccessful()) {
////                                                            Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
////                                                            submitdialog.dismiss();
////                                                            getFragmentManager().popBackStack();
////                                                            Log.d("DEEP", response.body().getMessage());
////                                                            response.body().getData().getBrand_id();
////                                                            Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
////                                                        } else {
////                                                            submitdialog.dismiss();
////                                                            Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
////
////                                                            Log.d("DEEP1", String.valueOf(response.errorBody()));
////                                                            Log.d("DEEP2", "Failer");
////                                                            Log.d("DEEP3", String.valueOf(response.raw()));
////                                                        }
////                                                    }
////
////                                                    @Override
////                                                    public void onFailure(Call<ordermodel2> call, Throwable t) {
////                                                        Log.d("DEEP", t.getMessage().toString());
////                                                        submitdialog.dismiss();
////                                                        Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
////                                                    }
////                                                });
////
////                                            }
////                                        } else {
////                                            if (sdesp.matches("")) {
////                                                Toast.makeText(getActivity(), "Please enter description", Toast.LENGTH_SHORT).show();
////                                            } else {
////                                                ArrayList<ItemXX> items = new ArrayList<ItemXX>();
////
////                                                for (int i = 0; i <= finallist.size() - 1; i++) {
////                                                    itemdetailsmodel model2 = new itemdetailsmodel();
////                                                    model2 = finallist.get(i);
////
////                                                    String m = model2.getSimei1();
////                                                    String mm = m.replace(", ", ", ");
////                                                    Log.d("tag", "checkingimeis,   " + m + "   " + mm);
////
////                                                    ItemXX item = new ItemXX(model2.getItem_price(), mm, model2.getValue(), model2.getScolor(),
////                                                            model2.getQty(), model2.getSku());
////                                                    items.add(item);
////                                                }
////                                                Log.d("tag", "checkempty" + address + login_id + user_unique_id);
////                                                ordermodel1 obj = new ordermodel1(address, Integer.parseInt(sessionManage.getUserDetails().get("DISTRIBUTOR_NAME")), items, totalamount, Integer.parseInt(user_unique_id), "", "", "","");
////
////
////                                                Call<ordermodel2> respons = apiinterface.buyproduct(obj);
////
////                                                respons.enqueue(new Callback<ordermodel2>() {
////                                                    @Override
////                                                    public void onResponse(Call<ordermodel2> call, Response<ordermodel2> response) {
////                                                        if (response.isSuccessful()) {
////                                                            Toast.makeText(getActivity(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
////                                                            submitdialog.dismiss();
////                                                            getFragmentManager().popBackStack();
////                                                            Log.d("DEEP", response.body().getMessage());
////                                                            response.body().getData().getBrand_id();
////                                                            Log.d("deep", String.valueOf(response.body().getData().getOrder_no()));
////                                                        } else {
////                                                            submitdialog.dismiss();
////                                                            Toast.makeText(getActivity(), "Something went wrong" + String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
////
////                                                            Log.d("DEEP1", String.valueOf(response.errorBody()));
////                                                            Log.d("DEEP2", "Failer");
////                                                            Log.d("DEEP3", String.valueOf(response.raw()));
////                                                        }
////                                                    }
////
////                                                    @Override
////                                                    public void onFailure(Call<ordermodel2> call, Throwable t) {
////                                                        Log.d("DEEP", t.getMessage().toString());
////                                                        submitdialog.dismiss();
////                                                        Toast.makeText(getActivity(), "Order placed failed" + t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
////                                                    }
////                                                });
////                                            }
////
////                                        }
////                                    }
////                                });
//
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                            }
//                        });
//                    }


                }


            }
        });


        // Inflate the layout for this fragment
        return v;
    }


    private void checkimei(String imeivalue, String distributor_id, String country_id) {
        editsearch.setFocusable(false);
        editsearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        Call call = apiinterface.checkimei(imeivalue, distributor_id, country_id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String error = jsonObject.getString("error");
                    String message = jsonObject.getString("message");

                    if (error.matches("true")) {
                        addphone.setFocusable(true);

                        String user_detail = jsonObject.getString("list");

                        JSONObject jsonObject1 = new JSONObject(user_detail);
                        String smodel = jsonObject1.optString("model");
                        scolor = jsonObject1.optString("marketing_name");
                        String simei1 = jsonObject1.optString("imei");
                        String simei2 = jsonObject1.optString("imei2");
                        String item_price = jsonObject1.optString("item_price");
                        String srno = jsonObject1.optString("srno");
                        sku = jsonObject1.optString("sku");
                        String brand = jsonObject1.optString("brand");
                        String seconday_direct = jsonObject1.optString("seconday_direct");
                        String tertialry_direct = jsonObject1.optString("tertialry_direct");


                        scanedlist.add(new itemdetailsmodel(smodel, scolor, simei1, simei2, item_price,
                                srno, sku, brand, seconday_direct, tertialry_direct, "1", item_price));
                        addphone.setEnabled(true);
                        addphone.setBackgroundColor(Color.parseColor("#011135"));


                        for (int i = 0; i < scanedlist.size() - 1; i++) {
                            for (int j = i + 1; j < scanedlist.size(); j++) {
                                if (scanedlist.get(j).getSimei1().equals(scanedlist.get(i).getSimei1())) {
                                    scanedlist.remove(j);

                                    Log.d("tag", "checkrepeat" + i);
                                }

                            }
                        }

//                        nscanedlist.clear();
                        nscanedlist.add(new itemdetailsmodel(smodel, scolor, simei1, simei2, item_price,
                                srno, sku, brand, seconday_direct, tertialry_direct, "1", item_price));

                        for (int i = 0; i < nscanedlist.size() - 1; i++) {
                            for (int j = i + 1; j < nscanedlist.size(); j++) {
                                if (nscanedlist.get(j).getSimei1().equals(nscanedlist.get(i).getSimei1())) {
                                    nscanedlist.remove(j);

                                    Log.d("tag", "checkrepeatnscan " + nscanedlist.size());
                                }

                            }
                        }

                        n1scanedlist.add(new itemdetailsmodel(smodel, scolor, simei1, simei2, item_price,
                                srno, sku, brand, seconday_direct, tertialry_direct, "1", item_price));

                        for (int i = 0; i < n1scanedlist.size() - 1; i++) {
                            for (int j = i + 1; j < n1scanedlist.size(); j++) {
                                if (n1scanedlist.get(j).getSimei1().equals(n1scanedlist.get(i).getSimei1())) {
                                    n1scanedlist.remove(j);

                                    Log.d("tag", "checkrepeatnscan " + n1scanedlist.size());
                                }

                            }
                        }





                        imeiselectAdapter = new imeiselectAdapter(nscanedlist, getActivity(), salesorderfragment.this);
                        imeiselectAdapter.notifyDataSetChanged();
                        imeilistrecyclerview.setAdapter(imeiselectAdapter);
                        imeilistrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        imeiscandialog.dismiss();

//                        scannedlist2.addAll(scanedlist1);


                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    imeiscandialog.dismiss();
                    Toast.makeText(getActivity(), "error" + e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

    private void scancode() {

        Log.d("tag", "checkpoint1");
        surfaceView = customLayout.findViewById(R.id.surface_view);
        barcodeValue = customLayout.findViewById(R.id.barcode_value);

        mViewModel = new ViewModelProvider(this).get(BarCodeScannerViewModel.class);
        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    //noinspection MissingPermission
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getActivity(), ex.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections detections) {


                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    barcodeValue.post(() -> {
                        if (barcodes.valueAt(0).email != null) {
//                            binding.barcodeValue.removeCallbacks(null);
                            intentData = barcodes.valueAt(0).email.address;
                            barcodeValue.setText(intentData);
//                            checkimei(intentData, distributor_id, country_id);
                        } else {
                            intentData = barcodes.valueAt(0).displayValue;
                            barcodeValue.setText(intentData);
                            if (scanedlist.size() > 0) {
                                for (int i = 0; i < scanedlist.size(); i++) {
                                    if (scanedlist.get(i).getSimei1().matches(intentData) ||
                                            scanedlist.get(i).getSimei2().matches(intentData)) {
                                        imeiscandialog.dismiss();
                                        Toast.makeText(getActivity(), "IMEI Already Added", Toast.LENGTH_SHORT).show();
                                    } else {

//                                                firstimeilist.add(scanedlist.size(), imeivalue);
//                                            showrecycler();
                                        checkimei(intentData, distributor_id, country_id);
                                    }
                                }


                            } else {
                                checkimei(intentData, distributor_id, country_id);
                            }

//                            backPressBarCode.OnbackpressBarcode(intentData);
                            cameraSource.release();
//                            getActivity().getSupportFragmentManager().popBackStack();
                        }


                    });
                }

            }
        });


    }


    private boolean checkPermission() {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            ArrayList<String> permissionsNotGranted = new ArrayList<>();
            for (String permission : neededPermissions) {
                if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(permission);
                }
            }


            if (permissionsNotGranted.size() > 0) {

                boolean shouldShowAlert = false;
                for (String permission : permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission);
                }

                if (shouldShowAlert) {
                    showPermissionAlert(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
                } else {
                    Toast.makeText(requireContext(), "Please allow Camera Permission", Toast.LENGTH_SHORT).show();
                    requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
                }

                return false;
            }
        }
        return true;
    }

    private void showPermissionAlert(final String[] permissions) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(requireContext());
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission Required");
        alertBuilder.setMessage("You must need to allow Permission");
        alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> requestPermissions(permissions));
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "granted", Toast.LENGTH_SHORT).show();
                        scan.performClick();
                    }
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Log.d("TAG", "onRequestPermissionsResult: if");
                        return;
                    }

                }
                Log.d("TAG", "onRequestPermissionsResult: id");
                // All permissions are granted. So, do the appropriate work now.
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String text = s;
        if (s.matches("")) {
            listview.setVisibility(View.GONE);
        } else {
            adapter.filter(text);
            listview.setVisibility(View.VISIBLE);
        }


        return false;
    }

    @Override
    public void onItemClicked(int position) {

        gotoorder.setVisibility(View.VISIBLE);

        editsearch.setFocusable(false);
        editsearch.clearFocus();
        editsearch.setEnabled(false);

        listview.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        Log.d("tag", "listview2" + list.get(position).getName());
        searchresult.setText(list.get(position).getName());
        tshopname.setText(list.get(position).getName());
        tcode.setText(list.get(position).getUser_unique_id());
        taddress.setText(list.get(position).getAddress());
        gotoorder.setEnabled(true);
        gotoorder.setBackgroundColor(Color.parseColor("#EDEDED"));

    }

    @Override
    public void onClick(int position) {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        builder.setTitle("Scanned IMEI");
        View customLayout1 = getLayoutInflater().inflate(R.layout.imeislistlayout,
                null);
        builder.setView(customLayout1);
        ListView recyclerView1 = customLayout1.findViewById(R.id.imeisholder);
        String[] lines = finallist.get(position).getSimei1().split("\r\n|\r|\n");

        String[] sub = finallist.get(position).getSimei1().split(", ");

        ss.clear();

        for (String s : sub) {
            ss.add(s);
        }

        cartadapter = new cartimeisAdapter(ss, getActivity(), salesorderfragment.this, position);
        recyclerView1.setAdapter(cartadapter);

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(
                    DialogInterface dialog,
                    int which) {


            }
        });


//        AlertDialog dialog
        cadialog = builder.create();
        cadialog.show();
//        if (finallist.size() == 0) {
//            dialog.dismiss();
//        }


    }

    @Override
    public void checked(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Remove IMEI");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                nscanedlist.remove(position);
                imeiselectAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onimeicross(int position, int item) {
        int q = Integer.parseInt(finallist.get(position).getQty());
        String imei = ss.get(item);
        String fimei = finallist.get(position).getSimei1();
        int ip = Integer.parseInt(finallist.get(position).getItem_price());
        int tv = Integer.parseInt(finallist.get(position).getValue());
        int q1 = q - 1;

        String nime = fimei.replace(imei + ", ", "");
//        String nm = nime.replace(", ", "");
//        String nm1 = nm.replace(", ", "")''
//        String test = nime.replaceAll("[\\\r\\\n]+","");
        Log.d("tag", "checkimeilist1   " + "     " + nime + "\n" + "  " + fimei);
        totalamount = String.valueOf(totalcartamount - ip);
        carttotal.setText("Order total\n" + String.valueOf(totalamount));
        totalcartamount = totalcartamount - Integer.parseInt(finallist.get(position).getItem_price());
        if (q1 == 0) {
            finallist.remove(position);
            if (finallist.size() == 0) {
                Toast.makeText(getActivity(), "you have removed all items from cart", Toast.LENGTH_SHORT).show();

                cadialog.dismiss();

                orderl.setVisibility(View.GONE);
                ordertitle.setVisibility(View.GONE);

                editsearch.setVisibility(View.GONE);
                searchresult.setVisibility(View.VISIBLE);
                editsearch.setEnabled(false);
                scan.setVisibility(View.VISIBLE);
                imeihold.setVisibility(View.VISIBLE);
                gotoorder.setVisibility(View.GONE);
                gotoorder.setEnabled(false);
                gotoorder.setBackgroundColor(Color.parseColor("#FBFBFB"));
                editsearch.setFocusable(false);
                scan.setEnabled(true);
                scan.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.search));
                addphone.setEnabled(true);

            }
            ss.remove(item);
        } else {

            finallist.get(position).setQty(String.valueOf(q - 1));
            finallist.get(position).setSimei1(nime);
            Log.d("tag", "checkimeilist12    " + finallist.get(position).getSimei1());
            finallist.get(position).setValue(String.valueOf(tv - ip));
            ss.remove(item);
        }
        cartadapter.notifyDataSetChanged();
        padapter.notifyDataSetChanged();


    }

    @Override
    public boolean onBackClick() {
        Log.d("tag", "cameback");
        Toast.makeText(getActivity(), "okay", Toast.LENGTH_SHORT).show();
        return false;
    }


}