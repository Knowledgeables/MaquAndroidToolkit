/*

 */

package im.maqu.matk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import im.maqu.matk.constant.CstScheme;

/**
 * @author : Maqu
 * @date   : 2013-9-19
 * @desc   : 安装包工具类
 * public method
 * 	<li>isApkInstalled(Context, String)		判断指定包名的应用是否安装 </li>
 * 	<li>installApk(Context, String)			安装应用</li>
 * 	<li>uninstallApk(Context, String)		卸载指定包名的应用</li>
 * 	<li>launchApk(Context, String)			打开指定包名的应用 </li>
 * 	<li>isUserApk(Context, String)			判断指定包名的应用是否是第三方应用 </li>
 * 	<li>isSystemApk(Context)				判断当前应用是否是系统应用 </li>
 * 	<li>isSystemApk(Context, String)		判断指定包名的应用是否是系统应用</li>
 */
public class UtilPackage {
	
	/**
	 * 判断指定包名的应用是否安装
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isApkInstalled(Context context, String packageName) {
		if(context == null || UtilString.isBlank(packageName)) return false;

		try {
			context.getPackageManager().getPackageInfo(packageName, 0);
			return true;
	    } catch (NameNotFoundException ex) {
	    	ex.printStackTrace();
	    }

	    return false;
	}

	/**
	 * 安装应用
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static boolean installApk(Context context, String filePath) {
        if (context == null || UtilFile.getFileSize(filePath) <=0) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(CstScheme.FILE + filePath), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

	/**
	 * 卸载指定包名的应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean uninstallApk(Context context, String packageName) {
		if(context==null
				|| UtilString.isBlank(packageName)
				|| !isApkInstalled(context, packageName)) return false;

		Uri uri = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		context.startActivity(intent);
		return true;
	}

	/**
	 * 打开指定包名的应用
	 * @param context
	 * @param packageName
	 */
	public static void launchApk(Context context, String packageName) {
		if(context == null || UtilString.isBlank(packageName)) return;

		try {
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			context.startActivity(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 判断指定包名的应用是否是第三方应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isUserApk(Context context, String packageName) {
		return isSystemApk(context, packageName);
	}

	/**
	 * 判断当前应用是否是系统应用
	 * @param context
	 * @return
	 */
	public static boolean isSystemApk(Context context) {
		return isSystemApk(context, context.getPackageName());
	}

	/**
	 * 判断指定包名的应用是否是系统应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApk(Context context, String packageName) {
        if(context == null || UtilString.isBlank(packageName)) return false;

        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (NameNotFoundException ex) {
            ex.printStackTrace();
        }

        return false;
    }

	/**
	 * 获取已安装的应用
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getInstalledApks(Context context) {
		if(context == null) {
			return null;
		}

		return  context.getPackageManager().getInstalledPackages(0);
	}

	/**
	 * 获取已安装的第三方应用
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getInstalledUserApks(Context context) {
		if(context == null) {
			return null;
		}

		List<PackageInfo> infos =  context.getPackageManager().getInstalledPackages(0);
		List<PackageInfo> userInfos = new ArrayList<PackageInfo>();

		if(UtilList.isNotEmpty(infos)) {
			for (PackageInfo info : infos) {
				if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
					userInfos.add(info);
				}
			}
		}
		
		return userInfos;
	}
	
	/**
	 * 获取已安装的系统应用
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getInstalledSystemApks(Context context) {
		if(context == null) {
			return null;
		}

		List<PackageInfo> infos =  context.getPackageManager().getInstalledPackages(0);
		List<PackageInfo> systemInfos = new ArrayList<PackageInfo>();

		if(UtilList.isNotEmpty(infos)) {
			for (PackageInfo info : infos) {
				if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
					systemInfos.add(info);
				}
			}
		}
		
		return systemInfos;
	}
}
