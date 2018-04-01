package xyz.chenqun.github.threadtest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

//    private DownloadBinder mBinder = new DownloadBinder();
//
//    public class DownloadBinder extends Binder{
//
//        public void startDownload(){
//            Log.d("MyService", "startDownload");
//        }
//
//        public int getProgress(){
//            Log.d("MyService", "getProgress");
//            return 0;
//        }
//
//    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
        //return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d("MyService", "onCreate");
        //前台服务

//        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
//        NotificationChannel channel = new NotificationChannel("1","channel1", NotificationManager.IMPORTANCE_DEFAULT);
//        channel.enableLights(true); //是否在桌面icon右上角展示小红点
//        channel.setLightColor(Color.RED); //小红点颜色
//        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
//        notificationManager.createNotificationChannel(channel);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
//        Notification notification = new NotificationCompat.Builder(this,"1")
//                .setContentTitle("This is content title")
//                .setContentText("this is content text")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setContentIntent(pi)
//                .build();
//        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy");
    }
}
