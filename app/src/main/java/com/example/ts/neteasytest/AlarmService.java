package com.example.ts.neteasytest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by ts on 20-1-14.
 */

public class AlarmService extends Service{
    public static int MDURATION = 10;
    //定义时长，即定时时间
    public static int MTIMECOUNT = 1;
    //定时器
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("启动定时佛务").setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();


        notificationManager.notify(1,notification);
        startForeground(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long setTime  = 1000 + SystemClock.elapsedRealtime();
        Intent i  =new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,0);
        //设置定时任务
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,setTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
