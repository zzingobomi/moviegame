package com.zzingobomi.moviegame;

/**
 * Created by JongChan on 2017-02-13.
 * MovieGame 의 모든 전역 설정 및 변수들을 여기에 선언합니다.
 * 1. 동영상 FullScreen 재생
 *android:theme="@android:style/Theme.NoTitleBar.Fullscreen
 * style/AppTheme
 */

/**
 * 화면에 보여주는 방식은 처음에는 MainActivity 에서 하나의 VideoView 로 보여주자.
 * 후에 가능하다면 더블 퍼버링 방식으로 FragmentActivity 를 이용해서 번갈아가며 보여주기 방식
 * 으로 개선 가능하다면 개선
 */

 /**
 * NextFile 방식은 LocalData 방식
 * SQLite 를 이용해서 DB 에다가 정보를 저장하고 여기서 읽어오는게 좋을듯 ( SQLIte 를 좀 연구 ) DB 구조 짜야함
  * Index, FileName, BtnType, Next1, Next2, Next3, Next4
 */

/**
 * 버튼 레이아웃은 여러가지 버튼의 유형을 미리 만든 후 각 챕터마다 버튼 유형을 선택?
 * 약간 어설픈 면은 있으나 이게 가장 현실적이면서 가능한 구조일듯..
 * 가로로 '2', '3', '4', '+' 이렇게 4개의 유형을 만든다
 */

public class GlobalData
{
    public static final String schema   = "http";
    public static final String ip       = "59.16.152.204";
    public static final String port     = "5005";
    public static final String id       = "testvideo";
    public static final String pw       = "info3775";
    public static final String dir      = "testvideo";
    public static final String fileName = "testvideo2.mp4";


    //public static final String dbName   = "moviegame.db";
    //public static final String tableName = "moviegame_item";
}
