/*
 * Copyright (c) 2016. Vijai Chandra Prasad R.
 *                                                                                                                                                
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *                                                                                                                                               
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *                                                                                                                                               
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses
 */

package com.shapp.www.interviewkazapp;

/**
 * Created by vijai on 12-10-2016.
 */

// POJO class for bunch of statics used across the app
public class Const {
    static final int EXTDIR_REQUEST_CODE = 1000;
    static final int AUDIO_REQUEST_CODE = 1001;
    static final int SYSTEM_WINDOWS_CODE = 1002;
    static final int SCREEN_RECORD_REQUEST_CODE = 1003;
    public static final int VIDEO_EDIT_REQUEST_CODE = 1004;
    public static final int VIDEO_EDIT_RESULT_CODE = 1005;
    static final String SCREEN_RECORDING_START = "com.shapp.www.interviewkazapp.services.action.startrecording";
    static final String SCREEN_RECORDING_PAUSE = "com.shapp.www.interviewkazapp.services.action.pauserecording";
    static final String SCREEN_RECORDING_RESUME = "com.shapp.www.interviewkazapp.services.action.resumerecording";
    static final String SCREEN_RECORDING_STOP = "com.shapp.www.interviewkazapp.services.action.stoprecording";
    static final int SCREEN_RECORDER_NOTIFICATION_ID = 5001;
    static final int SCREEN_RECORDER_SHARE_NOTIFICATION_ID = 5002;
    static final String RECORDER_INTENT_DATA = "recorder_intent_data";
    static final String RECORDER_INTENT_RESULT = "recorder_intent_result";
    public static final String TAG = "SCREENRECORDER";
    public static final String APPDIR = "screenrecorder";

    public static final String ALERT_EXTR_STORAGE_CB_KEY = "ext_dir_warn_donot_show_again";

    public static final String VIDEO_EDIT_URI_KEY = "edit_video";

    static final String ANALYTICS_URL = "http://analytics.orpheusdroid.com";
    static final String ANALYTICS_API_KEY = "07273a5c91f8a932685be1e3ad0d160d3de6d4ba";

    static final String PREFS_REQUEST_ANALYTICS_PERMISSION = "request_analytics_permission";

    public enum RecordingState {
        RECORDING, PAUSED, STOPPED
    }

    public enum analytics {
        CRASHREPORTING, USAGESTATS
    }
}
