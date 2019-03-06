# AMap Geolocation for Cordova

高德定位SDK版本：3.7.0

Cordova 使用高德Android定位SDK进行定位，兼容 W3C 的 geolocation 标准，解决中国大陆手机无法定位的问题。

## 安装方法 Windows下：

在控制台里，进入 cordova 项目目录，执行以下命令：

```bash
cordova plugin add https://github.com/yuanshoujing/cordova-plugin-geolocation-amap.git --variable API_KEY=高德分配的AK --save
```


如果需要同时在 iOS 里和 Android 里使用，请在 `config.xml` 里分别配置：
```xml
...
  <!-- android 使用本插件 -->
  <platform name="android">
    <plugin name="cordova-plugin-baidu-geolocation" spec="https://github.com/yuanshoujing/cordova-plugin-geolocation-amap.git">
      <variable name="API_KEY" value="高德分配的AK" />
    </plugin>
  </platform>
  
  <!-- iOS 使用官方插件 -->
  <platform name="ios">
    <plugin name="cordova-plugin-geolocation" spec="~1.0.0" />
  </platform>
...
```
## 关于 API_KEY
使用前需要在高德申请应用，获取 API_KEY。

## 使用方法
### navigator.geolocation.getCurrentPosition(success, [error], [options]);
获取当前位置，更多参数见高德AMapLocationClientOption
```
var options = {
  enableHighAccuracy: true,  // 是否使用 GPS
  maximumAge: 30000,         // 缓存时间
  timeout: 30000            // 超时时间
}
```
succes 原型：
```
function success(position) {
}
```
position 基本定义，扩展部分见AMapLocation属性：
```
{
  "coords": {
    "latitude": "number",
    "longitude": "number",
    "altitude": "number",
    "accuracy": "number",
    "heading": "string",
    "speed": "number"
  }
}
```

### navigator.geolocation.watchPosition(success, [error], [options]);
持续追踪位置变更
返回值：watchId

### navigator.geolocation.clearWatch(watchId);
清除位置追踪

## 关于坐标系
由于高德定位的限制，这个插件仅能获取中国偏移坐标系 GCJ02。如果需要坐标系的转换，请使用第三方服务。
如果期望离线转换坐标系，可以使用这个算法：
https://github.com/wandergis/coordtransform

