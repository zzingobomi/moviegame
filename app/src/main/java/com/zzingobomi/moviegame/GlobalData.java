package com.zzingobomi.moviegame;

/**
 * Created by JongChan on 2017-02-13.
 * MovieGame 의 모든 전역 설정 및 변수들을 여기에 선언합니다.
 */

/**
 * 핸드폰 전원 꺼짐 관련
 * PowerManager(이건 알림에서 쓰는거 같고..)
 * 유저 입력이 없더라도 화면 끄지 않기 Manifest Permission FLAG_KEEP_SCREEN_ON, WAKE_LOCK
 * VideoView 재생중에는 꺼지지 않는거 같음.. 좀 더 긴 영상으로 실험 필요 but 일시 정지 중에는 꺼짐
 */

/**
 * 핸드폰 꺼질때 크래시 남 - 마지막 영상이면 return 함으로서 해결.
 * 종료시 자원 정리는 어떻게 할 것인가. (안드로이드 게임 프로그래밍 참고?)
 */

/**
 * 중간에 게임을 Hide 시키거나 전화가 왔을때, 게임을 껐을때는 어떻게 처리할 것인가.
 * 이것도 SharedPrefabs 를 이용해서 처리?
 * 지금은 백그라운드로 내렸다가 다시 올라왔을때 Chapter_01 부터 시작하는거 같음
 */

/**
 * 다시 게임을 할때는 어떻게 처리할 것인가. SharedPrefabs 를 이용해서 처리할 것인가.
 * 즉 아예 유저가 한번 플레이를 했을 경우는 어떻게 처리?
 */

/**
 * 모바일 데이터일 경우 경고 팝업 2번 띄워주기
 *
 */

/**
 * 화면에 보여주는 방식은 처음에는 MainActivity 에서 하나의 VideoView 로 보여주자.
 * 후에 가능하다면 더블 퍼버링 방식으로 FragmentActivity 를 이용해서 번갈아가며 보여주기 방식
 * 으로 개선 가능하다면 개선
 */

/**
 * 버튼 레이아웃은 여러가지 버튼의 유형을 미리 만든 후 각 챕터마다 버튼 유형을 선택?
 * 약간 어설픈 면은 있으나 이게 가장 현실적이면서 가능한 구조일듯..
 * 가로로 '2', '3', '4', '+' 이렇게 4개의 유형을 만든다
 */

public class GlobalData
{
    ///
    /// 접속 관련
    ///
    public static final String schema           = "http";
    public static final String ip               = "59.16.152.204";
    public static final String port             = "5005";
    public static final String id               = "testvideo";
    public static final String pw               = "info3775";
    public static final String dir              = "testvideo";
    public static final String fileExten        = ".mp4";
    //public static final String fileName       = "testvideo2.mp4";

    ///
    /// DB 관련
    ///
    public static final int fileIndexColumn     = 0;
    public static final int fileNameColumn      = 1;
    public static final int startofStoryColumn  = 2;
    public static final int endofStoryColumn    = 3;
    public static final int btnTypeColumn       = 4;
    public static final int buttonTimeColumn    = 5;
    public static final int nextFileColumn      = 5;

    ///
    /// Time Handler
    ///
    public static final int delayTime           = 200;      // 0.2초마다 한번

    ///
    /// 버튼 타입 ( H - horizon, V - vertical, C - Cross )
    ///
    public enum BUTTON_TYPE
    {
        NONE,
        BT_H_2,
        BT_H_3,
        BT_H_4,
        BT_C_4,
        MAX
    }
}
