package com.fb.oauthsystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGeter {
    public static String getTimeString(String matcher){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(matcher);
        return format.format(date);
    }

    public static String getTimeString(){
        return getTimeString("yyyy-MM-dd hh:mm:ss");
    }

    public static long getTimeStamp(){
        return new Date().getTime();
    }

    public static long getTimeStampInSecond(){
        return getTimeStamp()/1000;
    }


}
