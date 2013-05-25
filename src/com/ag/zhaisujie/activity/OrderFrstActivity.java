package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ag.zhaisujie.R;

/**
 *    OrderFrstActivity.java
 *     <p>
 *     订单第一个页面
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class OrderFrstActivity extends Activity {
	private Button backBtn;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_frst);
		
		
		
	}


	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					OrderFrstActivity.this.finish();
				break;
				
			}
		}
	};

}
