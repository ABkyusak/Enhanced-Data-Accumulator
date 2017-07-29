package com.example.manshika.later_in;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class MyService extends IntentService {

    int mStartMode;

    IBinder mBinder;

    boolean mAllowRebind;
    UsageStatsManager mUsageStatsManager;
    String foregroundApp;


    public MyService()
    {
        super("MyService");
       // Log.d("TAG11","service is called");
    }

    Timer timer = new Timer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

    //    Log.d("TAG11", "started");
        mUsageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
               // Log.d("TAG11", "running");
                long time = System.currentTimeMillis();
                List<UsageStats> queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000, time);
             //   Log.d("TAG11","size of list is"+queryUsageStats.size());
                if (queryUsageStats != null) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                    for (UsageStats usageStats : queryUsageStats) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        foregroundApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    }
                }
             //   Log.d("TAG11", "app is"+foregroundApp);

            }

        },2000,3000);

        return START_STICKY;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("TAG11","heya!!");
    }


}