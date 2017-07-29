package com.example.manshika.later_in;

/**
 * Created by m.anshika on 4/18/2017.
 */
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by m.anshika on 3/21/2017.
 */

public class FloatIcon extends Service {
    private WindowManager windowManager;
    private ImageView chatHead;
    WindowManager.LayoutParams params;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.newicon);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

//        //this code is for dragging the chat head
//        chatHead.setOnTouchListener(new View.OnTouchListener() {
//            private int initialX;
//            private int initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        initialX = params.x;
//                        initialY = params.y;
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        params.x = initialX
//                                + (int) (event.getRawX() - initialTouchX);
//                        params.y = initialY
//                                + (int) (event.getRawY() - initialTouchY);
//                        windowManager.updateViewLayout(chatHead, params);
//                        return true;
//                }
//                return false;
//            }
//        });

        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.d("TAG12","chathead is clicked!");
                Intent n = new Intent(getApplication(), MainActivity3.class);
                n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(n);
            }
        });

        windowManager.addView(chatHead, params);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null)
            windowManager.removeView(chatHead);
    }
}