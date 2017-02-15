package com.zzingobomi.moviegame;

import android.content.Context;

/**
 * Created by JongChan on 2017-02-15.
 */

public class MovieGameItemManager
{
    Context mContext = null;

    public MovieGameItemManager(Context aContext)
    {
        mContext = aContext;
    }







    // 첫번째 재생할 MovieGameItem 가져오기
    private String getFirstMovieGameItem()
    {
        String firstMovieGameItem = "Chapter_01";
        return firstMovieGameItem;
    }
}
