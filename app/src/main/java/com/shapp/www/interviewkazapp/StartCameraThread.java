package com.shapp.www.interviewkazapp;


import android.widget.Button;

/**
 * Created by borikhankozha on 9/16/17.
 */

public class StartCameraThread extends Thread {

    Button camera;

    public StartCameraThread(Button camera) {
        this.camera = camera;
    }


    public void run() {
        try {
            Thread.sleep(7000);
            camera.post(new Runnable() {
                @Override
                public void run() {
                    camera.callOnClick();
//                    stop();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
