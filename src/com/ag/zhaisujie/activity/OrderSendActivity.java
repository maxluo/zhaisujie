package com.ag.zhaisujie.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ag.zhaisujie.App;
import com.ag.zhaisujie.HttpUtil;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.model.Order;
import com.ag.zhaisujie.utils.CustomGifView;
import com.ag.zhaisujie.utils.ThreadPoolUtils;

/**
 * OrderSendActivity.java
 * 
 * @author max.Luo
 * @email max_null@sina.com 2013-5-25
 */
public class OrderSendActivity extends BaseActivity {

	private Button backBtn;
	private CustomGifView progressImg;
	private TextView resultInfo;
	private TextView warnInfo;
	private Order order;
    private static final int ADD_ORDER_SUCCESS = 0;
    private static final int ADD_ORDER_FAIL = 1; 
    private static final int PAY_SEND = 2;
    private static final int PAY_SEND_SUCCESS = 3;
    private static final int PAY_SEND_FAIL = 4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_send);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		TextView title = (TextView) findViewById(R.id.title);
		//title.setText(R.string.order_send);

		progressImg = (CustomGifView) findViewById(R.id.order_send_process);
		progressImg.setResource(R.drawable.loading);
		resultInfo = (TextView) findViewById(R.id.order_result_info);
		warnInfo = (TextView) findViewById(R.id.order_warn_info);

		Bundle bundle = this.getIntent().getExtras();
		order = (Order) bundle.getSerializable("Order");
		
		ThreadPoolUtils.execute(run);
	}

	      
	Handler handler =  new Handler() {

		@Override
		public void handleMessage(Message msg) {
			/*Intent intent = new Intent(OrderSendActivity.this,
					OrderTraceActivity.class);
			intent.putExtras(getIntent());
			intent.putExtra("ActivityClass", OrderSendActivity.class.getCanonicalName());
			startActivity(intent);
			OrderSendActivity.this.finish();*/
			
			 switch (msg.what) {  
	           case ADD_ORDER_SUCCESS:  
	        	   resultInfo.setText(R.string.order_send_success);
	        	   warnInfo.setVisibility(View.VISIBLE);
	        	   
	               break; 
	           case ADD_ORDER_FAIL:
	        	   resultInfo.setText(R.string.order_send_fail);
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           case  PAY_SEND:
	        	   resultInfo.setText(R.string.pay_send);
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           case  PAY_SEND_SUCCESS:
	        	   resultInfo.setText(R.string.pay_send_success);
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           case  PAY_SEND_FAIL:
	        	   resultInfo.setText(R.string.pay_send_fail);
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           }  
		}

	};
	Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
				try{
					Map<String ,Object> orderMap=new HashMap<String ,Object>();
					orderMap.put("uid", App.getInstance().getUser().getUid());
					orderMap.put("username", App.getInstance().getUser().getUserName());
					orderMap.put("orderjson", order.toJson());
					String rtn=HttpUtil.getInfoFromServer(HttpUtil.URL_WEBSERVICE_SET_ORDER, orderMap).toString();
					Message msg = new Message();
					if(App.FAIL.equals(rtn)){
						msg.what=ADD_ORDER_FAIL;
					}else{
						msg.what=ADD_ORDER_SUCCESS;
						JSONTokener jsonParser = new JSONTokener(rtn);
						JSONObject job= (JSONObject)jsonParser.nextValue();
						order.setTaskId(job.getString("task_id"));
						order.setOrderNumber(job.getString("ordernumber"));//price
					}
					handler.sendMessage(msg);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	};

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				OrderSendActivity.this.finish();
				break;
			}
		}
	};
}
