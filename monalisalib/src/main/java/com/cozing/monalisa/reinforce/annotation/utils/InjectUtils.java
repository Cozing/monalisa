package com.cozing.monalisa.reinforce.annotation.utils;

import android.app.Activity;

import com.cozing.monalisa.reinforce.annotation.inject.BindView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * desc:获取到了activity的Class，接着获得类中的所有字段，遍历字段，如果有ViewInject注解，
 *      则获取注解的中的值，通过获取并执行类中的方法（findViewById）来获得对应View的实例，最后把实例赋值给当前的字段.
 *
 * useage:使用注解的时候，只需要在定义View的时候，在View的字段上添加对应的注解，把Id传入，
 *      然后在onCreate方法中调用这个工具类的方法即可.
 *
 * Created by wanghuquan on 2018/2/9.
 */

public class InjectUtils {

    public static void injectViews(Activity activity){
        Class<? extends Activity> clazz = activity.getClass(); //获取Activity的class
        Field[] fields = clazz.getDeclaredFields();    //通过class获取activity所有公开字段
        for(Field field : fields){
            BindView bindView = field.getAnnotation(BindView.class);
            if(bindView != null){
                int resourceId = bindView.value();      //获取字段注解的参数，就是传入的控件Id
                if(resourceId != -1){
                    try {
                        // 获取类中的findViewById方法，参数为int
                        Method method = clazz.getMethod("findViewById", int.class);
                        //执行该方法，返回一个View对象
                        Object resouceView = method.invoke(activity, resourceId);
                        field.setAccessible(true);
                        //把字段的值设置为该view的实例
                        field.set(activity, resouceView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
