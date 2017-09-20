package com.shapp.www.interviewkazapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shapp.www.interviewkazapp.folderpicker.ThreadDialog;

import java.io.File;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;
import ly.count.android.sdk.Countly;

/**
 * Created by borikhankozha on 9/16/17.
 */

public class QuestionsActivity extends Activity {

    private MediaProjection mMediaProjection;
    private MediaProjectionManager mProjectionManager;
    private PermissionResultListener mPermissionResultListener;
    private Button camera;
    private Intent intentCamera;
    private Button f;
    private Intent recorderService;
    boolean yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        yes = false;

        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        intentCamera = new Intent(this, CameraActivity.class);

        LinearTimerView linearTimerView = (LinearTimerView)
                findViewById(R.id.linearTimer);
        linearTimerView.setInitialColor(R.color.colorInitial);

        LinearTimer linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(7 * 1000)
                .build();
        linearTimer.startTimer();

        camera = (Button) findViewById(R.id.camera);
        f = (Button) findViewById(R.id.finish);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaProjection == null && !isServiceRunning(RecorderService.class)) {
                    //Request Screen recording permission
                    Log.d("MyTest","camera onclick");
                    startActivityForResult(mProjectionManager.createScreenCaptureIntent(), Const.SCREEN_RECORD_REQUEST_CODE);
                } else if (isServiceRunning(RecorderService.class)) {
                    //stop recording if the service is already active and recording
                    Toast.makeText(QuestionsActivity.this, "Screen already recording", Toast.LENGTH_SHORT).show();
                }
                // finish();
            }
        });
        StartCameraThread sct = new StartCameraThread(camera);
        sct.start();
    }

    @Override
    public void onBackPressed() {
    }

    //Overriding onActivityResult to capture screen mirroring permission request result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Result for system windows permission required to show floating controls
        if (requestCode == Const.SYSTEM_WINDOWS_CODE) {
            Log.d("MyTest","result/if");
            setSystemWindowsPermissionResult();
            return;
        }

        //The user has denied permission for screen mirroring. Let's notify the user
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this,
                    getString(R.string.screen_recording_permission_denied), Toast.LENGTH_SHORT).show();

//            //Return to home screen if the app was started from app shortcut
//            if (getIntent().getAction().equals(getString(R.string.app_shortcut_action)))
//                this.finish();
            finish();
            return;
        }
        /*If code reaches this point, congratulations! The user has granted screen mirroring permission
        * Let us set the recorderservice intent with relevant data and start service*/

        recorderService = new Intent(this, RecorderService.class);
        recorderService.setAction(Const.SCREEN_RECORDING_START);
        recorderService.putExtra(Const.RECORDER_INTENT_DATA, data);
        recorderService.putExtra(Const.RECORDER_INTENT_RESULT, resultCode);
        //     startService(recorderService);
        ThreadDialog td = new ThreadDialog(recorderService, this, f);
        td.start();
        Log.d("MyTest","result before");
        Log.d("MyTest","result after");
        startActivity(intentCamera);
        // this.finish();
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

    //Pass the system windows permission result to settings fragment
    @TargetApi(23)
    private void setSystemWindowsPermissionResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                mPermissionResultListener.onPermissionResult(Const.SYSTEM_WINDOWS_CODE,
                        new String[]{"System Windows Permission"},
                        new int[]{PackageManager.PERMISSION_GRANTED});
            } else {
                mPermissionResultListener.onPermissionResult(Const.SYSTEM_WINDOWS_CODE,
                        new String[]{"System Windows Permission"},
                        new int[]{PackageManager.PERMISSION_DENIED});
            }
        } else {
            mPermissionResultListener.onPermissionResult(Const.SYSTEM_WINDOWS_CODE,
                    new String[]{"System Windows Permission"},
                    new int[]{PackageManager.PERMISSION_GRANTED});
        }
    }

    // Marshmallow style permission request for audio recording
    public void requestPermissionAudio() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    Const.AUDIO_REQUEST_CODE);
        }
    }

    // Overriding onRequestPermissionsResult method to receive results of marshmallow style permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Const.EXTDIR_REQUEST_CODE:
                if ((grantResults.length > 0) &&
                        (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    Log.d(Const.TAG, "write storage Permission Denied");
                    /* Disable floating action Button in case write storage permission is denied.
                     * There is no use in recording screen when the video is unable to be saved */
                    camera.setEnabled(false);
                } else {
                    /* Since we have write storage permission now, lets create the app directory
                    * in external storage*/
                    Log.d(Const.TAG, "write storage Permission granted");
                    createDir();
                }
        }

        // Let's also pass the result data to SettingsPreferenceFragment using the callback interface
        if (mPermissionResultListener != null) {
            mPermissionResultListener.onPermissionResult(requestCode, permissions, grantResults);
        }
    }

    //Set the callback interface for permission result from SettingsPreferenceFragment
    public void setPermissionResultListener(PermissionResultListener mPermissionResultListener) {
        this.mPermissionResultListener = mPermissionResultListener;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (!BuildConfig.DEBUG) {
            if (Countly.sharedInstance().hasBeenCalledOnStart()) {
                Countly.sharedInstance().onStop();
                Log.d(Const.TAG, "Countly stopped");
            }
        }
        super.onStop();
    }

    //Method to create app directory which is default directory for storing recorded videos
    public static void createDir() {
        File appDir = new File(Environment.getExternalStorageDirectory() + File.separator + Const.APPDIR);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !appDir.isDirectory()) {
            appDir.mkdirs();
        }
    }



}
