package com.example.jpushdemo;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;



public class JGMainActivity extends InstrumentedActivity implements OnClickListener{

	private TextView mRegId;
	private EditText msgText;
	
	public static boolean isForeground = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getWindow().setStatusBarColor(0xffcccccc);
		initView();   
		registerMessageReceiver();  // used for receive msg
	}
	
	@SuppressLint("SetTextI18n")
	private void initView(){
		TextView mImei = findViewById(R.id.tv_imei);
		String udid =  ExampleUtil.getImei(this);
        if (null != udid) mImei.setText("IMEI: " + udid);
        
		TextView mAppKey = findViewById(R.id.tv_appkey);
		String appKey = ExampleUtil.getAppKey(getApplicationContext());
		if (null == appKey) appKey = "AppKey异常";
		mAppKey.setText("AppKey: " + appKey);

		mRegId = findViewById(R.id.tv_regId);
		mRegId.setText("RegId:");

		String packageName =  getPackageName();
		TextView mPackage = findViewById(R.id.tv_package);
		mPackage.setText("PackageName: " + packageName);

		String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
		TextView mDeviceId = findViewById(R.id.tv_device_id);
		mDeviceId.setText("deviceId:" + deviceId);
		
		String versionName =  ExampleUtil.GetVersion(getApplicationContext());
		TextView mVersion = findViewById(R.id.tv_version);
		mVersion.setText("Version: " + versionName);

		Button mInit = findViewById(R.id.init);
		mInit.setOnClickListener(this);

		Button mStopPush = findViewById(R.id.stopPush);
		mStopPush.setOnClickListener(this);

		Button mResumePush = findViewById(R.id.resumePush);
		mResumePush.setOnClickListener(this);

		Button mGetRid = findViewById(R.id.getRegistrationId);
		mGetRid.setOnClickListener(this);

		Button mSetting = findViewById(R.id.setting);
		mSetting.setOnClickListener(this);
		
		msgText = findViewById(R.id.msg_rec);
	}

	
	@SuppressLint("SetTextI18n")
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.init) {
			init();
		} else if (id == R.id.setting) {
			Intent intent = new Intent(JGMainActivity.this, PushSetActivity.class);
			startActivity(intent);
		} else if (id == R.id.stopPush) {
			JPushInterface.stopPush(getApplicationContext());
		} else if (id == R.id.resumePush) {
			JPushInterface.resumePush(getApplicationContext());
		} else if (id == R.id.getRegistrationId) {
			String rid = JPushInterface.getRegistrationID(getApplicationContext());
			if (!rid.isEmpty()) {
				mRegId.setText("RegId:" + rid);
			} else {
				Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void init(){
		 JPushInterface.init(getApplicationContext());
	}


	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	

	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
//	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
					String messge = intent.getStringExtra(KEY_MESSAGE);
					String extras = intent.getStringExtra(KEY_EXTRAS);
					StringBuilder showMsg = new StringBuilder();
					showMsg.append(KEY_MESSAGE + " : ").append(messge).append("\n");
					if (!ExampleUtil.isEmpty(extras)) {
						showMsg.append(KEY_EXTRAS + " : ").append(extras).append("\n");
					}
					setCostomMsg(showMsg.toString());
				}
			} catch (Exception e){
				LogUtil.e(e);
			}
		}
	}
	
	private void setCostomMsg(String msg){
		 if (null != msgText) {
			 msgText.setText(msg);
			 msgText.setVisibility(android.view.View.VISIBLE);
         }
	}

}