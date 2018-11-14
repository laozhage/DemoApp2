package com.demo.bs.demoapp2.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.demo.bs.demoapp2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PushService extends Service {

   // private List<BorrowInfo> bookinfos;
    public static int NOTIFICATION_ID=1;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.d("PushService", "推送服务启动");
        getBorrowInfolist();


        return super.onStartCommand(intent, flags, startId);
    }
    private void getBorrowInfolist() {
        Handler handler =new Handler();
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
//                HttpUtil httpUtil =new HttpUtil(getApplicationContext(),"GetBorrowList") {
//                    @Override
//                    protected void onCallback(String json) {
//                        try {
//                            Gson gson =new Gson();
//                            bookinfos =gson.fromJson(json,new TypeToken<List<BorrowInfo>>(){}.getType());
//                            for (int i=0;i<bookinfos.size();i++){
//                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                                String nowtime =df.format(new Date());
//                                if (getDaySub(nowtime,bookinfos.get(i).getReturn_date())<=0){
//                                    showNotification();
//                                }
//                            }
//                            getBorrowInfolist();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                };
//                httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
//                httpUtil.sendGetRequest();
            }
        };
        handler.postDelayed(runnable,60*1000);

    }
//由于推送太频繁,被拒绝访问了..所以我把推送次数延时高点,
    //现在测试推送..提醒到期,设置同一天..好了..
    protected void showNotification() {
        NotificationManager notificationManager =(NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        Notification notification =new Notification();

        notification.icon = R.drawable.ic_launcher;
        notification.tickerText="亲,您之前借的书籍即将到期,请按时归还~";
        //notification.setLatestEventInfo(getApplicationContext(), "图书馆系统给您发了一条信息","亲,您之前借的书籍即将到期,请按时归还~", null);
        notificationManager.cancel(0);
        notificationManager.notify(0, notification);


    }
    public long getDaySub(String beginDateStr, String endDateStr)
    {
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            System.out.println("相隔的天数="+day);
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day;
    }
}
