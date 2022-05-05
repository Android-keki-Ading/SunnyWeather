package com.lsilencej.sunnyweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;


/** Android 提供Application类，每次启动app，系统会自动初始化该类，于是可以利用这个类来设置全局Context
 * @package: com.lsilencej.sunnyweather
 * @className: SunnyWeatherApplication
 * @author: Ading
 * @time: 2022-05-02 15:48
 */
public class SunnyWeatherApplication extends Application {

    public static final String TOKEN = "EtDctMqJlaFYvJbU";

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    /** 这里获取的不是Activity或Service中的Context，而是Application的，
     * 它全局只会存在 一份实例, 且在整个应用程序的生命周期内都不会回收
     * 因此不存在内存泄漏风险， 可以用  @SuppressLint("StaticFieldLeak")忽略警告
     * @date: 2022-05-02 15:44
     */

    /**  给sunnyweather项目
     *  获取全局context
     * @date: 2022-05-02 15:57
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /** 最后还是需要在Adnroidmanifest.xml中
     * <application>指定SunnyWeatherApplication
     * @date: 2022-05-02 15:58
     */
    public static Context getContext() {
        return context;
    }
}
