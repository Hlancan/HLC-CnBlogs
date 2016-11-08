package com.lancan.cnblogs.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetWorkUtil {
	
	private static final String TAG = "NetWorkUtil";
	
	private static NetWorkUtil mNetWorkUtil;
	
	NetWorkReceiver mReceiver = new NetWorkReceiver();
	private Context mContext;
	
	private ConnectivityManager cm;
	
	private boolean isNetworkConnected = false;
	
	/**
	 * 枚举网络状态 NET_NO：没有网络 NET_2G:2g网络 NET_3G：3g网络 NET_4G：4g网络 NET_WIFI：wifi
	 * NET_UNKNOWN：未知网络
	 */
	public enum NetState {
		NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
	};
	
	public static synchronized NetWorkUtil getInstance(){
		if (mNetWorkUtil == null) {
			mNetWorkUtil = new NetWorkUtil();
		}
		return mNetWorkUtil;
	}
	
	/**
	 * 初始化,获取Context
	 * @param context
	 */
	public void init(Context context){
		if (context != null) {
			this.mContext = context.getApplicationContext();
			isNetworkConnected = getNetworkConnectState();
		}
	}
	
	public void registerNetWorkReceiver(){
		if (mContext != null) {
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
			mContext.registerReceiver(mReceiver, intentFilter);
		}
	}
	
	private boolean getNetworkConnectState(){
		NetState state = getNetWorkState();
		if (state == NetState.NET_NO) {
			return false;
		}
		if (!isMobileConn()&&!isWifiConn()) {
			return false;
		}
		return true;
	}
	
	public boolean isNetworkConnected(){
		return isNetworkConnected;
	}
	
	public boolean isWifiConn() {
		NetworkInfo networkInfo = getConnectivityManager().getNetworkInfo(
				ConnectivityManager.TYPE_WIFI);
		boolean wifiConn=networkInfo.isConnected()&&networkInfo.isAvailable();
		Log.d(TAG, " isMobileConn   wifiConn="+wifiConn);
		return wifiConn;
	}

	public boolean isMobileConn() {
		NetworkInfo networkInfo = getConnectivityManager().getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE);
		boolean mobileConn=networkInfo.isConnected()&&networkInfo.isAvailable();
		Log.d(TAG, " isMobileConn   mobileConn="+mobileConn);
		return mobileConn;
	}

	private NetState getNetWorkState() {
		Log.d(TAG, " MobileDataControllerImpl getNetWorkState ");
		
		NetworkInfo info = getConnectivityManager().getActiveNetworkInfo();
		NetState stateCode = NetState.NET_NO;
		if (info != null && info.isConnectedOrConnecting()) {
			switch (info.getType()) {
			case ConnectivityManager.TYPE_WIFI:
				Log.d(TAG, "ConnectivityManager.TYPE_WIFI");
				stateCode = NetState.NET_WIFI;
				break;
			case ConnectivityManager.TYPE_MOBILE:
				Log.d(TAG, "ConnectivityManager.TYPE_MOBILE");
				switch (info.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
				case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
				case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN:
					 stateCode = NetState.NET_2G;
					 break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_HSPAP:
					stateCode = NetState.NET_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_LTE:
					stateCode = NetState.NET_4G;
					break;
				default:
					stateCode = NetState.NET_UNKNOWN;
				}
				break;
			case ConnectivityManager.TYPE_BLUETOOTH:
				Log.d(TAG, "ConnectivityManager.TYPE_BLUETOOTH");
				break;

			default:
				stateCode = NetState.NET_UNKNOWN;
				break;
			}
		}
		return stateCode;
	}

	public ConnectivityManager getConnectivityManager() {
		cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm;
	}
	
	class NetWorkReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			boolean temp = getNetworkConnectState();
			Log.d(TAG, "temp = " + temp + "; isNetworkConnected = " + isNetworkConnected);
			if (temp != isNetworkConnected) {
				isNetworkConnected = temp;
//				notifyNetworkState(isNetworkConnected, getNetWorkState());
			}
		}
	}
	

	
}
