package com.futureproducts.gbrself.appsupport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.futureproducts.gbrself.loginactivity;

import java.util.HashMap;

public class SessionManage {

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences cart;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    // First time logic Check
    public static final String FIRST_TIME = "firsttime";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String LOGEDIN= "";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // userid address (make variable public to access from outside)
    public static final String USERID = "userid";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // Email address (make variable public to access from outside)
    public static final String KEYID = "keyid";

    // Mobile number (make variable public to access from outside)
    public static final String KEY_MOBiLE = "mobile";

    // user avatar (make variable public to access from outside)
    public static final String KEY_PHOTO = "photo";

    // number of items in our cart
    public static final String KEY_CART = "cartvalue";

    // number of items in our wishlist
    public static final String KEY_WISHLIST = "wishlistvalue";

    // check first time app launch
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";



    /*
     *
     *
     *   My Lava
     *
     */

    public static final String CARD_DATA = "CARD_DATA";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String NOTIFICATION_LIST_STORE = "NOTIFICATION_LIST_STORE";


    public static final String ID = "ID";
    public static final String USER_UNIQUE_ID = "USER_UNIQUE_ID";
    public static final String NAME = "NAME";
    public static final String UNAME = "UNAME";
    public static final String UMOBILE = "UMOBILE";
    public static final String USERNAME = "USERNAME";
    public static final String EMAIL = "EMAIL";
    public static final String MOBILE = "MOBILE";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String PASSWORD = "PASSWORD";
    public static final String GOVERNATE = "GOVERNATE";
    public static final String CITY = "CITY";
    public static final String LOCALITY = "LOCALITY";
    public static final String TIME = "TIME";
    public static final String ADDRESS = "ADDRESS";
    public static final String LOCALITY_ID = "LOCALITY_ID";
    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";
    public static final String DISTRIBUTOR_NAME = "DISTRIBUTOR_NAME";

    public static final String FIRST_TIME_LANGUAGE = "FIRST_TIME_LANGUAGE";
    public static final String BRAND_ID = "BRAND_ID";
    public static final String BRAND_NAME = "BRAND_NAME";
    public static final String BRAND_NAME_AR = "BRAND_NAME_AR";
    public static final String BRAND_NAME_FR = "BRAND_NAME_FR";

    public static final String LOCALITY_AR = "LOCALITY_AR";

    public static final String REMEMBER_MOB = "REMEMBER_MOB";
    public static final String REMEMBER_PSW = "REMEMBER_PSW";
    public static final String OUTLET_NAME = "OUTLET_NAME";
    public static final String USER_IMG = "USER_IMG";
    public static final String COUNTRY_NAME = "COUNTRY_NAME";
    public static final String COUNTRY_ID = "COUNTRY_ID";

    public static final String LOGIN_COUNTRY_ID = "LOGIN_COUNTRY_ID";
    public static final String LOGIN_COUNTRY_NAME = "LOGIN_COUNTRY_NAME";
    public static final String LOGIN_COUNTRY_NAME_AR = "LOGIN_COUNTRY_NAME_AR";
    public static final String LOGIN_COUNTRY_FLAG = "LOGIN_COUNTRY_FLAG";
    public static final String LOGIN_COUNTRY_CURRENCY = "LOGIN_COUNTRY_CURRENCY";
    public static final String LOGIN_COUNTRY_CURRENCY_SYMBOL = "LOGIN_COUNTRY_CURRENCY_SYMBOL";

    public static final String PROFILE_PERCENTAGE  = "PROFILE_PERCENTAGE";
    public static final String PROFILE_VERIFY_CHECK  = "PROFILE_VERIFY_CHECK";
    public static final String PROFILE_VERIFICATION  = "PROFILE_VERIFICATION";

    private static SessionManage ourInstance = null;

