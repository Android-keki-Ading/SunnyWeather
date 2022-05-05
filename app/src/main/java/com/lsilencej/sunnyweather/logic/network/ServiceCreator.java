package com.lsilencej.sunnyweather.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Retrofit对象(retrofit)是全局通用的，使用单例模式创建。
 * @package: com.lsilencej.sunnyweather.logic.network
 * @className: ServiceCreator
 * @author: Ading
 * @time: 2022-05-02 19:29
 */
public class ServiceCreator {

    /** 配置根路径（服务器）
     * 然后再指定服务器接口地址时候，只需使用相对路径。
     * @date: 2022-05-02 17:15
     */
    private static final String BASE_URL = "https://api.caiyunapp.com";

    /** 构建Retrofit对象，使用Retrofit.Builder()
     * .addConverterFactory() : 指定Retrofit解析数据时使用的转换库，即GsonConverterFactory
     * @date: 2022-05-02 19:10
     */
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /** 调用静态方法ServiceCreator.create()方法，只需要传入具体Service接口所对应的Class类型
     * 即可create一个该接口的动态代理对象。
     *
     * <p>使用public修饰符，暴露创建接口
     * @date: 2022-05-02 19:23
     */
    public static <T> T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
