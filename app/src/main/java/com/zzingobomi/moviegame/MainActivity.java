package com.zzingobomi.moviegame;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity implements View.OnClickListener
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

    ///
    /// 버튼 레이아웃 관련
    ///
    FrameLayout             mMainFrameLayout;
    LayoutInflater          mMainLayoutInflater;
    LinearLayout            mCurButtonLyaout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃 위젯
        mVideoView = (VideoView)this.findViewById(R.id.videoview);
        mMainFrameLayout = (FrameLayout)this.findViewById(R.id.framelayout_main);
        mMainLayoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        mMovieGameItemManager = new MovieGameItemManager(getApplicationContext(), this, mVideoView, mDbManager);
        mMovieGameItemManager.initMovieGameManager();
    }

    private void InitTimerUpdateManager()
    {
        mTimerUpdateManager = new TimerUpdateManager(getApplicationContext(), mMovieGameItemManager);
    }


    public void addButtonLayout(GlobalData.BUTTON_TYPE btnType)
    {
        if(btnType == GlobalData.BUTTON_TYPE.BT_H_2)
        {
            mCurButtonLyaout = (LinearLayout)mMainLayoutInflater.inflate(R.layout.buttontype_h_2, null);
            mMainFrameLayout.addView(mCurButtonLyaout);

            Button btn1 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_2_1);
            btn1.setOnClickListener(this);
            Button btn2 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_2_2);
            btn2.setOnClickListener(this);
        }
        else if(btnType == GlobalData.BUTTON_TYPE.BT_H_3)
        {
            mCurButtonLyaout = (LinearLayout)mMainLayoutInflater.inflate(R.layout.buttontype_h_3, null);
            mMainFrameLayout.addView(mCurButtonLyaout);

            Button btn1 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_3_1);
            btn1.setOnClickListener(this);
            Button btn2 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_3_2);
            btn2.setOnClickListener(this);
            Button btn3 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_3_3);
            btn3.setOnClickListener(this);
        }
        else if(btnType == GlobalData.BUTTON_TYPE.BT_H_4)
        {
            mCurButtonLyaout = (LinearLayout)mMainLayoutInflater.inflate(R.layout.buttontype_h_4, null);
            mMainFrameLayout.addView(mCurButtonLyaout);

            Button btn1 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_4_1);
            btn1.setOnClickListener(this);
            Button btn2 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_4_2);
            btn2.setOnClickListener(this);
            Button btn3 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_4_3);
            btn3.setOnClickListener(this);
            Button btn4 = (Button)mCurButtonLyaout.findViewById(R.id.button_h_4_4);
            btn4.setOnClickListener(this);
        }
        else if(btnType == GlobalData.BUTTON_TYPE.BT_C_4)
        {
            //LinearLayout li = (LinearLayout)mMainLayoutInflater.inflate(R.layout.buttontype_C_4, null);
        }
        else
        {
            Log.e("addButtonLayout", "addButtonLayout type error");
        }

        // TestCode
        //FrameLayout frameLayout = (FrameLayout)this.findViewById(R.id.framelayout_main);
        //LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout li = (LinearLayout)inflater.inflate(R.layout.buttontype_h_2, null);
        //frameLayout.addView(li);

        //Toast.makeText(getApplicationContext(), "Make Button UI Time~~~", Toast.LENGTH_LONG).show();
    }
    public void removeButtonLayout()
    {
        mMainFrameLayout.removeView(mCurButtonLyaout);
    }

    @Override
    public void onClick(View v)
    {
        mMovieGameItemManager.onClickButton(v);
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

    ///
    /// 게임이 끝날을 때
    ///
    public void GameEnd()
    {
        // 자원 정리
        // TODO:

        finish();
    }
}
