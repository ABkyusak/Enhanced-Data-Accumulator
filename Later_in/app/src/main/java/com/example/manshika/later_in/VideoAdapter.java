package com.example.manshika.later_in;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by m.anshika on 4/25/2017.
 */

public class VideoAdapter extends BaseAdapter {

    ArrayList<String> arr = new ArrayList<String>();
    private View myView;
    private static LayoutInflater myInflator;
    VideoView videoView;
    boolean chkArray_video[];
    ArrayList<String> uris1 = new ArrayList<>();
    private Context mContext;

    public VideoAdapter(Context c, ArrayList<String> arr1){
        mContext = c;
        arr = arr1;
        Log.d("Anshika","cONSTRUCTOR"+arr.size());
        chkArray_video = new boolean[arr.size()];
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
            v = LayoutInflater.from(mContext).inflate(R.layout.video_activity,null);
            // v.setLayoutParams(new GridView.LayoutParams(400,300));
        }
        else {
            v = convertView;
        }
        Log.d("tag11",Integer.toString(arr.size()));
        final String video_uri_string[]= new String[arr.size()];
        for(int i=0;i<arr.size();i++)
        {
            video_uri_string[i] = arr.get(i);
        }

        videoView = (VideoView) v.findViewById(R.id.videoView1);
        try {
            CheckBox c = (CheckBox) v.findViewById(R.id.chk1);
            ViewGroup.LayoutParams params=videoView.getLayoutParams();
            params.height=220;
            params.width = 220;
            videoView.setLayoutParams(params);
            MediaController mc = new MediaController(mContext);
            mc.setMediaPlayer(videoView);
            videoView.setMediaController(mc);
            videoView.setVideoURI(Uri.parse(video_uri_string[position]));
            videoView.start();
            //videoView.setOnPreparedListener(PreparedListener);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer m) {
                    try {
                        if (m.isPlaying()) {
                            m.stop();
                            m.release();
                            m = new MediaPlayer();
                        }
                        m.setVolume(0f, 0f);
                        m.setLooping(false);
                        m.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    Log.d("Anshika",Boolean.toString(isChecked));
                    Log.d("Anshika Mittal","size is"+Integer.toString(arr.size()));
                    if(isChecked)
                    {
                        Log.d("Anshika",+position + "ischeckec");
                        // MainActivity3.checkbox_arr.set(position,true);
                         chkArray_video[position] = true;
                         uris1.add(arr.get(position));
                        Log.d("Anshika","check check check");

                    }
                    else
                    {
                        Log.d("Anshika",+position + "is uncheckecked");
                        chkArray_video[position] = false;
                        /// MainActivity3.checkbox_arr.set(position,false);
                        uris1.remove(new String(arr.get(position)));
                    }
                }
            });
            for (int i = 0; i < chkArray_video.length; i++)
            {
                if(chkArray_video[position])
                {
                    Log.d("Anshika","when checking video"+position);
                    c.setChecked(true);
                }
                else
                {
                    c.setChecked(false);
                }
            }




        }
        catch (Exception e)
        {
            Log.d("TAG11","Video not found !");
        }

        return v;
    }

    public ArrayList<String> fn()
    {

        return uris1;
    }
}
