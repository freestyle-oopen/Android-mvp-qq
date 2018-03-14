package aiyagirl.nanchen.com.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/10/25.
 */

public class SharedPreferencesUtil {
    private static final String FILE_NAME = "config_info";
    private static SharedPreferencesUtil instance;
    private Context context;

    private SharedPreferencesUtil(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized SharedPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtil(context);
        }
        return instance;
    }

    public <T> T getData(String key, Class<T> classOfT) {
        if (this.context == null) {
            return null;
        } else {
            SharedPreferences s = this.context.getSharedPreferences(FILE_NAME, 0);
            if (s == null) {
                return null;
            } else {
                String dataJson = s.getString(key, "");
                if (TextUtils.isEmpty(dataJson)) {
                    return null;
                } else {
                    try {

                        return GsonUtil.getGson().fromJson(dataJson, classOfT);
                    } catch (Exception var6) {
                        var6.printStackTrace();
                        return null;
                    }
                }
            }
        }
    }


    public <T> T getData(String key, Type type) {
        if (this.context == null) {
            return null;
        } else {
            SharedPreferences s = this.context.getSharedPreferences(FILE_NAME, 0);
            if (s == null) {
                return null;
            } else {
                String dataJson = s.getString(key, "");
                if (TextUtils.isEmpty(dataJson)) {
                    return null;
                } else {
                    try {
                        return GsonUtil.getGson().fromJson(dataJson, type);
                    } catch (Exception var6) {
                        var6.printStackTrace();
                        return null;
                    }
                }
            }
        }
    }


    public void saveData(String key, Object data) {
        if (this.context != null) {
            SharedPreferences s = this.context.getSharedPreferences(FILE_NAME, 0);
            if (s != null) {
                SharedPreferences.Editor editor = s.edit();
                String dataJson = GsonUtil.toJson(data);
                editor.putString(key, dataJson);
                editor.commit();
            }
        }
    }

    public void removeData(String key) {
        if (this.context != null) {
            SharedPreferences s = this.context.getSharedPreferences(FILE_NAME, 0);
            if (s != null) {
                SharedPreferences.Editor editor = s.edit();
                editor.remove(key);
                editor.commit();
            }
        }
    }

    public void clearData() {
        if (this.context != null) {
            SharedPreferences s = this.context.getSharedPreferences(FILE_NAME, 0);
            if (s != null) {
                SharedPreferences.Editor editor = s.edit();
                editor.clear();
                editor.commit();
            }
        }
    }

}