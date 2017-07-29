package com.example.manshika.later_in;

/**
 * Created by m.anshika on 4/21/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class TabFragment2 extends Fragment {
    ArrayList<String> arr;
    Context context;
    TextAdapter text_adapter;
    GridView grid_text;
    ArrayList<String> uris;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = inflater.getContext();
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        dbAdapter obj = new dbAdapter(context);

        if(obj.extraction_text().size()>0)
        {
            try {
                arr = obj.extraction_text();
                ArrayList<String> data1 = new ArrayList<String>();
                String pathTextFile = arr.get(0);
                String[] arrBrokenPath;
                arrBrokenPath = pathTextFile.split("/");
                FileInputStream FIS;
                File file = new File(context.getFilesDir(), arrBrokenPath[arrBrokenPath.length - 1]);
                if (file.exists()) {
                    Log.d("TAG15", "exists");
                    String strFGAppTextFileData = "";
                    try {
                        FileInputStream fileIn = context.openFileInput(arrBrokenPath[arrBrokenPath.length - 1]);
                        InputStreamReader InputRead = new InputStreamReader(fileIn);
                        char[] inputBuffer = new char[100];
                        int charRead;
                        while ((charRead = InputRead.read(inputBuffer)) > 0) {
                            String readString = String.copyValueOf(inputBuffer, 0, charRead);
                            strFGAppTextFileData += readString;
                        }
                        InputRead.close();
                        String[] arrDataToShow = strFGAppTextFileData.split("###");
                        for (String str : arrDataToShow) {
                            data1.add(str);
                        }
                    } catch (Exception e) {
                        Log.d("TAG11", "could not open file");
                    }
                    MainActivity3.chkArray = new Boolean[data1.size()];
                    text_adapter = new TextAdapter(context,data1);
                    grid_text = (GridView) view.findViewById(R.id.grid_v1);
                    Log.d("TAG1","RETURNING TEXT FRAGMENT");
                    grid_text.setAdapter(text_adapter);
                }

                Button send = (Button) view.findViewById(R.id.Button_text);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strText="";
                        uris = text_adapter.fn();
                        for (String str: uris) {
                            strText += str;
                            strText += "\n";
                        }
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, strText);
                        shareIntent.setType("text/plain");
                        shareIntent.setPackage(DisplayData.FGApp);
                        startActivity(shareIntent);
                    }
                });
            }
            catch (Exception e) {
                Log.d("TAG11","No data for text");
            }
        }
        return view;

    }


}

