package com.zzingobomi.moviegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.widget.VideoView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JongChan on 2017-02-15.
 */

public class MovieGameItemManager
{
    private Context         mContext        = null;
    private MainActivity    mMainActivity   = null;

    private VideoView       mVideoView      = null;
    private DBManager       mDbManager      = null;

    private MovieGameItem   mCurMovieGameItem   = null;

    private String          mUserSelectNextfileName = null;

    public MovieGameItemManager(Context aContext, MainActivity aActivity, VideoView aVideoView, DBManager aDbManager)
    {
        mContext = aContext;
        mMainActivity = aActivity;
        mVideoView = aVideoView;
        mDbManager = aDbManager;
    }

    public void initMovieGameManager()
    {
        //동영상이 재생준비가 완료되었을때 호출되는 리스너
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                onPreparedVideo();
            }
        });

        // 동영상 재생이 완료되었을 때
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                onCompleteVideo();
            }
        });

        // 시작 영상 보여주기
        makeMovieGameItemAndSetUriAuth( getFirstMovieGameItem() );
    }

    private void onPreparedVideo()
    {
        mUserSelectNextfileName = null;

        // 비디오 재생하기
        playVideo();
    }

    private void onCompleteVideo()
    {
        // 1. 마지막 영상인가?
        if(mCurMovieGameItem.isEndOfStory())
        {
            gameEndManager();
        }

        // 2. 유저가 선택한 버튼(영상) 확인 ( 만약 없다면? )
        if(mUserSelectNextfileName == null)
        {
            // TODO: 우선은 첫번째 영상으로.. 정책을 정해야 할듯..
            mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 1);
        }

        // 3. 기존 UI 삭제
        mMainActivity.removeButtonLayout();

        // 4. 다음 재생 영상 셋팅하기
        makeMovieGameItemAndSetUriAuth( mUserSelectNextfileName );
    }

    public void update()
    {
        if(mVideoView != null && mVideoView.isPlaying())
        {
            // UI 만들 시간이 지났는지 확인 (UI 만들시간이 지났고 아직 UI가 없다면)
            if( mCurMovieGameItem != null && (mVideoView.getCurrentPosition() > mCurMovieGameItem.getButtonTime() * 1000) &&!mCurMovieGameItem.getExgistButtonUi())
            {
                mMainActivity.addButtonLayout(mCurMovieGameItem.getBtnType());
                mCurMovieGameItem.setExgistButtonUi(true);
            }
        }
    }

    public void onClickButton(View v)
    {
        switch (v.getId())
        {
            // ButtonType_H_2
            case R.id.button_h_2_1:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 1);
                break;
            case R.id.button_h_2_2:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 2);
                break;

            // ButtonType_H_3
            case R.id.button_h_3_1:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 1);
                break;
            case R.id.button_h_3_2:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 2);
                break;
            case R.id.button_h_3_3:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 3);
                break;

            // ButtonType_H_4
            case R.id.button_h_4_1:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 1);
                break;
            case R.id.button_h_4_2:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 2);
                break;
            case R.id.button_h_4_3:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 3);
                break;
            case R.id.button_h_4_4:
                mUserSelectNextfileName = getNextMovieGameItem(mCurMovieGameItem.getFileName(), 4);
                break;

            default:
                break;
        }
    }

    // 첫번째 재생할 MovieGameItem 가져오기
    private String getFirstMovieGameItem()
    {
        return mDbManager.getStartofStoryFileName();
    }

    // 다음에 재생할 영상 가져오기
    private String getNextMovieGameItem(String curFilename, int iNextIndex)
    {
        return mDbManager.getNextfileFromFilename(curFilename, iNextIndex);
    }

    private void makeMovieGameItemAndSetUriAuth(String nextFileName)
    {
        // MovieGameItme 만들기
        MovieGameItem tempMovieGameItem = new MovieGameItem(
                mDbManager.getFileindexFromFilename(nextFileName),
                nextFileName,
                mDbManager.isStartofStoryFile(nextFileName),
                mDbManager.isEndofStoryFile(nextFileName),
                mDbManager.getButtonType(nextFileName),
                mDbManager.getButtonTime(nextFileName),
                mDbManager.getNextfileFromFilename(nextFileName, 1),
                mDbManager.getNextfileFromFilename(nextFileName, 2),
                mDbManager.getNextfileFromFilename(nextFileName, 3),
                mDbManager.getNextfileFromFilename(nextFileName, 4)
                );

        mCurMovieGameItem = tempMovieGameItem;

        // VideoView 에 URI 설정하기
        setVideoUriAuth(nextFileName);
    }

    // VideoView 에 URI 설정하기
    public void setVideoUriAuth(String nextFileName)
    {
        Uri uri = Uri.parse(GlobalData.schema + "://" +
                GlobalData.ip + ":" +
                GlobalData.port + "/" +
                GlobalData.dir + "/" +
                nextFileName +
                GlobalData.fileExten);
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

    private void playVideo()
    {
        mVideoView.seekTo(0);
        mVideoView.start();
    }

    private void stopVideo()
    {
        mVideoView.pause();
    }

    private void gameEndManager()
    {
        // 자원 정리
        // TODO:

        // 앱 종료
        mMainActivity.GameEnd();
    }
}
