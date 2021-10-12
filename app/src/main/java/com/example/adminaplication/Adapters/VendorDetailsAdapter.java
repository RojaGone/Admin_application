package com.example.adminaplication.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminaplication.Fragments.viewVendorDetailsFragment;
import com.example.adminaplication.Models.VendorDetailsItemModel;
import com.example.adminaplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VendorDetailsAdapter extends FirebaseRecyclerAdapter<VendorDetailsItemModel,VendorDetailsAdapter.ViewHolder> {
    Context context;

    public VendorDetailsAdapter(@NonNull FirebaseRecyclerOptions<VendorDetailsItemModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.vendor_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull VendorDetailsItemModel model) {

        if(model.getIsConfirm().equals("done") || model.getIsConfirm().equals("true")) {
            FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
            DatabaseReference databaseReference2 = firebaseDatabase2.getReference();
            databaseReference2.child("ShopProfile").orderByChild("id").equalTo(model.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String signup_shopname = ds.child("signup_shopname").getValue(String.class);
                        String signup_shoplogo = ds.child("signup_shoplogo").getValue(String.class);
                        String shopaddress = ds.child("signup_shopaddress").getValue(String.class);
                        String shopphoneNo = ds.child("signup_shopphoneNo").getValue(String.class);
                        String pincode = ds.child("signup_pincode").getValue(String.class);
                        String city = ds.child("signup_city").getValue(String.class);
                        String opentime = ds.child("signup_opentime").getValue(String.class);
                        String closetime = ds.child("signup_closetime").getValue(String.class);

                        holder.vdi_vendorName.setText(model.getFname());
                        holder.vdi_shopName.setText(signup_shopname);
                        holder.vdi_email.setText(model.getEmail());
                        holder.vdi_mobile.setText(model.getMobile());
                        Log.e("vendor name", "onclick :" + model.getFname());
                        Log.e("shop name", "onclick :" + signup_shopname);

                        String mBase64string = model.getPicture();

                        try {
                            byte[] imageAsBytes = Base64.decode(mBase64string.getBytes(), Base64.DEFAULT);
                            holder.vdi_shopImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        }catch (IllegalArgumentException e){
                            Log.e("mBase64string", "onclick :" + mBase64string);
                            Uri image = Uri.parse(model.getPicture());
                            Glide.with(context.getApplicationContext()).load(image).into( holder.vdi_shopImage);
                        }

                        Log.e(" currentItem getIsOrderConfirm adapter", "onclick :" + model.getIsConfirm());
                        if (model.getIsConfirm().equals("true")) {
                            holder.approved_btn.setVisibility(View.VISIBLE);
                            holder.approve_btn.setVisibility(View.GONE);
                            holder.relativeLayout.setBackgroundResource(R.drawable.color_bg);
                            holder.reject_btn.setVisibility(View.GONE);
                            holder.delete_btn.setVisibility(View.VISIBLE);
                            holder.approve_btn.setEnabled(false);
                        } else {
                            holder.approve_btn.setVisibility(View.VISIBLE);
                            holder.approved_btn.setVisibility(View.GONE);
                            holder.relativeLayout.setBackgroundResource(R.drawable.gray_stoke_bg);
                            holder.reject_btn.setVisibility(View.VISIBLE);
                            holder.delete_btn.setVisibility(View.GONE);
                        }

                        holder.approve_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String approve = "true";
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                firebaseDatabase.getReference().child("VendorRegistration").child(model.getId()).child("isConfirm").setValue(approve);
                                Toast.makeText(view.getContext(), "Approved item successfully ", Toast.LENGTH_SHORT).show();
                            }
                        });

                        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                firebaseDatabase.getReference().child("VendorRegistration").child(model.getId()).removeValue();
                                Toast.makeText(view.getContext(), "Delete item successfully ", Toast.LENGTH_SHORT).show();
                            }
                        });

                        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String reject = "false";
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                firebaseDatabase.getReference().child("VendorRegistration").child(model.getId()).child("isConfirm").setValue(reject);
                                Toast.makeText(view.getContext(), "Rejected item ...", Toast.LENGTH_SHORT).show();
                            }
                        });

                        holder.details_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Bundle bundle = new Bundle();
                                bundle.putString("shopName", signup_shopname);
                                bundle.putString("shopMobile", shopphoneNo);
                                bundle.putString("Shopaddress", shopaddress);
                                bundle.putString("pincode", pincode);
                                bundle.putString("city", city);
                                bundle.putString("opentime", opentime);
                                bundle.putString("closetime", closetime);
                                bundle.putString("shopImage", signup_shoplogo);
                                viewVendorDetailsFragment fragobj = new viewVendorDetailsFragment();
                                fragobj.setArguments(bundle);
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragobj).addToBackStack(null).commit();

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            holder.itemView.setLayoutParams(params);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vdi_vendorName, vdi_shopName, vdi_email, vdi_mobile;
        ImageView vdi_shopImage;
        RelativeLayout relativeLayout;
        Button approve_btn, details_btn, delete_btn, reject_btn, approved_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.vdi_vendorName = (TextView) itemView.findViewById(R.id.vdi_vendorName);
            this.vdi_shopName = (TextView) itemView.findViewById(R.id.vdi_shopName);
            this.vdi_email = (TextView) itemView.findViewById(R.id.vdi_email);
            this.vdi_mobile = (TextView) itemView.findViewById(R.id.vdi_mobile);
            this.vdi_shopImage = (ImageView) itemView.findViewById(R.id.vdi_shopImage);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            this.approve_btn = (Button) itemView.findViewById(R.id.approve_btn);
            this.details_btn = (Button) itemView.findViewById(R.id.details_btn);
            this.delete_btn = (Button) itemView.findViewById(R.id.delete_btn);
            this.reject_btn = (Button) itemView.findViewById(R.id.reject_btn);
            this.approved_btn = (Button) itemView.findViewById(R.id.approved_btn);
        }

    }
}



