package com.zzingobomi.moviegame;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity
{
    ///
    /// 비디오 관련
    ///
    VideoView               mVideoView;
    MediaController         mMediaController;
    MovieGameItemManager    mMovieGameItemManager;


    ///
    /// DB 관련
    ///
    DBManager               mDbManager;

    ///
    /// 시간 관련
    ///
    TimerUpdateManager      mTimerUpdateManager;

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

        // DB 에서 정보 가져오기 (가장 첫번째)
        DBConnect();

        // 비디오 뷰 포커스 요청
        mVideoView.requestFocus();

        // 무비게임 매니저 초기화 (두번째)
        InitMovieGameManager();

        // 타이머 Update 초기화 (세번째)
        InitTimerUpdateManager();

        // TestCode
        FrameLayout frameLayout = (FrameLayout)this.findViewById(R.id.framelayout_main);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout li = (LinearLayout)inflater.inflate(R.layout.buttontype_h_2, null);
        frameLayout.addView(li);
    }


    private void DBConnect()
    {
        mDbManager = new DBManager(getApplicationContext());

        //String nextfile1 = mDbManager.getNextfileFromFilename("Chapter_01", 1);
        //String nextfile4 = mDbManager.getNextfileFromFilename("Chapter_01", 4);
        //mDbManager.isEndofStoryFile("Chapter_04");
    }

    private void InitMovieGameManager()
    {
        mMovieGameItemManager = new MovieGameItemManager(getApplicationContext(), mVideoView, mDbManager);
        mMovieGameItemManager.initMovieGameManager();
    }

    private void InitTimerUpdateManager()
    {
        mTimerUpdateManager = new TimerUpdateManager(getApplicationContext(), mMovieGameItemManager);
    }


    ///
    /// 네트워크 관련
    ///
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
