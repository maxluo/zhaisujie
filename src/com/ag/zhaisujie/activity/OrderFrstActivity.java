package com.ag.zhaisujie.activity;

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ag.zhaisujie.R;
import com.ag.zhaisujie.ToastUtil;
import com.ag.zhaisujie.ValidUtil;
import com.ag.zhaisujie.model.Order;

/**
 *    OrderFrstActivity.java
 *     <p>
 *     订单第一个页面
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class OrderFrstActivity extends BaseActivity {
	private Button backBtn;
	private TextView titleTxt;
	private EditText addrTxt;
	private Button dateBtn;
	private Button orderBtn;
	private EditText dateTxt;
	private RadioButton time2Btn;
	private RadioButton time3Btn;
	private RadioButton time4Btn;
	private int mYear; 
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    
    private Button timeBtn;
	private EditText timeTxt;

    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1; 
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;
    private Order order;
    
    private Date minDate;
    private Date maxDate;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_frst);
		backBtn=(Button)findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		titleTxt=(TextView)findViewById(R.id.title);
		titleTxt.setText(R.string.order_frst_title);
		addrTxt=(EditText)findViewById(R.id.order_txt_addr);
		dateBtn=(Button)findViewById(R.id.date_btn_input);
		dateBtn.setOnClickListener(listener);
		dateTxt=(EditText)findViewById(R.id.date_txt_input);
		timeBtn=(Button)findViewById(R.id.time_btn_input);
		timeBtn.setOnClickListener(listener);
		timeTxt=(EditText)findViewById(R.id.time_txt_input);
		
		orderBtn=(Button)findViewById(R.id.order_btn_next);
		orderBtn.setOnClickListener(listener);
		time2Btn=(RadioButton)findViewById(R.id.time_btn_2);
		time3Btn=(RadioButton)findViewById(R.id.time_btn_3);
		time4Btn=(RadioButton)findViewById(R.id.time_btn_4);
		
		//设置地址
		order=(Order)getIntent().getSerializableExtra("Order");
		String addr=order.getAddress();
		addrTxt.setText(addr);
		//设置焦点
		CharSequence text = addrTxt.getText();
		if (text instanceof Spannable) {
		    Spannable spanText = (Spannable)text;
		     Selection.setSelection(spanText, text.length());
		}
		
		//时间初始
		
		 final Calendar c = Calendar.getInstance();
		 minDate=c.getTime();
         mYear = c.get(Calendar.YEAR); 
         mMonth = c.get(Calendar.MONTH); 
         mDay = c.get(Calendar.DAY_OF_MONTH);
         mHour = c.get(Calendar.HOUR_OF_DAY);
         mMinute = c.get(Calendar.MINUTE);
         c.add(Calendar.DAY_OF_MONTH, 6);
         maxDate=c.getTime();
	}

    /**
     * 设置日期
     */
	private void setDateTime(){
       final Calendar c = Calendar.getInstance();  
       
       mYear = c.get(Calendar.YEAR);  
       mMonth = c.get(Calendar.MONTH);  
       mDay = c.get(Calendar.DAY_OF_MONTH); 
  
       updateDateDisplay(); 
	}
	
	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay(){
		dateTxt.setText(new StringBuilder().append(mYear).append("-")
    		   .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
               .append((mDay < 10) ? "0" + mDay : mDay)); 
	}
	
    /** 
     * 日期控件的事件 
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
	 * 设置时间
	 */
	private void setTimeOfDay(){
	   final Calendar c = Calendar.getInstance(); 
       mHour = c.get(Calendar.HOUR_OF_DAY);
       mMinute = c.get(Calendar.MINUTE);
       updateTimeDisplay();
	}
	
	/**
	 * 更新时间显示
	 */
	private void updateTimeDisplay(){
		timeTxt.setText(new StringBuilder().append(mHour).append(":")
               .append((mMinute < 10) ? "0" + mMinute : mMinute)); 
	}
    
    /**
     * 时间控件事件
     */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			if (minute > 0 && minute < 30) {
				view.setCurrentMinute(30);
			} else {
				view.setCurrentMinute(0);
				view.setCurrentHour(hourOfDay+1);
			}
			updateTimeDisplay();
		}
	};
    
    @SuppressLint("NewApi")
	@Override  
    protected Dialog onCreateDialog(int id) {  
       switch (id) {  
       case DATE_DIALOG_ID:  
    	    DatePickerDialog d = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    	    DatePicker dp = d.getDatePicker();
    	    dp.setMaxDate(maxDate.getTime());
    	    dp.setMinDate(minDate.getTime());
    	    return d;
       case TIME_DIALOG_ID:
    	   TimePickerDialog t=new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
    	   return t;
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
     * 处理日期和时间控件的Handler 
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
    //下面页面
	private void nextPage(){
		if(addrTxt.getText().toString().trim().length()==0){
			ToastUtil.show(this, "服务地址不能为空！");
			return;
		}else if(!(time2Btn.isChecked()||time3Btn.isChecked()||time4Btn.isChecked())){
			ToastUtil.show(this, "请选择服务时长！");
			return;
		}else if(dateTxt.getText().toString().trim().length()==0){
			ToastUtil.show(this, "请选择服务日期！");
			return;
		}else if(!ValidUtil.isDate(dateTxt.getText().toString())){
			ToastUtil.show(this, "请选择输入正确日期：yyyy-mm-dd");
			return;
		}else if(timeTxt.getText().toString().trim().length()==0){
			ToastUtil.show(this, "请选择服务时间！");
			return;
		}else if (ValidUtil.validTime(timeTxt.getText().toString())!=null){
			ToastUtil.show(this, ValidUtil.validTime(timeTxt.getText().toString()));
			return;
		}
		int timeLong=0;
		int money=0;
		if(time2Btn.isChecked()){
			timeLong=2;
			money=60;
		}else if(time3Btn.isChecked()){
			timeLong=3;
			money=90;
		}else if(time4Btn.isChecked()){
			timeLong=4;
			money=120;
		}
		String dateTime=dateTxt.getText().toString()+" "+timeTxt.getText().toString();
		order.setClean_hours(timeLong);
		order.setPrice(money);
		order.setBegin_time(dateTime);
		order.setAddress(addrTxt.getText().toString().trim());
		Intent intent = new Intent(OrderFrstActivity.this, ContactDetailActivity.class);
		Bundle bundle = new Bundle();  
		bundle.putSerializable("Order", order);
		intent.putExtras(bundle);//传递地址到下一页面
		OrderFrstActivity.this.startActivity(intent);
		
	}
	
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					OrderFrstActivity.this.finish();
					break;
				case R.id.date_btn_input:
					Message msgd = new Message();
					msgd.what = SHOW_DATAPICK; 
	                dateandtimeHandler.sendMessage(msgd);
	                break;
				case R.id.time_btn_input:   
	                Message msgt = new Message();
	                msgt.what = SHOW_TIMEPICK; 
	                dateandtimeHandler.sendMessage(msgt);
					break;
				case R.id.order_btn_next:
					nextPage();
					break;
			}
		}
	};

}
