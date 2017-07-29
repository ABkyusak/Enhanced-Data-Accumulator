package com.example.manshika.later_in;

/**
 * Created by m.anshika on 3/6/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.CancelledKeyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by m.anshika on 2/8/2017.
 */

public class Adapter extends BaseAdapter {

    private Context context;
    private String[] appNames;
    private static LayoutInflater myInflator;
    private List<ResolveInfo> myAppIntentList;
    private View myView;
    private boolean arrI[];
    private GridView gvAdapter;
    private int[] UIDs;
    static Set<String> selectedApps = new HashSet<>();
    //ArrayList<String> appPaths;

    public Adapter(MainActivity mainActivity, String[] myAppNames, List<ResolveInfo> appIntentList, GridView g, int[] uids)
    {
        context = mainActivity;
        appNames = myAppNames;
        myInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myAppIntentList = appIntentList;
        arrI = new boolean[appNames.length];
        gvAdapter = g;
        for (int in = 0; in<appNames.length; in++){
            arrI[in] = false;
        }
        UIDs = uids;
        // appPaths = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return appNames.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Container {
        TextView tv;
        ImageView iv;
        CheckBox cb;
        TextView tvHid;
    }

    private Container objContainer;
    private ArrayList<String> arrLstString = new ArrayList<>();

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        if((!appNames.equals(null)) || (!appNames.equals(null))){

            myView = myInflator.inflate(R.layout.programlist, parent, false);

            objContainer = new Container();
            objContainer.iv = (ImageView) myView.findViewById(R.id.imageView1);
            objContainer.tv = (TextView) myView.findViewById(R.id.textView1);
            objContainer.cb = (CheckBox) myView.findViewById(R.id.chk1);
            objContainer.tvHid = (TextView) myView.findViewById(R.id.tvHidden);

            PackageManager pm = context.getPackageManager();
            objContainer.tv.setText(appNames[position]);
            objContainer.tv.setTextColor(Color.BLACK);

            final ResolveInfo resolveInfo = myAppIntentList.get(position);
            objContainer.iv.setImageDrawable(resolveInfo.loadIcon(pm));

            objContainer.tvHid.setText(Integer.toString(UIDs[position]));
            // Log.d("TAG11", Integer.toString(UIDs[position]));

            int noOfItems = gvAdapter.getChildCount();
            //Log.d("TAG11", Integer.toString(noOfItems));
            for(int i = 0; i<noOfItems; i++)
            {
                LinearLayout child = (LinearLayout) gvAdapter.getChildAt(i);
                TextView tvName = (TextView) child.findViewById(R.id.textView1);
                CheckBox cbChild = (CheckBox) child.findViewById(R.id.chk1);
                TextView tvChild = (TextView) child.findViewById(R.id.tvHidden);

                for (String str:arrLstString) {
                    if(str.equals(tvChild.getText().toString()))
                    {
                        cbChild.setChecked(true);
//                        }
                    }
                }
            }

        }

        objContainer.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnSetChkBox(v, position);
            }
        });

        objContainer.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnSetChkBox(v, position);
            }
        });

        objContainer.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll1 = (LinearLayout) v.getParent();
                TextView tvH = (TextView) ll1.findViewById(R.id.tvHidden);
                String packageName = ((ResolveInfo) myAppIntentList.get(position)).activityInfo.packageName;
                CheckBox cb = (CheckBox) v;
                if(cb.isChecked())
                {
                    selectedApps.add(packageName);
                    arrLstString.add(tvH.getText().toString());
                }
                else
                {
                    selectedApps.remove(packageName);
                    arrLstString.remove(tvH.getText().toString());
                }
            }
        });

        return myView;
    }

    private void fnSetChkBox(View v, int pos) {
        LinearLayout ll1 = (LinearLayout) v.getParent();
        CheckBox cb = (CheckBox) ll1.findViewById(R.id.chk1);
        TextView tvH = (TextView) ll1.findViewById(R.id.tvHidden);

        String packageName = ((ResolveInfo) myAppIntentList.get(pos)).activityInfo.packageName;

        if(cb.isChecked())
        {
            cb.setChecked(false);
            arrI[pos] = false;
            selectedApps.remove(packageName);
            arrLstString.remove(tvH.getText().toString());
        }
        else
        {
            cb.setChecked(true);
            arrI[pos] = true;
            selectedApps.add(packageName);
            arrLstString.add(tvH.getText().toString());
        }
    }
}