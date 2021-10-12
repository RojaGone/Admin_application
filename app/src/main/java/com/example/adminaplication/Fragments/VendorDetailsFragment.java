package com.example.adminaplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminaplication.Adapters.VendorDetailsAdapter;
import com.example.adminaplication.Models.ShopProfileModel;
import com.example.adminaplication.Models.VendorDetailsItemModel;
import com.example.adminaplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorDetailsFragment extends Fragment {
    VendorDetailsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.vendordetails_fragment, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<VendorDetailsItemModel> options =
                new FirebaseRecyclerOptions.Builder<VendorDetailsItemModel>()
                        .setQuery(FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("VendorRegistration"), VendorDetailsItemModel.class)
                        .build();

        adapter = new VendorDetailsAdapter(options,getActivity());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
