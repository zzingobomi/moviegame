package com.zzingobomi.moviegame;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by happy on 2017-02-14.
 */

public class DBManager
{
    private final String    DB_NAME         = "moviegame.db";
    private final String    DB_TABLE_NAME   = "moviegame_item";
    private final int       DB_VERSION      = 1;

    private Context         mContext        = null;
    private OpenHelper      mOpener         = null;
    private SQLiteDatabase  mDbController   = null;

    private class OpenHelper extends SQLiteOpenHelper
    {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase aDb)
        {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
        }
    }

    public DBManager(Context aContext)
    {
        this.mContext = aContext;
        this.mOpener = new OpenHelper(mContext, DB_NAME, null, DB_VERSION);

        try
        {
            boolean bResult = isCheckDB(mContext);
            if(!bResult)
            {
                copyDB(mContext);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    // DB 가 있나 체크하기
    private boolean isCheckDB(Context context)
    {
        String filePath = "/data/data/" + context.getPackageName() + "/databases/" + DB_NAME;
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
        String filePath = "/data/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try
        {
            InputStream is = manager.open("db/" + DB_NAME, AssetManager.ACCESS_BUFFER);
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

/*
    public void dbOpen()
    {
        this.mDbController = mOpener.getWritableDatabase();
    }

    public void dbClose()
    {
        this.mDbController.close();
    }

    public void insertData(String aSql, ContactData aCData)
    {
        String[] sqlData = aCData.getCDataArray();
        this.mDbController.execSQL(aSql, sqlData);
    }

    public void deleteData(String aSql, ContactData aCData)
    {
        String[] sqlData = {aCData.getPhoneNumber()};
        this.mDbController.execSQL(aSql, sqlData);
    }

    public void selectAll(String aSql, ArrayList<ContactData> aCDataList)
    {
        Cursor results = this.mDbController.rawQuery(aSql, null);
        results.moveToFirst();
        while (!results.isAfterLast())
        {
            ContactData cData = new ContactData(
                    results.getString(1),
                    results.getString(2),
                    results.getString(3));
            aCDataList.add(cData);
            results.moveToNext();
        }
        results.close();
    }
    */
}
