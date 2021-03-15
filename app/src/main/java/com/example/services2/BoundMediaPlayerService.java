package com.example.services2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BoundMediaPlayerService extends Service {
    private MediaPlayer player;

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        BoundMediaPlayerService getService() {
            return BoundMediaPlayerService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        player = MediaPlayer.create(getApplicationContext(),R.raw.broke_for_free_night_owl);
        player.setLooping(true);
        player.setVolume(50,50);
        return binder;
    }

    public void play() {
        player.start();
    }
    public void pause() {
        player.pause();
    }
    public void ff() {
        int seekPos = Math.min(player.getCurrentPosition()+1000,player.getDuration());
        player.seekTo(seekPos);
    }
    public void rev() {
        int seekPos = Math.max(player.getCurrentPosition()-1000,0);
        player.seekTo(seekPos);

    }


}