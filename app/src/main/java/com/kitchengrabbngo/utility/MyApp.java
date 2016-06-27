package com.kitchengrabbngo.utility;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import io.fabric.sdk.android.Fabric;


/**
 * Created by sandeep on 8/25/2015.
 */
public class MyApp extends Application {


    static public int merchant_id=1;
    static public RequestQueue reqstQ;
    public static final String PREFS_NAME = "MyPrefsFile";
    public  static String url="http://grabbngoserver-env.wpptrebp2w.us-east-1.elasticbeanstalk.com/";
    static public SharedPreferences sharedPreferences;
    static public MixpanelAPI mixpanel;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        String projectToken = "769c50e8ab7abe030e310e15adac6e07"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        mixpanel = MixpanelAPI.getInstance(this, projectToken);
        reqstQ= Volley.newRequestQueue(getApplicationContext());
        sharedPreferences=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
