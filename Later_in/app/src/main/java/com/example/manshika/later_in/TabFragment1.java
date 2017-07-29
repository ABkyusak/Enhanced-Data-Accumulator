package com.example.manshika.later_in;

/**
 * Created by m.anshika on 4/21/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;

public class TabFragment1 extends Fragment {
    static ArrayList<String> arr;
    Context context;
    ImageAdapter img_adapter;
    GridView gv_image;
    static ArrayList<String> uris = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = inflater.getContext();
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        dbAdapter obj = new dbAdapter(getContext());
        Log.d("Anshika","fragment 1");
        try {
            arr = obj.extraction_image();
            img_adapter = new ImageAdapter(context, arr);
            gv_image = (GridView) view.findViewById(R.id.grid_v1);
            Log.d("TAG1","IMAGE");
            gv_image.setAdapter(img_adapter);
            gv_image.setNumColumns(3);
            Button send = (Button) view.findViewById(R.id.button);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uris = img_adapter.fn();
                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                   for(int i=0;i<uris.size();i++)
                   {
                       imageUris.add(Uri.parse(uris.get(i)));
                   }
                    Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                        // shareIntent.setType("vnd.android-dir/mms-sms");
                        shareIntent.setType("image/*");
                        shareIntent.setPackage(DisplayData.FGApp);
                        startActivity(shareIntent);
                }
            });

        }
        catch (Exception e){}


        return view;
    }
}