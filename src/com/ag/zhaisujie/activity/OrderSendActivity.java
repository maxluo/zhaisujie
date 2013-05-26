package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ag.zhaisujie.R;

/**
 * OrderSendActivity.java
 * @author max.Luo
 * @email max_null@sina.com
 * 2013-5-25
 */
public class OrderSendActivity extends Activity {
	
	private Button backBtn;
	private ProgressBar progressBar;
	private TextView resultInfo;
	private TextView warnInfo;
	
	private Handler handler=null;
	private Thread thread;
	private boolean flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_send);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		
		//创建属于主线程的handler  
		if(handler ==null) {
			handler=new Handler() {

				@Override
				public void handleMessage(Message msg) {
					flag = false;
					Intent intent = new Intent(OrderSendActivity.this, OrderTraceActivity.class);
					startActivity(intent);
				}
	        	
	        };
		}
        
		Bundle bundle = this.getIntent().getExtras();
		
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.order_send);
		
		progressBar = (ProgressBar)findViewById(R.id.order_send_process_bar);
		resultInfo = (TextView)findViewById(R.id.order_result_info);
		warnInfo = (TextView)findViewById(R.id.order_warn_info);
		
		if(thread == null) {
			thread = new Thread(run);
			thread.start();
		}
		flag = true;
	}
	
	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(flag) {
				try {
					Thread.sleep(3000);
				Message msg = new Message();					
				Bundle bundle = new Bundle();
				msg.setData(bundle);
				handler.sendMessage(msg);
				
					//进行支付or获取跟踪信息
					/*ImageView iv = new ImageView(OrderSendActivity.this);
					iv.setImageBitmap(BitmapFactory.decodeStream(null));*/
//					Thread.sleep(3000);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
	};
	
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					flag = false;
					OrderSendActivity.this.finish();
				break;
			}
		}
	};
}

