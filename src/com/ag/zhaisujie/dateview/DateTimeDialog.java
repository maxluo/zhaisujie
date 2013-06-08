package com.ag.zhaisujie.dateview;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ag.zhaisujie.R;

/**
 *    DateTimeDialog.java
 *     <p>
 *     自定义日期时间选择
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class DateTimeDialog extends AlertDialog implements
		OnClickListener{
	private final OnDateTimeSetListener mCallBack;
	private final Calendar mCalendar;
	final WheelView wv_month, wv_hours, wv_mins;
	private final int mYear;
	public DateTimeDialog(Context context,final int mYear,final int mMonth,final int mDay,int mHour,int mMin,OnDateTimeSetListener callBack) {
		super(context);
		this.mYear=mYear;
		mCallBack = callBack;
		mCalendar=Calendar.getInstance();
		mCalendar.set(mYear, mMonth, mDay, mHour, mMin);
		
	    setButton(context.getText(R.string.ok),this);
	    setButton2(context.getText(R.string.cancle), (OnClickListener) null);
		setIcon(R.drawable.ic_launcher);
		setTitle("日期时间选择");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);
      
		int textSize = 0;
		textSize = adjustFontSize(getWindow().getWindowManager()); 
		String[] arrayOfString1=new String[7];
		for(int i=0;i<7;i++){
			arrayOfString1[i]=mCalendar.get(Calendar.MONTH) +"月"+mCalendar.get(Calendar.DAY_OF_MONTH)+"日"+"  周"+mCalendar.get(Calendar.DAY_OF_WEEK);
			mCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		// 月
		wv_month = (WheelView) view.findViewById(R.id.month_day);
		wv_month.setAdapter(new ArrayWheelAdapter(arrayOfString1,arrayOfString1.length));
		wv_month.setCyclic(false);
		wv_month.setCurrentItem(0);

		// 时
		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(mHour);
		String[] arrayOfString3 = { "00", "30"};
		// 分
		wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setAdapter(new ArrayWheelAdapter(arrayOfString3,arrayOfString3.length));
		wv_mins.setCyclic(false);
		wv_mins.setCurrentItem(mMin);
		
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		setView(view);
	}
	public void onClick(DialogInterface dialog, int which) {

		int curr_year = 2013;
		int curr_month = wv_month.getCurrentItem() + 1;
		int curr_day=1;
		int curr_hour = wv_hours.getCurrentItem();
		int curr_minute = wv_mins.getCurrentItem();
		if (mCallBack != null) {
			mCallBack.onDateTimeSet(curr_year, curr_month, curr_day, curr_hour,
					curr_minute);
		}
	}
	
	public void show() {
	        super.show();
	}
	public  interface OnDateTimeSetListener {
			void onDateTimeSet(int year, int monthOfYear, int dayOfMonth, int hour,
					int minute);
	}
	public static int adjustFontSize(WindowManager windowmanager) {

		 int screenWidth = windowmanager.getDefaultDisplay().getWidth();
	     int screenHeight = windowmanager.getDefaultDisplay().getHeight();
	     /*  DisplayMetrics dm = new DisplayMetrics();
	      dm = windowmanager.getApplicationContext().getResources().getDisplayMetrics();
	     int widthPixels = dm.widthPixels;
	     int heightPixels = dm.heightPixels;
	     float density = dm.density;
	     fullScreenWidth = (int)(widthPixels * density);
	     fullScreenHeight = (int)(heightPixels * density);*/
		if (screenWidth <= 240) { // 240X320 屏幕
			return 10;
		} else if (screenWidth <= 320) { // 320X480 屏幕
			return 14;
		} else if (screenWidth <= 480) { // 480X800 或 480X854 屏幕
			return 24;
		} else if (screenWidth <= 540) { // 540X960 屏幕
			return 26;
		} else if (screenWidth <= 800) { // 800X1280 屏幕
			return 30;
		} else { // 大于 800X1280
			return 30;
		}
	}
}

