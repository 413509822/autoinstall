package com.example.admin.autoinstall;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class check extends Service {
    private Context context;
    private static String apkpath=null;
    private String pckname;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        context=this;

//        Toast.makeText(install.context,"666",Toast.LENGTH_LONG).show();
        Log.i("apk",apkpath+1);
        if (apkpath!=null){
            pckname=getpn(apkpath);
            Log.i("apk",pckname);
            Log.i("apk",getApplicationNameByPackageName(context,pckname)+2);
            install();
        }
        Log.i("se",apkpath+66);
        stopSelf();

    }

        public static String getApplicationNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String Name ; try {
            Name=pm.getApplicationLabel(pm.getApplicationInfo(packageName,PackageManager.GET_META_DATA)).toString(); }
            catch (PackageManager.NameNotFoundException e) {
            Name = "" ; }
            return Name ;}


    public static boolean isInstalled(Context context, String packageName) {
        boolean installed = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo in : installedApplications) {
            if (packageName.equals(in.packageName)) {
                installed = true;
                break;
            } else {
                installed = false;
            }
        }
        return installed;
    }
    public static void setApkpath(String apkpath) {
        check.apkpath = apkpath;
      //  Log.i("apk",apkpath);
    }
     String getpn(String s){

        PackageManager packageManager=getPackageManager();
        PackageInfo packageInfo=packageManager.getPackageArchiveInfo(s,0);
        ApplicationInfo ai=packageInfo.applicationInfo;
        ai.sourceDir=s;
        ai.publicSourceDir=s;
        return ai.packageName;
    }
    private void install(){
        //  Toast.makeText(this,"start",Toast.LENGTH_LONG).show();
        //JavaShellUtil.execCommand("su ");
        //  Log.i("install",strings[1]);
     //   Log.i("oncli",apkpath);
      //  Log.i("nex", pckname);
              JavaShellUtil.execCommand("pm install -r "+apkpath,true);
           //   Log.i("shell","finish");
              if (JavaShellUtil.CommandResult.result==0)
         Toast.makeText(this,getApplicationNameByPackageName(this,pckname)+"已安装",Toast.LENGTH_SHORT).show();
         else
                  Toast.makeText(this,getApplicationNameByPackageName(this,pckname)+"安装失败",Toast.LENGTH_SHORT).show();
    }

}
