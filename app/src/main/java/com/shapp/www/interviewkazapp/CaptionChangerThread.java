package com.shapp.www.interviewkazapp;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

/**
 * Created by borikhankozha on 9/16/17.
 */

public class CaptionChangerThread extends Thread {

    TextView caption;
    LinearTimer linearTimer;
    LinearTimerView linearTimerView;
    int question;
    boolean finish;
    Button n;
    boolean stop;
    TextToSpeech textToSpeech;
    boolean used;

    public CaptionChangerThread(TextView caption,
                                LinearTimer linearTimer,
                                LinearTimerView linearTimerView,
                                Button n,
                                TextToSpeech textToSpeech
                                ) {
        this.caption = caption;
        this.linearTimer = linearTimer;
        this.linearTimerView = linearTimerView;
        question = 0;
        finish = false;
        this.n = n;
        this.textToSpeech = textToSpeech;
        used = false;
    }


    public void next() {
        if (!used) {
            caption.post(new Runnable() {
                @Override
                public void run() {
                    caption.setVisibility(View.INVISIBLE);
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            caption.post(new Runnable() {
                @Override
                public void run() {
                    caption.setVisibility(View.VISIBLE);
                }
            });
            caption.post(new Runnable() {
                @Override
                public void run() {
                    if (question == 1) {
                        caption.setText("What is your name?");
                        textToSpeech.speak(caption.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if (question == 2) {
                        caption.setText("How old are you?");
                        textToSpeech.speak(caption.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if (question == 3) {
                        caption.setText("Are you a good person?");
                        textToSpeech.speak(caption.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if (question == 4) {
                        caption.setText("What have you done?");
                        textToSpeech.speak(caption.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if (question == 5) {
                        caption.setText("Are you cool?");
                        textToSpeech.speak(caption.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if (question == 6) {
                        caption.setText("How are you?");
                        textToSpeech.speak(caption.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        used = true;
                    }
                    if (question <= 6) {
                        LinearTimer linearTimer = new LinearTimer.Builder()
                                .linearTimerView(linearTimerView)
                                .duration(10 * 1000)
                                .build();
                        linearTimer.startTimer();
                    }
                }
            });
        }
        stop = false;
        if (question == 6) {

            n.post(new Runnable() {
                @Override
                public void run() {
                    stop = true;
                    //linearTimer.animationComplete();
                    n.callOnClick();
                }
            });
            return;
        }
//        if (stop) {
//            stop();
//        }
        question++;
        int count = 0;
        finish = false;
        while (true) {
            count++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (finish) {
                break;
            }
            if (count > 100) {
                break;
            }
        }
//        if (question == 5) {
//            n.post(new Runnable() {
//                @Override
//                public void run() {
//                    n.callOnClick();
//
//                }
//            });
//        }
        if (question <= 6) {
            next();
        }
    }

    public void run() {
        try {
            Thread.sleep(1000);
            linearTimerView.post(new Runnable() {
                @Override
                public void run() {
                    linearTimerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish = true;
                        }
                    });
                }
            });
            next();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
