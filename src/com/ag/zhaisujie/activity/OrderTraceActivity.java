package com.ag.zhaisujie.activity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.kobjects.base64.Base64;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ag.zhaisujie.App;
import com.ag.zhaisujie.HttpUtil;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.ToastUtil;
import com.ag.zhaisujie.model.Order;
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
	private Button serviceBtn;
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
	private Order order;
	
	private LinearLayout norderLayout;
	private RelativeLayout orderLayout;
	private RelativeLayout companyLayout;
	private RelativeLayout peopleLayout;
	private RelativeLayout completeLayout;
	
	private LinearLayout contactCompanyLinear;
	private LinearLayout peopleLinear;
	private LinearLayout completeLinear;
	private Bitmap  waitorPhoto;
	
	private final int INIT_ORDER=0;//初始化
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_trace);

		Bundle bundle = this.getIntent().getExtras();

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.order_trace);
		backBtn = (Button) findViewById(R.id.title_btn_setting);
		serviceBtn= (Button) findViewById(R.id.title_btn_order);
		backBtn.setText(R.string.order_btn);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		
		serviceBtn.setText(R.string.setting);
		serviceBtn.setVisibility(View.VISIBLE);
		serviceBtn.setOnClickListener(listener);

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
		waiterImg =(ImageView) findViewById(R.id.people_View);
		
		norderLayout=(LinearLayout) findViewById(R.id.no_order);
		orderLayout=(RelativeLayout) findViewById(R.id.add_order_layout);
		companyLayout=(RelativeLayout) findViewById(R.id.company_order_layout);
		peopleLayout=(RelativeLayout) findViewById(R.id.people_order_layout);
		completeLayout=(RelativeLayout) findViewById(R.id.complete_order_layout);
		contactCompanyLinear=(LinearLayout) findViewById(R.id.contact_home_Linear);
		peopleLinear=(LinearLayout) findViewById(R.id.people_Linear);
		completeLinear=(LinearLayout) findViewById(R.id.complete_Linear);
		
		order_success_time.setText(commitTime);
		company_name.setText(companyName);
		company_phone.setText(companyPhone);
		match_time.setText(matchTime);
		waiter_name.setText(waiterName);
		waiter_phone.setText(waiterPhone);
		waiter_time.setText(serviceTime);
		service_done_time.setText(serviceDoneTime);
		//初始化
		Message msg = new Message();
		msg.what=INIT_ORDER;
		handler.sendMessage(msg);
	}
	
	private void getOrderDetial(){
		try{
			Map<String ,Object> orderMap=new HashMap<String ,Object>();
			orderMap.put("uid", App.getInstance().getUser().getUid());
			orderMap.put("username", App.getInstance().getUser().getUserName());
			String rtn=HttpUtil.getInfoFromServer(HttpUtil.URL_WEBSERVICE_GET_ORDER_DETAIL, orderMap).toString();
			if(App.FAIL.equals(rtn)){
				ToastUtil.show(this, "没有订单！");
				this.finish();
			}else{
				JSONTokener jsonParser = new JSONTokener(rtn);
				JSONObject job= (JSONObject)jsonParser.nextValue();
				if("null".equals(job.getString("task_id"))||job.getString("task_id").trim().length()==0){
					ToastUtil.show(this, "没有订单！");
					norderLayout.setVisibility(View.VISIBLE);
				}else{//处理显示
					orderLayout.setVisibility(View.VISIBLE);
					companyLayout.setVisibility(View.VISIBLE);
					peopleLayout.setVisibility(View.VISIBLE);
					completeLayout.setVisibility(View.VISIBLE);
					
					order=new Order();
					order.setTaskId(job.getString("task_id"));
					order.setOrderNumber(job.getString("ordernumber"));
					order_success_time.setText(job.getString("created"));
					
					if(!job.isNull("bids")){
						JSONObject bids=job.getJSONObject("bids");
						if(bids!=null){
							contactCompanyLinear.setVisibility(View.VISIBLE);
							contactHomeCompanyBtn.setVisibility(View.VISIBLE);
							
							company_name.setText(bids.getString("department"));
							companyPhone=bids.getString("mobile");
							company_phone.setText(companyPhone);
							match_time.setText(bids.getString("created"));
						}
						if(!bids.isNull("ayi")){
							JSONObject ayi=bids.getJSONObject("ayi");
							if(ayi!=null){
								peopleLinear.setVisibility(View.VISIBLE);
								contactWaiterBtn.setVisibility(View.VISIBLE);
								waiter_name.setText(ayi.getString("name"));
								waiterPhone=ayi.getString("mobile");
								waiter_phone.setText(waiterPhone);
								//waiter_time.setText(ayi.getString("created"));
								//设置图片
								try{
									InputStream  tmpStream= getStringStream(Base64.decode(ayi.getString("photo").replaceAll(" ", "+")));
									waitorPhoto= BitmapFactory.decodeStream(tmpStream); 
									waiterImg.setImageDrawable(new BitmapDrawable(waitorPhoto)); 
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}
						}
						
					}
					//处理时间
					JSONArray proes=job.getJSONArray("process");
					
					for(int i=0;i<proes.length();i++){
						JSONObject tmpObj=proes.getJSONObject(i);
						if(tmpObj.getInt("step")==1){
							order_success_time.setText(tmpObj.getString("step_date"));
						}
						if(tmpObj.getInt("step")==2){
							match_time.setText(tmpObj.getString("step_date"));
						}
						if(tmpObj.getInt("step")==3){
							waiter_time.setText(tmpObj.getString("step_date"));
						}
						if(tmpObj.getInt("step")==4){
							completeLinear.setVisibility(View.VISIBLE);
							service_done_time.setText(tmpObj.getString("step_date"));
						}
					}
					
					
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.finish();
		}
	}

	public InputStream getStringStream(byte[] bytes) {
		ByteArrayInputStream tInputStringStream = null;
		if (bytes != null) {
			tInputStringStream = new ByteArrayInputStream(bytes);
		}
		return tInputStringStream;
	}
	private void sendCancel(){
		try{
			Map<String ,Object> orderMap=new HashMap<String ,Object>();
			orderMap.put("uid", App.getInstance().getUser().getUid());
			orderMap.put("username", App.getInstance().getUser().getUserName());
			orderMap.put("task_id", order.getTaskId());
			String rtn=HttpUtil.getInfoFromServer(HttpUtil.URL_WEBSERVICE_CANCEL_ORDER, orderMap).toString();
			if(App.FAIL.equals(rtn)){
				ToastUtil.show(this, "取消订单失败！");
			}else{
				ToastUtil.show(this, "取消订单成功！");
				myExit();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			ToastUtil.show(this, "取消订单失败！");
		}
	}
   protected void myExit() {  
        Intent intent = new Intent();  
        intent.setAction("ExitApp");  
        this.sendBroadcast(intent);  
        super.finish();  
    }  
   
   Handler handler =  new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			 switch (msg.what) {  
	           case INIT_ORDER:  
	        	   getOrderDetial();
	               break; 
	           }  
		}

	};
   
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_setting:
				myExit();
				break;
			case R.id.order_cancel:
				sendCancel();
				
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
				Intent intent2 = new Intent(OrderTraceActivity.this, OrderSendActivity.class);
				intent2.putExtras(getIntent());
				startActivity(intent2);
				break;
			case R.id.title_btn_order:
				Intent intent3 = new Intent(OrderTraceActivity.this, ServiceActivity.class);
				startActivity(intent3);
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
