package com.example.hieudo.phuocnguyenintern.others.services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class LocationFinder  extends BroadcastReceiver {
    private static final String TAG = "LocationFinder";
    private Context context;
    private Snackbar snackbar;

    public LocationFinder(Context context, Snackbar snackbar) {
        this.context = context;
        this.snackbar = snackbar;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(intent.getAction().matches("android.location.PROVIDERS_CHANGED")){
            boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(gpsEnabled){
                if(snackbar != null){
                    snackbar.dismiss();
                }

                Log.d(TAG,"gps enabled");
            } else {
                snackbar.show();
                Log.d(TAG,"gps disabled");
            }
        }
    }
}
