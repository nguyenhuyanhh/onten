package com.onten.android.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onten.android.R;

/**
 * Created by Peiying.Lim on 17/3/2017.
 */

public class ViewPagerIndicator {
    LinearLayout main_image_holder;

    public static void createDotScrollBar(Context context, LinearLayout main_holder, int selectedPage, int count) {
        main_holder.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = null;

            dot = new ImageView(context);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int px = Math.round(10 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(px, px);
            int margin = Math.round(3 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            vp.setMargins(margin, 0, margin, 0);
            dot.setLayoutParams(vp);
            if (i == selectedPage) {
                try {
                    dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_background));
                } catch (Exception e) {
                }
            } else {
                if (QuizSecurePreferences.getBooleanPreference(context, "" + i)) {
                    dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_background));
                } else {
                    dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_white));
                }
            }
            main_holder.addView(dot);
        }
        main_holder.invalidate();
    }
}
