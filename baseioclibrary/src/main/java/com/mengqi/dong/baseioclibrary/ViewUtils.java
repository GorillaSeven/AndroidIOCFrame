package com.mengqi.dong.baseioclibrary;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by dong on 2017/5/7.
 */

public class ViewUtils {

    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }

    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }

    public static void inject(View view,Object object){
        inject(new ViewFinder(view),object);
    }

    //兼容 上面三个方法  object-->发射需要去执行的类
    private static void inject(ViewFinder finder,Object object){
        injectField(finder,object);
        injectEvent(finder,object);
    }

    /**
     * 注入属性
     * @param finder
     * @param object
     */
    private static void injectField(ViewFinder finder,Object object){
        //1.获取类里面所有的属性
        Class<?> clazz = object.getClass();
        //获取所有属性包括私有和公有
        Field[] fields = clazz.getDeclaredFields();
        //2.获取ViewById的value值
        for (Field field: fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if(viewById!=null){
                //获取注解里面的id值--->R.id.tv_test
                int valueId = viewById.value();
                //3.findViewById找到View
                View view = finder.findViewById(valueId);
                if(view!=null){
                    //能够注入所有的修饰符 private public
                    field.setAccessible(true);
                    //4.动态的注入找到View
                    try {
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }
    private static void injectEvent(ViewFinder finder,Object object){
        //1.获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        //2.获取onClick里面value的值
        for (Method method:methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if(onClick!=null){
                int[] viewIds = onClick.value();
                for (int viewId:viewIds) {
                    //3.findViewById找到View
                    View view = finder.findViewById(viewId);

                    //扩展功能 检测网络
                    boolean  isCheckNet = method.getAnnotation(CheckNet.class) != null;


                    if(view!=null){
                        //4.setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method,object,isCheckNet));
                    }
                }
            }
        }
    }


    private static class DeclaredOnClickListener implements View.OnClickListener{
        private Method mMethod;
        private Object mObject;
        private boolean mIsCheckNet;
        public DeclaredOnClickListener(Method method,Object object,boolean isCheckNet){
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            if(mIsCheckNet){
                //检测是否有网
                if(isNetworkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(),"亲,你的网络不给力！",Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            //点击会调用此方法
            try {
                //能够注入所有的修饰符 private public
                mMethod.setAccessible(true);
                //5.反射执行方法
                mMethod.invoke(mObject,v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject,null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

}
