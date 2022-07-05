package com.futureproducts.gbrself.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.barcode.BarCodeScannerViewModel;
import com.futureproducts.gbrself.reportrsmodel.reportsmodel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class imeireportdetail extends Fragment {

    View v;
    LinearLayout sacnbtn, itemcontainer;
    public static final int REQUEST_CODE = 100;
    private final String[] neededPermissions = new String[]{Manifest.permission.CAMERA};
    View customLayout;
    ProgressDialog imeiscandialog;
    SurfaceView surfaceView;
    TextView barcodeValue;
    private BarCodeScannerViewModel mViewModel;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    String intentData = "";
    TextView imeireportbtng;
    Apiinterface apiinterface;
    TextView tmname, tbname, tdrname, trname, tsdate, tselldate, tacdate;
    ProgressDialog dialog;
    SessionManage sessionManage;
    TextView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_imeireportdetail, container, false);
        sacnbtn = v.findViewById(R.id.scan_btn);
        imeiscandialog = new ProgressDialog(getActivity());
        imeiscandialog.setTitle("loading please wait");
        imeireportbtng = v.findViewById(R.id.imeireport);
        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        itemcontainer = v.findViewById(R.id.items_container);
        tmname = v.findViewById(R.id.tmname);
        tbname = v.findViewById(R.id.tbname);
        tdrname = v.findViewById(R.id.tdbrname);
        trname = v.findViewById(R.id.trname);
        tsdate = v.findViewById(R.id.tsdate);
        tselldate = v.findViewById(R.id.tsoutdate);
        tacdate = v.findViewById(R.id.tacdate);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("loading Please wait");
        back = v.findViewById(R.id.backbtn);
        sessionManage = SessionManage.getInstance(getActivity());

        sacnbtn.setOnClickListener(new View.OnClickListener() {
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

                        alert.setPositiveButton("enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                Editable YouEditTextValue = edittext.getText();
                                //OR
                                String imeivalue = edittext.getText().toString();
                                if (imeivalue.matches("") || imeivalue.length() != 15) {
//                                    editsearch.setFocusable(false);
                                    Toast.makeText(getActivity(), "Please enter valid imei number", Toast.LENGTH_SHORT).show();
                                } else {
                                    imeiscandialog.show();
                                    checkimei(imeivalue);
                                }
                            }


                        });

                        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });

                        alert.show();
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacnbtn.setVisibility(View.VISIBLE);
                itemcontainer.setVisibility(View.GONE);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void checkimei(String imeivalue) {
        dialog.show();
        if(imeivalue.length()!= 15){
            Toast.makeText(getActivity(), "Invalid IMEI", Toast.LENGTH_SHORT).show();
        }else{
            sessionManage.getUserDetails().get("DISTRIBUTOR_ID");
            Call<reportsmodel> call = apiinterface.getreports(imeivalue, "300088");
            call.enqueue(new Callback<reportsmodel>() {
                @Override
                public void onResponse(Call<reportsmodel> call, Response<reportsmodel> response) {
                    Log.d("tag", "checkreportresp   "+ response.body());
                    if(response.body().getError() == false){
                        dialog.dismiss();
                        sacnbtn.setVisibility(View.GONE);
                        itemcontainer.setVisibility(View.VISIBLE);
                        tmname.setText(response.body().getList().getMarketing_name());
                        tbname.setText(String.valueOf(response.body().getList().getBrand()));
                        tdrname.setText(response.body().getList().getDistributor_name());
                        trname.setText(String.valueOf(response.body().getList().getRetailer_name()));
                        tsdate.setText(String.valueOf(response.body().getList().getSeconday_direct()));
                        tselldate.setText(String.valueOf(response.body().getList().getSell_out_date()));
                        tacdate.setText(String.valueOf(response.body().getList().getGrn_date()));


                    }else{
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Report not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<reportsmodel> call, Throwable t) {

                    Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });




        }

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

                            checkimei(intentData);
//                            backPressBarCode.OnbackpressBarcode(intentData);
                            cameraSource.release();
//                            getActivity().getSupportFragmentManager().popBackStack();
                        }


                    });
                }

            }
        });


    }


}