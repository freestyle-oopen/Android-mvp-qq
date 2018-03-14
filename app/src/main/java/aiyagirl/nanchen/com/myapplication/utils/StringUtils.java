package aiyagirl.nanchen.com.myapplication.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/9/28.
 */

public class StringUtils {
    public static boolean checkUserName(String name){
        if(name==null|| TextUtils.isEmpty(name)){
          return false;
        }
        return name.matches("^[a-zA-Z]\\w{2,19}$");

    }

    public static boolean checkPwd(String pwd){
        if(pwd==null|| TextUtils.isEmpty(pwd)){
            return false;
        }
        return pwd.matches("^[0-9]{3,20}$");
    }

    public static String getFirstIndex(String string){
        String str="";
        if(string!=null && !"".equals(string)){
            str= string.substring(0, 1);
        }
        return str.toUpperCase();
    }
}
