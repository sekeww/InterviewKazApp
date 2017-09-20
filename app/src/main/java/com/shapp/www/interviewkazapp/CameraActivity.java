/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shapp.www.interviewkazapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class CameraActivity extends Activity implements Camera2VideoFragment.PauseInterface {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Display display = getWindowManager().getDefaultDisplay();
        SCREEN_HEIGHT = display.getHeight();
        SCREEN_WIDTH = display.getWidth();
        Log.d("MyTest","window: " + display.getWidth() + " " + display.getHeight());

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void pause() {
        Intent stopIntent = new Intent(this, RecorderService.class);
        stopIntent.setAction(Const.SCREEN_RECORDING_STOP);
        startService(stopIntent);
    }

    @Override
    public void onBackPressed() {
    }

}
