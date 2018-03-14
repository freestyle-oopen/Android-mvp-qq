package aiyagirl.nanchen.com.myapplication.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ActivityUtil {
    public ActivityUtil() {
    }

    public static void startActivityForResult(@NonNull Activity context, @NonNull Class<? extends Activity> target, int requestCode) {
        Intent intent = new Intent(context.getApplicationContext(), target);
        context.startActivityForResult(intent, requestCode);

    }

    public static void startActivityForResult(@NonNull Activity context, @NonNull Class<? extends Activity> target, int requestCode, Bundle data) {
        Intent intent = new Intent(context.getApplicationContext(), target);
        intent.putExtras(data);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResultFromFragment(@NonNull Fragment fragment, @NonNull Class<? extends Activity> target, int requestCode) {
        Intent intent = new Intent(fragment.getActivity().getApplicationContext(), target);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResultFromFragment(@NonNull Fragment fragment, @NonNull Class<? extends Activity> target, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fragment.getActivity().getApplicationContext(), target);
        intent.putExtras(bundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(@NonNull Activity context, @NonNull Class<? extends Activity> target) {
        Intent intent = new Intent(context.getApplicationContext(), target);
        context.startActivity(intent);
    }

    public static void startActivity(@NonNull Activity context, @NonNull Class<? extends Activity> target, Bundle data) {
        Intent intent = new Intent(context.getApplicationContext(), target);
        intent.putExtras(data);
        context.startActivity(intent);
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}

