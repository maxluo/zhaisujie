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
 *     ������ӭҳ�� 3���ӽ���ҳ��
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
		timer.schedule(task, 100);//�������task���ӳ�����֮���Զ�ִ��
	}
	
	private void goHome() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		//Bundle bundle = new Bundle();  
		//bundle.putSerializable("Order", new Order());
		//intent.putExtras(bundle);//���ݵ�ַ����һҳ��
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	TimerTask task = new TimerTask() { //timertaskʵ��runnable�ӿ�,TimerTask��ʹ���һ����ָ��ʱ����ִ�е�task
		   @Override
		   public void run() {
			   goHome();
		   }
	 };
}
