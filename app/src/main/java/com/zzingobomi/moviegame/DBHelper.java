package com.zzingobomi.moviegame;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JongChan on 2017-02-14.
 */

public class DBHelper extends SQLiteOpenHelper
{
    Context             mContext;
    SQLiteDatabase      sqLiteDatabase;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            boolean bResult = isCheckDB(mContext);
            if(!bResult)
                copyDB(mContext);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    public void getResult(String filename)
    {
        sqLiteDatabase = getReadableDatabase();
        String result = "";

        String querySQL = "SELECT nextfile1 FROM " + GlobalData.dbName + " where filename=" + filename;
        Cursor cursor = sqLiteDatabase.rawQuery(querySQL, null);
        while (cursor.moveToNext())
        {
            result = cursor.getString(3);
        }
    }

    // DB 가 있나 체크하기
    private boolean isCheckDB(Context context)
    {
        String filePath = "/data/data/" + context.getPackageName() + "/databases/" + GlobalData.dbName;
        File file = new File(filePath);
        if(file.exists())
        {
            return true;
        }

        return false;
    }

    // DB 복사하기
    private void copyDB(Context context)
    {
        AssetManager manager = context.getAssets();
        String folderPath = "/data/data/" + context.getPackageName() + "/databases/";
        String filePath = "/data/data/" + context.getPackageName() + "/databases/" + GlobalData.dbName;
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try
        {
            InputStream is = manager.open("db/" + GlobalData.dbName);
            BufferedInputStream bis = new BufferedInputStream(is);
            if(folder.exists()) { }
            else
                folder.mkdirs();

            if(file.exists())
            {
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while((read = bis.read(buffer, 0, 1024)) != -1)
            {
                bos.write(buffer, 0, read);
            }

            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
