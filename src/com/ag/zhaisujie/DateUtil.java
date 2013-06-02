package com.ag.zhaisujie;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat  sdf=new SimpleDateFormat("M月d日 HH:mm");
	private static SimpleDateFormat  sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
	/**
	 * Datatime 时间转Date
	 * */
    public static Date toDateByDT(String dateTime){
    	try{
    	    return sdf1.parse(dateTime);
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }

	/**
	 * Date 转XX月XX xx:xx 日 格式
	 * */
    public static String formatDateToMDHM(Date dateTime){
    	try{
    	    return sdf.format(dateTime);
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }
}
