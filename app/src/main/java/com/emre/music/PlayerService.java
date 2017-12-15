package com.emre.music;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private static final int DURATION = 335;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private Worker mWorker;

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mWorker != null) {
            mWorker.interrupt();
        }
        return super.onUnbind(intent);
    }

    public void play() {
        if (mWorker == null) {
            mWorker = new Worker();
            mWorker.start();
        } else {
            mWorker.doResume();
        }
    }

    public boolean isPlaying() {
        return mWorker != null && mWorker.isPlaying();
    }

    public void pause() {
        if (mWorker != null) {
            mWorker.doPause();
        }
    }

    public int getPosition() {
        if (mWorker != null) {
            return mWorker.getPosition();
        }
        return 0;
    }

    public int getDuration() {
        return DURATION;
    }

    private static class Worker extends Thread {

        boolean paused = false;
        int position = 0;

        @Override
        public void run() {
            try {
                while (position < DURATION) {
                    sleep(1000);
                    if (!paused) {
                        position++;
                    }
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "Player unbounded");
            }
        }

        void doResume() {
            paused = false;
        }

        void doPause() {
            paused = true;
        }

        boolean isPlaying() {
            return !paused;
        }

        int getPosition() {
            return position;
        }
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {

        public PlayerService getService() {
            // Return this instance of PlayerService so clients can call public methods
            return PlayerService.this;
        }
    }
}