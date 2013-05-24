package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ag.zhaisujie.R;

/**
 * 
 * @author max.Luo
 * @email max_null@sina.com ÁªÏµ±¸×¢
 * 
 */
public class ContactDetailActivity extends Activity {
	private Button backBtn;
	private Button submitBtn;
	
	private EditText contactName;
	private EditText contactPhone;
	private EditText contactMemo;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_memo);
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.contact_detail_title);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);

		submitBtn = (Button) findViewById(R.id.contact_submit);
		submitBtn.setOnClickListener(listener);
		
		contactName = (EditText)findViewById(R.id.contact_name);
		contactPhone = (EditText)findViewById(R.id.contact_phone);
		contactMemo = (EditText)findViewById(R.id.contact_memo);
		

	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					ContactDetailActivity.this.finish();
				break;
				case R.id.contact_submit:
					//do something
					Intent intent = new Intent(ContactDetailActivity.this, OrderConfirmActivity.class);
					
					Bundle bundle = new Bundle();
					bundle.putString("contactName", "" + contactName.getText());
					bundle.putString("contactPhone", "" + contactPhone.getText());
					bundle.putString("contactMemo", "" + contactMemo.getText());
					intent.putExtras(bundle);
					
					startActivity(intent);
				break;
			}
		}
	};

}
