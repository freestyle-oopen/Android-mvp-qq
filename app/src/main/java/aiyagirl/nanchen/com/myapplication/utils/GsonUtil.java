package aiyagirl.nanchen.com.myapplication.utils;

/**
 * Created by Administrator on 2017/10/25.
 */

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class GsonUtil {
    private static Gson gson = new Gson();

    public GsonUtil() {
    }

    public static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }
}
