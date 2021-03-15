package com.example.services2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.services2.BoundMediaPlayerService.LocalBinder;

public class MainActivity extends AppCompatActivity {
    BoundMediaPlayerService mService;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        backgroundService();
//        foregroundService();
    }




    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(),BoundMediaPlayerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        boundServiceMethods();
        Toast.makeText(getApplicationContext(),"Started bound service",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void boundServiceMethods() {
        setContentView(R.layout.activity_bound);
        ImageButton playBtn = findViewById(R.id.btnPlay);
        ImageButton pauseBtn = findViewById(R.id.btnP);
        ImageButton ffBtn = findViewById(R.id.btnFF);
        ImageButton revBtn = findViewById(R.id.btnRev);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBound) {
                    mService.play();
                }
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBound) {
                    mService.pause();
                }
            }
        });

        ffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBound) {
                    mService.ff();
                }
            }
        });

        revBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBound) {
                    mService.rev();
                }
            }
        });

    }


    public void backgroundService() {
        Intent service = new Intent(this, BackgroundMediaPlayerService.class);
        startService(service);
        Toast.makeText(getApplicationContext(),"Started background service",Toast.LENGTH_LONG).show();
        Button btn = findViewById(R.id.stopStartBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean stop = btn.getText().toString().toLowerCase().contains("stop");
                if(stop) {
                    stopService(service);
                    btn.setText("CLICK TO START");
                } else {
                    startService(service);
                    btn.setText("CLICK TO STOP");
                }
            }
        });
    }


    public void foregroundService() {
        Intent service = new Intent(getApplicationContext(), ForegroundMediaPlayerService.class);
        startForegroundService(service);
        Toast.makeText(getApplicationContext(),"Started foreground service",Toast.LENGTH_LONG).show();



        Button btn = findViewById(R.id.stopStartBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean stop = btn.getText().toString().toLowerCase().contains("stop");
                if(stop) {
                    stopService(service);
                    btn.setText("CLICK TO START");
                } else {
                    startForegroundService(service);
                    btn.setText("CLICK TO STOP");
                }
            }
        });
    }


}