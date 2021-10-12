package com.example.adminaplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminaplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardFragment extends Fragment {
    TextView Today_requests,Total_vendorRequests,Accepted_requests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.dashboard_fragment, container, false);

        Today_requests = root.findViewById(R.id.Today_requests);
        Total_vendorRequests = root.findViewById(R.id.Total_vendorRequests);
        Accepted_requests = root.findViewById(R.id.Accepted_requests);

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

        return root;
    }
}
