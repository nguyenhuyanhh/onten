package com.onten.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onten.android.fragment.Config;
import com.onten.android.fragment.SecurePreferences;
import com.onten.android.fragment.UserInfoContainer;

/**
 * Created by Peiying.Lim on 5/4/2017.
 */
public class MainLoginActivity extends AppCompatActivity {
    private EditText edtemail, edtpassword;
    private TextView txtlogin, registration, txtviewguest;
    //    private Quize.MyViewPager recyclerViewadapter;
    ProgressDialog progressDialog;
    private DatabaseReference databaseDatas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginfragment);


        Firebase.setAndroidContext(MainLoginActivity.this);
        Firebase ref = new Firebase(Config.FIREBASE_URL);
        databaseDatas = FirebaseDatabase.getInstance().getReference("registration");

        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpassword = (EditText) findViewById(R.id.edtpassword);
        txtlogin = (TextView) findViewById(R.id.txtlogin);
        registration = (TextView) findViewById(R.id.registration);
        txtviewguest = (TextView) findViewById(R.id.txtviewguest);

        progressDialog = new ProgressDialog(MainLoginActivity.this);
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(false);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtemail.getText().toString().equals("")) {
                    Toast.makeText(MainLoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(MainLoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    String result = edtemail.getText().toString().replaceAll("[-+.^:,]", "");
                    addUserChangeListener(result, edtpassword.getText().toString());
                }
            }
        });
        txtviewguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                finish();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainRegistrationActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    private void addUserChangeListener(String childid, final String password) {
        databaseDatas.child(childid).addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserInfoContainer data = dataSnapshot.getValue(UserInfoContainer.class);
                if (data == null) {
                    return;
                } else {
                    String datapassword = data.getPassword();
                    if (password.equals(datapassword)) {
                        String result = data.getEmail().replaceAll("[-+.^:,]", "");
                        SecurePreferences.savePreferences(getApplicationContext(), "user_id", result);
                        SecurePreferences.savePreferences(getApplicationContext(), "username", data.getName());
                        SecurePreferences.savePreferences(getApplicationContext(), "email", data.getEmail());
                        SecurePreferences.savePreferences(getApplicationContext(), "course", data.getCourse());
                        SecurePreferences.savePreferences(getApplicationContext(), "isLogin", true);
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(MainLoginActivity.this, "Invalid username and password", Toast.LENGTH_SHORT).show();
                    }
                }

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
