package com.example.hannah.nyanclock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class CatReceiver extends BroadcastReceiver {

    final static int CA_PENDINGINTENT = 0;
    final static int NOTIF_ID = 0;

    public CatReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                CA_PENDINGINTENT,
                new Intent(context, CatActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(context)
                .setTicker("Your Cat needs attention!")
                .setContentTitle("Hungry/Sad Cat")
                .setContentText("Play/Feed your cat to make it feel better.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        ((NotificationManager)context.getSystemService(Service.NOTIFICATION_SERVICE))
                .notify(NOTIF_ID, builder.build());

    }
}
