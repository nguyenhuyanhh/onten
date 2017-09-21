package com.onten.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.games.Games;
import com.onten.android.MainActivity;
import com.onten.android.R;

/**
 * Created by Peiying.Lim on 25/3/2017.
 */
public class LeaderboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboardpage, container, false);
        if (MainActivity.mGoogleApiClient != null) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(MainActivity.mGoogleApiClient,
                    getString(R.string.greenmember)), 20);
        }
        return view;
    }
}

