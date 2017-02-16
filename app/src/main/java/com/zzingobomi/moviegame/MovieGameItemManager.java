package com.zzingobomi.moviegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Base64;
import android.widget.VideoView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JongChan on 2017-02-15.
 */

public class MovieGameItemManager
{
    Context mContext = null;
    VideoView mVideoView = null;

    public MovieGameItemManager(Context aContext, VideoView aVideoView)
    {
        mContext = aContext;
        mVideoView = aVideoView;
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
                setVideoUriAutu( getNextMovieGameItem() );
            }
        });

        /* 어떻게 해당 position 에 위치했을때 함수를 호출할 것인가?
         * Update 문 없나..
         */
        //mVideoView.getCurrentPosition();

        // 시작 영상 보여주기
        setVideoUriAutu( getFirstMovieGameItem() );
    }

    // 첫번째 재생할 MovieGameItem 가져오기
    private String getFirstMovieGameItem()
    {
        // TODO: getFirstMovieGameItem

        String firstMovieGameItem = "Chapter_01";
        return firstMovieGameItem;
    }

    // 다음에 재생할 영상 가져오기
    private String getNextMovieGameItem()
    {
        // TODO: getNextMovieGameItem



        String nextMovieGameItem = "Chapter_02";
        return nextMovieGameItem;
    }

    // VideoView 에 URI 설정하기
    public void setVideoUriAutu(String nextFileName)
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
