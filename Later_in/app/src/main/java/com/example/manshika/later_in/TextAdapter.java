package com.example.manshika.later_in;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
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
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by m.anshika on 4/25/2017.
 */

public class TextAdapter extends BaseAdapter {

    ArrayList<String> arr;
    private View myView;
    private static LayoutInflater myInflator;
    ArrayList<String> path_data = new ArrayList<String>();
    CheckBox c;
    int c_boxes[];
    boolean chkArray_text[];
    ArrayList<String> uris = new ArrayList<>();

    private Context mContext;

    public TextAdapter(Context c,ArrayList<String> d1){
        mContext = c;
        path_data = d1;
        chkArray_text = new boolean[path_data.size()];
    }

    public int getCount(){
        return path_data.size();
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View v;
        // Log.v("Anshika","get view called - view = " + convertView + " position = " + position);
        final RecyclerView.ViewHolder holder;
        if (convertView == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.text_activity,null);
            v.setLayoutParams(new GridView.LayoutParams(1200,150));

        }
        else {
            v = convertView;
        }
        TextView t = (TextView)v.findViewById(R.id.textView2);
        c = (CheckBox) v.findViewById(R.id.chk1);

        t.setText(path_data.get(position));
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Anshika",Boolean.toString(isChecked));
                if(isChecked)
                {
                    Log.d("Anshika",+position + "ischeckec");
                    // MainActivity3.checkbox_arr.set(position,true);
                    chkArray_text[position] = true;
                    uris.add(path_data.get(position));
                    Log.d("Anshika","length is is"+uris.size());
                }
                else
                {
                    Log.d("Anshika",+position + "is uncheckecked");
                    chkArray_text[position] = false;
                    uris.remove(new String(path_data.get(position)));
                    // MainActivity3.checkbox_arr.set(position,false);
                    Log.d("Anshika","length is is"+uris.size());
                }
            }
        });
//
//                for (int i = 0; i < chkArray.length; i++)
//                {
//                    if(chkArray[position])
//                    {
//                        Log.d("Anshika","when checking"+position);
//                        c.setChecked(true);
//                    }
//                    else
//                    {
//                        c.setChecked(false);
//                    }
//            }
        for (int i = 0; i < chkArray_text.length; i++)
        {
            if(chkArray_text[position])
            {
                Log.d("Anshika","when checking"+position);
                c.setChecked(true);

                Log.d("Anshika","length is is"+uris.size());
            }
            else
            {
                c.setChecked(false);
            }
        }
        return v;
    }

    public ArrayList<String> fn()
    {
        Log.d("Anshika","length is"+uris.size());
        return uris;

    }
}
