package com.onten.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.onten.android.fragment.SecurePreferences;

/**
 * Created by Peiying.Lim on 5/4/2017.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        if (SecurePreferences.getBooleanPreference(getApplicationContext(), "isLogin")) {
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
            finish();
        } else {
            Intent in = new Intent(getApplicationContext(), MainLoginActivity.class);
            startActivity(in);
            finish();
        }

    }
}
