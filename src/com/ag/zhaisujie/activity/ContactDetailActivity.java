package com.ag.zhaisujie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ag.zhaisujie.App;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.ToastUtil;
import com.ag.zhaisujie.ValidUtil;
import com.ag.zhaisujie.model.Order;

/**
 * 
 * @author max.Luo
 * @email max_null@sina.com 联系备注
 * 
 */
public class ContactDetailActivity extends BaseActivity {
	private Button backBtn;
	private Button submitBtn;
	private EditText contactName;
	private EditText contactPhone;
	private EditText contactMemo;
	private Order order;

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
		contactPhone.setText(App.getInstance().getUser().getUserName());
		order=(Order)getIntent().getSerializableExtra("Order");

	}
	
	private void nextPage(){
		if(contactName.getText().toString().trim().length()==0){
			ToastUtil.show(this, "请输入联系人！");
			return;
		}else if(contactPhone.getText().toString().trim().length()==0){
			ToastUtil.show(this, "请输入联系电话！");
			return;
		}else if(!ValidUtil.isMobileNO(contactPhone.getText().toString().trim())){
			ToastUtil.show(this, "手机号码非法！");
			return;
		}
		
		order.setLinkman(contactName.getText().toString());
		order.setLinkmobile(contactPhone.getText().toString());
		order.setContent(contactMemo.getText().toString().trim().length()==0?"无":contactMemo.getText().toString());
		
		Intent intent = new Intent(ContactDetailActivity.this, OrderConfirmActivity.class);
		Bundle bundle = new Bundle();  
		bundle.putSerializable("Order", order);
		intent.putExtras(bundle);//传递地址到下一页面
		startActivity(intent);
	}
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
				case R.id.title_btn_back:
					ContactDetailActivity.this.finish();
				break;
				case R.id.contact_submit:
					nextPage();
				break;
			}
		}
	};

}
