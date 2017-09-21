package com.onten.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.onten.android.MainActivity;
import com.onten.android.R;

/**
 * Created by Peiying.Lim on 5/4/2017.
 */
public class MainFragment extends Fragment {
    Button btn_book;
    TextView txtwelcome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainlayout, container, false);
        btn_book = (Button) view.findViewById(R.id.btn_book);
        txtwelcome = (TextView) view.findViewById(R.id.txtwelcome);

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(new Detailviewfragment2());
            }
        });
        txtwelcome.setText("Welcome To " + SecurePreferences.getStringPreference(getActivity(), "username"));
        return view;
    }
}
