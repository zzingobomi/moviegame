package com.zzingobomi.moviegame;

/**
 * Created by JongChan on 2017-02-13.
 * MovieGame 의 한 챕터를 나타냅니다. 여기서 챕터란 유저가 선택 이후 다음 선택을 하기까지의 영상
 * 즉 하나의 동영상 파일을 나타냅니다.
 */

public class MovieGameItem
{
    private String mFileName;



    public String getFileName()
    {
        return mFileName;
    }
    public void setFileName(String mFileName)
    {
        this.mFileName = mFileName;
    }
}
