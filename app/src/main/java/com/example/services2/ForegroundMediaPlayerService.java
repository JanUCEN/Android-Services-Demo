package com.example.services2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ForegroundMediaPlayerService extends Service {
    MediaPlayer player;
    static int stamp = 0;

    public static final String CHANNEL_ID = "mp_service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        https://freemusicarchive.org/music/charts/all/
        player = MediaPlayer.create(this,R.raw.broke_for_free_night_owl);
        player.setLooping(true);
        player.setVolume(50,50);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Music Player Service", NotificationManager.IMPORTANCE_NONE);
        channel.setName("Music Player Service");
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        final String CHANNEL_ID = "mp_service";

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,0,notificationIntent,0);

        Notification note = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_playing)
                .setContentTitle("Music player")
                .setContentText("Playing Broke for Free")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_STATUS)
                .setContentIntent(pendingIntent)
                .build();
        player.seekTo(stamp);

        startForeground(100,note);
        player.start();

        return Service.START_STICKY;
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return startForegroundService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stamp = player.getCurrentPosition();
        player.pause();
    }
}