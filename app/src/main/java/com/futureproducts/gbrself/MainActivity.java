package com.futureproducts.gbrself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.futureproducts.gbrself.apisupport.BackButtonHandlerInterface;
import com.futureproducts.gbrself.apisupport.OnBackClickListener;
import com.futureproducts.gbrself.appsupport.SessionManage;
import com.futureproducts.gbrself.fragments.home;
import com.futureproducts.gbrself.fragments.menu;
import com.futureproducts.gbrself.fragments.orders;
import com.futureproducts.gbrself.fragments.profile;
import com.futureproducts.gbrself.fragments.viewtickets;
import com.futureproducts.gbrself.homefragments.salesorderfragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BackButtonHandlerInterface {

    BottomNavigationView bottomNav;
    DrawerLayout dLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    SessionManage sessionManage;
    MenuItem id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNav = findViewById(R.id.bottomnav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        navigationView = findViewById(R.id.sidenavi);
        sessionManage = SessionManage.getInstance(this);

        dLayout = findViewById(R.id.my_drawer_layout);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                id1 = item;
                item.setChecked(true);
//                dLayout.closeDrawer(GravityCompat.START);
                Fragment selectedFragment = null;
                switch (id) {
                    case R.id.logout:
                        sessionManage.loginuser("logedout");
                        Intent go = new Intent(MainActivity.this, loginactivity.class);
                        startActivity(go);
                        finish();
                        break;
                    case R.id.vticket:
                        id1.setChecked(false);
                        selectedFragment = new viewtickets();
                        replacefragment(selectedFragment);
                        dLayout.closeDrawers();
                        break;
                    case R.id.home:
                        id1.setChecked(false);
                        selectedFragment = new home();
                        replacefragment(selectedFragment);
                        dLayout.closeDrawers();
                        break;
                    case R.id.orders:
                        id1.setChecked(false);
                        selectedFragment = new orders();
                        replacefragment(selectedFragment);
                        dLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        id1.setChecked(false);
                        selectedFragment = new profile();
                        replacefragment(selectedFragment);
                        dLayout.closeDrawers();
                        break;
                }

                return true;
            }
        });

        if (id1 != null) {
            if (id1.isChecked()) {


            }
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:

//                    id1.setChecked(false);
                    selectedFragment = new home();
                    replacefragment(selectedFragment);


                    break;
                case R.id.orders:
//                    id1.setChecked(false);
                    selectedFragment = new orders();
                    replacefragment(selectedFragment);
                    break;
                case R.id.profile:
//                    id1.setChecked(false);
                    selectedFragment = new profile();
                    replacefragment(selectedFragment);
                    break;
                case R.id.menu:
                    actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, dLayout, 0, 0);

                    // pass the Open and Close toggle for the drawer layout listener
                    // to toggle the button
                    dLayout.addDrawerListener(actionBarDrawerToggle);
                    actionBarDrawerToggle.syncState();
                    dLayout.openDrawer(GravityCompat.END);
//                    selectedFragment = new menu();
                    break;
            }
            // It will help to replace the
            // one fragment to other.

            return true;
        }
    };

    private void replacefragment(Fragment selectedFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {

        salesorderfragment salesorderfragment;
        if((salesorderfragment = (com.futureproducts.gbrself.homefragments.salesorderfragment)
                getSupportFragmentManager().findFragmentByTag("salesOrder"))!=null){

            if(salesorderfragment.isAdded()){
                salesorderfragment.onBackClick();
            }else{
                super.onBackPressed();
            }

        }else{

            super.onBackPressed();

        }

//        finish();
//        Toast.makeText(this, "backpress", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void addBackClickListener(OnBackClickListener onBackClickListener) {

    }

    @Override
    public void removeBackClickListener(OnBackClickListener onBackClickListener) {

    }


}