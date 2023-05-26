package com.example.ztv;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.ztv.Utils.CheckNetworkConnection;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!CheckNetworkConnection.IsConnectedToInternet(context)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            @SuppressLint("InflateParams") View layout = LayoutInflater.from(context)
                    .inflate(R.layout.networkstate, null);
            builder.setView(layout);
            AppCompatButton button = layout.findViewById(R.id.btn_retry);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCancelable(false);


            button.setOnClickListener(c -> {
                dialog.dismiss();
                // For Recursion (Try to find another way --> As it isn't efficient)
                /**
                 * It Seems It Uses A lot of Device Resources ^_^
                 */
                onReceive(context, intent);
            });

        } else {
            Toast.makeText(context, "Welcome Back !!", Toast.LENGTH_SHORT).show();
        }
    }

}
