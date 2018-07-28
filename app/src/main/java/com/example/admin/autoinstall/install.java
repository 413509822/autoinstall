package com.example.admin.autoinstall;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class install  extends  Activity{
    private String apkPath,path;
    private Uri uri;
    public static Context context;
    private boolean isContentUri=false;;
   // private String[] strings={"su"," "};
    private StringBuffer stringBuffer,stringBuffer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=install.this;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(install.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            Toast.makeText(this,"请授予读写权限",Toast.LENGTH_LONG).show();
         //return
            finish();
        }else {
        finish();
        Intent intent=getIntent();
        stringBuffer=new StringBuffer(intent.getData().toString());
        //intent.gt
  //  next();
       // finish();
        //Log.i("onclick",stringBuffer.toString());
       // Log.i("info",getSourceApkPath(getIntent().getData()));
        uri=getIntent().getData();
        next();
   start();}
    }
    private void content(){
        File tempFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".apk");
        //if (isContentUri) {
            try {
                writeFile(getContentResolver().openInputStream(uri), tempFile);
                apkPath = tempFile.getPath();
             //   install(apkPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            check.setApkpath(apkPath);

    }
    public static int writeFile(InputStream is, File file) throws IOException {
        int code = -1;
        OutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[is.available()];
        while ((code = is.read(buf)) != -1) {
            fos.write(buf);
            fos.flush();
        }
        fos.close();
        is.close();
        return code;
    }



    private String getSourceApkPath(Uri uri1){
        //Intent intent1 = getIntent();
        Uri uri =uri1;
        if (uri != null) {
            this.uri = uri;
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                isContentUri = true;
            }
            return uri.getPath();
        }
        return null;
    }



    private boolean check(CharSequence charSequence){
           return  stringBuffer.toString().contains(charSequence);
}
private void next(){
    if (check("file://"))
     {
  check.setApkpath(stringBuffer.substring(7));

          // install(apkPath);
     }
   else{
   content(); Log.i("next",apkPath);
           }
    }
    /**
     * 应用是否安装
     * @param context
     * @param packageName
     * @return
     */


   private void  start(){
      startService(new Intent(this,check.class));
   }
}
