package com.example.manshika.later_in;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by m.anshika on 4/25/2017.
 */

public class ImageAdapter extends BaseAdapter {

    ArrayList<String> arr;
    private View myView;
    private static LayoutInflater myInflator;
    String images[];
    private Context mContext;
    boolean chkArray_image[];
    ArrayList<String> uris;

    public ImageAdapter(Context c, ArrayList<String> data){
        mContext = c;
        arr = data;
        chkArray_image = new boolean[data.size()];
       uris = new ArrayList<>();
    }

    public int getCount(){
        return arr.size();
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View v;
        if (convertView == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.image_activity,null);
            v.setLayoutParams(new GridView.LayoutParams(400,400));
        }
        else {
            v = convertView;
        }
        Log.d("tag11",Integer.toString(arr.size()));
        images= new String[arr.size()];
        for(int i=0;i<arr.size();i++)
        {
            images[i] = arr.get(i);
        }

        CheckBox c = (CheckBox) v.findViewById(R.id.chk1);

        ImageView imageview = (ImageView)v.findViewById(R.id.imageView1);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.setPadding(2, 2, 2, 2);

        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(images[position]));
            bitmap = getResizedBitmap1(bitmap , 240,240);

            imageview.setImageBitmap(bitmap);
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("Anshika",Boolean.toString(isChecked));
                    if(isChecked)
                    {
                        Log.d("Anshika",+position + "ischeckec");
                        // MainActivity3.checkbox_arr.set(position,true);
                        chkArray_image[position] = true;
                        uris.add(images[position]);
                    }
                    else
                    {
                        Log.d("Anshika",+position + "is uncheckecked");
                        chkArray_image[position] = false;
                        uris.remove(new String(images[position]));
                        // MainActivity3.checkbox_arr.set(position,false);
                    }
                }
            });

            for (int i = 0; i < chkArray_image.length; i++)
            {
                if(chkArray_image[position])
                {
                    Log.d("Anshika","when checking"+position);
                    c.setChecked(true);
                    // get the uri of the image there in string form!!
                }
                else
                {
                    c.setChecked(false);
                }
            }
        }
        catch(Exception e)
        {
            Log.d("TAG11","MEDIASOTE=");
        }

        return v;
    }

    public ArrayList<String> fn()
    {
        return uris;
    }

    public Bitmap getResizedBitmap1(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
