package com.house.variety.commons.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/24 10:53
 * @描述 异步任务管理
 */
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    /**
     * 单例模式
     */
    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }


    /**
     * 描述  执行任务<br/>
     * 参数  [task] <br/>
     * 返回值  void <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/24 10:54
     */
    public void execute(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 描述  执行任务<br/>
     * 参数  [runnable] <br/>
     * 返回值  void <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/24 10:54
     */
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
