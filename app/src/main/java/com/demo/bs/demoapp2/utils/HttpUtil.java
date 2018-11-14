package com.demo.bs.demoapp2.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/6.
 */
public abstract class HttpUtil {
    public String urlpath ="http://120.79.79.221/TInfoWebServer/servlet/";
   //public String urlpath ="http://10.0.2.2:8080/TInfoWebServer/servlet/";
   // public String urlpath ="http://192.168.43.89:8080/TInfoWebServer/servlet/";
    private Context context;
    private Map<String,Object> paramsmap =new HashMap<>();
    protected abstract void onCallback(String json);
    private AsyncTask myhttp;
    private String httpurl;
    //HttpUtil的构造函数
    public HttpUtil(Context context, String path) {
        this.context = context;
        httpurl =path;
    }
    public void addParams (String key, String value){
        paramsmap.put(key,value);
    }
    public void sendGetRequest() {
        myhttp = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    return getRequest(httpurl, paramsmap);
                } catch (Exception e) {
                    return "error";
                }
            }
            @Override
            protected void onPostExecute(String s) {
                onCallback(s);
            }


        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    public void sendPostRequest() {
        myhttp = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    return postRequest(httpurl,paramsmap);
                } catch (Exception e) {
                    return "error";
                }
            }
            @Override
            protected void onPostExecute(String s) {
                onCallback(s);
            }


        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    private String getRequest(String path, Map<String,Object> params)throws IOException {
        StringBuilder url = new StringBuilder(urlpath+path);
        url.append("?");
        for (Map.Entry<String,Object> entity : params.entrySet()) {
            String getkey = URLEncoder.encode(entity.getKey(), "utf-8");
            String getvalue =  URLEncoder.encode((String) entity.getValue(), "utf-8");
            url.append(getkey).append("=");
            url.append(getvalue);
            url.append("&");
        }
        url.deleteCharAt(url.length() - 1);
        Log.d("cjy", url + "");
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString())
                .openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {

            InputStream in = conn.getInputStream();
            int len;
            byte[] buffer = new byte[10*1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = in.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            String s = bos.toString();

            Log.d("http Response","------------");
            Log.d("http Response",s);
            Log.d("http Response","------------");
            return s;
        } else {
            return "error";
        }
    }
    private String postRequest(String path, Map<String,Object> params)throws IOException {
        StringBuilder s = new StringBuilder(urlpath+path);

        try {
            // 根据地址创建URL对象
            URL url = new URL(s.toString());
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            StringBuilder data =new StringBuilder();
            for (Map.Entry<String,Object> entity : params.entrySet()) {
                String getkey = URLEncoder.encode(entity.getKey(), "utf-8");
                String getvalue =  URLEncoder.encode((String) entity.getValue(), "utf-8");
                data.append(getkey).append("=");
                data.append(getvalue);
                data.append("&");
            }
            Log.d("cjy", data.toString().getBytes() + "");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
//            // 设置请求的头
//            urlConnection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.toString().getBytes("utf-8").length));
            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.toString().getBytes("utf-8"));
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[10*1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                Log.d("http Response","------------");
                Log.d("http Response",result);
                Log.d("http Response","------------");
                return result;
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "error";
    }

}
