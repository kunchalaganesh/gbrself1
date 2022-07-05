package com.futureproducts.gbrself;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.futureproducts.gbrself.apisupport.ApiClient;
import com.futureproducts.gbrself.apisupport.Apiinterface;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.utils.NetworkCheck;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginactivity extends AppCompatActivity {

    private boolean REMEMBER_ME = false;
    RelativeLayout loginbtn;
    EditText eemail, epassword;
    String semail, spassword;
    Apiinterface apiinterface;
    SessionManage sessionManage;
    CheckBox checkBox;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        
        loginbtn = findViewById(R.id.loginbtn);
        eemail = findViewById(R.id.email);
        epassword = findViewById(R.id.password);
        checkBox = findViewById(R.id.rememberme);
        dialog = new ProgressDialog(this);
        dialog.setTitle("loading please wait");

        apiinterface = ApiClient.getClient().create(Apiinterface.class);
        sessionManage = SessionManage.getInstance(this);

        if(sessionManage.getUserDetails().get("REMEMBER_MOB") != null && sessionManage.getUserDetails().get("REMEMBER_PSW")!= null){
            eemail.setText(sessionManage.getUserDetails().get("REMEMBER_MOB"));
            epassword.setText(sessionManage.getUserDetails().get("REMEMBER_PSW"));
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    REMEMBER_ME = true;
                }else{
                    REMEMBER_ME = false;
                }
            }
        });
        
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new NetworkCheck().haveNetworkConnection(loginactivity.this)){
//                    Toast.makeText(loginactivity.this, "connected", Toast.LENGTH_SHORT).show();
                    semail = eemail.getText().toString();
                    spassword = epassword.getText().toString();
                    if(semail.matches("")||spassword.matches("")){
                        Toast.makeText(loginactivity.this, "Please enter login id and password", Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.show();
                        Log.d("tag", "checkpoint");
                       signin(semail, spassword);
                    }
                    
                }else{
                    dialog.setTitle("No Network, Please check internet");
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 2000);
                    Toast.makeText(loginactivity.this, "please check internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signin(String semail, String spassword) {
        Log.d("tag", "checkpoint1");
        Call call = apiinterface.Login(semail, spassword);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                Log.d("tag", "checkpoint2");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String error = jsonObject.getString("error");
                    String message = jsonObject.getString("message");

                    if(error.matches("false")){
                        dialog.dismiss();

                        String user_detail = jsonObject.getString("user_detail");


                        JSONObject jsonObject1 = new JSONObject(user_detail);
                        String id = jsonObject1.optString("id");
                        String loginid = jsonObject1.getString("login_id");
                        String password = jsonObject1.getString("password");//300080  1
                        String distributor_id = jsonObject1.optString("dis_id");
                        String name = jsonObject1.optString("name");
                        String email = jsonObject1.optString("email");
                        String mobile = jsonObject1.optString("mobile");
                        String address = jsonObject1.optString("address");
                        String dis_name = jsonObject1.optString("dis_name");
                        String branch_id = jsonObject1.optString("branch_id");
                        String branch_name = jsonObject1.optString("branch_name");
                        String active_status = jsonObject1.optString("active_status");
                        String created_date = jsonObject1.optString("created_date");
                        String last_login = jsonObject1.optString("last_login");
                        String device_token = jsonObject1.optString("device_token");
                        String active_token = jsonObject1.optString("active_token");

                        sessionManage.homedetails(distributor_id, loginid, mobile, name);
                        sessionManage.userdetails(name);
                        Log.d("tag", "checkname1"+name +"   "+ distributor_id+"   "+mobile);

                        if(REMEMBER_ME){
                            sessionManage.RememberMe(semail, spassword);
                        }
//usernam mobile dseid
                        sessionManage.loginuser("logedin");
                        String s = sessionManage.loginn();
                        Log.d("tag", "checkstate1"+s);

                        Intent go = new Intent(loginactivity.this, MainActivity.class);
                        go.putExtra("name", name);
                        startActivity(go);
                        finish();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(loginactivity.this, "Incorrect login id  or password", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(loginactivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "failure: " + t.getLocalizedMessage().toString());
                dialog.dismiss();
                Toast.makeText(loginactivity.this, "something went wrong"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}