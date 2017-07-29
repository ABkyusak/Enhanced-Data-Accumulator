package com.example.manshika.later_in;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by m.anshika on 4/25/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by k.yogesh on 5/9/2017.
 */

public class OtherAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrUrisOthers;
    Boolean chkArray_others[];
    ArrayList<String> uris = new ArrayList<>();
    public OtherAdapter(Context c, ArrayList<String> ar)
    {
        context = c;
        arrUrisOthers = ar;
        chkArray_others = new Boolean[ar.size()];
    }

    public int getCount(){
        return arrUrisOthers.size();
    }

    public Object getItem(int position){

        return null;
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        //Log.d("TAG11", "inside getview text");
        View v = null;

        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.other_activity, null);
            v.setLayoutParams(new GridView.LayoutParams(1200, 150));
        } else {
            v = convertView;
        }
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.chk1);
        TextView textView = (TextView) v.findViewById(R.id.textView2);
        ImageView iv = (ImageView) v.findViewById(R.id.logo);

        String result = null;
        if (Uri.parse(arrUrisOthers.get(position)).getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(Uri.parse(arrUrisOthers.get(position)), null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = Uri.parse(arrUrisOthers.get(position)).getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        Log.v("TAG11", "file path = " + result);
//        InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(arrUrisOthers.get(position)));
//        Drawable yourDrawable = Drawable.createFromStream(inputStream,arrUrisOthers.get(position));
        textView.setText(result, TextView.BufferType.EDITABLE);
        //int imageResource = context.getResources().getIdentifier(arrUrisOthers.get(position), null, context.getPackageName());
        //  Drawable res = context.getResources().getDrawable(imageResource,context.getTheme());
        // iv.setImageDrawable(res);
        try{
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("Anshika", Boolean.toString(isChecked));
                    if (isChecked) {
                        Log.d("Anshika", +position + "ischeckec");
                        // MainActivity3.checkbox_arr.set(position,true);
                        chkArray_others[position] = true;
                        uris.add(arrUrisOthers.get(position));
                    } else {
                        Log.d("Anshika", +position + "is uncheckecked");
                        chkArray_others[position] = false;
                        uris.remove(new String(arrUrisOthers.get(position)));
                        // MainActivity3.checkbox_arr.set(position,false);
                    }
                }
            });
            for (int i = 0; i < chkArray_others.length; i++) {
                if (chkArray_others[position]) {
                    Log.d("Anshika", "when checking" + position);
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        }
        catch(Exception e)
        {}
        return v;
//        Drawable icon=null;
//        try{
//
//            InputStream i = context.getContentResolver().openInputStream(Uri.parse(arrUrisOthers.get(position)));
//            Log.d("TAG13","inside try of drawable");
//            icon = Drawable.createFromStream(i,arrUrisOthers.get(position));
//
//        }
//        catch(Exception e)
//        {
//            Log.d("TAG13","inside catch of drawable");
//        }
//        iv.setImageDrawable();
    }

    public ArrayList<String> fn()
    {
        // Log.d("Anshika","length is"+uris.size());
        return uris;

    }
}
