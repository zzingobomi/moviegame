package com.zzingobomi.moviegame;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        // 네트워크 타입 체크
        CheckNetwork();
    }

    private void StartGame()
    {
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

            // 버튼의 배경과 글씨를 없앤다.
            if(!GlobalData.debugMode)
            {
                btn1.setText("");
                btn1.setBackgroundColor(0x00000000);
                btn2.setText("");
                btn2.setBackgroundColor(0x00000000);
            }
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

            // 버튼의 배경과 글씨를 없앤다.
            if(!GlobalData.debugMode)
            {
                btn1.setText("");
                btn1.setBackgroundColor(0x00000000);
                btn2.setText("");
                btn2.setBackgroundColor(0x00000000);
                btn3.setText("");
                btn3.setBackgroundColor(0x00000000);
            }
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

            // 버튼의 배경과 글씨를 없앤다.
            if(!GlobalData.debugMode)
            {
                btn1.setText("");
                btn1.setBackgroundColor(0x00000000);
                btn2.setText("");
                btn2.setBackgroundColor(0x00000000);
                btn3.setText("");
                btn3.setBackgroundColor(0x00000000);
                btn4.setText("");
                btn4.setBackgroundColor(0x00000000);
            }
        }
        else if(btnType == GlobalData.BUTTON_TYPE.BT_C_4)
        {
            //LinearLayout li = (LinearLayout)mMainLayoutInflater.inflate(R.layout.buttontype_C_4, null);
        }
        else
        {
            Log.e("addButtonLayout", "addButtonLayout type error");
        }
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
    // 네트워크 타입에 따라 와이파이 유도
    private void CheckNetwork()
    {
        //  1. 네트워크에 연결되어 있는가
        boolean bConnectNetwork = isConnected();
        if(bConnectNetwork)
        {
            // 2. 모바일인지 WI-Fi 인지
            int iNetType = getNetworkType();
            if(iNetType == ConnectivityManager.TYPE_WIFI)
            {
                // 정상적인 게임 시작
                StartGame();
            }
            else
            {
                // 3. WI-Fi 접속 유도 팝업 띄우기
                openFirstWiFiConnectPopup();
            }
        }
        else
        {
            // 네트워크에 연결되어 있지 않다고 확인창 띄워준후 종료
            openNoNetworkConnectPopup();
        }
    }

    private void openNoNetworkConnectPopup()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                GameEnd();
            }
        });
        alert.setMessage(R.string.noconnect_networkpopup_content);
        alert.show();
    }
    private void openFirstWiFiConnectPopup()
    {
        AlertDialog.Builder firstPopup = new AlertDialog.Builder(this);
        firstPopup.setMessage(R.string.first_networkconfirm_content).setCancelable(false).setPositiveButton(R.string.confirm_button,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 두번째 팝업창 띄워주기
                        dialog.dismiss();
                        openSecondWiFiConnectPopup();
                    }
                }).setNegativeButton(R.string.cancel_button,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        GameEnd();
                    }
                });
        AlertDialog alert = firstPopup.create();
        alert.show();
    }
    private void openSecondWiFiConnectPopup()
    {
        AlertDialog.Builder secondPopup = new AlertDialog.Builder(this);
        secondPopup.setMessage(R.string.second_networkconfirm_content).setCancelable(false).setPositiveButton(R.string.confirm_button,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 게임 시작
                        dialog.dismiss();
                        StartGame();
                    }
                }).setNegativeButton(R.string.cancel_button,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        GameEnd();
                    }
                });
        AlertDialog alert = secondPopup.create();
        alert.show();
    }

    // 에뮬레이터인지 실제 단말인지 구분 (안드로이드의 신 책 참조)
    private boolean isRealPhone()
    {
        return false;
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
