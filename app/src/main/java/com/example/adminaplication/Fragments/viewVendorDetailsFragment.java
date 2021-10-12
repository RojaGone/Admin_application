package com.example.adminaplication.Fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminaplication.Models.VendorDetailsItemModel;
import com.example.adminaplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewVendorDetailsFragment extends Fragment {
    TextView vvd_shopName,vvd_shopMobile,vvd_shopAddress,vvd_pincode,vvd_city,vvd_openTime,vvd_closeTime;
    ImageView vvd_shopImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.view_vendordetails_fragment, container, false);

        vvd_shopName = root.findViewById(R.id.vvd_shopName);
        vvd_shopMobile = root.findViewById(R.id.vvd_shopMobile);
        vvd_shopAddress = root.findViewById(R.id.vvd_shopAddress);
        vvd_pincode = root.findViewById(R.id.vvd_pincode);
        vvd_city = root.findViewById(R.id.vvd_city);
        vvd_openTime = root.findViewById(R.id.vvd_openTime);
        vvd_closeTime = root.findViewById(R.id.vvd_closeTime);
        vvd_shopImage = root.findViewById(R.id.vvd_shopImage);

        String get_shopName = getArguments().getString("shopName");
        Log.e("get_shopName","onclick :"+get_shopName);
        String get_shopMobile = getArguments().getString("shopMobile");
        String get_Shopaddress = getArguments().getString("Shopaddress");
        String get_pincode = getArguments().getString("pincode");
        String get_city = getArguments().getString("city");
        String get_opentime = getArguments().getString("opentime");
        String get_closetime = getArguments().getString("closetime");
        String get_shopImage = getArguments().getString("shopImage");

        vvd_shopName.setText(get_shopName);
        vvd_shopMobile.setText(get_shopMobile);
        vvd_shopAddress.setText(get_Shopaddress);
        vvd_pincode.setText(get_pincode);
        vvd_city.setText(get_city);
        vvd_openTime.setText(get_opentime);
        vvd_closeTime.setText(get_closetime);

        byte[] imageAsBytes = Base64.decode(get_shopImage.getBytes(), Base64.DEFAULT);
        vvd_shopImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        return root;
    }
}
