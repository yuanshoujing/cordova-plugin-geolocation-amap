package com.gxlu.plugins.geolocation.amap;

import org.json.JSONObject;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class Geolocation {

  private String TAG = "AMapGeolocation";
  private AMapLocationClient client;
  private AMapLocationListener listener;


  Geolocation(Context context) {
    client = new AMapLocationClient(context);
  }

  private AMapLocationClientOption setOptions(JSONObject options) {
	AMapLocationClientOption locationOption = new AMapLocationClientOption();

	// *使用签到定位场景  签到场景SignIn   运动场景Sport   出行场景Transport
	// 该部分功能从定位SDK v3.7.0开始提供。如果开发者选择了对应的定位场景，
	// 那么则不用自行设置AMapLocationClientOption中的其他参数，SDK会根据选择的场景自行定制option参数的值，
	// 当然开发者也可以在基础上进行自行设置。实际按最后一次设置的参数值生效。	
	locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
	
	// *设置首次定位是否等待GPS定位结果，默认false
	locationOption.setGpsFirst(true);

	// *设置联网超时时间，默认30000毫秒
	locationOption.setHttpTimeOut(30000);

	// *设置发起定位请求的时间间隔，默认2000毫秒
	locationOption.setInterval(2000);

	// 设置退出时是否杀死进程，默认值:false
	// 注意：如果设置为true，并且配置的service不是remote的则会杀死当前页面进程，请慎重使用
	locationOption.setKillProcess(false);

	// *设置是否使用缓存策略, 默认为true 使用缓存策略
	locationOption.setLocationCacheEnable(true);

	// *设置定位模式，设置高精度Hight_Accuracy  低功耗模式Battery_Saving  仅设备(Gps)模式Device_Sensors
	locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

	// 设置定位协议  HTTP  HTTPS
	// locationOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
	
	// 设置是否允许模拟位置  
	// 从3.4.0开始，默认值为true，允许模拟;
	// 3.4.0之前的版本，默认值为false，不允许模拟
	locationOption.setMockEnable(false);

	// 设置是否返回地址信息，默认返回地址信息  默认值：true
	locationOption.setNeedAddress(true);

	// *设置是否单次定位 默认值：false
	locationOption.setOnceLocation(false);

	// 设置定位是否等待WIFI列表刷新 定位精度会更高，但是定位速度会变慢1-3秒
	// 从3.7.0版本开始，支持连续定位（连续定位时首次会等待刷新） 
	// 3.7.0之前的版本，仅适用于单次定位，当设置为true时，连续定位会自动变为单次定位
	locationOption.setOnceLocationLatest(true);

	// 设置是否使用设备传感器 默认值：false 不使用设备传感器
	locationOption.setSensorEnable(false);

	// 设置是否允许调用WIFI刷新 默认值为true，
	// 当设置为false时会停止主动调用WIFI刷新，将会极大程度影响定位精度，但可以有效的降低定位耗电
	locationOption.setWifiScan(true);

    if (options!=null)
    {
		// *使用签到定位场景  签到场景SignIn   运动场景Sport   出行场景Transport
		// 该部分功能从定位SDK v3.7.0开始提供。如果开发者选择了对应的定位场景，
		// 那么则不用自行设置AMapLocationClientOption中的其他参数，SDK会根据选择的场景自行定制option参数的值，
		// 当然开发者也可以在基础上进行自行设置。实际按最后一次设置的参数值生效。
		String purpose = getString(options, "locationPurpose", "Transport");
		if ("SignIn".equals(purpose)) {
			locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
		} else if ("Sport".equals(purpose)) {
			locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.运动场景Sport);
		} else {
			locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
		}

		// *设置联网超时时间，默认30000毫秒
		locationOption.setHttpTimeOut(getInt(options, "timeOut", 30000));

		// *设置发起定位请求的时间间隔，默认2000毫秒
		locationOption.setInterval(getInt(options, "interval", 2000));

		// *设置是否使用缓存策略, 默认为true 使用缓存策略
		locationOption.setLocationCacheEnable(getBoolean(options, "cacheEnable", true));

		// *设置定位模式，设置高精度Hight_Accuracy  低功耗模式Battery_Saving  仅设备(Gps)模式Device_Sensors
		boolean enableHighAccuracy = getBoolean(options, "enableHighAccuracy", true);
		if (enableHighAccuracy) {
			locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		} else {
			locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
		}

		// 设置是否返回地址信息，默认返回地址信息  默认值：true
		locationOption.setNeedAddress(getBoolean(options, "needAddress", false));

		// *设置是否单次定位 默认值：false
		locationOption.setOnceLocation(getBoolean(options, "onceLocation", false));

		// 设置定位是否等待WIFI列表刷新 定位精度会更高，但是定位速度会变慢1-3秒
		// 从3.7.0版本开始，支持连续定位（连续定位时首次会等待刷新） 
		// 3.7.0之前的版本，仅适用于单次定位，当设置为true时，连续定位会自动变为单次定位
		locationOption.setOnceLocationLatest(getBoolean(options, "onceLocationLatest", true));

		// 设置是否使用设备传感器 默认值：false 不使用设备传感器
		locationOption.setSensorEnable(getBoolean(options, "sensorEnable", false));

		// 设置是否允许调用WIFI刷新 默认值为true，
		// 当设置为false时会停止主动调用WIFI刷新，将会极大程度影响定位精度，但可以有效的降低定位耗电
		locationOption.setWifiScan(getBoolean(options, "wifiScan", true));		
    }

	client.setLocationOption(locationOption); // 设置定位参数

	return locationOption;
  }

  public boolean getCurrentPosition(JSONObject options, final AMapLocationListener callback) {
    listener = new AMapLocationListener() {
      @Override
      public void onLocationChanged(AMapLocation location) {
        callback.onLocationChanged(location);
        clearWatch();
      }
    };
    setOptions(options).setOnceLocation(true);
    client.setLocationListener(listener);
	client.stopLocation();
    client.startLocation();
    return true;
  }

  public boolean watchPosition(JSONObject options, AMapLocationListener callback) {
    listener = callback;
    setOptions(options);
    client.setLocationListener(listener);
	client.stopLocation();
    client.startLocation();
    return true;
  }

  public boolean clearWatch() {
	// 停止定位后，本地定位服务并不会被销毁
    client.stopLocation();

	// 销毁定位客户端，同时销毁本地定位服务。
	// 销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
	client.onDestroy(); 
    listener = null;
    return true;
  }

  private String getString(JSONObject json, String key, String defualt) {
	try
	{
		return json.getString(key);
	}
	catch (Exception e)
	{
		return defualt;
	}
  }

  private boolean getBoolean(JSONObject json, String key, boolean defualt) {
	try
	{
		return json.getBoolean(key);
	}
	catch (Exception e)
	{
		return defualt;
	}	
  }

  private int getInt(JSONObject json, String key, int defualt) {
	try
	{
		return json.getInt(key);
	}
	catch (Exception e)
	{
		return defualt;
	}	  
  }
}
