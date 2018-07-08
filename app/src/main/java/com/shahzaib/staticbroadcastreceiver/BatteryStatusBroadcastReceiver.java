package com.shahzaib.staticbroadcastreceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class BatteryStatusBroadcastReceiver extends BroadcastReceiver {
    public static final String BATTERY_POWER_STATUS = "BatteryPowerStatus";
    boolean isPowerConnected;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.i("123456", "Intent received is null");
            return;
        }


        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED) || intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                isPowerConnected = true;
            } else {
                isPowerConnected = false;
            }

            showNotification(context, isPowerConnected);
        }

    }

    private void showNotification(Context context, boolean isPowerConnected) {

        // Build the Content of notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0");
        if(isPowerConnected) builder.setContentTitle("Power Connected");
        if(!isPowerConnected) builder.setContentTitle("Power Disconnected");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true); // when user click , it disappear
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // show the Notification & also create channel for devices running android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(
                    "0",
                    "batteryNotificationChannel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(nChannel);
        }

        if (notificationManager != null)
            notificationManager.notify(1, builder.build());

    }


}
