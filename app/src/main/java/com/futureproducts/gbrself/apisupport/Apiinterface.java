package com.futureproducts.gbrself.apisupport;

import com.futureproducts.gbrself.models.likes;
import com.futureproducts.gbrself.models.myorders;
import com.futureproducts.gbrself.models.newOrder;
import com.futureproducts.gbrself.models.njorder;
import com.futureproducts.gbrself.models.njorder1;
import com.futureproducts.gbrself.models.ordermodel1;
import com.futureproducts.gbrself.models.ordermodel2;
import com.futureproducts.gbrself.reportrsmodel.DataRecieved;

import com.futureproducts.gbrself.reportrsmodel.ImgRecieved;
import com.futureproducts.gbrself.reportrsmodel.ImgUrl;
import com.futureproducts.gbrself.reportrsmodel.TicketRespons;
import com.futureproducts.gbrself.reportrsmodel.reportsmodel;

import com.futureproducts.gbrself.viewtickets.myticketmodel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Apiinterface {


    @POST("login")
    @FormUrlEncoded
    Call<Object> Login(@Field("login_id") String mobile, @Field("password") String password);

    @POST("shop_list")
    @FormUrlEncoded
    Call<Object> Shoplist(@Field("branch_id") String branchid);

    @POST("check_imei")
    @FormUrlEncoded
    Call<Object> checkimei(@Field("imei") String imei, @Field("distributor_id") String distributor_id, @Field("country_id") String country_id);

    @POST("buy_products")
    Call<ordermodel2> buyproduct(@Body ordermodel1 jsonObject);

    @POST("my_orders")
    @FormUrlEncoded
    Call<njorder> getOrders(@Field("dse_id") String dse_id, @Field("start_date") String start_date, @Field("end_date") String end_date);

    @POST("profile_detail")
    @FormUrlEncoded
    Call<Object> myprofile(@Field("login_id")String login_id);

    @POST("check_imei_report")
    @FormUrlEncoded
    Call<reportsmodel> getreports(@Field("imei") String imei, @Field("distributor_id") String dse_id);
//    @POST("submit_ticket_dse")
//    Call<ImgUrl> submitticket(@Body DataRecievedX cmode, @Body ImgRecieved imodel);

//    @Multipart
//    @POST("submit_ticket_dse")
//    Call<ImgUrl>  submitticket(@Part("dse_id") RequestBody body,@Part("user_name") RequestBody body1,@Part("user_mobile") RequestBody body2,@Part("heading") RequestBody body3,@Part("description") RequestBody body4,
//                               @Part MultipartBody.Part file);

    @POST("my_submit_ticket_dse")
    @FormUrlEncoded
    Call<myticketmodel> gettickets(@Field("dse_id") String dse_id);

    @POST("submit_ticket_dse")
    @Multipart
    Call<TicketRespons> submitticket(@Part MultipartBody.Part img_url,
                                     @Part("user_mobile")RequestBody usermobile,
                                     @Part("heading")RequestBody heading,
                                     @Part("description")RequestBody description,
                                     @Part("dse_id")RequestBody dse_id,
                                     @Part("user_name")RequestBody user_name);


//    @POST("submit_ticket_dse")
//    @FormUrlEncoded
//    Call<ImgUrl> submitticket(@Field("dse_id") String dse_id, @Field("user_name") String user_name, @Field("user_mobile") String user_mobile,
//                                    @Field("heading") String heading, @Field("description") String description, @Field("img_url") String img_url);

//give me the api

}
