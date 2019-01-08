package com.example.huxinwu.ipaddress.action;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.huxinwu.ipaddress.model.AddressResult;
import com.example.huxinwu.ipaddress.model.IPResult;

public class IPAction {


    public IPResult getIPResult(String ip) throws Exception{
        IPResult response = null;
        String url = "http://ip-api.com/json/"+ip+"?fields=520191&lang=en";
        String result = OkHttpUtils.getInstance().get(url);
        if(!TextUtils.isEmpty(result)){
            Log.e("result", result);
            response = JSON.parseObject(result, IPResult.class);
        }
        return response;
    }

    public AddressResult getAddressResult(String latlng, String query) throws Exception{
        AddressResult response = null;
        //https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlng+"&sensor=true_or_false&key=AIzaSyBgkDffWb9-hePhEbFu-UM8Cg9ntyXkeQs
        //https://maps.googleapis.com/maps/api/place/textsearch/json?query=Street&location=42.3675294,-71.186966&radius=5000&key=AIzaSyBgkDffWb9-hePhEbFu-UM8Cg9ntyXkeQs
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        if(TextUtils.isEmpty(query)){
            url.append("query=").append(query).append("&");
        }
        url.append("location=").append(latlng).append("&radius=10000&key=AIzaSyBgkDffWb9-hePhEbFu-UM8Cg9ntyXkeQs");
        String result = OkHttpUtils.getInstance().get(url.toString());
        if(!TextUtils.isEmpty(result)){
            Log.e("result", result);
            response = JSON.parseObject(result, AddressResult.class);
        }
        return response;
    }

}
