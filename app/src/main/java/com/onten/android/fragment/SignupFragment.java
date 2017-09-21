package com.onten.android.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.onten.android.R;

/**
 * Created by Peiying.Lim on 17/3/2017.
 */


public class SignupFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signupfragment, container, false);
        Firebase.setAndroidContext(getActivity());
        Firebase ref = new Firebase(Config.FIREBASE_URL);

        databaseDatas = FirebaseDatabase.getInstance().getReference("registration");

        firebaseAuth = FirebaseAuth.getInstance();


        edtname = (EditText) view.findViewById(R.id.edtname);
        edtadminno = (EditText) view.findViewById(R.id.edtadminno);
        edtemail = (EditText) view.findViewById(R.id.edtemail);
        edtpassword = (EditText) view.findViewById(R.id.edtpassword);
        chkmicroprocessor = (CheckBox) view.findViewById(R.id.chkmicroprocessor);
        checkenge = (CheckBox) view.findViewById(R.id.checkenge);
        checksignals = (CheckBox) view.findViewById(R.id.checksignals);


        txtsignup = (TextView) view.findViewById(R.id.txtsignup);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(false);

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtadminno.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Admission No.", Toast.LENGTH_SHORT).show();
                } else if (edtname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (edtemail.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (chkmicroprocessor.isChecked() || checkenge.isChecked() || checksignals.isChecked()) {
                        progressDialog.show();
                        if (ifUserExist(edtemail.getText().toString())) {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getActivity(), "User Already Exist", Toast.LENGTH_SHORT).show();
                        } else {
                            createUser(edtname.getText().toString(), edtemail.getText().toString(), edtadminno.getText().toString(), "Microprocessor", edtpassword.getText().toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Select one course", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;

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
        if (TextUtils.isEmpty(personName)) {
            personName = databaseDatas.push().getKey();
        }

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
                    Toast.makeText(getActivity(), "Signup Successfully", Toast.LENGTH_SHORT).show();
                }

                Uri imgUri = Uri.parse(Config.FIREBASE_URl_REGISTRATION);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
