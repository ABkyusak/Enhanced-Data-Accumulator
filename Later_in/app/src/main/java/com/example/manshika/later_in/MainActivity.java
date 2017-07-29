package com.example.manshika.later_in;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Parcelable;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.content.pm.ResolveInfo;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import java.io.File;

public class MainActivity extends Activity {

    public String[] prgmNameList;

    GridView gv;

    RelativeLayout rl1, rl2;
    TextView tv;
    Button btnGo;

    String strAction;
    String strType;

    Intent receivedIntent;
    Intent mainIntent;
    Adapter adapter;

    List<ResolveInfo> appIntentList;
    int[] arrUIDs;

    dbAdapter objDBAdapter;

    Uri uri = null;
    Uri uri_new = null;
    String sharedText = null;
    ArrayList<Parcelable> listUri = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, AppService.class));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        // registerReceiver(mStatusReceiver, new IntentFilter(intentFilter));

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width_org = metrics.widthPixels;
        int height_org = metrics.heightPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (3 * height_org) / 5;
        params.width = width_org;
        params.y = 700;

        gv = (GridView) findViewById(R.id.grid1);

        rl1 = (RelativeLayout) findViewById(R.id.rlHeaderOuter);
        rl2 = (RelativeLayout) findViewById(R.id.rlButtonInner);
        btnGo = (Button) findViewById(R.id.btnGo);
        tv = (TextView) findViewById(R.id.mainTv);

        rl1.setMinimumWidth((2 * width_org) / 3);
        rl1.setMinimumHeight(height_org / 11);

        rl2.setMinimumWidth(width_org / 3);

        btnGo.setMinimumWidth(3 * rl2.getMinimumWidth() / 4);
        btnGo.setTextSize(20);
        btnGo.setTextColor(Color.WHITE);

        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);

        //getting the shared data
        receivedIntent = getIntent();
        strAction = receivedIntent.getAction();
        strType = receivedIntent.getType();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ArrayList<String> receivedUris = new ArrayList<>();
        Adapter.selectedApps.clear();


        if ((Intent.ACTION_SEND.equals(strAction) || Intent.ACTION_SEND_MULTIPLE.equals(strAction)
                || Intent.ACTION_PROCESS_TEXT.equals(strAction)) && (strType != null))
        {
            gv = (GridView) findViewById(R.id.grid1);
            if(strType.startsWith("text/plain"))
            {
                if(strAction.equals(Intent.ACTION_PROCESS_TEXT))
                {
                    sharedText = receivedIntent.getStringExtra(Intent.EXTRA_PROCESS_TEXT);
                }
                else {
                    sharedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                }

                if (sharedText != null)
                {
                    mainIntent = new Intent();
                    mainIntent.setAction(Intent.ACTION_SEND);
                    mainIntent.setType("text/plain");
                    fnCall();
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Nothing selected.", Toast.LENGTH_LONG).show();
                    adapter = new Adapter(MainActivity.this, null, null, gv, arrUIDs);
                    gv.setAdapter(adapter);
                }
            }
            else
            {
                if(strAction.equals("android.intent.action.SEND_MULTIPLE"))
                {

                    listUri = receivedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);

                }
                else
                {
                    uri =  (Uri)receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                }

                mainIntent = new Intent(Intent.ACTION_SEND);
                mainIntent.setType(strType);
                fnCall();
            }

            gv.setNumColumns(3);
            gv.setHorizontalSpacing(20);
            gv.setVerticalSpacing(40);
            gv.setPadding(0, 50, 0, 0);

        }
        else {
            Toast.makeText(MainActivity.this, "Nothing selected.", Toast.LENGTH_LONG).show();

        }

    }

    public void fnBtnGoClicked(View v)
    {
        if(Adapter.selectedApps.isEmpty())
        {
            Toast.makeText(MainActivity.this, "No application selected", Toast.LENGTH_LONG).show();
        }
        else
        {
            objDBAdapter = new dbAdapter(this);

            if(sharedText != null)
            {
                for (String str: Adapter.selectedApps)
                {
                    char READ_BLOCK_SIZE = 100;
                    FileOutputStream outputStream;


                    File file = new File(this.getFilesDir(), str);

                    if(file.exists())
                    {
                        try
                        {
                            String appendData = sharedText.trim();

                            //reading data
                            try {
                                FileInputStream fileIn=openFileInput(str);
                                InputStreamReader InputRead= new InputStreamReader(fileIn);

                                char[] inputBuffer= new char[READ_BLOCK_SIZE];
                                String s="";
                                int charRead;
                                while ((charRead=InputRead.read(inputBuffer))>0) {
                                    String readString=String.copyValueOf(inputBuffer,0,charRead);
                                    s +=readString;
                                }
                                InputRead.close();
                                if(isEqual(s, appendData))
                                {
                                    // Log.d("TAG11", "data already exists");
                                }
                                else
                                {
//
                                    appendData += "###";
                                    outputStream = openFileOutput(str, MODE_APPEND);
                                    outputStream.write(appendData.getBytes());
                                    outputStream.close();

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        catch(Exception e)
                        {

                        }
                    }
                    else
                    {

                        try {
                            String insertData = sharedText.trim() + "###";
                            outputStream = openFileOutput(str, MODE_PRIVATE);
                            outputStream.write(insertData .getBytes());
                            outputStream.close();
                            // Log.d("TAG11", "file created and text inserted");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Uri fileUri = Uri.fromFile(file);
                    objDBAdapter.fnInsert(strType, fileUri, null);
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();

                }

            }
            else if(uri != null)
            {


                objDBAdapter.fnInsert( strType, uri, null);
            }
            else if(listUri != null)
            {
                objDBAdapter.fnInsert(strType, null, listUri);
            }
            else
            {
                Toast.makeText(MainActivity.this, "could not find path for selected file", Toast.LENGTH_LONG).show();
            }

            finish();
        }
    }

    private boolean isEqual(String readData, String appendData)
    {
        boolean flag = false;
        String[] arr = readData.split("###");
        for(int i=0;i<arr.length;i++)
        {
            if(arr[i].trim().equals(appendData)) {
                flag = true;
            }
        }
        return flag;
    }

    private void fn(String str)
    {
        try
        {
            FileInputStream fileIn=openFileInput(str);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                String readString=String.copyValueOf(inputBuffer,0,charRead);
                s +=readString;
            }
            InputRead.close();

        }
        catch (Exception e){}

    }

    private void fnCall() {
        appIntentList = getPackageManager().queryIntentActivities(mainIntent, 0);
        prgmNameList = new String[appIntentList.size()];
        arrUIDs = new int[prgmNameList.length];
        int index = 0;
        for (Object object : appIntentList) {
            ResolveInfo info = (ResolveInfo) object;
            final String title = (String)getBaseContext().getPackageManager().getApplicationLabel(info.activityInfo.applicationInfo);
            prgmNameList[index] = title;
            int id = ((ResolveInfo) object).activityInfo.applicationInfo.uid;
            arrUIDs[index] = id;
            index++;
        }

        adapter = new Adapter(MainActivity.this, prgmNameList, appIntentList, gv, arrUIDs);
        gv.setAdapter(adapter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
