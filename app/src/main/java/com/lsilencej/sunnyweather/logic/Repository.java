package com.lsilencej.sunnyweather.logic;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lsilencej.sunnyweather.logic.dao.PlaceDao;
import com.lsilencej.sunnyweather.logic.model.DailyResponse.DailyResponse;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.Place;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.PlaceResponse;
import com.lsilencej.sunnyweather.logic.model.RealtimeResponse.RealtimeResponse;
import com.lsilencej.sunnyweather.logic.model.Weather.Weather;
import com.lsilencej.sunnyweather.logic.network.SunnyWeatherNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 单例模式
 * @package: com.lsilencej.sunnyweather.logic
 * @className: Repository
 * @author: Ading
 * @time: 2022-05-02 20:22
 */
public class Repository {

    public static LiveData<PlaceResponse> searchPlaces(final String query) {
        final MutableLiveData<PlaceResponse> placeResponseMutableLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /** 使用PlaceResponse对象接受网络响应结果反序列化的对象
                     * @date: 2022-05-02 20:28
                     */
                    PlaceResponse placeResponse = SunnyWeatherNetwork.searchPlace(query);
                    placeResponseMutableLiveData.postValue(placeResponse);
                } catch (IOException e) {
                    PlaceResponse placeResponse = new PlaceResponse("failed", "网络出错", null);
                    placeResponseMutableLiveData.postValue(placeResponse);
                }
            }
        }).start();
        return placeResponseMutableLiveData;
    }

    public static LiveData<Weather> refreshWeather(final String lng, final String lat) {
        final MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<RealtimeResponse> realtimeResponses = new ArrayList<>();
                final List<DailyResponse> dailyResponses = new ArrayList<>();
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            realtimeResponses.add(SunnyWeatherNetwork.getRealtimeWeather(lng, lat));
                        } catch (IOException e) {
                            RealtimeResponse realtimeResponse = new RealtimeResponse("failed", "网络出错", null);
                            realtimeResponses.add(realtimeResponse);
                        }
                    }
                });
                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dailyResponses.add(SunnyWeatherNetwork.getDailyWeather(lng, lat));
                        } catch (IOException e) {
                            DailyResponse dailyResponse = new DailyResponse("failed", "网络出错", null);
                            dailyResponses.add(dailyResponse);
                        }
                    }
                });
                t1.start();
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    Log.e("Repository", e.toString() );
                }
                try {
                    if (realtimeResponses.size() == 1 && realtimeResponses.get(0).getStatus().equals("ok") && dailyResponses.size() == 1 && dailyResponses.get(0).getStatus().equals("ok")) {
                        weatherMutableLiveData.postValue(new Weather(realtimeResponses.get(0).getResult().getRealtime(), dailyResponses.get(0).getResult().getDaily()));
                    } else {
                        weatherMutableLiveData.postValue(new Weather("failed", "网络出错", null, null));
                    }
                } catch (NullPointerException e) {
                    weatherMutableLiveData.postValue(new Weather("failed", "系统出错", null, null));
                }
            }
        }).start();
        return weatherMutableLiveData;
    }

    public static void savePlace(Place place) {
        PlaceDao.savePlace(place);
    }

    public static Place getSavedPlace() {
        return PlaceDao.getSavedPlace();
    }

    public static Boolean isPlaceSaved() {
        return PlaceDao.isPlaceSaved();
    }

}
