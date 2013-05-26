package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ag.zhaisujie.R;

/**
 * 订单确认界面
 * @author max.Luo
 * @email max_null@sina.com 
 * 
 */
public class OrderConfirmActivity extends Activity {
	private Button backBtn;
	private Button submitBtn;
	
	private String contactName;
	private String contactPhone;
	private String contactMemo;
	
	private TextView order_user_address;
	
	private Button addressButton;
	private EditText addressInput;
	
	private String oriAddress;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confirm);
		
		Bundle bundle = this.getIntent().getExtras();
		contactName = bundle.getString("contactName");
		contactPhone = bundle.getString("contactPhone");
		contactMemo = bundle.getString("contactMemo");
		
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.order_confirm_title);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);

		submitBtn = (Button) findViewById(R.id.pay_submit);
		submitBtn.setOnClickListener(listener);
		
		order_user_address = (TextView)findViewById(R.id.order_user_address);
		addressButton = (Button)findViewById(R.id.address_btn);
		addressInput = (EditText)findViewById(R.id.address_input);
		
		addressButton.setOnClickListener(listener);
		
		oriAddress = order_user_address.getText().toString();
		
	}


	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					OrderConfirmActivity.this.finish();
				break;
				case R.id.address_btn:
					if(addressButton.getText().toString().equals(getString(R.string.address_input))) {
						addressInput.setVisibility(View.VISIBLE);
						addressButton.setText(R.string.confirm);
					} else {
						addressInput.setVisibility(View.GONE);
						addressButton.setText(R.string.address_input);
						
						order_user_address.setText(oriAddress + addressInput.getText().toString());
					}
					addressInput.setText("");
				break;
				
				case R.id.pay_submit:
					//调用支付接口，然后跳转页面
					Intent intent = new Intent(OrderConfirmActivity.this, OrderSendActivity.class);
					startActivity(intent);
				break;
			}
		}
	};

}
