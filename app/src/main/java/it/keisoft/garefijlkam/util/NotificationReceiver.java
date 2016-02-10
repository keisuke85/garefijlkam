package it.keisoft.garefijlkam.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import it.keisoft.garefijlkam.R;

/**
 * Created by mmarcheselli on 09/02/2016.
 */
public class NotificationReceiver extends WakefulBroadcastReceiver {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);


/*        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        Bundle extras = intent.getExtras();

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // emette una notifica sul dispositivo
                sendNotification(context, "E' arrivata la tua prima notifica attraverso GCM!");
            }
        }*/
    }

/*    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d("NOTIFICATION", "From: " + from);
        Log.d("NOTIFICATION", "Message: " + message);
        // Handle received message here.
    }

    private void sendNotification(Context ctx,String msg) {
        mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // scelta suoneria per notifica
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
                                                .setSmallIcon(R.drawable.oro)
                                                .setContentTitle("Push Notifications: primo esperimento")
                                                .setContentText(msg)
                                                .setSound(sound);

        // effettua la notifica
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }*/
}