    public static SessionManage getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SessionManage(context);
        }
        return ourInstance;
    }

    // Constructor
    private SessionManage(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(DISTRIBUTOR_ID, pref.getString(DISTRIBUTOR_ID, null));
        user.put(DISTRIBUTOR_NAME, pref.getString(DISTRIBUTOR_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user phone number
        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));
        user.put(USERNAME, pref.getString(USERNAME, null));
        user.put(UMOBILE, pref.getString(UMOBILE, null));

        // user avatar
        user.put(KEYID, pref.getString(KEYID, null));
        user.put(USERID, pref.getString(USERID, null));

//        CARD_DATA
        user.put(CARD_DATA, pref.getString(CARD_DATA, null));

        user.put(LANGUAGE, pref.getString(LANGUAGE, null));

        user.put(ID, pref.getString(ID, null));

        user.put(FIRST_TIME_LANGUAGE, pref.getString(FIRST_TIME_LANGUAGE, null));

        user.put(BRAND_ID, pref.getString(BRAND_ID, null));
        user.put(USER_UNIQUE_ID, pref.getString(USER_UNIQUE_ID, null));
        user.put(BRAND_NAME, pref.getString(BRAND_NAME, null));
        user.put(BRAND_NAME_AR, pref.getString(BRAND_NAME_AR, null));
        user.put(LOCALITY_ID, pref.getString(LOCALITY_ID, null));
//        user.put(IS_LOGIN, pref.getString(IS_LOGIN, null));



        user.put(NOTIFICATION_LIST_STORE, pref.getString(NOTIFICATION_LIST_STORE, null));

        user.put(LOCALITY, pref.getString(LOCALITY, null));
        user.put(NAME, pref.getString(NAME, null));
        user.put(UNAME, pref.getString(UNAME, null));
        user.put(MOBILE, pref.getString(MOBILE, null));
        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(ADDRESS, pref.getString(ADDRESS, null));
        user.put(LOCALITY_AR, pref.getString(LOCALITY_AR, null));
        user.put(REMEMBER_MOB, pref.getString(REMEMBER_MOB, null));
        user.put(REMEMBER_PSW, pref.getString(REMEMBER_PSW, null));
        user.put(GOVERNATE, pref.getString(GOVERNATE, null));
        user.put(OUTLET_NAME, pref.getString(OUTLET_NAME, null));
        user.put(USER_IMG, pref.getString(USER_IMG, null));
        user.put(PASSWORD, pref.getString(PASSWORD, null));
        user.put(COUNTRY_NAME, pref.getString(COUNTRY_NAME, null));
        user.put(COUNTRY_ID, pref.getString(COUNTRY_ID, null));
        user.put(LOGIN_COUNTRY_ID, pref.getString(LOGIN_COUNTRY_ID, null));
        user.put(LOGIN_COUNTRY_NAME, pref.getString(LOGIN_COUNTRY_NAME, null));
        user.put(PROFILE_VERIFY_CHECK, pref.getString(PROFILE_VERIFY_CHECK, null));
        user.put(PROFILE_VERIFICATION, pref.getString(PROFILE_VERIFICATION, null));
        user.put(LOGIN_COUNTRY_CURRENCY, pref.getString(LOGIN_COUNTRY_CURRENCY, null));
        user.put(LOGIN_COUNTRY_CURRENCY_SYMBOL, pref.getString(LOGIN_COUNTRY_CURRENCY_SYMBOL, null));
        user.put(LOGIN_COUNTRY_NAME_AR, pref.getString(LOGIN_COUNTRY_NAME_AR, null));
        user.put(LOGIN_COUNTRY_FLAG, pref.getString(LOGIN_COUNTRY_FLAG, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, loginactivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /*
     *
     * lava session
     *
     * */


    public void addcard(String json) {
        editor.putString(CARD_DATA, json);
        editor.commit();
    }

    public  void userdetails(String name){
        editor.putString(UNAME, name);
        editor.commit();
    }

    public void homedetails(String dis_id, String login_id, String mobile, String name ){
        Log.d("tag", "checkname1sess    "+name +"   "+ dis_id+"   "+mobile);
        editor.putString(DISTRIBUTOR_ID, dis_id);
        editor.putString(DISTRIBUTOR_NAME, login_id);
        editor.putString(UMOBILE, mobile);
        editor.putString(USERNAME, name);
        editor.commit();
    }

    public void setlanguage(String language) {
        editor.putString(LANGUAGE, language);
        editor.commit();
    }


    public void clearaddcard() {
        editor.remove(CARD_DATA).commit();
    }


    public void UserDetail(String id , String user_unique_id, String name, String email, String mobile, String user_type, String password
            , String governate, String locality_ar, String locality, String time, String address
            , String locality_id , String outlet_name , String img , String country_name , String country_id) {
        editor.putString(ID, id);
        editor.putString(USER_UNIQUE_ID, user_unique_id);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);
        editor.putString(USER_TYPE, user_type);
        editor.putString(PASSWORD, password);
        editor.putString(GOVERNATE, governate);
        editor.putString(LOCALITY_AR, locality_ar);
        editor.putString(LOCALITY, locality);
        editor.putString(TIME, time);
        editor.putString(ADDRESS, address);
        editor.putString(LOCALITY_ID, locality_id);
        editor.putString(OUTLET_NAME, outlet_name);
        editor.putString(USER_IMG, img);
        editor.putString(COUNTRY_NAME, country_name);
        editor.putString(COUNTRY_ID, country_id);
        editor.commit();
    }

    public void FirstTimeLanguage(String firsttime) {
        editor.putString(FIRST_TIME_LANGUAGE, firsttime);
        editor.commit();
    }

    public void brandSelect(String brandid , String brand_name , String brand_name_ar ,String brand_name_fr ) {
        editor.putString(BRAND_ID, brandid);
        editor.putString(BRAND_NAME, brand_name);
        editor.putString(BRAND_NAME_AR, brand_name_ar);
        editor.putString(BRAND_NAME_FR, brand_name_fr);
        editor.commit();
    }

    public void logout() {
        editor.remove(FIRST_TIME_LANGUAGE).commit();
        editor.remove(BRAND_ID).commit();
    }

    public void loginuser(String login){
        editor.putString(LOGEDIN, login);
        editor.commit();
    }

    public String loginn(){
       return pref.getString(LOGEDIN, "");
    }

    public void NotificationStore(String list) {
        editor.putString(NOTIFICATION_LIST_STORE, list).commit();
    }

    public void RemoveNotificationStore() {
        editor.remove(NOTIFICATION_LIST_STORE).commit();
    }

    public void RememberMe(String mob , String psw){
        editor.putString(REMEMBER_MOB , mob);
        editor.putString(REMEMBER_PSW , psw);
        editor.commit();
    }

    public void BrandClear(){
        editor.remove(BRAND_ID).commit();
        editor.remove(BRAND_NAME).commit();
        editor.remove(BRAND_NAME_AR).commit();
    }

    public void AddressChange(String address){
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public void LOGIN_COUNTRY(String country_id , String country_name , String currency , String currency_symbol , String country_name_ar , String flag){
        editor.putString(LOGIN_COUNTRY_ID , country_id);
        editor.putString(LOGIN_COUNTRY_NAME , country_name);
        editor.putString(LOGIN_COUNTRY_CURRENCY , currency);
        editor.putString(LOGIN_COUNTRY_CURRENCY_SYMBOL , currency_symbol);
        editor.putString(LOGIN_COUNTRY_NAME_AR , country_name_ar);
        editor.putString(LOGIN_COUNTRY_FLAG , flag);
        editor.commit();
    }

    public void Profile_Percentage(String countNum){
        editor.putString(PROFILE_PERCENTAGE , countNum).commit();
    }

    public int GetProfilePercent(){
        double tot= 8;
        int fill= 0;
        if(!pref.getString(NAME,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(EMAIL,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(OUTLET_NAME,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(ADDRESS,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(COUNTRY_NAME,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(LOCALITY,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(USER_IMG,"").isEmpty()){
            fill++;
        }
        if(!pref.getString(GOVERNATE,"").isEmpty()){
            fill++;
        }
        double div = fill/tot;
        double per= div*100;
        return (int) per;
    }

    public void PROFILE_VERIFY_CHECK(String check){
        editor.putString(PROFILE_VERIFY_CHECK , check).commit();
    }

    public void PROFILE_VERIFICATION(String check){
        editor.putString(PROFILE_VERIFICATION , check).commit();
    }


}




























