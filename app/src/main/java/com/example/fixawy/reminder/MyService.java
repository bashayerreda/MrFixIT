package com.example.fixawy.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fixawy.Pojos.JobAccepted;
import com.example.fixawy.R;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageRepository;
import com.example.fixawy.Worker.JobAccepted.JobAcceptedActivity;
import com.example.fixawy.Worker.MapActivity.MapActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class MyService extends Service {
    String data;
    DatabaseReference databaseReference;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    public MyService() {
    }






    @Override
    public void onCreate() {
        super.onCreate();



        AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.alarm);
        try {

            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            mediaPlayer.prepare();//Let the Mediaplayer enter the ready state
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String date = JobAcceptedActivity.historyDate;
        String name = JobAcceptedActivity.historyUserName;
        String phone = JobAcceptedActivity.historyUserPhone;
        String time = JobAcceptedActivity.historyTime;
        String address = JobAcceptedActivity.historyUserAddress;

        String worker_phone = JobAcceptedActivity.worker_phone;
        String worker_jobTitle = JobAcceptedActivity.worker_job_title;


         RemoteViews customView =new RemoteViews(getPackageName(), R.layout.notification_reminder);
       // Intent notificationIntent =new Intent(getApplicationContext(), RequestedHomePageActivity.class);
        //Intent hungupIntent =new Intent(getApplicationContext(), MyReceiver.class);
        Intent answerIntent = new Intent(this, MapActivity.class);

        //answerIntent.putExtra(UpcomingTripsFragment.UPCOMING_DETAILS_EXTRA,trip);
        answerIntent.putExtra("dataHistoryName",name);
        answerIntent.putExtra("dataHistoryDate",date);
        answerIntent.putExtra("dataHistoryTime",time);
        answerIntent.putExtra("dataHistoryPhone",phone);
        answerIntent.putExtra("dataHistoryLocation",address);

        answerIntent.putExtra("phone_worker",worker_phone);
        answerIntent.putExtra("jobTitle_worker",worker_jobTitle);



        customView.setTextViewText(R.id.tripEndPoint, phone);
        customView.setTextViewText(R.id.tripName, address);
        //customView.setImageViewBitmap(R.id.photo, NotificationImageManager().getImageBitmap(intent.getStringExtra("user_thumbnail_image")))

     //   PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      //  PendingIntent hungupPendingIntent = PendingIntent.getBroadcast(this, 0, hungupIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent answerPendingIntent = PendingIntent.getActivity(this, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        customView.setOnClickPendingIntent(R.id.btnStart, answerPendingIntent);

        //customView.setOnClickPendingIntent(R.id.btnDismiss, hungupPendingIntent);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel =new NotificationChannel("IncomingCall",
                    "IncomingCall", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "IncomingCall");
            notification.setContentTitle("reminder");
            notification.setTicker("Call_STATUS");
            notification.setContentText("IncomingCall");
            notification.setSmallIcon(R.drawable.ic_alarm_clock);
            notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
            notification.setCategory(NotificationCompat.CATEGORY_CALL);
            notification.setVibrate(null);
            notification.setOngoing(true);
            notification.setSound(soundUri);
           // notification.setFullScreenIntent(pendingIntent, true);
            notification.setPriority(NotificationCompat.PRIORITY_HIGH);
            notification.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
            notification.setCustomContentView(customView);
            notification.setCustomBigContentView(customView);

            startForeground(1124, notification.build());
        } else {
            NotificationCompat.Builder notification =new NotificationCompat.Builder(this);
            notification.setContentTitle("app_name");
            notification.setTicker("Call_STATUS");
            notification.setContentText("IncomingCall");
            notification.setSmallIcon(R.drawable.ic_alarm_clock);
            notification.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_alarm_clock));
            notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
            notification.setVibrate(null);
            notification.setSound(soundUri);
           // notification.setContentIntent(pendingIntent);
            notification.setOngoing(true);
            notification.setCategory(NotificationCompat.CATEGORY_CALL);
            notification.setPriority(NotificationCompat.PRIORITY_HIGH);
//            NotificationCompat.Action hangupAction =new NotificationCompat.Action.Builder(android.R.drawable.sym_action_chat, "HANG UP", hungupPendingIntent)
//                    .build();
//            notification.addAction(hangupAction);
            startForeground(1124, notification.build());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null)mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
}