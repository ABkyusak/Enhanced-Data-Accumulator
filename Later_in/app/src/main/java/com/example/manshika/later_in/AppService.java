package com.example.manshika.later_in;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Display;

import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class AppService extends IntentService{

    Context mainContext;
    public void setContext(Context c)
    {
        mainContext = c;
    }
    public AppService()
    {
        super("AppService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    Timer timer  =  new Timer();
    //  mUsageStatsManager;
    String foregroundApp="";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        // Log.d("TAG11", "started");

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                //  Log.d("TAG11", "running");

//                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//                boolean isScreenOn = pm.isInteractive();
//                if(isScreenOn)
//                {
//                    Log.d("TAG11", "on");
//                }
//                else {
//                    Log.d("TAG11", "off");
//                }
                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
                long time = System.currentTimeMillis();
                UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
                // UsageStatsManager mUsageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService("usagestats");
                List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
                // Sort the stats by the last time used
                if (stats != null) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : stats) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        String topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        foregroundApp = topPackageName;


                    }
                }

//                    long time = System.currentTimeMillis();
//                List<UsageStats> queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*1000, time);
//                if (queryUsageStats != null) {
//                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
//                    for (UsageStats usageStats : queryUsageStats) {
//                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
//                    }
//                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
//                        foregroundApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                        Log.d("TAG11","app is"+foregroundApp);
//                    }
//                }
                //  Log.d("TAG11", foregroundApp);
                dbAdapter objdbAdapter = new dbAdapter(getApplicationContext());
                if(objdbAdapter.fnCheckTable(foregroundApp))
                {
                    DisplayData.FGApp=foregroundApp;
                    Log.d("TAG11",DisplayData.FGApp);
                    //  Log.d("TAG11", "exists");
                    startService(new Intent(getBaseContext(), FloatIcon.class));
                }
                else
                {
                    // Log.d("TAG11", "doesnot exist");
                    stopService(new Intent(getBaseContext(), FloatIcon.class));

                }
            }

        },100,200);

        return START_STICKY;
    }


}