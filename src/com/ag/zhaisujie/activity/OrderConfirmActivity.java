package com.ag.zhaisujie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ag.zhaisujie.DateUtil;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.model.Order;

/**
 * 订单确认界面
 * 
 * @author max.Luo
 * @email max_null@sina.com
 * 
 */
public class OrderConfirmActivity extends BaseActivity {
	private Button backBtn;
	private Button submitBtn;

	private TextView order_user_name;
	private TextView order_user_address;
	private TextView order_user_phone;
	private TextView pay_amnt;

	private TextView service_time;
	private TextView service_start_time;
	private TextView service_memo;

	private Button addressButton;
	private EditText addressInput;

	private String oriAddress;

	private Order order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confirm);

		Bundle bundle = this.getIntent().getExtras();

		order = (Order) bundle.getSerializable("Order");

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.order_confirm_title);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);

		submitBtn = (Button) findViewById(R.id.pay_submit);
		submitBtn.setOnClickListener(listener);

		order_user_name = (TextView) findViewById(R.id.order_user_name);
		order_user_address = (TextView) findViewById(R.id.order_user_address);
		order_user_phone = (TextView) findViewById(R.id.order_user_phone);
		pay_amnt = (TextView) findViewById(R.id.pay_amnt);
		service_time = (TextView) findViewById(R.id.service_time);
		service_start_time = (TextView) findViewById(R.id.service_start_time);
		service_memo = (TextView) findViewById(R.id.service_memo);

		addressButton = (Button) findViewById(R.id.address_btn);
		addressInput = (EditText) findViewById(R.id.address_input);

		addressButton.setOnClickListener(listener);

		order_user_name.setText(order.getLinkman());
		order_user_phone.setText(order.getLinkmobile());
		order_user_address.setText(order.getAddress());
		pay_amnt.setText("实际付款          " + Double.valueOf(order.getPrice()).intValue()+"元");
		service_time.setText(order.getClean_hours() + "小时");
		if(DateUtil.toDateByDT(order.getBegin_time())!=null){
			service_start_time.setText(DateUtil.formatDateToMDHM(DateUtil.toDateByDT(order.getBegin_time())));
		}else{
			service_start_time.setText(order.getBegin_time());
		}
		
		service_memo.setText(order.getContent());

		oriAddress = order.getAddress();

	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				OrderConfirmActivity.this.finish();
				break;
			case R.id.address_btn:
				if (addressButton.getText().toString()
						.equals(getString(R.string.address_input))) {
					addressInput.setVisibility(View.VISIBLE);
					addressButton.setText(R.string.confirm);
				} else {
					addressInput.setVisibility(View.GONE);
					addressButton.setText(R.string.address_input);

					order_user_address.setText(oriAddress
							+ addressInput.getText().toString());
				}
				addressInput.setText("");
				break;

			case R.id.pay_submit:
				// 调用支付接口，然后跳转页面
				Intent intent = new Intent(OrderConfirmActivity.this,
						OrderSendActivity.class);
				intent.putExtras(getIntent());
				startActivity(intent);
				break;
			}
		}
	};

}
