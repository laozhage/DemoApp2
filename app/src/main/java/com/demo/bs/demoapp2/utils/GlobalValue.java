package com.demo.bs.demoapp2.utils;


import com.demo.bs.demoapp2.bean.UserInfo;

public class GlobalValue {
    public static String TAG ="cjy";

    private static UserInfo userInfo;

    public static UserInfo getUserInfo() {
        if (userInfo==null){
            userInfo=new UserInfo();
        }
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        GlobalValue.userInfo = userInfo;
    }
}
