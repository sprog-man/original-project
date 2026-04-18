package com.waimai.skycommon.context;


/**
 * 使用 ThreadLocal 存储当前登录用户的 ID,实现在同一线程中共享用户信息
 */

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     *  存当前用户 ID 到线程上下文
     */

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取当前用户 ID
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }

    /**
     * 移除当前用户 ID(防止内存泄漏)
     */

    public static void removeCurrentId(){
        threadLocal.remove();
    }
}
