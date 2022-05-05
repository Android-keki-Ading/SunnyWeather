package com.lsilencej.sunnyweather.logic.network;

import com.lsilencej.sunnyweather.SunnyWeatherApplication;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** API: https://api.caiyunapp.com/v2/place?query=北京&token={token}& lang=zh_CN
 * @date: 2022-05-02 16:41
 */
public interface PlaceService {

    /** @GET 注解： 当调用searchPlaces方法，Retrofit就会自动发起一个GET请求
     *  同时API中的query参数需要动态指定，于是使用@Query
     *  另外，该方法返回Call<PlaceResponse>, 作用是自动将返回的Json反序列为PlaceResponse对象
     * @date: 2022-05-02 16:38
     */
    @GET("/v2/place?token=" + SunnyWeatherApplication.TOKEN + "&lang=zh_CN")
    public Call<PlaceResponse> searchPlaces(@Query("query") String query);

}
