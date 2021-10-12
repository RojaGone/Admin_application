package com.example.adminaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.adminaplication.Fragments.ChangePasswordFragment;
import com.example.adminaplication.Fragments.DashboardFragment;
import com.example.adminaplication.Fragments.VendorDetailsFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    TextView Today_requests,Total_vendorRequests,Accepted_requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }

        Today_requests = findViewById(R.id.Today_requests);
        Total_vendorRequests = findViewById(R.id.Total_vendorRequests);
        Accepted_requests = findViewById(R.id.Accepted_requests);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("VendorRegistration").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                int count_todayOrders = 0;
                int count_acceptedOrders = 0;
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String isConfirm = ds.child("isConfirm").getValue(String.class);
                    Log.e(" isConfirm", "onclick :" + isConfirm);
                    String currentDate = ds.child("currentDate").getValue(String.class);
                    Log.e(" currentDate", "onclick :" + currentDate);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    Calendar c = Calendar.getInstance();
                    String date = sdf.format(c.getTime());
                    Log.e("date", "onClick: " + date );

                    if(isConfirm.equals("true") || isConfirm.equals("done")){
                        count++;
                    }
                    if (isConfirm.equals("true")){
                        count_acceptedOrders++;
                    }
                    if (currentDate.equals(date)){
                        count_todayOrders++;
                    }
                }
                Log.e("total orders","onclick :"+count);
                Log.e(" count_todayOrders","onclick :"+count_todayOrders);
                Log.e("accepted orders","onclick :"+count_acceptedOrders);
                Today_requests.setText(String.valueOf(count_todayOrders));
                Total_vendorRequests.setText(String.valueOf(count));
                Accepted_requests.setText(String.valueOf(count_acceptedOrders));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
                break;
            case R.id.nav_changePass:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChangePasswordFragment()).commit();
                Bundle bundle = new Bundle();
                bundle.putString("changePasswordClick","changePasswordClick");
                ChangePasswordFragment fragobj = new ChangePasswordFragment();
                fragobj.setArguments(bundle);
                break;
            case R.id.nav_vendorDetails:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new VendorDetailsFragment()).commit();
                break;
            case R.id.nav_logout:
                logoutMethod();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutMethod() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        Log.e("fragment.. ","onlcick :"+fragment);
        if(fragment != null){
            transaction= fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }else if(drawerLayout.isDrawerOpen(GravityCompat.START)){
           drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }


    }
}