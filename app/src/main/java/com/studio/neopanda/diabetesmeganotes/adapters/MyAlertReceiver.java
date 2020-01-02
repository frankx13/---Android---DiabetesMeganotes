package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAlertReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Alarm Time !", Toast.LENGTH_SHORT).show();
        }
}
