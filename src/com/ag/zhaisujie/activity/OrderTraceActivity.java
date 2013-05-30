package com.ag.zhaisujie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ag.zhaisujie.R;
import com.ag.zhaisujie.utils.SimpleFuncUtils;

/**
 * 订单追踪
 * 
 * @author max.Luo
 * @email max_null@sina.com
 * 
 */
@SuppressWarnings("all")
public class OrderTraceActivity extends BaseActivity {
	private Button backBtn;

	private String commitTime = "2012-09-30 15:30";// 提交成功时间

	private String companyName = "爱君家政接单";// 家政公司名称
	private String companyPhone = "电话：10086";// 家政公司电话
	private String matchTime = "2012-09-30 15:30";// 匹配时间

	private ImageView waiterImg;// 服务人员头像
	private String waiterName = "路陈刚";// 服务人员名称
	private String waiterPhone = "手机: 10086";// 服务人员电话
	private String serviceTime = "2012-09-30 15:30";// 服务时间

	private String serviceDoneTime = "2012-09-30 15:30";// 服务完成时间

	private Button orderCancelBtn;
	private Button contactHomeCompanyBtn;
	private Button contactWaiterBtn;
	private Button confirmBtn;

	private TextView order_success_time;
	private TextView company_name;
	private TextView company_phone;
	private TextView match_time;
	private TextView waiter_name;
	private TextView waiter_phone;
	private TextView waiter_time;
	private TextView service_done_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_trace);

		Bundle bundle = this.getIntent().getExtras();

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.order_trace);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setText(R.string.order_btn);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);

		orderCancelBtn = (Button) findViewById(R.id.order_cancel);
		orderCancelBtn.setOnClickListener(listener);

		contactHomeCompanyBtn = (Button) findViewById(R.id.contact_home_company);
		contactHomeCompanyBtn.setOnClickListener(listener);

		contactWaiterBtn = (Button) findViewById(R.id.contact_waiter);
		contactWaiterBtn.setOnClickListener(listener);

		confirmBtn = (Button) findViewById(R.id.confirm_done);
		confirmBtn.setOnClickListener(listener);

		order_success_time = (TextView) findViewById(R.id.order_success_time);
		company_name = (TextView) findViewById(R.id.company_name);
		company_phone = (TextView) findViewById(R.id.company_phone);
		match_time = (TextView) findViewById(R.id.match_time);
		waiter_name = (TextView) findViewById(R.id.waiter_name);
		waiter_phone = (TextView) findViewById(R.id.waiter_phone);
		waiter_time = (TextView) findViewById(R.id.waiter_time);
		service_done_time = (TextView) findViewById(R.id.service_done_time);

		order_success_time.setText(commitTime);
		company_name.setText(companyName);
		company_phone.setText(companyPhone);
		match_time.setText(matchTime);
		waiter_name.setText(waiterName);
		waiter_phone.setText(waiterPhone);
		waiter_time.setText(serviceTime);
		service_done_time.setText(serviceDoneTime);
		
		String from = getIntent().getStringExtra("ActivityClass");
    	if(from == null || from.equals(OrderSendActivity.class.getCanonicalName())) {
    		backBtn.setVisibility(View.GONE);
    	}

	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				
				Intent intent = new Intent(OrderTraceActivity.this, OrderFrstActivity.class);
				intent.putExtras(getIntent());
				startActivity(intent);
				
				break;
			case R.id.order_cancel:
				break;
			case R.id.contact_home_company:
				SimpleFuncUtils.startPhoneIntent(OrderTraceActivity.this,
						getPhoneNum(companyPhone));
				break;
			case R.id.contact_waiter:
				SimpleFuncUtils.startPhoneIntent(OrderTraceActivity.this,
						getPhoneNum(waiterPhone));
				break;
			case R.id.confirm_done:
				break;
			}
		}

		private String getPhoneNum(String phoneNum) {
			if (!phoneNum.contains(":")) {
				return phoneNum.trim();
			}

			return phoneNum.split(":")[1].trim();
		}
	};

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

	    switch (keyCode) {
	        case KeyEvent.KEYCODE_BACK:
	        	//判断来源Activity
	        	String from = getIntent().getStringExtra("ActivityClass");
	        	if(from == null || from.equals(OrderSendActivity.class.getCanonicalName())) {
	        		return true;
	        	}
	    }
	    return super.onKeyDown(keyCode, event);
	}*/
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		if(item.getItemId()==R.id.action_exit){
			finish();
		}
		return true;
	}*/
}
