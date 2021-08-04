package com.example.fixawy.NotificationToClient;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.R;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebasePushNotification extends FirebaseMessagingService {
    String title,message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "example.fixawy.Notification.test";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Code shere");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANEL_ID);
//        Intent resultIntent = new Intent(this, HomePageClientActivity.class);
//        PendingIntent resultPendingInten = PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.bb)
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("Info")
                .setAutoCancel(true);
        //               .setContentIntent(resultPendingInten);

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());

    }


//   public void displayClientNotification(){
//
//       NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//       String NOTIFICATION_CHANEL_ID_CLIENT = "example.fixawy.Notification.test";
//
//       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//       {
//           NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID_CLIENT,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
//           notificationChannel.setDescription("Code shere");
//           notificationChannel.enableLights(true);
//           notificationChannel.setLightColor(Color.BLUE);
//           notificationManager.createNotificationChannel(notificationChannel);
//       }
//
//       NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANEL_ID_CLIENT);
//        Intent resultIntent = new Intent(this, HomePageClientActivity.class);
//        PendingIntent resultPendingInten = PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//       notificationBuilder.setAutoCancel(true)
//               .setDefaults(Notification.DEFAULT_ALL)
//               .setWhen(System.currentTimeMillis())
//               .setSmallIcon(R.drawable.bbpurple)
//               .setContentTitle(title)
//               .setContentText(message)
//               .setContentInfo("Info")
//               .setAutoCancel(true)
//               .setContentIntent(resultPendingInten);
//
//       notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
//
//    }






//    public void displayWorkerNotification(){
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATION_CHANEL_ID_WORKER = "example.fixawy.Notification.test2";
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID_WORKER,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
//            notificationChannel.setDescription("Code shere");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.BLUE);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANEL_ID_WORKER);
//        Intent resultIntent = new Intent(this, RequestedHomePageActivity.class);
//        PendingIntent resultPendingInten = PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.bbpurple)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setContentInfo("Info")
//                .setAutoCancel(true)
//                .setContentIntent(resultPendingInten);
//
//        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
//
//    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d("TokenFirebase",s);
    }
}
