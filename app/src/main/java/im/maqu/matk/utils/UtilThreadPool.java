/*

 */

package im.maqu.matk.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author : Maqu
 * @date   : 2013-9-19
 * @desc   : 线程池工具类
 */
public class UtilThreadPool {
	//默认建议使用线程池的大小
    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();
   
    /**
     * 获取建议使用线程池的大小，最多个数为16个
     * @return
     */
    public static int getDefaultThreadPoolSize() {
        return getDefaultThreadPoolSize(16);
    }

    /**
     * 获取建议使用线程池的大小，大小将为num =（4 * cpu + 1）， 如果num > max,
     * 则大小为max
     * @param max 最多可用线程池数
     * @return
     */
    public static int getDefaultThreadPoolSize(int max) { 
        int processors = 4 * Runtime.getRuntime().availableProcessors() + 1;
        return processors > max ? max : processors;
    }
    
    /**
     * 获取单线程池
     * @return
     */
    public static ExecutorService getSingleThreadPool() {
    	return Executors.newSingleThreadExecutor();
    }
    
    /**
     * 获取缓存线程池
     * @return
     */
    public static ExecutorService getCachedThreadPool() {
    	return Executors.newCachedThreadPool();
    }
    
    /**
     * 获取固定线程池，线程池大小请查看getDefaultThreadPoolSize(int)
     * @param size
     * @return
     */
    public static ExecutorService getFixedThreadPool(int size) {
    	return Executors.newFixedThreadPool(getDefaultThreadPoolSize(size)); 
    }
    
    /**
     * 获取固定线程池，线程池大小为8个
     * @return
     */
    public static ExecutorService getFixedThreasdPool() {
    	return Executors.newFixedThreadPool(getDefaultThreadPoolSize()); 
    }
    
    /**
     * 获取定时线程池，线程池大小请查看getDefaultThreadPoolSize(int)
     * @param size
     * @return
     */
    public static ScheduledExecutorService getScheduledThreadPool(int size) {
    	return Executors.newScheduledThreadPool(getDefaultThreadPoolSize(size));
    }
    
    /**
     * 获取定时线程池，线程池大小为8个
     * @return
     */
    public static ScheduledExecutorService getScheduledThreadPool() {
    	return Executors.newScheduledThreadPool(getDefaultThreadPoolSize());
    }
}
