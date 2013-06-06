package com.ag.zhaisujie;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat  sdf=new SimpleDateFormat("M��d�� HH:mm");
	private static SimpleDateFormat  sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat  sdf2=new SimpleDateFormat("yyyy-MM-dd");
	 public static final long M_PER_DAY = 1000 * 60 * 60 * 24;
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

	/**
	 * Date yyyy-mm-dd ���ڸ�ʽ
	 * */
    public static Date formatDate(String dateStr) {
    	try{
    	    return sdf2.parse(dateStr);
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }
    public static int getDaysBetween(Date preDate, Date nextDate) {
        return (int) ((nextDate.getTime() - preDate.getTime()) / M_PER_DAY);
    }
}
