package com.futureproducts.gbrself.fragments;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.futureproducts.gbrself.apisupport.RealPathUtil.getRealPathFromURI_BelowAPI11;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.futureproducts.gbrself.apisupport.RealPathUtil;
import com.futureproducts.gbrself.apisupport.VolleyMultipartRequest;
import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.apisupport.FileUtils;
import com.futureproducts.gbrself.apisupport.VolleyMultipartRequest;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.loginactivity;
import com.futureproducts.gbrself.reportrsmodel.ImgUrl;
import com.futureproducts.gbrself.reportrsmodel.TicketRespons;
import com.futureproducts.gbrself.utils.fileutil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class complaintfragment extends Fragment {

    View v;
    EditText heading, desp;
    TextView upload, submit;
    String sdse, susername, sheading, sdesp;
    String smobile = "";
    Apiinterface apiinterface;
    int pickcode = 101;
    Uri pickURI;
    private static final int GALLERY = 34;
    private Uri mImageCaptureUri;
    private String currentPhotoPath, finalimage, prefinal;
    private static final int BUFFER_SIZE = 1024 * 2;
    SessionManage sessionManage;
    boolean ispdf = false;
    MultipartBody.Part fileToUpload;
    EditText filetext;
    RequestBody filename;

    String mediaPath, mediaPath1, sfile;
    private static final String IMAGE_DIRECTORY = "/gbrself";
    ImageView imgView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog dialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_complaintfragment, container, false);

        apiinterface = ApiClient.getClient().create(Apiinterface.class);


        sessionManage = SessionManage.getInstance(getActivity());
        heading = v.findViewById(R.id.et_heading);
        desp = v.findViewById(R.id.et_desp);
        upload = v.findViewById(R.id.et_browse);
        submit = v.findViewById(R.id.et_submit);
        filetext = v.findViewById(R.id.filechoose);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("submiting ticket please wait...");

        sdse = sessionManage.getUserDetails().get("DISTRIBUTOR_ID");
        susername = sessionManage.getUserDetails().get("UNAME");
        smobile = sessionManage.getUserDetails().get("UMOBILE");
        Log.d("tag", "checkc     " + sdse + "   " + susername + "   " + smobile);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager())
                        Toast.makeText(getActivity(), "We Have Permission1", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "You Denied the permission", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "You Denied the permission", Toast.LENGTH_SHORT).show();
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {


                    if (checkPermission()) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        startActivityForResult(intent, GALLERY);
                        Toast.makeText(getActivity(), "WE Have Permission", Toast.LENGTH_SHORT).show();   // WE have a permission just start your work.
                    } else {
                        requestPermission(); // Request Permission

                    }
                } else {

                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, GALLERY);


                } else {
                    Toast.makeText(getActivity(), "permissions granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, GALLERY);
                }
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sheading = heading.getText().toString();
                sdesp = desp.getText().toString();
                if (sdse == null || sdse.matches("")) {
                    Toast.makeText(getActivity(), "Please enter distribuutor id", Toast.LENGTH_SHORT).show();

                    Intent go = new Intent(getActivity(), loginactivity.class);
                    startActivity(go);
                    getActivity().finish();
                } else if (susername == null || susername.matches("")) {
                    Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
                    Intent go = new Intent(getActivity(), loginactivity.class);
                    startActivity(go);
                    getActivity().finish();
                } else if (smobile == null || smobile.matches("")) {
                    Toast.makeText(getActivity(), "please enter mobile number", Toast.LENGTH_SHORT).show();
                    Intent go = new Intent(getActivity(), loginactivity.class);
                    startActivity(go);
                    getActivity().finish();
                } else if (sheading.matches("")) {
                    Toast.makeText(getActivity(), "Please enter heading", Toast.LENGTH_SHORT).show();
                } else if (sdesp.matches("")) {
                    Toast.makeText(getActivity(), "please enter description", Toast.LENGTH_SHORT).show();
                } else if (sfile == null || sfile.matches("")) {
                    Toast.makeText(getActivity(), "Please choose image/pdf file", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
//                    Call<ImgUrl> call = null;
//                    File file = new File(mediaPath);

                    // Parsing any Media type file

//                    RequestBody requestBodyid = RequestBody.create(MediaType.parse("text/plain"), "test");
                    RequestBody dse_id = RequestBody.create(MediaType.parse("multipart/form-data"), sdse);
                    RequestBody usre_name = RequestBody.create(MediaType.parse("multipart/form-data"), susername);
                    RequestBody usre_mobile = RequestBody.create(MediaType.parse("multipart/form-data"), smobile);
                    RequestBody headingb = RequestBody.create(MediaType.parse("multipart/form-data"), sheading);
                    RequestBody descriptionb = RequestBody.create(MediaType.parse("multipart/form-data"), sdesp);//RequestBody.create(MediaType.parse("multipart/form-data"), sdse);
//                    RequestBody requestBodyid1 = RequestBody.create(MultipartBody.FORM, fileToUpload);
//                    map.put("img_url", )

                    Log.d("tag", "checkfinal   " + fileToUpload);
                    Call<TicketRespons> call = apiinterface.submitticket(fileToUpload, usre_mobile, headingb, descriptionb, dse_id, usre_name);
                    call.enqueue(new Callback<TicketRespons>() {
                        @Override
                        public void onResponse(Call<TicketRespons> call, Response<TicketRespons> response) {
                            if (response.isSuccessful()) {
                                Log.d("DEEP", response.body().getMessage());
                                Log.d("DEEP", String.valueOf(response.body().getErrorCode()));
                                Log.d("DEEP", response.body().getDataRecieved().getDseId());
//                                Log.d("DEEP", String.valueOf((response.body().getImgRecieved().getImgUrl().getSize())));
                                Log.d("DEEP", String.valueOf((response.body().getImgRecieved().getImgUrl().getName())));
                                heading.setText("");
                                desp.setText("");
                                filetext.setText("");


                                Toast.makeText(getActivity(), "Ticket submited successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Log.d("DEEP1", String.valueOf(response.errorBody()));
                                Log.d("DEEP2", "Failer");
                                Log.d("DEEP3", String.valueOf(response.raw()));
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "ticket submit failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TicketRespons> call, Throwable t) {
                            Log.d("teg", "checkimgresp1  " + t.getMessage().toString());
                            dialog.dismiss();
                            Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

        return v;
    }


    private String[] permissions = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission")
                    .setMessage("Please give the Storage permission")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Log.d("tag", "checkpermissonokay  ");
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getActivity().getApplicationContext().getPackageName()})));
                                activityResultLauncher.launch(intent);
                            } catch (Exception e) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                activityResultLauncher.launch(intent);
                                Log.d("tag", "checkpermissionerror  " + e.getMessage().toString());
                            }
                        }
                    })
                    .setCancelable(false)
                    .show();

        } else {

            ActivityCompat.requestPermissions(getActivity(), permissions, 30);
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), READ_EXTERNAL_STORAGE);
            int writeCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return readCheck == PackageManager.PERMISSION_GRANTED && writeCheck == PackageManager.PERMISSION_GRANTED;
        }
    }

