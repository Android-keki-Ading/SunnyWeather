package com.lsilencej.sunnyweather.logic.network;

import com.lsilencej.sunnyweather.logic.model.DailyResponse.DailyResponse;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.PlaceResponse;
import com.lsilencej.sunnyweather.logic.model.RealtimeResponse.RealtimeResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/** 这是一个非常关键的类，且有许多高级技巧
 * @package: com.lsilencej.sunnyweather.logic.network
 * @className: SunnyWeatherNetwork
 * @author: Ading
 * @time: 2022-05-02 19:44
 */
public class SunnyWeatherNetwork {

    /** 使用ServiceCreator.create(serviceClass)创建动态代理对象
     * @date: 2022-05-02 19:45
     */
    private static PlaceService placeService = ServiceCreator.create(PlaceService.class);
    private static WeatherService weatherService = ServiceCreator.create(WeatherService.class);

    /** 调用动态代理对象placeService的接口方法: searchPlaces()
     * <p>请求城市数据</p>
     * @method
     * @date: 2022-05-02 19:46
     * @param  * String query
     * @return
     */
    public static PlaceResponse searchPlace(String query) throws IOException {
        return await(placeService.searchPlaces(query));
    }

    public static RealtimeResponse getRealtimeWeather(String lng, String lat) throws IOException {
        return await(weatherService.getRealtimeWeather(lng, lat));
    }

    public static DailyResponse getDailyWeather(String lng, String lat) throws IOException {
        return await(weatherService.getDailyWeather(lng, lat));
    }


    /** 不同的Service返回的数据类型不同，所以要使用T泛型。<br>
     * 这是一个挂起函数<br>
     * <p></p>
     * @method  await(Call<T>)
     * @date: 2022-05-02 20:12
     * @param  * T 泛型
     * @return
     */
    private static <T> T await(Call<T> call) throws IOException {
        T result = null;
        try {
            Response<T> response = call.execute();
            result = response.body();
            if(result == null){
                throw new SearchException("response.body() is null!");
            }
        } catch (IOException e){
            throw new SearchException("call.execute() fail!");
        }
        return result;
    }

}
