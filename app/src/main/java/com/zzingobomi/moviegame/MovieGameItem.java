package com.zzingobomi.moviegame;

/**
 * Created by JongChan on 2017-02-13.
 * MovieGame 의 한 챕터를 나타냅니다. 여기서 챕터란 유저가 선택 이후 다음 선택을 하기까지의 영상
 * 즉 하나의 동영상 파일을 나타냅니다.
 */

public class MovieGameItem
{
    private int                         mFileIndex;
    private String                      mFileName;
    private boolean                     mStartOfStory;
    private boolean                     mEndOfStory;
    private GlobalData.BUTTON_TYPE      mBtnType;
    private float                       mButtonTime;
    private String[]                    mNextfileNames;

    public MovieGameItem(int mFileIndex, String mFileName, boolean mStartOfStory, boolean mEndOfStory, GlobalData.BUTTON_TYPE mBtnType, float mButtonTime,
                         String nextFile1, String nextFile2, String nextFile3, String nextFile4 )
    {
        this.mFileIndex = mFileIndex;
        this.mFileName = mFileName;
        this.mStartOfStory = mStartOfStory;
        this.mEndOfStory = mEndOfStory;
        this.mBtnType = mBtnType;
        this.mButtonTime = mButtonTime;

        this.mNextfileNames = new String[4];
        this.mNextfileNames[0] = nextFile1;
        this.mNextfileNames[1] = nextFile2;
        this.mNextfileNames[2] = nextFile3;
        this.mNextfileNames[3] = nextFile4;
    }

    public int getFileIndex()
    {
        return mFileIndex;
    }
    public void setFileIndex(int mFileIndex)
    {
        this.mFileIndex = mFileIndex;
    }

    public String getFileName()
    {
        return mFileName;
    }
    public void setFileName(String mFileName)
    {
        this.mFileName = mFileName;
    }

    public boolean isStartOfStory()
    {
        return mStartOfStory;
    }
    public void setStartOfStory(boolean mStartOfStory)
    {
        this.mStartOfStory = mStartOfStory;
    }

    public boolean isEndOfStory()
    {
        return mEndOfStory;
    }
    public void setEndOfStory(boolean mEndOfStory)
    {
        this.mEndOfStory = mEndOfStory;
    }

    public GlobalData.BUTTON_TYPE getBtnType()
    {
        return mBtnType;
    }
    public void setBtnType(GlobalData.BUTTON_TYPE mBtnType)
    {
        this.mBtnType = mBtnType;
    }

    public float getButtonTime()
    {
        return mButtonTime;
    }
    public void setButtonTime(float mButtonTime)
    {
        this.mButtonTime = mButtonTime;
    }



    /*
    public String[] getNextfileNames()
    {
        return mNextfileNames;
    }
    public void setNextfileNames(String[] mNextfileNames)
    {
        this.mNextfileNames = mNextfileNames;
    }
    */
}
