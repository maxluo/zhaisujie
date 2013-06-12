package com.ag.zhaisujie.activity;

import java.net.URLEncoder;
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
import com.ag.zhaisujie.pay.MobileSecurePayHelper;
import com.ag.zhaisujie.pay.MobileSecurePayer;
import com.ag.zhaisujie.pay.PartnerConfig;
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
    public static final int PAY_INSTALL = 5;
    
    private MobileSecurePayHelper msPayHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_send);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.pay_send);

		progressImg = (CustomGifView) findViewById(R.id.order_send_process);
		progressImg.setResource(R.drawable.loading);
		resultInfo = (TextView) findViewById(R.id.order_result_info);
		warnInfo = (TextView) findViewById(R.id.order_warn_info);

		Bundle bundle = this.getIntent().getExtras();
		order = (Order) bundle.getSerializable("Order");
		msPayHelper=new MobileSecurePayHelper(this,handler);
		if (!msPayHelper.detectService()) {
            msPayHelper.downloadAliMSP();
            return;
	    }else{
	    	resultInfo.setText(R.string.order_send);
	    	ThreadPoolUtils.execute(run);
	    }
	}
	      
	Handler handler =  new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			 switch (msg.what) {  
	           case ADD_ORDER_SUCCESS:  
	        	   resultInfo.setText(R.string.order_send_success);
	               break; 
	           case ADD_ORDER_FAIL:
	        	   resultInfo.setText(R.string.order_send_fail);
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           case  PAY_SEND:
	        	   resultInfo.setText(R.string.pay_send);
	        	   //progressImg.setVisibility(View.GONE);
	        	   payOrder();
	        	   break;
	           case  PAY_SEND_SUCCESS:
	        	   
	        	   String ret = (String) msg.obj;
	        	   
	        	   // 获取交易状态码，具体状态代码请参看文档
	        	   String tradeStatus = "resultStatus={";
	        	   int imemoStart = ret.indexOf("resultStatus=");
	        	   imemoStart += tradeStatus.length();
	        	   int imemoEnd = ret.indexOf("};memo=");
	        	   tradeStatus = ret.substring(imemoStart, imemoEnd);
	        	   if(tradeStatus.equals("9000"))//判断交易状态码，只有9000表示交易成功
					{
						resultInfo.setText(R.string.pay_send_success);
		        	    warnInfo.setVisibility(View.VISIBLE);
		        	    //
		        	    try {
		    				Thread.sleep(3*1000);
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			}
		        	    Intent intent = new Intent(OrderSendActivity.this,OrderTraceActivity.class);
		   			    startActivity(intent);
					}else{
						resultInfo.setText(R.string.pay_send_fail);
					}
					
	        	   break;
	           case  PAY_SEND_FAIL:
	        	   resultInfo.setText(R.string.pay_send_fail);
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           case  PAY_INSTALL:
	        	   resultInfo.setText(R.string.pay_install_success);
	        	   msPayHelper.installAliMSP();
	        	   sendOrder();
	        	   //progressImg.setVisibility(View.GONE);
	        	   break;
	           }  
		}

	};
	private void sendOrder(){
		Message msg = new Message();
		try{
			Map<String ,Object> orderMap=new HashMap<String ,Object>();
			orderMap.put("uid", App.getInstance().getUser().getUid());
			orderMap.put("username", App.getInstance().getUser().getUserName());
			orderMap.put("orderjson", order.toJson());
			String rtn=HttpUtil.getInfoFromServer(HttpUtil.URL_WEBSERVICE_SET_ORDER, orderMap).toString();
			
			if(App.FAIL.equals(rtn)){
				msg.what=ADD_ORDER_FAIL;
			}else{
				msg.what=PAY_SEND;
				JSONTokener jsonParser = new JSONTokener(rtn);
				JSONObject job= (JSONObject)jsonParser.nextValue();
				order.setTaskId(job.getString("task_id"));
				order.setOrderNumber(job.getString("ordernumber"));//price
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			msg.what=ADD_ORDER_FAIL;
		}
		handler.sendMessage(msg);
	}
	
	private void payOrder() {
		MobileSecurePayer msp = new MobileSecurePayer();
		
		String payStr = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		payStr += "&";
		payStr += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		payStr += "&";
		payStr += "out_trade_no=" + "\"" +order.getOrderNumber() + "\"";
		payStr += "&";
		payStr += "subject=" + "\"" + "宅速洁服务费"
				+ "\"";
		payStr += "&";
		payStr += "body=" + "\"" + "宅速洁服务费" + "\"";
		payStr += "&";
		payStr += "total_fee=" + "\""
				+ 0.01+"\"";
		//		+ order.getPrice()+"\"";
		payStr += "&";
		payStr += "notify_url=" + "\""
				+ PartnerConfig.Notify_URL + "\"";
		Message msg = new Message();
		try
		{
			String sign = URLEncoder.encode(MobileSecurePayHelper.RsaSign(
					payStr, PartnerConfig.RSA_PRIVATE));
			payStr += String.format("&sign=\"%s\"", sign);
			payStr += String.format("&sign_type=\"%s\"", "RSA");
			
	        if (!msp.pay(payStr, handler, PAY_SEND_SUCCESS, OrderSendActivity.this)) {
	        	
	        	msg.what=PAY_SEND_FAIL;
	        	
	        }
        }catch(Exception ex){
        	ex.printStackTrace();
        	msg.what=PAY_SEND_FAIL;
        }
		handler.sendMessage(msg);
    }
		
	Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
				sendOrder();
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
