package com.example.manshika.later_in;
import android.app.ActivityManager;
import android.app.AliasActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by k.yogesh on 2/20/2017.
 */

public class dbAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbUseLaterIn";
    private static final int DATABASE_VERSION = 1;
    private static final String COL_ID = "ID";
    private static final String COL_PATH = "PATH";
    private static final String COL_TYPE = "TYPE";
    private static final String TABLENAME_PREFIX = "t_";
    private SQLiteDatabase db;
    String qryCreate;
    private Context mainContext;

    public dbAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mainContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public void fnInsert(String strType, Uri uri, ArrayList<Parcelable> listUri)
    {
        //Log.d("TAG11", uri.toString());
        //URL url = new URL(uri.getScheme(), uri.getHost(), uri.getPath());
        for (String str: Adapter.selectedApps)
        {
            str = str.replace(".", "_");
            String tableName = TABLENAME_PREFIX + str;
            //Log.d("TAG11", "entering try");
            try
            {
                //Log.d("TAG11", "inside try");
                db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
                if(cursor != null)
                {
                    // Log.d("TAG11", "inside if of try block of fninsert,table " + tableName + " exists and cursor not null");
                    if(fnInsert2(tableName, strType, uri, listUri))
                    {
                        //Log.d("TAG11", "inside if of try block of fninsert, when fninsert2 returns true.data inserted successfully noexception");
                        Toast.makeText(mainContext, "Data inserted successfully.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Log.d("TAG11", "data not inserted successfully");
                        String i[] = str.split("_");
                        str = i[i.length-1];
                        //Toast.makeText(mainContext, "Selected item already exists for " + str, Toast.LENGTH_LONG).show();
                        //Log.d("TAG11",  "inside else of try block of fninsert, fninsert2 returned false so Selected item already exists for " + str);
                        Toast.makeText(mainContext, "Data inserted successfully...", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    //Log.d("TAG11", "cursor returned null");
                    //Toast.makeText(mainContext, "Error inserting data.", Toast.LENGTH_LONG).show();
                }
                db.close();
            }
            catch (Exception e)
            {
                //Log.d("TAG11", "inside exception of fninsert, table doesnt exist, creating table..");
                String qryCreate = "CREATE TABLE " + tableName + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_PATH + " TEXT, " + COL_TYPE + " TEXT)" ;

                try
                {
                    db = this.getWritableDatabase();
                    db.execSQL(qryCreate);
                    //Log.d("TAG11", "inside try block of exception of fninsert and table " + tableName + " created");
                    //Log.d("TAG11", uri.toString());
                    if(fnInsert2(tableName, strType, uri, listUri))
                    {
                        // Log.d("TAG11", "inside if block of try block of exception because fninsert2 returned true,data inserted");
                        Toast.makeText(mainContext, "Data inserted successfully.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Log.d("TAG11", "data not inserted");
                        String i[] = str.split("_");
                        str = i[i.length-1];
                        //Toast.makeText(mainContext, "Selected item already selected for " + str, Toast.LENGTH_LONG).show();
                        //Log.d("TAG11",  "inside else block of try of exception of fninsert because fninsert2 returned false,Selected item already exists for " + str);
                        Toast.makeText(mainContext, "Data inserted successfully...", Toast.LENGTH_LONG).show();
                    }
                    db.close();
                }
                catch(Exception e1)
                {
                    // Log.d("TAG11", "inside catch block because exception occured while creating table,Unable to create table.");
                    //Toast.makeText(mainContext, "Error creating table", Toast.LENGTH_LONG).show();
                    // Log.d("TAG11",  "Selected item already exists for " + str);
                    Toast.makeText(mainContext, "Error Inserting Data.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private boolean fnInsert2(String tableName, String strType, Uri uri, ArrayList<Parcelable> listUri)
    {
        //Log.d("TAG11", "inside fninsert2, calling fncheck..");
        //Log.d("TAG11", uri.toString());
        boolean flag = false;
        if(listUri != null)
        {
            for (Parcelable p: listUri)
            {
                Uri u = (Uri) p;
                flag = fn(tableName, strType, u);
                //Log.d("TAG11", "uri to check:" + u.toString());

            }
        }
        else
        {
            // Log.d("TAG11", "listUri null, donot know about uri");
            if(uri != null)
            {
                //Log.d("TAG11", "listUri null, uri not null");
                flag = fn(tableName, strType, uri);
            }
        }
        return flag;
    }

    private boolean fn(String tableName,String strType, Uri u)
    {   //Log.d("TAG11", "fn called, received uri:" + u.toString());
        boolean check = fnCheck(tableName, u);

        if(!check)
        {
            //Log.d("TAG11", "fncheck returned false, file doesnt exist");
            //Log.d("TAG11", "inside if of fninsert2 because fncheck returned false ie data doesnt exist,data will be inserted");
            try
            {
                //Log.d("TAG11", "inside try of if of fninsert2,inserting data into table");
                db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put(COL_PATH, u.toString());

                String parts[] = strType.split("/");
                String Type = parts[0];

                cv.put(COL_TYPE, Type);
                Log.d("TAG15",Type);

                db.insert(tableName, null, cv);
                //Log.d("TAG11", "inside try of if of fninsert2,data inserted.., returning true");
                db.close();
                return true;
                //flag = true;
            }
            catch (Exception e)
            {
                //Log.d("TAG11", "inside catch of if of fninsert2 because data couldnot be inserted, returning false");
                return  false;
                //flag = false;
            }

        }
        else
        {
            // Log.d("TAG11", "fncheck returned false, data exists");
            //Log.d("TAG11", "inside else of fninsert2 because fncheck returned true,data exists..");
            return false;
            //flag = false;
        }
    }

    private boolean fnCheck(String tableName, Uri path)
    {
        // Log.d("TAG11", "fncheck called, received uri:" + path.toString());
        Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT * FROM "+tableName, null );
        if(myCursor != null && myCursor.moveToFirst())
        {
//            Log.d("TAG11", myCursor.getString(myCursor.getColumnIndex(COL_PATH)));
            Log.d("TAG11", "inside if of fncheck because cursor not returned null.starting loop..");
            do {
                //Log.d("TAG11", "contents of table -" + tableName + " are: " + myCursor.getString(myCursor.getColumnIndex(COL_PATH)));
                Log.d("TAG11", "id:" + myCursor.getString(myCursor.getColumnIndex(COL_ID)) + "\n path:" + myCursor.getString(myCursor.getColumnIndex(COL_PATH)) + "\n type:" + myCursor.getString(myCursor.getColumnIndex(COL_TYPE)) + "\n");
                if(path.toString().equals(myCursor.getString(myCursor.getColumnIndex(COL_PATH))))
                {
                    Log.d("TAG11", "uri match, returning true");
                    return  true;

                }
            }while (myCursor.moveToNext());
            //Log.d("TAG11", "uri didnt match, loop completed, returning false");
            return  false;
        }
        else
        {
            //Log.d("TAG11", "inside fncheck,cursor null while checking.");
            return  false;
        }
    }

//    public ArrayList<String> extraction(String t)
//    {
//        String app = DisplayData.FGApp;
//        app = app.replace(".","_");
//        app = TABLENAME_PREFIX + app;
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<String> arr = new ArrayList<String>();
//        //arr = extract_multiple("images");
//        Cursor c1 = db.rawQuery("SELECT * FROM " +app+" WHERE TYPE = "+t,null);
//        c1.moveToFirst();
//        while(c1.isAfterLast()==false)
//        {
//            String s = c1.getString(c1.getColumnIndex("TYPE"));
//            if(s.equals("text") )
//            {
//                arr.add(c1.getString(c1.getColumnIndex("PATH")));
//            }
//            else if(s.equals("images")  )
//            {
//                arr.add(c1.getString(c1.getColumnIndex("PATH")));
//            }
//            else if(s.equals("video"))
//            {
//                arr.add(c1.getString(c1.getColumnIndex("PATH")));
//            }
//            else if(s.equals("*"))
//            {
//                if(s.indexOf("images") > 0 )
//                {
//                    arr.add(c1.getString(c1.getColumnIndex("PATH")));
//                }
//                else if(s.indexOf("video")>0) {
//                    arr.add(c1.getString(c1.getColumnIndex("PATH")));
//                }
//            }
//
//            else
//            {
//                arr.add(c1.getString(c1.getColumnIndex("PATH")));
//            }
//            c1.moveToNext();
//
//        }
//        return arr;
//    }

    public ArrayList<String> extraction_image()
    {
        Log.d("TAB1","inside extraction image function");
        String app = DisplayData.FGApp;
        Log.d("TAB1",app);
        app = app.replace(".","_");
        app = TABLENAME_PREFIX + app;
        Log.d("TAB1",app);
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arr = new ArrayList<String>();
        //arr = extract_multiple("images");
        Cursor c1 = db.rawQuery("SELECT * FROM " +app+ " WHERE TYPE = 'image' ",null);
        c1.moveToFirst();
        while(c1.isAfterLast()==false)
        {
            Log.d("TAG12",c1.getString(c1.getColumnIndex("PATH")));
            arr.add(c1.getString(c1.getColumnIndex("PATH")));
            c1.moveToNext();
        }
        ArrayList<String> arr1 = new ArrayList<String>();
        arr1 = extract_multiple("images");
        for(int i=0;i<arr1.size();i++)
        {
            arr.add(arr1.get(i));
        }
        c1.close();
        db.close();
        return arr;

    }


    public ArrayList<String> extraction_video()
    {
        String app = DisplayData.FGApp;
        app = app.replace(".","_");
        app = TABLENAME_PREFIX + app;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM " +app+ " WHERE TYPE = 'video' ",null);
        c1.moveToFirst();
        ArrayList<String> arr = new ArrayList<String>();
        while(c1.isAfterLast()==false)
        {
            Log.d("TAG12",c1.getString(c1.getColumnIndex("PATH")));
            arr.add(c1.getString(c1.getColumnIndex("PATH")));
            c1.moveToNext();
        }
        ArrayList<String> arr1 = new ArrayList<String>();
        arr1 = extract_multiple("video");
        for(int i=0;i<arr1.size();i++)
        {
            arr.add(arr1.get(i));
        }

        c1.close();
        db.close();
        return arr;

    }

    public ArrayList<String> extraction_text()
    {
        String app = DisplayData.FGApp;
        app = app.replace(".","_");
        app = TABLENAME_PREFIX + app;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arr = new ArrayList<>();
        //arr = extract_multiple("text");
        Cursor c1 = db.rawQuery("SELECT * FROM " +app+ " WHERE TYPE = 'text' ",null);
        c1.moveToFirst();
        while(!c1.isAfterLast())
        {
            arr.add(c1.getString(c1.getColumnIndex("PATH")));
            c1.moveToNext();
        }
        // Log.d("TAG14",arr.get(0));
        c1.close();
        db.close();
        return arr;
    }

    public ArrayList<String> extraction_others()
    {
        String app = DisplayData.FGApp;
        app = app.replace(".","_");
        app = TABLENAME_PREFIX + app;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arr = new ArrayList<>();
        //arr = extract_multiple("text");
        Cursor c1 = db.rawQuery("SELECT * FROM " +app+ " WHERE TYPE = 'application' ",null);
        c1.moveToFirst();
        while(!c1.isAfterLast())
        {
            arr.add(c1.getString(c1.getColumnIndex("PATH")));
            c1.moveToNext();
        }
        c1.close();
        db.close();

        return arr;
    }

    public ArrayList<String> extract_multiple(String t)
    {
        String app = DisplayData.FGApp;
        app = app.replace(".","_");
        app = TABLENAME_PREFIX + app;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM " +app+ " WHERE TYPE = '*' ",null);
        Log.d("TAG18",Integer.toString(c1.getCount()));
        c1.moveToFirst();
        ArrayList<String> arr = new ArrayList<String>();
        if(t.equals("video"))
        {
            while(!c1.isAfterLast())
            {

                if(c1.getString(c1.getColumnIndex("PATH")).indexOf("video") > 0)
                {
                    Log.d("TAG18","video " + c1.getString(c1.getColumnIndex("PATH")));
                    arr.add(c1.getString(c1.getColumnIndex("PATH")));
                }
                c1.moveToNext();
            }
        }
        else if(t.equals("images"))
        {
            while(!c1.isAfterLast())
            {
                // Log.d("TAG18"," " + c1.getString(c1.getColumnIndex("PATH")));

                if(c1.getString(c1.getColumnIndex("PATH")).indexOf("images") > 0)
                {
                    Log.d("TAG18","images " + c1.getString(c1.getColumnIndex("PATH")));
                    arr.add(c1.getString(c1.getColumnIndex("PATH")));

                }
                c1.moveToNext();
            }
        }

        c1.close();
        db.close();
        return arr;
    }

    public boolean fnCheckTable(String packageName)
    {

        boolean tableExists = false;
        /* get cursor on it */
        try
        {
            packageName = packageName.replace(".", "_");
//        db = this.getReadableDatabase();
            String tableName = TABLENAME_PREFIX + packageName;
            // Log.d("TAG11", tableName);

            Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM "+tableName, null );
            if(c != null)
            {
                c.close();
                tableExists = true;
            }

        }
        catch (Exception e) {
            tableExists = false;
            //  Log.d("TAG11", " doesn't exist :(((");
        }

        return tableExists;
    }
}