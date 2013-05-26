package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ag.zhaisujie.R;

/**
 * 订单追踪
 * 
 * @author max.Luo
 * @email max_null@sina.com
 * 
 */
@SuppressWarnings("all")
public class OrderTraceActivity extends Activity {
	private Button backBtn;

	private String commitTime = "";// 提交成功时间

	private String companyName = "";// 家政公司名称
	private String companyPhone = "";// 家政公司电话
	private String matchTime = "";// 匹配时间

	private ImageView waiterImg;// 服务人员头像
	private String waiterName = "";// 服务人员名称
	private String waiterPhone = "";// 服务人员电话
	private String serviceTime = "";// 服务时间

	private String serviceDoneTime = "";// 服务完成时间

	private Button orderCancelBtn;
	private Button contactHomeCompanyBtn;
	private Button contactWaiterBtn;
	private Button confirmBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_trace);

		Bundle bundle = this.getIntent().getExtras();

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.order_trace);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);

		orderCancelBtn = (Button) findViewById(R.id.order_cancel);
		orderCancelBtn.setOnClickListener(listener);

		contactHomeCompanyBtn = (Button) findViewById(R.id.contact_home_company);
		contactHomeCompanyBtn.setOnClickListener(listener);

		contactWaiterBtn = (Button) findViewById(R.id.contact_waiter);
		contactWaiterBtn.setOnClickListener(listener);

		confirmBtn = (Button) findViewById(R.id.confirm_done);
		confirmBtn.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				OrderTraceActivity.this.finish();
				break;
			case R.id.order_cancel:
				break;
			case R.id.contact_home_company:
				break;
			case R.id.contact_waiter:
				break;
			case R.id.confirm_done:
				Intent intent = new Intent(OrderTraceActivity.this, ServiceActivity.class);
				startActivity(intent);
				break;
			}
		}
	};

}
