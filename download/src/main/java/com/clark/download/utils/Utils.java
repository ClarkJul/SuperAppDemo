package com.clark.download.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * 从asset路径下读取对应文件转String输出
     * @param context
     * @return
     */
    public static String getJson(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 文件大小转为"1.2M"这样的字符串
     */
    public static String LengthToString(long length) {
        if (length / 1024 <= 0) {
            return "" + length + "B";
        } else if (length / 1024 > 0 && length / (1024 * 1024) <= 0) {
            return "" + length / 1024 + "KB";
        } else {
            return "" + length / (1024 * 1024) + "." + (length % (1024 * 1024)) / 1024 / 10 + "MB";
        }
    }

    /**
     * 字符串空格转为%20
     */
    public static String fillSpace(String orgName) {
        if (orgName.contains(" ")) {
            return orgName.replace(" ", "%20");
        }
        return orgName;
    }

    /**
     * 用于获取一个String的md5值
     */
    public static String getMd5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    /**
     * drawable转bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽  
        int w = drawable.getIntrinsicWidth();  
        int h = drawable.getIntrinsicHeight();  
  
        // 取 drawable 的颜色格式  
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap  
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布  
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);  
        // 把 drawable 内容画到画布中  
        drawable.draw(canvas);  
        return bitmap;  
    }

    public static void setWhiteStatusBar(Activity activity) {
        try {
            Class<?> c = Class.forName("android.view.View");
            Field field = c.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR");
            int property = (Integer) field.get(c);
            activity.getWindow().getDecorView().setSystemUiVisibility(property);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                activity.getWindow().setNavigationBarColor(0xfff0f0f0);
                View decorView = activity.getWindow().getDecorView();
                int lightFlag = Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 ? 0x4000 : 0x10;
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | lightFlag);
                try {
                    Method method = activity.getWindow().getClass().getMethod("setNavigationDividerEnable", new Class[] {boolean.class});
                    method.invoke(activity.getWindow(), new Object[]{true});
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 获取path中的apk文件
     * @param path
     * @return
     */
    public static List<File> getFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        List<File> list = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            if (files[i].getName().contains(".apk")){
                list.add(files[i]);
            }
        }
        return list;
    }

}
