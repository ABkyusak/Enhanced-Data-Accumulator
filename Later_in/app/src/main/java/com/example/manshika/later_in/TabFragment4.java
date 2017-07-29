package com.example.manshika.later_in;

/**
 * Created by m.anshika on 4/28/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TabFragment4 extends Fragment {
    GridView g;
    OtherAdapter oa;
    ArrayList<String> arr = new ArrayList<>();
    Context context;
    ArrayList<String> uris;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view =  inflater.inflate(R.layout.tab_fragment_4, container, false);
        g = (GridView) view.findViewById(R.id.grid_v1);

        dbAdapter obj = new dbAdapter(context);
        arr = obj.extraction_others();
        try
        {
            oa = new OtherAdapter(context, arr);
            g = (GridView) view.findViewById(R.id.grid_v1);
            g.setAdapter(oa);
            Button send_others = (Button) view.findViewById(R.id.button);
            send_others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uris = oa.fn();
                    ArrayList<Uri> UrisOthers = new ArrayList<Uri>();
                    for(int i=0;i<uris.size();i++)
                    {
                        UrisOthers.add(Uri.parse(uris.get(i)));
                    }
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,UrisOthers );
                    shareIntent.setType("*/*");
                    shareIntent.setPackage(DisplayData.FGApp);
                    startActivity(shareIntent);
                }
            });
        }
        catch (Exception e) {
            Log.d("TAG11"," for text");
        }
        return view;

    }
}