package xyz.chenqun.github.threadtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main2Activity extends AppCompatActivity {

    public LocationClient mLocationClient;

    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private Button getLocBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
//                StringBuffer currentPosition = new StringBuffer();
//                currentPosition.append("纬度:").append(bdLocation.getLatitude()).append("\n");
//                currentPosition.append("经度:").append(bdLocation.getLongitude()).append("\n");
//                currentPosition.append("国家:").append(bdLocation.getCountry()).append("\n");
//                currentPosition.append("省:").append(bdLocation.getProvince()).append("\n");
//                currentPosition.append("市:").append(bdLocation.getCity()).append("\n");
//                currentPosition.append("区:").append(bdLocation.getDistrict()).append("\n");
//                currentPosition.append("街道:").append(bdLocation.getStreet()).append("\n");
//                currentPosition.append("定位方式:");
//                if (bdLocation.getLocType()==BDLocation.TypeGpsLocation){
//                    currentPosition.append("GPS");
//                }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
//                    currentPosition.append("网络");
//                }
//                positionText.setText(currentPosition);
                Log.d("Main2Activity", "onReceiveLocation: "+bdLocation.getLocType());
                if ((bdLocation.getLocType()==BDLocation.TypeGpsLocation)||(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)){
                    LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                    NavigateTo(ll);
                }

            }
        });

        SDKInitializer.initialize(getApplicationContext());


        setContentView(R.layout.activity_main2);



        mapView= (MapView)findViewById(R.id.bmapView);

        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        positionText =(TextView) findViewById(R.id.position_text);
        List<String> permissionList = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(Main2Activity.this,permissions,1);
        }else {
            requestLocation();
        }

    }

    private void NavigateTo(LatLng latLng){
        if (isFirstLocate){
            //切换位置
            //LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            //设置地图缩放级别,正相关
            MapStatusUpdate zoomUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng,16f);
            baiduMap.animateMapStatus(zoomUpdate);
            isFirstLocate = false;
        }
        MyLocationData myLocationData = new MyLocationData.Builder()
        .latitude(latLng.latitude)
        .longitude(latLng.longitude).build();
        baiduMap.setMyLocationData(myLocationData);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }

    private void requestLocation(){
        mLocationClient.start();
        initLocation();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();

        mapView.onDestroy();

        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意全部权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
