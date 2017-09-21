package com.onten.android.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.games.achievement.Achievements;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onten.android.DataContainer.ExamResultContainer;
import com.onten.android.MainActivity;
import com.onten.android.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Peiying.Lim on 18/3/2017.
 */
public class QuizCompletedFragment extends Fragment {
    private List<quizequestion> datalist = new ArrayList<>();
    ProgressDialog progressDialog;
    TextView txtquestion, txtexit, txtreview, txtusername, txtqtype, txtdescription, qdate;
    private Achievements achievements;
    private DatabaseReference databaseDatas;
    String qtimel, qtype;
    ImageView newimage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizcompletedview, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(false);

        Firebase ref = new Firebase(Config.FIREBASE_URL);
        Firebase.setAndroidContext(getActivity());
        progressDialog.show();
        databaseDatas = FirebaseDatabase.getInstance().getReference("examresult");


        qtimel = getArguments().getString("qtime");
        qtype = getArguments().getString("qtype");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    quizequestion person = postSnapshot.getValue(quizequestion.class);
                    datalist.add(person);
                }
                setResult();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });

        newimage = (ImageView) view.findViewById(R.id.newimage);
        txtquestion = (TextView) view.findViewById(R.id.txtquestion);
        txtexit = (TextView) view.findViewById(R.id.txtexit);
        txtreview = (TextView) view.findViewById(R.id.txtreview);
        txtusername = (TextView) view.findViewById(R.id.txtusername);
        txtqtype = (TextView) view.findViewById(R.id.txtqtype);
        txtdescription = (TextView) view.findViewById(R.id.txtdescription);
        qdate = (TextView) view.findViewById(R.id.qdate);

        txtexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecurePreferences.cleasharedPrref(getActivity());
//                ((MainActivity) getActivity()).clearBackStack();
                Detailviewfragment2 detailviewfragment2 = new Detailviewfragment2();
                Bundle bundle2 = new Bundle();
                bundle2.putString("qtype", qtype);
                detailviewfragment2.setArguments(bundle2);
                ((MainActivity) getActivity()).changeFragment(detailviewfragment2);
            }
        });
        txtreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(new AllQuizResult());

