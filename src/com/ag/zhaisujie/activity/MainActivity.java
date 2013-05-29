package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ag.zhaisujie.App;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.ToastUtil;
import com.ag.zhaisujie.model.Order;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 
 *    MainActivity.java
 *     <p>
 *     选择服务地点主页面
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class MainActivity extends Activity {
	private String key="B380D1FC76F5489BEA2C6B9B1C69E2744D1FB471";
	private BMapManager mBMapMan = null;
	private MapView mMapView = null;
	private MapController mapController = null;
	private MyLocationOverlay myLocationOverlay;
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private MKSearch mkSerach;
	private TextView locatFrom;
	private static App app;
	private static Button loginBtn;
	private static Button orderBtn;
	private static Button settingBtn;
	private static Button localBtn;
	
	private Button goOnbtn;//下单
	private EditText addrTxt;//补充地址
	private GeoPoint globleGP;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(key, null);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.main);
		locatFrom=(TextView)findViewById(R.id.from_location);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		initMap();//初始化地图
		app=App.getInstance(this);
		loginBtn=(Button) findViewById(R.id.title_btn_login);
		loginBtn.setOnClickListener(listener);
		orderBtn=(Button) findViewById(R.id.title_btn_order);
		settingBtn=(Button) findViewById(R.id.title_btn_setting);
		goOnbtn=(Button) findViewById(R.id.search_clear_bt);
		addrTxt=(EditText) findViewById(R.id.search_bar_et);
		localBtn=(Button) findViewById(R.id.mylocation);
		localBtn.setOnClickListener(listener);
		
		goOnbtn.setOnClickListener(listener);
		orderBtn.setOnClickListener(listener);
		settingBtn.setOnClickListener(listener);
		initButton();
		
	}
	public static void initButton(){
		//设置抬头
		if(app.getUser()==null){
			loginBtn.setVisibility(View.VISIBLE);
			orderBtn.setVisibility(View.GONE);
			settingBtn.setVisibility(View.GONE);
		}else{
			loginBtn.setVisibility(View.GONE);
			orderBtn.setVisibility(View.VISIBLE);
			settingBtn.setVisibility(View.VISIBLE);
		}
	}
	private void initMap(){
		
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		mapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mapController.setCenter(point);// 设置地图中心点
		mapController.setZoom(12);// 设置地图zoom级别
		
		// 添加定位图层
		myLocationOverlay = new MyLocationOverlay(mMapView);
		// 开启磁场感应传感器
		myLocationOverlay.enableCompass();
		mMapView.getOverlays().add(myLocationOverlay);
		
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);//禁止启用缓存定位
		//option.setPoiNumber(5);	//最多返回POI个数	
		//option.setPoiDistance(1000); //poi查询距离		
		//option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息		
		mLocationClient.setLocOption(option);
		//开始
		mLocationClient.start();
		mLocationClient.requestLocation();
		/*
		mMapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					// 做我想做的事 ，显示相关信息，这一步，我不知道怎么处理了。
					// 这一步想要的效果是：在屏幕中心点图标上面，有个冒泡框显示相关信息
					// 获取mapview的中心坐标
					globleGP=null;//全局
					GeoPoint gpt = mMapView.getMapCenter();
					search(gpt);//显示
					return true;
				}
				return false;
			}
		});*/

		MKMapViewListener mapViewListener = new MKMapViewListener() {
			 
	        @Override
	        public void onMapMoveFinish() {
	            // 此处可以实现地图移动完成事件的状态监听
	        	globleGP=null;//全局
				GeoPoint gpt = mMapView.getMapCenter();
				search(gpt);//显示
	        }
	                        
	        @Override
	        public void onClickMapPoi(MapPoi arg0) {
	                // 此处可实现地图点击事件的监听
	        }

			@Override
			public void onGetCurrentMap(Bitmap arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapAnimationFinish() {
				// TODO Auto-generated method stub
				
			}
		};
		mMapView.regMapViewListener(mBMapMan, mapViewListener);  //注册监听
		
		//搜索初始化
		mkSerach = new MKSearch();
		mkSerach.init(mBMapMan, new MKSearchListener() {

			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetAddrResult(MKAddrInfo info, int arg1) {
				locatFrom.setText(info.strAddr);
				globleGP=info.geoPt;
			}
		});
		
		
	}
	
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			LocationData locData = new LocationData();
			//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要
			//使用百度经纬度坐标（bd09ll）
			locData.latitude  = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			locData.speed     = location.getSpeed();
			locData.satellitesNum     = location.getSatelliteNumber();
			myLocationOverlay.setData(locData);
			mMapView.refresh();
			GeoPoint gp= new GeoPoint((int)(locData.latitude*1e6),
					(int)(locData.longitude* 1e6));
			mMapView.getController().animateTo(gp);
			search(gp);//显示
			mLocationClient.stop();
		
		}
		public void onReceivePoi(BDLocation poiLocation) {
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		if(item.getItemId()==R.id.action_exit){
			finish();
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		if(mLocationClient!=null){
			mLocationClient.stop();
			mLocationClient=null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		if(mLocationClient!=null){
			mLocationClient.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		if(mLocationClient!=null){
			mLocationClient.start();
		}
		super.onResume();
	}
	
	private void search(GeoPoint gp){
		mkSerach.reverseGeocode(gp);
		locatFrom.setText("获取位置中...");
	}
	
	//去下一个联系页面
	private void addOrder(){
		if(app.getUser()==null){
			ToastUtil.show(this, "请先登录！");
			return;
		}else if(globleGP==null){
			ToastUtil.show(this, "正在定位请稍后！");
			return;
		}
		String addr=locatFrom.getText().toString()+addrTxt.getText().toString();
		Order order =new Order();
		order.setAddress(addr);
		order.setLatitude(globleGP.getLatitudeE6());
		order.setLongitude(globleGP.getLongitudeE6());
		Intent intent = new Intent(MainActivity.this, OrderFrstActivity.class);
		Bundle bundle = new Bundle();  
		bundle.putSerializable("Order", order);
		intent.putExtras(bundle);//传递地址到下一页面
		MainActivity.this.startActivity(intent);
	}
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_login:
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				MainActivity.this.startActivity(intent);
				//MainActivity.this.finish();
				break;
			case R.id.title_btn_order:
				break;
			case R.id.title_btn_setting:
				Intent intent2 = new Intent(MainActivity.this, ServiceActivity.class);
				MainActivity.this.startActivity(intent2);
				break;
			case R.id.search_clear_bt:
				addOrder();
				break;
			case R.id.mylocation:
				//开始
				mLocationClient.start();
				mLocationClient.requestLocation();
				break;
				
			}
		}
	};
	
}
