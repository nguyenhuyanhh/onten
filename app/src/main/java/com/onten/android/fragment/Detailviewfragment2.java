package com.onten.android.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onten.android.MainActivity;
import com.onten.android.MainLoginActivity;
import com.onten.android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peiying.Lim on 18/3/2017.
 */

public class Detailviewfragment2 extends Fragment {
    private TextView txtquestionno;
    private TextView txtquestion;

    private RadioGroup radioGroup;
    private RadioButton radio0;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;

    private TextView txtbtnprev;
    private TextView txtbtnnext;
    private ViewPager viewPager;
    private MyViewPager myViewPagerAdapter;
    private ProgressDialog progressDialog;
    private TextView txthours, txtminutes, txtseconds;
    private Chronometer timerABWithTimer;
    private List<quizequestion> datalist = new ArrayList<>();
    private LinearLayout rl_dotlyt;
    private Handler handler;
    private Runnable runnable;
    long diff = 15;
    //    private Quize.MyViewPager recyclerViewadapter;
    String qtype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailviewfragment2, container, false);
        Firebase.setAndroidContext(getActivity());
        Firebase ref = new Firebase(Config.FIREBASE_URL);


        timerABWithTimer = (Chronometer) view.findViewById(R.id.timerABWithTimer);
        rl_dotlyt = (LinearLayout) view.findViewById(R.id.rl_dotlyt);
        txtbtnprev = (TextView) view.findViewById(R.id.txtbtnprev);
        txtbtnnext = (TextView) view.findViewById(R.id.txtbtnnext);
        txthours = (TextView) view.findViewById(R.id.txthours);
        txtminutes = (TextView) view.findViewById(R.id.txtminutes);
        txtseconds = (TextView) view.findViewById(R.id.txtseconds);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(false);


        if (!SecurePreferences.getBooleanPreference(getActivity(), "isLogin")) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning")
                    .setMessage("You need to login first")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), MainLoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            progressDialog.show();
        }

        qtype = getArguments().getString("qtype");
        txtbtnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = viewPager.getCurrentItem();
                if (p != 0) {
                    viewPager.setCurrentItem(p - 1);
                }

            }
        });
        txtbtnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = viewPager.getCurrentItem();
                if (p != myViewPagerAdapter.getCount() - 1) {
                    viewPager.setCurrentItem(p + 1);
                } else {
                    if (txtbtnnext.getText().toString().equals("Submit")) {
                        String remain = "";
                        for (int i = 0; i < datalist.size(); i++) {
                            if (QuizSecurePreferences.getStringPreference(getActivity(), "ans" + datalist.get(i).getId()).equals("")) {
                                if (remain.equals("")) {
                                    remain = datalist.get(i).getId();
                                } else {
                                    remain = remain + "," + datalist.get(i).getId();
                                }
                            }
                        }
                        if (!remain.equals("")) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("Unable to submit, Please complete Question of " + remain)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        } else {
                            QuizCompletedFragment quizeCompletedFragment = new QuizCompletedFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("question", "" + myViewPagerAdapter.getCount());
                            bundle.putString("qtime", "" + (15 - Integer.parseInt(txtminutes.getText().toString())) + ":" + (60 - Integer.parseInt(txtseconds.getText().toString())));
                            bundle.putString("qtype", qtype);

                            quizeCompletedFragment.setArguments(bundle);

                            ((MainActivity) getActivity()).changeFragment(quizeCompletedFragment);
                        }
                    }
                }
            }
        });
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    quizequestion person = postSnapshot.getValue(quizequestion.class);
                    datalist.add(person);
                }
                setData();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });
        viewPager = (ViewPager) view.findViewById(R.id.pager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == myViewPagerAdapter.getCount() - 1) {
                    txtbtnnext.setText("Submit");
                } else {
                    txtbtnnext.setText("Next");
                }
                ViewPagerIndicator.createDotScrollBar(getActivity(), rl_dotlyt, viewPager.getCurrentItem(),
                        viewPager.getAdapter().getCount());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        return view;

    }

    private void setData() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
