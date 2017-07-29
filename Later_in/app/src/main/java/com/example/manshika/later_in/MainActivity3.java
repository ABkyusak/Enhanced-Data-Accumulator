package com.example.manshika.later_in;

/**
 * Created by m.anshika on 4/21/2017.
 */
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.manshika.later_in.PagerAdapter;

import java.util.ArrayList;


public class MainActivity3 extends AppCompatActivity {
    static ArrayList<String> arr_tabs;
    static ArrayList<Integer> checkbox_arr = new ArrayList<>();
    static Boolean chkArray[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width_org = metrics.widthPixels;
        int height_org = metrics.heightPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (3 * height_org) / 5;
        params.width = width_org;
        params.y = 700;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        dbAdapter d = new dbAdapter(getApplicationContext());
        arr_tabs = new ArrayList<>();

        if(d.extraction_image().size()>0)
        {
            Log.d("TAG1","inside image");
            tabLayout.addTab(tabLayout.newTab().setText("Images"));
            arr_tabs.add("images");
        }

        if(d.extraction_text().size()>0)
        {
            Log.d("Hello","inside");
            tabLayout.addTab(tabLayout.newTab().setText("Text"));
            arr_tabs.add("text");
        }
        if(d.extraction_video().size()>0)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Videos"));
            arr_tabs.add("video");
        }
        if(d.extraction_others().size()>0)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Others"));
            arr_tabs.add("others");
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
