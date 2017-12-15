package com.emre.onlineradio;

import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.music.MusicCoverView;
import com.emre.music.MusicContent;
import com.emre.view.TransitionAdapter;

import co.mobiwise.library.RadioListener;
import co.mobiwise.library.RadioManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends PlayerActivity implements RadioListener {


    FirebaseDatabase db;
    private MusicCoverView mCoverView;
    private ImageView forward,rewind;
    //http://dinle.romantikses.com:5959/listen.pls
    private String[] RADIO_URL = {"http://www.dostmedya.com/ply/1001fm.pls", "http://46.20.4.61/listen.pls"};
    String [] radyolistesi={"Radyo Alaturka","Kral FM","PowerTürk","Best FM","Baba Radyo","Türkü Radyo","Joy Türk","Radyo Seymen","Süper FM","Radyo Viva"};


    RadioManager mRadioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        ((TextView)findViewById(R.id.sanatci)).setText("");
        ((TextView)findViewById(R.id.sarkiadi)).setText(radyolistesi[MusicContent.radioposition]);

        vericek();

        mRadioManager = RadioManager.with(getApplicationContext());
        mRadioManager.registerListener(this);
        mRadioManager.setLogging(true);


        forward = (ImageView) findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicContent.radioposition==RADIO_URL.length-1)
                {
                    MusicContent.radioposition=0;
                }
                else {
                    MusicContent.radioposition++;
                }
                mRadioManager.stopRadio();
                pause();

                play();
                mRadioManager.startRadio(RADIO_URL[MusicContent.radioposition]);

                ((TextView)findViewById(R.id.sanatci)).setText("");
                ((TextView)findViewById(R.id.sarkiadi)).setText(radyolistesi[MusicContent.radioposition]);

            }
        });


        rewind = (ImageView) findViewById(R.id.rewind);
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicContent.radioposition==0)
                {
                    MusicContent.radioposition=RADIO_URL.length-1;
                }
                else {
                    MusicContent.radioposition--;
                }
                mRadioManager.stopRadio();
                pause();

                play();
                mRadioManager.startRadio(RADIO_URL[MusicContent.radioposition]);

                ((TextView)findViewById(R.id.sanatci)).setText("");
                ((TextView)findViewById(R.id.sarkiadi)).setText(radyolistesi[MusicContent.radioposition]);
            }
        });

        mCoverView = (MusicCoverView) findViewById(R.id.cover);
        mCoverView.setCallbacks(new MusicCoverView.Callbacks() {
            @Override
            public void onMorphEnd(MusicCoverView coverView) {
                // Nothing to do
            }

            @Override
            public void onRotateEnd(MusicCoverView coverView) {
                supportFinishAfterTransition();
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {


                if (!mRadioManager.isPlaying()){

                    play();
                    mCoverView.start();
                    mRadioManager.startRadio(RADIO_URL[MusicContent.radioposition]);
                }
                else
                    mRadioManager.stopRadio();

            }
        });
    }


    @Override
    public void onBackPressed() {
        onFabClick(null);
    }

    public void onFabClick(View view) {


        if (!mRadioManager.isPlaying()){
            mRadioManager.startRadio(RADIO_URL[MusicContent.radioposition]);
        }
        else
        {
            pause();
            mCoverView.stop();
            mRadioManager.stopRadio();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRadioManager.connect();
    }

    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                //Toast.makeText(getApplicationContext(),"RADIO STATE : LOADING...",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRadioConnected() {

    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                //mTextViewControl.setText("RADIO STATE : PLAYING...");
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                //mTextViewControl.setText("RADIO STATE : STOPPED.");
            }
        });
    }

    @Override
    public void onMetaDataReceived(String s, String s1) {
        //TODO Check metadata values. Singer name, song name or whatever you have.


    }

    public void onError() {

    }



    public void vericek()
    {
        db=FirebaseDatabase.getInstance();

                DatabaseReference okuma = db.getReference("radio");
                okuma.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        String furkan="";
                        Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                        for (DataSnapshot key:keys)
                        {
                            furkan+=key.getValue().toString()+'\n';
                        }
                        RADIO_URL = furkan.split("\n");


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }
}
