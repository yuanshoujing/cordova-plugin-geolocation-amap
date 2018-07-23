package com.gxlu.plugins.geolocation.amap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationQualityReport;

public class MessageBuilder {

  AMapLocation location;

  MessageBuilder(AMapLocation location) {
    this.location = location;
  }

  public JSONArray build() {
	  JSONObject json = new JSONObject();
      try {
    	  JSONObject coords = new JSONObject();        
    	  coords.put("locType", location.getLocationType()); //
    	  coords.put("latitude", location.getLatitude()); //
    	  coords.put("longitude", location.getLongitude()); //
    	  coords.put("accuracy", location.getAccuracy()); //
    	  coords.put("heading", location.getBearing()); //
    	  coords.put("speed", location.getSpeed()); //
    	  coords.put("altitude", location.getAltitude()); //
    	  coords.put("address", location.getAddress()); //

    	  // coords.put("locationID", location.getLocationID());
    	  coords.put("country", location.getCountry()); //
		  coords.put("province", location.getProvince()); //
    	  // coords.put("countryCode", location.getCountryCode());
    	  coords.put("city", location.getCity()); //
    	  coords.put("cityCode", location.getCityCode()); //
    	  coords.put("district", location.getDistrict()); //
    	  coords.put("street", location.getStreet()); //
    	  coords.put("streetNumber", location.getStreetNum()); //
    	  coords.put("locationDescribe", location.getLocationDetail());
    	  coords.put("poiList", location.getPoiName()); //

    	  coords.put("buildingID", location.getBuildingId()); //
    	  coords.put("floor", location.getFloor()); //

		  // 获取当前可用卫星数量, 仅在GPS定位时有效,
		  coords.put("satellites", location.getSatellites());

		  // 获取定位提供者 getProvider()
		  coords.put("provider", location.getProvider());

		  // 获取定位信息描述
		  // coords.put("locdetail", location.getLocationDetail());

		  // 获取卫星信号强度，仅在gps定位时有效,值为 #GPS_ACCURACY_BAD，#GPS_ACCURACY_GOOD，#GPS_ACCURACY_UNKNOWN
		  if (location.getGpsAccuracyStatus() == AMapLocation.GPS_ACCURACY_GOOD) {
			  coords.put("gpsAccuracy", "卫星信号强");	
		  } else if (location.getGpsAccuracyStatus() == AMapLocation.GPS_ACCURACY_BAD) {
			  coords.put("gpsAccuracy", "卫星信号弱");		
		  } else {
			  coords.put("gpsAccuracy", "卫星状态未知");
		  }
		  
		  
		  // 获取错误信息 getErrorInfo()
		  coords.put("errorinfo", location.getErrorInfo());

		  // 获取位置语义信息 getDescription()
		  coords.put("description", location.getDescription());

		  // 获取兴趣面名称 2.9.0之前的版本定位类型为AMapLocation.LOCATION_TYPE_GPS时不会返回兴趣面名称 
		  // 自2.9.0版本开始，当AMapLocation.LOCATION_TYPE_GPS时也可以返回兴趣面名称 getAoiName()
		  coords.put("aoiname", location.getAoiName());

	      // 获取定位质量 getLocationQualityReport()
		  AMapLocationQualityReport qualityReport = location.getLocationQualityReport();

          if (location.getLocationType() == AMapLocation.LOCATION_TYPE_GPS) {
        	  coords.put("describe", "GPS定位成功");
          } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_SAME_REQ) {
        	  coords.put("describe", "前次定位结果");
          } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_FIX_CACHE) {
        	  coords.put("describe", "缓存定位结果");
          } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_WIFI) {
        	  coords.put("describe", "Wifi定位结果");
          } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_CELL) {
        	  coords.put("describe", "基站定位结果");
          } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_OFFLINE) {
        	  coords.put("describe", "离线定位结果");
          } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_LAST_LOCATION_CACHE) {
        	  coords.put("describe", "最后位置缓存");
          }

          if (location.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
        	  coords.put("locstatus", "定位成功");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_INVALID_PARAMETER) {
        	  coords.put("locstatus", "一些重要参数为空");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_WIFI_INFO) {
        	  coords.put("locstatus", "定位失败，由于设备仅扫描到单个wifi，不能精准的计算出位置信息");
          } else if (location.getLocationType() == AMapLocation.ERROR_CODE_FAILURE_LOCATION_PARAMETER) {
        	  coords.put("locstatus", "获取到的请求参数为空，可能获取过程中出现异常");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_CONNECTION) {
        	  coords.put("locstatus", "网络连接异常");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_PARSER) {
        	  coords.put("locstatus", "解析XML出错");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_LOCATION) {
        	  coords.put("locstatus", "定位结果错误");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_AUTH) {
        	  coords.put("locstatus", "KEY错误");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_UNKNOWN) {
        	  coords.put("locstatus", "其他错误");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_INIT) {
        	  coords.put("locstatus", "初始化异常");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_SERVICE_FAIL) {
        	  coords.put("locstatus", "定位服务启动失败");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_CELL) {
        	  coords.put("locstatus", "错误的基站信息，请检查是否安装SIM卡");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION) {
        	  coords.put("locstatus", "缺少定位权限,请检查是否配置定位权限");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_NOWIFIANDAP) {
        	  coords.put("locstatus", "网络定位失败，请检查设备是否插入sim卡、开启移动网络或开启了wifi模块");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_NOENOUGHSATELLITES) {
        	  coords.put("locstatus", "GPS定位失败，可用卫星数不足");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_SIMULATION_LOCATION) {
        	  coords.put("locstatus", "定位位置可能被模拟");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_AIRPLANEMODE_WIFIOFF) {
        	  coords.put("locstatus", "定位失败，飞行模式下关闭了WIFI开关，请关闭飞行模式或者打开WIFI开关");
          } else if (location.getErrorCode() == AMapLocation.ERROR_CODE_NOCGI_WIFIOFF) {
        	  coords.put("locstatus", "定位失败，没有检查到SIM卡，并且关闭了WIFI开关，请打开WIFI开关或者插入SIM卡");
          }

          json.put("coords", coords);
      } catch (JSONException e) {
    	  e.printStackTrace();
          //String errMsg = e.getMessage();
          //json.put("error", errMsg);
      }

	  JSONArray array = new JSONArray();

	  array.put(json);
      
      return array;
  }
}
