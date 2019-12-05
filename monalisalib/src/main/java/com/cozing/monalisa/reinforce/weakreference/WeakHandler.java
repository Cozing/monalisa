package com.cozing.monalisa.reinforce.weakreference;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * desc:弱引用Handler，具有弱引用特性的Handler.
 * 内部维护一个弱引用对象Activity/Fragment，通常用于持有Activity/Fragment等上下文对象，当Handler处理消息时，
 * 如果宿主对象已被GC（垃圾回收机制）回收，则回调onHandle
 *
 * <pre>
 *     //创建弱引用Handler对象，传入宿主对象，该宿主会被Handler弱引用
 *     private final MyHandler myHandler = new MyHandler(this);
 *
 *     private static class MyHandler extends WeakHandler<MyActivity>{
 *          private static final int HANDLER_MSG  = 1;
 *
 *          public MyHandler(MyActivity host){
 *              super(Looper.getMainLooper(), host);
 *          }
 *
 *          //弱引用宿主存在的情况下回调此方法
 *          protected void handleMessageWithHost(Message msg, MyActivity host) {
 *              switch(msg.what){
 *                  case HANDLER_MSG:
 *
 *                      //do something
 *
 *                      break;
 *
 *                  default:
 *                      break;
 *              }
 *          }
 *
 *          //弱引用宿主不存在的情况下回调此方法（非必须重写）
 *          protected void handleMessageWithoutHost(Message msg) {}
 *     }
 * </pre>
 *
 *
 * Created by wanghuquan on 2018/2/9.
 */

public abstract class WeakHandler<T> extends Handler{

    private WeakReference<T> host;

    /**
     * 构造方法，创建WeakHandler实例
     *
     * @param host 弱引用宿主对象，通常是Activity/Fragment
     */
    public WeakHandler(T host){
        super();
        this.host = new WeakReference<T>(host);
    }

    /**
     * @param looper Looper,Looper.getMainLooper()或者Looper.myLooper()得到
     * @param host 弱引用宿主对象，通常是Activity/Fragment
     */
    public WeakHandler(Looper looper, T host) {
        super(looper);
        this.host = new WeakReference<T>(host);
    }

    /**
     * 获取弱引用宿主对象，如果被GC回收不存在，返回Null
     */
    protected T getHost(){
        if(host == null) return null;
        return host.get();
    }

    /**
     * 改造Handler的handleMessage();
     * 如果弱引用宿主对象存在，回调handleMessageWithHost()，不存在回调handleMessageWithoutHost();
     */
    @Override
    public void handleMessage(Message msg) {

        final T host = getHost();
        if(host == null){
            handleMessageWithoutHost(msg);
        }else{
            handleMessageWithHost(msg, host);
        }
    }

    /**
     * 当宿主存在（未被GC回收）时，调用此方法
     *
     * @param msg Handlee处理的消息
     * @param host 宿主对象，不会为空
     */
    public abstract void handleMessageWithHost(Message msg, T host);

    /**
     * 当宿主不存在（已被GC回收）时，调用此方法，非必须重写
     *
     * @param msg Handler处理的消息
     */
    public void handleMessageWithoutHost(Message msg){

    }
}
