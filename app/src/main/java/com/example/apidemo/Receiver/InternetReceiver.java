package com.example.apidemo.Receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            if(isOnline(context)){
                Toast.makeText(context, "Internet available....", Toast.LENGTH_SHORT).show();
            }else{
                alertBox(context);
            }
        }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    private boolean isOnline(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null) {
                for (NetworkInfo info : networkInfo) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private  void alertBox(Context context){
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setMessage("No internet connection");
        alert.setPositiveButton("Retry", (dialog, which) -> {
            if(!isOnline(context)){
                alertBox(context);
            }else {
                Toast.makeText(context, "Internet connection available...", Toast.LENGTH_SHORT).show();}
        });
        alert.show();
    }
}