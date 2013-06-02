package com.ag.zhaisujie;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat  sdf=new SimpleDateFormat("M��d�� HH:mm");
	private static SimpleDateFormat  sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
	/**
	 * Datatime ʱ��תDate
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
	 * Date תXX��XX xx:xx �� ��ʽ
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
