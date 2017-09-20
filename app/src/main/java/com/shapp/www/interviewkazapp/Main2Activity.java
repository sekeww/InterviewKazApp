package com.shapp.www.interviewkazapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends Activity {

    public Button readyButton;
    public Button settingsButton;
    public static SharedPreferences sharedPref;


    private MediaProjection mMediaProjection;
    private MediaProjectionManager mProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

//        if (mMediaProjection == null && !isServiceRunning(RecorderService.class)) {
//            //Request Screen recording permission
//            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), Const.SCREEN_RECORD_REQUEST_CODE);
//        } else if (isServiceRunning(RecorderService.class)) {
//            //stop recording if the service is already active and recording
////            Toast.makeText(QuestionsActivity.this, "Screen already recording", Toast.LENGTH_SHORT).show();
//        }

        sharedPref = this.getSharedPreferences("type", 0);

        readyButton = (Button) findViewById(R.id.readyButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);

        final Intent intentQuestion = new Intent(this, QuestionsActivity.class);
        final Intent intentInstruction = new Intent(this, InstructionActivity.class);
        final Intent intentSettings = new Intent(this, SettingsActivity.class);
        final Intent intentVideo = new Intent(this, VideoActivity.class);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentSettings);
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentQuestion);
            }
        });
    }
    //Method to check if the service is running
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
