package com.example.adminaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminaplication.Fragments.ChangePasswordFragment;
import com.example.adminaplication.Fragments.viewVendorDetailsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    ImageView password_visibility,password_visibility_off;
    EditText password,email;
    TextView forgot_pass;
    Button login_btn;
    FirebaseAuth firebaseAuth;
    RelativeLayout login_relative;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar=findViewById(R.id.progressbar);
        relativeLayout=findViewById(R.id.prog_relay);
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);


        password_visibility = (ImageView)findViewById(R.id.password_visibility);
        password_visibility_off = (ImageView)findViewById(R.id.password_visibility_off);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        login_btn = (Button)findViewById(R.id.login_btn);
        forgot_pass = (TextView)findViewById(R.id.forgot_pass);
        login_relative = findViewById(R.id.login_relative);

        password_visibility.setVisibility(View.VISIBLE);
        password_visibility_off.setVisibility(View.GONE);

        firebaseAuth =FirebaseAuth.getInstance();

        password_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_visibility.setVisibility(View.GONE);
                password_visibility_off.setVisibility(View.VISIBLE);
                password.setTransformationMethod(null);
            }
        });
        password_visibility_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_visibility.setVisibility(View.VISIBLE);
                password_visibility_off.setVisibility(View.GONE);
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                String get_email = email.getText().toString();
                String get_password = password.getText().toString();
                Log.e("password","onclick :"+get_password);
                if( TextUtils.isEmpty(get_email) || TextUtils.isEmpty(get_password)){
                    Toast.makeText(MainActivity.this, "Empty credential!", Toast.LENGTH_SHORT).show();
                    login_btn.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);

                }else{
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                    DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                    databaseReference1.child("AdminReg").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                String cat = snapshot.child("Email").getValue(String.class);
                                Log.e("email ","onclick :"+cat);
                                if( get_email.equals(cat)){
                                    loginUser(get_email, get_password);
                                }else {
                                    login_btn.setEnabled(true);
                                    Toast.makeText(MainActivity.this, "this email is not exit", Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    password.setText("");
                                    progressBar.setVisibility(View.GONE);
                                    relativeLayout.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_relative.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putString("forgotPasswordClick","forgotPasswordClick");
                ChangePasswordFragment fragobj = new ChangePasswordFragment();
                fragobj.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout_log,fragobj).commit();
                count = 1;
                Log.e("count in forgot pass","onclick :"+count);
            }
        });

    }

    private void loginUser(String get_email, String get_password) {
        firebaseAuth.signInWithEmailAndPassword(get_email,get_password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    FirebaseUser user =firebaseAuth.getCurrentUser();
                    Log.e("user Uid....","onclick :  "+user.getUid());
                    Log.e("user Email....","onclick :  "+user.getEmail());

                    Toast.makeText(MainActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(MainActivity.this, DashboardActivity.class);
//                    Log.e("Uid is ...","onClick :"+userID);
//                    intent.putExtra("Uid",userID);
                    startActivity(intent);
                    finish();
                }else{
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    login_btn.setEnabled(true);
                    email.setText("");
                    password.setText("");
                    Toast.makeText(MainActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // malli malli login cheyalsina avasram ledhu e code direct ga main page ni open chesthadi
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent= new Intent(MainActivity.this, DashboardActivity.class);
//            intent.putExtra("Uid",userID);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Log.e("hello","onclick");
        Log.e("count out","onclick :"+count);
        if (count == 1){
          Intent intent = new Intent(MainActivity.this,MainActivity.class);
          startActivity(intent);
          finish();
          count=0;
          Log.e("count inner","onclick :"+count);
        }else{
            super.onBackPressed();
        }
    }


}