package com.qiu.base.lib.tools.sys;

import static android.content.pm.PackageManager.MATCH_UNINSTALLED_PACKAGES;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.qiu.base.lib.utils.App;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Catch mobile base info, such as dip/px, system version, and so on.
 */
public class MobileInfoTools {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    private MobileInfoTools() {
    }

    @NonNull
    public static float[] getScreenDpi(@NonNull Context context) {
        float[] result = new float[2];
        result[0] = context.getResources().getDisplayMetrics().xdpi;
        result[1] = context.getResources().getDisplayMetrics().ydpi;
        return result;
    }

    @NonNull
    public static List<PackageInfo> getInstalledAppList() {
        final List<PackageInfo> infoList = new ArrayList<>();
        final PackageManager packageManager = App.i().getApplicationContext().getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo info : packageInfoList) {
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                infoList.add(info);
            }
        }
        return infoList;
    }

    @NonNull
    public static String getAppName(@NonNull PackageInfo packageInfo) {
        return packageInfo.applicationInfo.loadLabel(App.i().getPackageManager()).toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean checkNotificationPermission(@NonNull String packageName) {
        PackageManager pm = App.i().getPackageManager();
        try {
            AppOpsManager appOps = (AppOpsManager) App.i().getApplicationContext()
                    .getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo =
                    pm.getApplicationInfo(packageName, MATCH_UNINSTALLED_PACKAGES);
            int uid = appInfo.uid;
            Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,
                    Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(appOps, value, uid, packageName)
                    == AppOpsManager.MODE_ALLOWED);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