//        countDownStart();
        countDownStart();
        Collections.shuffle(datalist);
        myViewPagerAdapter = new MyViewPager(getActivity(), datalist);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(datalist.size());
        ViewPagerIndicator.createDotScrollBar(getActivity(), rl_dotlyt, 0, datalist.size());
    }

    public class MyViewPager extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Context context;
        List<quizequestion> datalist;

        public MyViewPager(Context context, List<quizequestion> datalist) {
            this.datalist = datalist;
            this.context = context;
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = layoutInflater.inflate(R.layout.quiz, container, false);
            Firebase.setAndroidContext(context);
            txtquestionno = (TextView) view.findViewById(R.id.txtquestionno);
            txtquestion = (TextView) view.findViewById(R.id.txtquestion);

            radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            radio0 = (RadioButton) view.findViewById(R.id.radio0);
            radio1 = (RadioButton) view.findViewById(R.id.radio1);
            radio2 = (RadioButton) view.findViewById(R.id.radio2);
            radio3 = (RadioButton) view.findViewById(R.id.radio3);
//            radio4 = (RadioButton) view.findViewById(R.id.radio4);
//
            final quizequestion person = datalist.get(position);

            String str = "Question " + person.getId();
            txtquestionno.setText(str);

            String string = person.getQuestion();
            txtquestion.setText(string);

            String vlue1 = person.getA();
            radio0.setText(vlue1);

            String vlue2 = person.getB();
            radio1.setText(vlue2);

            String vlue3 = person.getC();
            radio2.setText(vlue3);

            String vlue4 = person.getD();
            radio3.setText(vlue4);

            quizequestion selectedperson = datalist.get(viewPager.getCurrentItem());
            String selectedans = QuizSecurePreferences.getStringPreference(getActivity(), "ans" + selectedperson.getId());

            if (selectedans.equals(selectedperson.getA())) {
                radio0.setSelected(true);
            } else if (selectedans.equals(selectedperson.getB())) {
                radio1.setSelected(true);
            } else if (selectedans.equals(selectedperson.getC())) {
                radio2.setSelected(true);
            } else if (selectedans.equals(selectedperson.getD())) {
                radio3.setSelected(true);
            }
//            String vlue5 = person.getAns();
//            radio4.setText(vlue5);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int radioButtonID = group.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) group.findViewById(radioButtonID);
                    QuizSecurePreferences.savePreferences(getActivity(), "" + position, true);
                    QuizSecurePreferences.savePreferences(getActivity(), "ans" + person.getId(), radioButton.getText().toString());
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

    private int getItem(int i) {

        return viewPager.getCurrentItem() + i;
    }

    public void countDownStart() {
//        900000
        new CountDownTimer(900000, 1000) {

            public void onTick(long millisUntilFinished) {
//                long minutes = millisUntilFinished / (60 * 1000);
//                txtminutes.setText("" + TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
//                txtseconds.setText("" + TimeUnit.MICROSECONDS.toSeconds(millisUntilFinished));
                long minutes = millisUntilFinished / (60 * 1000);
                millisUntilFinished -= minutes * (60 * 1000);
                long seconds = millisUntilFinished / 1000;

                txtminutes.setText("" + String.format("%02d", minutes));
                txtseconds.setText("" + String.format("%02d", seconds));
//                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
            }

            public void onFinish() {
                ViewDialog();

//                mTextField.setText("done!");
            }

        }.start();

    }

    private void ViewDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Warning")
                .setMessage("Your quiz time is completed please try again!!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Detailviewfragment2 detailviewfragment4 = new Detailviewfragment2();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("qtype", qtype);
                        detailviewfragment4.setArguments(bundle2);
                        // continue with delete
                        QuizSecurePreferences.cleasharedPrref(getActivity());
//                ((MainActivity) getActivity()).clearBackStack();
                        ((MainActivity) getActivity()).changeFragment(detailviewfragment4);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
