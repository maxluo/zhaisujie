package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ag.zhaisujie.R;
/**
 * 
 *    LoginActivity.java
 *     <p>
 *     µÇÂ¼Ò³Ãæ
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class LoginActivity extends Activity {
	private Button backBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		backBtn=(Button)findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		
	}
	
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				//Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				//LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
				break;
			}
		}
	};

}
