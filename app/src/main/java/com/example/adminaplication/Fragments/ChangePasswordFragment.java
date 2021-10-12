package com.example.adminaplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminaplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordFragment extends Fragment {
    EditText cp_email;
    Button cp_send_btn;
    TextView changePassword_title,forgotPassword_title;

    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.changepassword_fragment, container, false);

        cp_email = root.findViewById(R.id.cp_email);
        cp_send_btn = root.findViewById(R.id.cp_send_btn);
        changePassword_title = root.findViewById(R.id.changePassword_title);
        forgotPassword_title = root.findViewById(R.id.forgotPassword_title);

        firebaseAuth = FirebaseAuth.getInstance();

        try {
            String get_forgotPasswordClick = getArguments().getString("forgotPasswordClick");

            if (get_forgotPasswordClick.equals("forgotPasswordClick")) {
                changePassword_title.setVisibility(View.GONE);
                forgotPassword_title.setVisibility(View.VISIBLE);
            }
        }catch (NullPointerException e){

                changePassword_title.setVisibility(View.VISIBLE);
                forgotPassword_title.setVisibility(View.GONE);

        }

        cp_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
//                DatabaseReference databaseReference1 = firebaseDatabase.getReference();
//                databaseReference1.child("AdminReg").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot ds : snapshot.getChildren()){
//                            String email = snapshot.child("Email").getValue(String.class);
//                            if(email.equals("kalpanagone4@gmail.com")){
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                firebaseAuth.sendPasswordResetEmail(cp_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Password send to your email....please check your email", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return root;
    }
}
