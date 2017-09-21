package com.onten.android.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onten.android.R;

/**
 * Created by Peiying.Lim on 25/3/2017.
 */

public class LoginFragment extends Fragment {
    private EditText edtemail, edtpassword;
    private TextView txtlogin;
    //    private Quize.MyViewPager recyclerViewadapter;
    ProgressDialog progressDialog;
    private DatabaseReference databaseDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginfragment, container, false);
        Firebase.setAndroidContext(getActivity());
        Firebase ref = new Firebase(Config.FIREBASE_URL);
        databaseDatas = FirebaseDatabase.getInstance().getReference("registration");

        edtemail = (EditText) view.findViewById(R.id.edtemail);
        edtpassword = (EditText) view.findViewById(R.id.edtpassword);
        txtlogin = (TextView) view.findViewById(R.id.txtlogin);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(false);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtemail.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    String result = edtemail.getText().toString().replaceAll("[-+.^:,]", "");
                    addUserChangeListener(result, edtpassword.getText().toString());
                }
            }
        });

        return view;
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


                        Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Invalid username and password", Toast.LENGTH_SHORT).show();
                    }
                }

                Uri imgUri = Uri.parse(Config.FIREBASE_URl_REGISTRATION);
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
