package com.zzingobomi.moviegame;


import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity
{
    VideoView               mVideoView;
    MediaController         mMediaController;

    DBManager               mDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃 위젯
        mVideoView = (VideoView)this.findViewById(R.id.videoview);

        // 미디어 컨트롤러 추가
        mMediaController = new MediaController(MainActivity.this);
        mVideoView.setMediaController(mMediaController);

        // DB 에서 정보 가져오기
        DBConnect();

        // 비디오 뷰 포커스 요청
        mVideoView.requestFocus();

        // 비디오 뷰 URI 설정
        setVideoUriAutu();



        //동영상이 재생준비가 완료되었을때 호출되는 리스너
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                //Toast.makeText(getApplicationContext(), "onPrepared", Toast.LENGTH_SHORT).show();
                playVideo();
            }
        });

        // 동영상 재생이 완료되었을 때
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                //Toast.makeText(getApplicationContext(), "onCompletion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playVideo()
    {
        mVideoView.seekTo(0);
        mVideoView.start();
    }

    private void stopVideo()
    {
        mVideoView.pause();
    }

    private void setVideoUriAutu()

    {
        Uri uri = Uri.parse(GlobalData.schema + "://" +
                GlobalData.ip + ":" +
                GlobalData.port + "/" +
                GlobalData.dir + "/" +
                GlobalData.fileName);
        try
        {
            // http id, pw 인증
            Method setVideoURIMetod = mVideoView.getClass().getMethod("setVideoURI", Uri.class, Map.class);
            Map<String, String> header = new HashMap<String, String>(1);
            final String cred = GlobalData.id+ ":" + GlobalData.pw;
            final String auth = "Basic " + Base64.encodeToString(cred.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP);
            header.put("Authorization", auth);

            // VideoView 에 Uri 설정
            setVideoURIMetod.invoke(mVideoView, uri, header);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void DBConnect()
    {
        mDbManager = new DBManager(getApplicationContext());

        String nextfile1 = mDbManager.getNextfileFromFilename("Chapter_01", 1);
        if(nextfile1 != null)
        {
            Log.d("MG", nextfile1);
        }
        String nextfile4 = mDbManager.getNextfileFromFilename("Chapter_01", 4);
        if(nextfile4 == null)
        {
            Log.d("MG", "NULL");
        }
    }


    // 네트워크에 연결되어 있는 상태인가
    private boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

        return isConnected;
    }

    // 어떤 네트워크 타입인가
    private int getNetworkType()
    {
        /*
        ConnectivityManager.TYPE_MOBILE             // 3G or 4G
        ConnectivityManager.TYPE_WIFI               // Wi-Fi
        */

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork.getType();
    }
}
