package com.ag.zhaisujie.activity;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ag.zhaisujie.R;
import com.ag.zhaisujie.ToastUtil;
import com.ag.zhaisujie.ValidUtil;
import com.ag.zhaisujie.dateview.DateTimeDialog;
import com.ag.zhaisujie.dateview.DateTimeDialog.OnDateTimeSetListener;
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
    
    private static final int SHOW_DATAPICK = 0;
    private Order order;
    
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

		orderBtn=(Button)findViewById(R.id.order_btn_next);
		orderBtn.setOnClickListener(listener);
		dateTxt.setOnClickListener(txtListener);
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
         mYear = c.get(Calendar.YEAR); 
         mMonth = c.get(Calendar.MONTH)+1; 
         mDay = c.get(Calendar.DAY_OF_MONTH);
         mHour = c.get(Calendar.HOUR_OF_DAY);
         mMinute = c.get(Calendar.MINUTE);
         
         if(mHour>=9&&mHour<13){
	         c.add(Calendar.HOUR_OF_DAY, 6);
	         mHour=c.get(Calendar.HOUR_OF_DAY);
         }
         if(mHour>13&&mHour<17){
        	 mHour=9+(6-(18-mHour));
        	 c.add(Calendar.DAY_OF_MONTH, 1);
        	 mDay = c.get(Calendar.DAY_OF_MONTH);
         }
         if(mHour>=17&&mHour<9){
        	 mHour=9+6;
        	 c.add(Calendar.DAY_OF_MONTH, 1);
        	 mDay = c.get(Calendar.DAY_OF_MONTH);
         }
         if(mMinute>0&&mMinute<=30){
        	 mMinute=30;
         }else{
        	 mMinute=0;
         }
	}
	
	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay(int year, int month, int day,int hour, int minute){
		dateTxt.setText(new StringBuilder().append(year).append("-")
    		   .append((month) < 10 ? "0" + (month) : (month)).append("-")
               .append((day < 10) ? "0" + day : day)
               .append(" ")
               .append((hour < 10) ? "0" + hour : hour)
               .append(":")
               .append((minute < 10) ? "0" + minute : minute)); 
	}
	
 
    /** 
     * 处理日期和时间控件的Handler 
     */  
    Handler dateandtimeHandler = new Handler() {
  
       @Override  
       public void handleMessage(Message msg) {  
           switch (msg.what) {  
           case SHOW_DATAPICK:  
        	   DateTimeDialog mdt=new DateTimeDialog(OrderFrstActivity.this,mYear,mMonth,mDay,mHour,mMinute, new OnDateTimeSetListener() {
					
					@Override
					public void onDateTimeSet(int year, int month, int day,int hour, int minute) {
						updateDateDisplay(year,month,day,hour,minute);	
					}
				});
				
				mdt.show();

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
		String dateTime=dateTxt.getText().toString();
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
				case R.id.date_txt_input:
					Message msgi = new Message();
					msgi.what = SHOW_DATAPICK; 
	                dateandtimeHandler.sendMessage(msgi);
	                break;
				case R.id.order_btn_next:
					nextPage();
					break;
			}
		}
	};
	
	OnClickListener txtListener = new OnClickListener() {
		public void onClick(View v) {
			TextView btn = (TextView) v;
			switch (btn.getId()) {
				case R.id.date_txt_input:
					Message msgi = new Message();
					msgi.what = SHOW_DATAPICK; 
	                dateandtimeHandler.sendMessage(msgi);
	                break;
			}
		}
	};


}
