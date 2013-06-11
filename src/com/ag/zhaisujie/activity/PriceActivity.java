package com.ag.zhaisujie.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ag.zhaisujie.R;

/**
 *    PriceActivity.java
 *     <p>
 *     �۸�˵��ҳ��
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class PriceActivity extends BaseActivity {
	private Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.price_info);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.price_title);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
	}
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				PriceActivity.this.finish();
				break;
			}
		}
	};
}
