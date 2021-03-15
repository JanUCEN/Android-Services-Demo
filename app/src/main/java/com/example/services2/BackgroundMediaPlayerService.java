package com.example.services2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMediaPlayerService extends Service {
    MediaPlayer player;

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
        player.setVolume(10,10);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}