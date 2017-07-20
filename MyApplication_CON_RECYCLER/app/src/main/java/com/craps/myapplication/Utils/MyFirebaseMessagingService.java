package com.craps.myapplication.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.craps.myapplication.R;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by dh1 on 10/07/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //remoteMessage.getNotification()); --> Aca puedo obtener el titulo de mi notificacion


        //Aca puedo abrir el bundle y ver de esta manera todas las claves y valores que contiene
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("DATA DE FIREBASE", "FCM Data Key Message: " + key);
            Log.d("DATA DE FIREBASE", "FCM Data Value Message: " + value);

        }
        //Este en metodo que va a crear igualmente una notificacion para mostrar
        //En el notification drawer de android (arriba de todo con las demas notificaciones)
        showNotification(remoteMessage.getData().get("message"));
    }

    private void showNotification(String message) {

        Intent i = new Intent(this,ActivityMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Andy test")
                .setContentText(message)
                .setSmallIcon(R.drawable.abc_ic_commit_search_api_mtrl_alpha)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }
}
