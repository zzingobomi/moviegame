package com.zzingobomi.moviegame;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


/**
 * Created by JongChan on 2017-02-16.
 * Android Game Programming 구조를 조금 본다음에 수정..?
 */

public class TimerUpdateManager
{
    Context                 mContext                = null;
    Handler                 mHandler                = null;
    MovieGameItemManager    mMovieGameItemManager    = null;


    public TimerUpdateManager(Context aContext, MovieGameItemManager aMovieGameItemManager)
    {
        mContext = aContext;
        mMovieGameItemManager = aMovieGameItemManager;

        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);

                mMovieGameItemManager.update();

                mHandler.sendEmptyMessageDelayed(0, GlobalData.delayTime);
            }
        };

        mHandler.sendEmptyMessageDelayed(0, GlobalData.delayTime);
    }


}
