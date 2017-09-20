package com.shapp.www.interviewkazapp.folderpicker;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

/**
 * Created by borikhankozha on 9/17/17.
 */

public class ThreadDialog extends Thread {

    Context c;
    Intent i;
    Button b;

    public ThreadDialog(Intent i, Context c, Button b) {
        this.i = i;
        this.c = c;
        this.b = b;
    }

    public void run() {
        try {
            Thread.sleep(1500);
            b.post(new Runnable() {
                @Override
                public void run() {
                    c.startService(i);
                    b.callOnClick();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
