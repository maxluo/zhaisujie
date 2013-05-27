package com.ag.zhaisujie.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ag.zhaisujie.R;

/**
 *    OrderFrstActivity.java
 *     <p>
 *     ������һ��ҳ��
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class OrderFrstActivity extends Activity {
	private Button backBtn;
	private TextView titleTxt;
	private TextView addrTxt;
	private Button timeBtn;
	private EditText timeTxt;
	private int mYear; 
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1; 
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_frst);
		backBtn=(Button)findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		titleTxt=(TextView)findViewById(R.id.title);
		titleTxt.setText(R.string.order_frst_title);
		addrTxt=(TextView)findViewById(R.id.order_txt_addr);
		timeBtn=(Button)findViewById(R.id.time_btn_input);
		timeBtn.setOnClickListener(listener);
		timeTxt=(EditText)findViewById(R.id.time_txt_input);
		//���õ�ַ
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		String addr=bundle.getString("Addr");
		addrTxt.setText(addr);
		//ʱ���ʼ
		
		 final Calendar c = Calendar.getInstance();
         mYear = c.get(Calendar.YEAR); 
         mMonth = c.get(Calendar.MONTH); 
         mDay = c.get(Calendar.DAY_OF_MONTH);
         mHour = c.get(Calendar.HOUR_OF_DAY);
         mMinute = c.get(Calendar.MINUTE);
          
         setDateTime();

	}

    /**
     * ��������
     */
	private void setDateTime(){
       final Calendar c = Calendar.getInstance();  
       
       mYear = c.get(Calendar.YEAR);  
       mMonth = c.get(Calendar.MONTH);  
       mDay = c.get(Calendar.DAY_OF_MONTH); 
  
       updateDateDisplay(); 
	}
	
	/**
	 * ����������ʾ
	 */
	private void updateDateDisplay(){
		timeTxt.setText(new StringBuilder().append(mYear).append("-")
    		   .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
               .append((mDay < 10) ? "0" + mDay : mDay)); 
		onCreateDialog(TIME_DIALOG_ID);//��ʾʱ��
	}
	
    /** 
     * ���ڿؼ����¼� 
     */  
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
  
       public void onDateSet(DatePicker view, int year, int monthOfYear,  
              int dayOfMonth) {  
           mYear = year;  
           mMonth = monthOfYear;  
           mDay = dayOfMonth;  

           updateDateDisplay();
       }  
    }; 
	
	/**
	 * ����ʱ��
	 */
	private void setTimeOfDay(){
	   final Calendar c = Calendar.getInstance(); 
       mHour = c.get(Calendar.HOUR_OF_DAY);
       mMinute = c.get(Calendar.MINUTE);
       updateTimeDisplay();
	}
	
	/**
	 * ����ʱ����ʾ
	 */
	private void updateTimeDisplay(){
		timeTxt.setText(new StringBuilder().append(timeTxt.getText().toString()).append(" ").append(mHour).append(":")
               .append((mMinute < 10) ? "0" + mMinute : mMinute)); 
	}
    
    /**
     * ʱ��ؼ��¼�
     */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			
			updateTimeDisplay();
		}
	};
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
       switch (id) {  
       case DATE_DIALOG_ID:  
           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
                  mDay);
       case TIME_DIALOG_ID:
    	   return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
       }
    	   
       return null;  
    }  
  
    @Override  
    protected void onPrepareDialog(int id, Dialog dialog) {  
       switch (id) {  
       case DATE_DIALOG_ID:  
           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
           break;
       case TIME_DIALOG_ID:
    	   ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
    	   break;
       }
    }  
  
    /** 
     * �������ں�ʱ��ؼ���Handler 
     */  
    Handler dateandtimeHandler = new Handler() {
  
       @Override  
       public void handleMessage(Message msg) {  
           switch (msg.what) {  
           case SHOW_DATAPICK:  
               showDialog(DATE_DIALOG_ID);  
               break; 
           case SHOW_TIMEPICK:
        	   showDialog(TIME_DIALOG_ID);
        	   break;
           }  
       }  
  
    }; 

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					OrderFrstActivity.this.finish();
					break;
				case R.id.time_btn_input:
					//onCreateDialog(DATE_DIALOG_ID);
					Message msg = new Message();
	                msg.what = SHOW_DATAPICK; 
	                dateandtimeHandler.sendMessage(msg);
					break;
				
			}
		}
	};

}
