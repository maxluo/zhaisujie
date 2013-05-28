package com.ag.zhaisujie.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ag.zhaisujie.R;
import com.ag.zhaisujie.model.Order;
/**
 * 
 *    SplashActivity.java
 *     <p>
 *     启动欢迎页面 3秒钟进主页面
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class SplashActivity extends Activity {
	private Timer timer = new Timer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		timer.schedule(task, 100);//设置这个task在延迟三秒之后自动执行
	}
	
	private void goHome() {
		Intent intent = new Intent(SplashActivity.this, OrderConfirmActivity.class);
		Bundle bundle = new Bundle();  
		bundle.putSerializable("Order", new Order());
		intent.putExtras(bundle);//传递地址到下一页面
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	TimerTask task = new TimerTask() { //timertask实现runnable接口,TimerTask类就代表一个在指定时间内执行的task
		   @Override
		   public void run() {
			   goHome();
		   }
	 };
}