//                startActivityForResult(Games.Achievements.getAchievementsIntent(MainActivity.mGoogleApiClient),
//                        10);
            }
        });

        achievements = new Achievements() {
            @Override
            public Intent getAchievementsIntent(GoogleApiClient googleApiClient) {
                return null;
            }

            @Override
            public PendingResult<LoadAchievementsResult> load(GoogleApiClient googleApiClient, boolean b) {
                return null;
            }

            @Override
            public void reveal(GoogleApiClient googleApiClient, String s) {

            }

            @Override
            public PendingResult<UpdateAchievementResult> revealImmediate(GoogleApiClient googleApiClient, String s) {
                return null;
            }

            @Override
            public void unlock(GoogleApiClient googleApiClient, String s) {

            }

            @Override
            public PendingResult<UpdateAchievementResult> unlockImmediate(GoogleApiClient googleApiClient, String s) {
                return null;
            }

            @Override
            public void increment(GoogleApiClient googleApiClient, String s, int i) {

            }

            @Override
            public PendingResult<UpdateAchievementResult> incrementImmediate(GoogleApiClient googleApiClient, String s, int i) {
                return null;
            }

            @Override
            public void setSteps(GoogleApiClient googleApiClient, String s, int i) {

            }

            @Override
            public PendingResult<UpdateAchievementResult> setStepsImmediate(GoogleApiClient googleApiClient, String s, int i) {
                return null;
            }
        };

        return view;
    }

    private void setResult() {

        int quizecount = SecurePreferences.getIntegerPreference(getActivity(), "quizecount");
        quizecount++;


        int count = 0;
        for (int i = 0; i < datalist.size(); i++) {
            if (SecurePreferences.getStringPreference(getActivity(), "ans" + datalist.get(i).getId()).equals(datalist.get(i).getAns())) {
                count += 1;
            }
        }
        String qquestion = "" + datalist.size();
        String qanswer = "" + count;
        saveData(qquestion, qanswer, qtimel, qtype);
        QuizSecurePreferences.cleasharedPrref(getActivity());

        if (count > 0 && count < 7) {
            newimage.setImageResource(R.drawable.adventurerbadge);
        } else if (count > 6 && count < 13) {
            newimage.setImageResource(R.drawable.explorerbadge);
        } else if (count > 13 && count < 18) {
            newimage.setImageResource(R.drawable.superstarbadge);
        }

        txtdescription.setText("Quiz Time:-" + qtimel);
        qdate.setText("Date:- " + getCurrentDate());
        txtusername.setText(SecurePreferences.getStringPreference(getActivity(), "username"));
        txtqtype.setText(qtype);

//        ,txtusername,txttitle,txtqtype,txtdescription,qdate;


        txtquestion.setText("Congratulation you have scored " + count + "/" + datalist.size());
//        if (quizecount == 1) {
//            if (MainActivity.mGoogleApiClient != null && MainActivity.mGoogleApiClient.isConnected()) {
//                // Call a Play Games services API method, for example:
//                achievements.unlock(MainActivity.mGoogleApiClient, getResources().getString(R.string.Domination_badge));
//            }
//            SecurePreferences.savePreferences(getActivity(), "quizecount", quizecount);
//        } else if (quizecount == 5) {
//            if (MainActivity.mGoogleApiClient != null && MainActivity.mGoogleApiClient.isConnected()) {
//                // Call a Play Games services API method, for example:
//                achievements.unlock(MainActivity.mGoogleApiClient, getResources().getString(R.string.EnFuegobadge));
//            }
//            SecurePreferences.savePreferences(getActivity(), "quizecount", quizecount);
//        } else if (quizecount == 10) {
//            if (MainActivity.mGoogleApiClient != null && MainActivity.mGoogleApiClient.isConnected()) {
//                // Call a Play Games services API method, for example:
//                achievements.unlock(MainActivity.mGoogleApiClient, getResources().getString(R.string.Veteran_badge));
//            }
//            SecurePreferences.savePreferences(getActivity(), "quizecount", quizecount);
//        } else if (count > 8) {
//            if (MainActivity.mGoogleApiClient != null && MainActivity.mGoogleApiClient.isConnected()) {
//                // Call a Play Games services API method, for example:
//                achievements.unlock(MainActivity.mGoogleApiClient, getResources().getString(R.string.Veteran_badge));
//            }
//            SecurePreferences.savePreferences(getActivity(), "quizecount", quizecount);
//        }
//        if (MainActivity.mGoogleApiClient != null && MainActivity.mGoogleApiClient.isConnected()) {
//            achievements.unlock(MainActivity.mGoogleApiClient, getResources().getString(R.string.Domination_badge));
//            Games.Leaderboards.submitScore(MainActivity.mGoogleApiClient, getString(R.string.greenmember), 5);
//        }


    }

    private void saveData(String qquestion, String qanswer, String qtime, String qtype) {
        ExamResultContainer data = new ExamResultContainer();
        data.setQquestion(qquestion);
        data.setQanswer(qanswer);
        data.setQtime(qtime);
        data.setQuizeType(qtype);
        data.setQdate(getCurrentDate());
        data.setQusername(SecurePreferences.getStringPreference(getActivity(), "username"));
        databaseDatas.child(System.currentTimeMillis() + "" + SecurePreferences.getStringPreference(getActivity(), "user_id")).setValue(data);

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        addUserChangeListener();
    }

    private void addUserChangeListener() {

        final String id = databaseDatas.push().getKey();
        databaseDatas.child(id).addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), "Quiz Data stored successfully.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private String getCurrentDate() {
        Date date = new Date();

        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        String newDateStr = curFormater.format(date);
        return newDateStr;
    }

}