//Add these line of code in onCreate Method


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK) {


            Uri selectedImage = data.getData();
//            Log.d("tag", "checkfile   " + selectedImage + "   " + createCopyAndReturnRealPath(getActivity(), selectedImage, "pdf"));//fileutil.getPath(getActivity(), selectedImage));
            //new File(RealPathUtil.getRealPath(getActivity(), selectedImage));

            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            String extension = mime.getExtensionFromMimeType(getActivity().getContentResolver().getType(selectedImage));


            File file = new File(createCopyAndReturnRealPath(getActivity(), selectedImage, extension));
            filetext.setText(file.toString());
            sfile = file.toString();
            Log.d("tag", "checkuripath1   "+sfile);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("img_url", file.getName(), requestBody);


//            Uri file1 = Uri.fromFile(file);
//            try {
//
//                String fileExt = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(path));
//                String extension = path.substring(path.toString().lastIndexOf("."));
//                Log.d("tag", "checkfileext "+extension+"  "+fileExt);
//                if(fileExt.toLowerCase(Locale.ROOT).matches("pdf")){
//                    String uriString = selectedImage.toString();
//                    File myFile = new File(path);
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
//                    fileToUpload = MultipartBody.Part.createFormData("img_url", myFile.getName(), requestBody);
//
//
//                }else{
//                    File file = new File(RealPathUtil.getRealPath(getActivity(), selectedImage));
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    fileToUpload = MultipartBody.Part.createFormData("img_url", file.getName(), requestBody);
//                }
//
////                if(fileExt.toLowerCase(Locale.ROOT).matches("jpeg")||fileExt.toLowerCase(Locale.ROOT).matches("png")||fileExt.toLowerCase(Locale.ROOT).matches("jpg")) {
//
//
//                    Log.d("tag", "checkbodypart   " + fileToUpload);
////                }else if(fileExt.toLowerCase(Locale.ROOT).matches("pdf")){
////                    ispdf = true;
////                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
////                    fileToUpload = MultipartBody.Part.createFormData("img_url", file.getName(), requestBody);
////                }
//
//            }catch (Exception e){
//                Log.d("tag", "checkbodypart1   "+e.getMessage().toString());
//            }

//            mediaPath =  getPath(selectedImage);// FileUtils.getPath(getActivity(), selectedImage);
//            Log.d("tag", "checkpath   "+file);

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    public static String createCopyAndReturnRealPath(
            @NonNull Context context, @NonNull Uri uri, String ext) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator
                + "gbrfile." + ext;

        File file = new File(filePath);

        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);

            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            Log.d("tag", "checkpoint12  " + ignore.getMessage().toString());
            return null;

        }

        return file.getAbsolutePath();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS) + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            Log.d("tag", "checkpath1  " + copyFile.getAbsolutePath());
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

}