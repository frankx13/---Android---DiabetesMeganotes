package com.studio.neopanda.diabetesmeganotes.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.TextView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.activities.DashboardActivity;
import com.studio.neopanda.diabetesmeganotes.activities.MainActivity;

public class Utils {

    @SuppressLint("ClickableViewAccessibility")
    public static void backToDashboard(TextView title, Context context, Activity activity) {
        title.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= title.getRight() - title.getTotalPaddingRight()) {
                    // your action for drawable click event
                    Intent intent = new Intent(context, DashboardActivity.class);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
                    activity.finish();

                    return true;
                }
            }
            return true;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void logoutUser(TextView title, Context context, Activity activity){
        title.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= title.getRight() - title.getTotalPaddingRight()) {
                    // your action for drawable click event
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
                    activity.finish();

                    return true;
                }
            }
            return true;
        });
    }
}
