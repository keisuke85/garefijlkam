package it.keisoft.garefijlkam.util;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.keisoft.garefijlkam.CurrentTournamentActivity;
import it.keisoft.garefijlkam.R;
import it.keisoft.garefijlkam.RouteActivity;
import it.keisoft.garefijlkam.ShowTableActivity;

/**
 * Created by mmarcheselli on 10/02/2016.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        String msg = extras.getString("price");
        if (!extras.isEmpty() && GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            sendNotification(msg);
        }
        NotificationReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String title = jsonObject.getString("title");
            String text = jsonObject.getString("text");
            //comportamento diverso a seconda del tipo di notifica
            String id_gara = jsonObject.getString("id_gara");
            String id_cat = jsonObject.getString("id_cat");
            String id_atl = jsonObject.getString("id_atl");
            Intent resultIntent = null;
            if(id_gara != null && !id_gara.equalsIgnoreCase("")) {
                if (id_cat != null && !id_cat.equalsIgnoreCase("")){
                    resultIntent = new Intent(getApplication(), ShowTableActivity.class);
                    resultIntent.putExtra(ShowTableActivity.WEIGHT, id_cat);
                    resultIntent.putExtra(ShowTableActivity.ID_GARA, id_gara);
                }else if (id_atl != null && !id_atl.equalsIgnoreCase("")) {
                    resultIntent = new Intent(getApplication(), RouteActivity.class);
                    resultIntent.putExtra(RouteActivity.ID_ATLETA, id_atl);
                    resultIntent.putExtra(RouteActivity.ID_GARA, id_gara);
                } else {
                    resultIntent = new Intent(getApplication(), CurrentTournamentActivity.class);
                    resultIntent.putExtra(CurrentTournamentActivity.ID_GARA, id_gara);
                }
            }

            if(resultIntent != null) {
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplication());
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(ShowTableActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//                mBuilder.setContentIntent(resultPendingIntent);
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
//                mNotificationManager.notify(0, mBuilder.build());

                NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    //            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, BaseActivity.class), 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                        .setContentText(text);

                mBuilder.setContentIntent(resultPendingIntent);
                mBuilder.setAutoCancel(true);
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
                     //prova notifica
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplication())
                                .setSmallIcon(R.drawable.oro)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");
                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(getApplication(), ShowTableActivity.class);
                resultIntent.putExtra(ShowTableActivity.WEIGHT, item);
                resultIntent.putExtra(ShowTableActivity.ID_GARA, id_gara);
                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplication());
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(ShowTableActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
                // fine notifica
*/

}
