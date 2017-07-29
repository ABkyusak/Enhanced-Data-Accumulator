package com.example.manshika.later_in;

/**
 * Created by m.anshika on 4/21/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabFragment3 extends Fragment {
    static ArrayList<String> arr;
    GridView g;
    VideoAdapter va;
    Context context;
    ArrayList<String> uris;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = inflater.getContext();
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        dbAdapter objdbAdapter = new dbAdapter(getContext());
        Log.d("Anshika","fragment 3");
        try {

            arr = objdbAdapter.extraction_video();
            va = new VideoAdapter(context, arr);
            g = (GridView) view.findViewById(R.id.grid_v1);
            g.setAdapter(va);
            g.setNumColumns(3);
            Button send_video = (Button) view.findViewById(R.id.button_video);
            send_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uris = va.fn();
                    ArrayList<Uri> UrisVideos = new ArrayList<Uri>();
                    for(int i=0;i<uris.size();i++)
                    {
                        UrisVideos.add(Uri.parse(uris.get(i)));
                    }
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,UrisVideos );
                    shareIntent.setType("*/*");
                    shareIntent.setPackage(DisplayData.FGApp);
                    startActivity(shareIntent);
                }
            });
        }
        catch(Exception e)
        {
            Log.d("TAG11","No data for video");
        }
        return view;
    }
}