package com.ag.zhaisujie.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ag.zhaisujie.App;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.utils.SimpleFuncUtils;

/**
 * 服务界面
 * 
 * @author max.Luo
 * @email max_null@sina.com
 * 
 */
public class ServiceActivity extends BaseActivity {
	private Button orderBtn;
	private Button exitBtn;
	private Button callBtn;

	private TextView servicePhoneNum;
	private TextView accountNum;

	private TextView userName;
	
	private ImageView userPic;
	private ImageView aboutUsImg;
	private ImageView priceImg;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service);

		Bundle bundle = this.getIntent().getExtras();

		servicePhoneNum = (TextView)findViewById(R.id.service_phone_num);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.service_title);
		orderBtn = (Button) findViewById(R.id.title_btn_setting);
		orderBtn.setText(R.string.order_btn);
		orderBtn.setVisibility(View.VISIBLE);
		orderBtn.setOnClickListener(listener);

		exitBtn = (Button) findViewById(R.id.exit_btn);
		exitBtn.setOnClickListener(listener);

		callBtn = (Button) findViewById(R.id.call_btn);
		callBtn.setOnClickListener(listener);
		
		accountNum = (TextView)findViewById(R.id.order_account_num);
		userName = (TextView)findViewById(R.id.order_user_name);
		
		userPic = (ImageView)findViewById(R.id.user_info_img);
		aboutUsImg = (ImageView)findViewById(R.id.about_us_img);
		priceImg =  (ImageView)findViewById(R.id.price_img);
		
		
		userPic.setOnClickListener(listener);
		aboutUsImg.setOnClickListener(listener);
		priceImg.setOnClickListener(listener);
		
		String acc = "";
		try {
			acc = App.getInstance().getUser().getUserName();
		} catch (Exception e) {
		}
		accountNum.setText(acc);

	}
	
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_btn_setting:
				myExit();
				break;
			case R.id.exit_btn:
//				android.os.Process.killProcess(android.os.Process.myPid());
				//更新界面
				accountNum.setText("");
				App.getInstance().setUser(null);
				MainActivity.initButton();
				ServiceActivity.this.finish();
				break;

			case R.id.call_btn:
				// 调用支付接口，然后跳转页面
//				Intent intent = new Intent();
//				intent.setAction(Intent.ACTION_CALL);
//				intent.setData(Uri.parse("tel:" + servicePhoneNum.getText()));
				TextView tv=new TextView(ServiceActivity.this);
				tv.setText(servicePhoneNum.getText().toString());
				tv.setGravity(Gravity.CENTER);
				new AlertDialog.Builder(ServiceActivity.this)
				.setView(tv)
				//.setMessage(servicePhoneNum.getText().toString())
				.setPositiveButton("呼叫",  
					new DialogInterface.OnClickListener() {  
					    @Override  
					    public void onClick(DialogInterface dialog, int which) {  
					    	SimpleFuncUtils.startPhoneIntent(ServiceActivity.this, "" + servicePhoneNum.getText().toString().replaceAll("-", "")); 
					    }  
				})
				.setNegativeButton("取消", null)
				.show();
				
				break;
			case R.id.user_info_img:
				Log.d("sss", "user_info_img");
				break;
			case R.id.about_us_img:
				Log.d("sss", "about_us_img");
				break;
			case R.id.price_img:
				Log.d("sss", "price_img");
				break;
			}
			
		}
	};
   protected void myExit() {  
        Intent intent = new Intent();  
        intent.setAction("ExitApp");  
        this.sendBroadcast(intent);  
        super.finish();  
    }  
}
