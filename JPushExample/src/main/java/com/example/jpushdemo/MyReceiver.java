package com.example.jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			if(bundle == null){
				LogUtil.d("===============bundle == null");
				return;
			}
			LogUtil.d("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				LogUtil.d("[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				LogUtil.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				LogUtil.d("[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				LogUtil.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				LogUtil.d("[MyReceiver] 用户点击打开了通知");

				//打开自定义的Activity
				Intent i = new Intent(context, TestActivity.class);
				i.putExtras(bundle);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				LogUtil.d("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				LogUtil.i("[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				LogUtil.d("[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){
			LogUtil.e(e);
		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		if(bundle == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			switch (key) {
				case JPushInterface.EXTRA_NOTIFICATION_ID:
					sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
					break;
				case JPushInterface.EXTRA_CONNECTION_CHANGE:
					sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
					break;
				case JPushInterface.EXTRA_EXTRA:
					if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
						LogUtil.i("This message has no Extra data");
						continue;
					}

					try {
						JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
						Iterator<String> it = json.keys();

						while (it.hasNext()) {
							String myKey = it.next();
							sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
						}
					} catch (JSONException e) {
						LogUtil.e("Get message extra JSON error!");
					}

					break;
				default:
					sb.append("\nkey:").append(key).append(", value:").append(bundle.get(key));
					break;
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (JGMainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(JGMainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(JGMainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(JGMainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {
					LogUtil.e(e);
				}

			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
		}
	}
}
