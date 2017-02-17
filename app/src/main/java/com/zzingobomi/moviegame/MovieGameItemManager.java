package com.zzingobomi.moviegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.VideoView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JongChan on 2017-02-15.
 */

public class MovieGameItemManager
{
    Context         mContext        = null;
    VideoView       mVideoView      = null;
    DBManager       mDbManager      = null;

    MovieGameItem   mCurMovieGameItem   = null;

    public MovieGameItemManager(Context aContext, VideoView aVideoView, DBManager aDbManager)
    {
        mContext = aContext;
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
                playVideo();
            }
        });

        // 동영상 재생이 완료되었을 때
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                //setVideoUriAutu( getNextMovieGameItem() );
            }
        });

        // 시작 영상 보여주기
        makeMovieGameItemAndSetUriAuth( getFirstMovieGameItem() );
    }

    public void update()
    {
        if(mVideoView != null && mVideoView.isPlaying())
        {
            Log.i("TIME", "Update Playing");
        }
    }

    // 첫번째 재생할 MovieGameItem 가져오기
    private String getFirstMovieGameItem()
    {
        return mDbManager.getStartofStoryFileName();
    }

    // 다음에 재생할 영상 가져오기
    private String getNextMovieGameItem()
    {
        // TODO: getNextMovieGameItem



        String nextMovieGameItem = "Chapter_02";
        return nextMovieGameItem;
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
}
