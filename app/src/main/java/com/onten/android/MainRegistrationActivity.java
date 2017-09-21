package com.onten.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onten.android.fragment.Config;
import com.onten.android.fragment.SecurePreferences;
import com.onten.android.fragment.UserInfoContainer;

/**
 * Created by Peiying.Lim on 5/4/2017.
 */
public class MainRegistrationActivity extends AppCompatActivity {
    EditText edtemail, edtpassword, edtadminno, edtname;
    TextView txtsignup;
    CheckBox chkmicroprocessor, checkenge, checksignals;
    long diff = 15;
    //    private Quize.MyViewPager recyclerViewadapter;
    private DatabaseReference databaseDatas;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    boolean isexist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupfragment);
        Firebase.setAndroidContext(MainRegistrationActivity.this);
        Firebase ref = new Firebase(Config.FIREBASE_URL);

        databaseDatas = FirebaseDatabase.getInstance().getReference("registration");

        firebaseAuth = FirebaseAuth.getInstance();


        edtname = (EditText) findViewById(R.id.edtname);
        edtadminno = (EditText) findViewById(R.id.edtadminno);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpassword = (EditText) findViewById(R.id.edtpassword);
        chkmicroprocessor = (CheckBox) findViewById(R.id.chkmicroprocessor);
        checkenge = (CheckBox) findViewById(R.id.checkenge);
        checksignals = (CheckBox) findViewById(R.id.checksignals);


        txtsignup = (TextView) findViewById(R.id.txtsignup);

        progressDialog = new ProgressDialog(MainRegistrationActivity.this);
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(false);

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtadminno.getText().toString().equals("")) {
                    Toast.makeText(MainRegistrationActivity.this, "Please Enter Admission No.", Toast.LENGTH_SHORT).show();
                } else if (edtname.getText().toString().equals("")) {
                    Toast.makeText(MainRegistrationActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (edtemail.getText().toString().equals("")) {
                    Toast.makeText(MainRegistrationActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(MainRegistrationActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (chkmicroprocessor.isChecked() || checkenge.isChecked() || checksignals.isChecked()) {
                        progressDialog.show();
                        if (ifUserExist(edtemail.getText().toString())) {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(MainRegistrationActivity.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                        } else {
                            String finalcourse = "";
                            if (chkmicroprocessor.isChecked()) {
                                finalcourse = chkmicroprocessor.getText().toString();
                            }
                            if (checkenge.isChecked()) {
                                if (finalcourse.equals("")) {
                                    finalcourse = checkenge.getText().toString();
                                } else {
                                    finalcourse = finalcourse + "," + checkenge.getText().toString();
                                }
                            }
                            if (checksignals.isChecked()) {
                                if (finalcourse.equals("")) {
                                    finalcourse = checksignals.getText().toString();
                                } else {
                                    finalcourse = finalcourse + "," + checksignals.getText().toString();
                                }
                            }
                            createUser(edtname.getText().toString(), edtemail.getText().toString(), edtadminno.getText().toString(), finalcourse, edtpassword.getText().toString());
                        }
                    } else {
                        Toast.makeText(MainRegistrationActivity.this, "Please Select one course", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean ifUserExist(String userid) {
        String result = userid.replaceAll("[-+.^:,]", "");
        databaseDatas.child(result).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isexist = true;
                } else {
                    isexist = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
        return isexist;
    }


    private void createUser(String personName, String personEmail, String adminno, String course, String password) {
        UserInfoContainer data = new UserInfoContainer();
        data.setName(personName);
        data.setEmail(personEmail);
        data.setAdminno(adminno);
        data.setPassword(password);
        data.setCourse(course);
        String result = personEmail.replaceAll("[-+.^:,]", "");
        databaseDatas.child(result).setValue(data);

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        final String id = databaseDatas.push().getKey();
        databaseDatas.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserInfoContainer data = dataSnapshot.getValue(UserInfoContainer.class);
                if (data == null) {
                    return;
                } else {
                    String result = data.getEmail().replaceAll("[-+.^:,]", "");
                    SecurePreferences.savePreferences(getApplicationContext(), "user_id", result);
                    SecurePreferences.savePreferences(getApplicationContext(), "username", data.getName());
                    SecurePreferences.savePreferences(getApplicationContext(), "email", data.getEmail());
                    SecurePreferences.savePreferences(getApplicationContext(), "course", data.getCourse());
                    Intent in = new Intent(getApplicationContext(), MainLoginActivity.class);
                    startActivity(in);
                    MainRegistrationActivity.this.finish();
                }

                Uri imgUri = Uri.parse(Config.FIREBASE_URl_REGISTRATION);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
