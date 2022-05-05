package com.lsilencej.sunnyweather.logic.model.PlaceResponse;

import com.google.gson.annotations.SerializedName;

/** Place实体类对应json中的places数组
 * @package: com.lsilencej.sunnyweather.logic.model.PlaceResponse
 * @className: Place
 * @author: Ading
 * @time: 2022-05-02 16:33
 */
public class Place {

    private String name;  // 地区名
    private Location location; // 经纬度

    /** 使用@SerializedName注解，可以将自定义的字段名与json数据里面的字段对应起来；
     * @date: 2022-05-02 16:32
     */
    @SerializedName("formatted_address")
    private String address;  //地区地址

    public Place(String name, Location location, String address) {
        this.name = name;
        this.location = location;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
