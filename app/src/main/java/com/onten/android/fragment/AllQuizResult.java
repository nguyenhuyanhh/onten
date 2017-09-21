package com.onten.android.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onten.android.DataContainer.ExamResultContainer;
import com.onten.android.R;

import java.util.ArrayList;

/**
 * Created by Peiying.Lim on 6/4/2017.
 */

public class AllQuizResult extends Fragment {
    RecyclerView rv_scorecard;
    ArrayList<ExamResultContainer> examResulContainerArrayList;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allresult, container, false);
        rv_scorecard = (RecyclerView) view.findViewById(R.id.rv_scorecard);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_scorecard.setLayoutManager(layoutManager);

        Firebase.setAndroidContext(getActivity());
        Firebase ref = new Firebase(Config.FIREBASE_URl_RESULT);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("processing...");
        progressDialog.setCancelable(true);
        examResulContainerArrayList = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ExamResultContainer examResulContainer = postSnapshot.getValue(ExamResultContainer.class);
                    examResulContainerArrayList.add(examResulContainer);
                }
                setAdapter();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });


        return view;
    }

    private void setAdapter() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        ResultAdapter resultAdapter = new ResultAdapter(getActivity(), examResulContainerArrayList);
        rv_scorecard.setAdapter(resultAdapter);
    }

    public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<ExamResultContainer> chatsList;
        LayoutInflater inflater;

        public ResultAdapter(Activity context, ArrayList<ExamResultContainer> chatsList) {
            this.chatsList = chatsList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.livenewitem, parent, false);
            return new NewsViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder_main, final int position) {
            ExamResultContainer chatcon = chatsList.get(position);
            if (holder_main instanceof NewsViewHolder) {
                final NewsViewHolder holder = (NewsViewHolder) holder_main;
                holder.txttitle.setText("Result:- " + chatcon.getQanswer() + "/" + chatcon.getQquestion());
                holder.txtdescription.setText("Quiz Time:-" + chatcon.getQtime());
                holder.qdate.setText("Date:- " + chatcon.getQdate());
                holder.txtusername.setText(chatcon.getQusername());
                holder.txtqtype.setText(chatcon.getQuizeType());
                holder.newimage.setImageBitmap(null);

                if (Integer.parseInt(chatcon.getQanswer()) > 0 && Integer.parseInt(chatcon.getQanswer()) < 7) {
                    holder.newimage.setImageResource(R.drawable.adventurerbadge);
                } else if (Integer.parseInt(chatcon.getQanswer()) > 6 && Integer.parseInt(chatcon.getQanswer()) < 13) {
                    holder.newimage.setImageResource(R.drawable.explorerbadge);
                } else if (Integer.parseInt(chatcon.getQanswer()) > 13 && Integer.parseInt(chatcon.getQanswer()) < 18) {
                    holder.newimage.setImageResource(R.drawable.superstarbadge);
                }
            }
        }

        @Override
        public int getItemCount() {
            return chatsList.size();
        }

        public class NewsViewHolder extends RecyclerView.ViewHolder {
            TextView txtdescription, txttitle, qdate, txtusername, txtqtype;
            ImageView newimage;

            public NewsViewHolder(View v) {
                super(v);
                txttitle = (TextView) v.findViewById(R.id.txttitle);
                txtdescription = (TextView) v.findViewById(R.id.txtdescription);
                qdate = (TextView) v.findViewById(R.id.qdate);
                txtusername = (TextView) v.findViewById(R.id.txtusername);
                txtqtype = (TextView) v.findViewById(R.id.txtqtype);
                newimage = (ImageView) v.findViewById(R.id.newimage);
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
            }

        }

    }

}
