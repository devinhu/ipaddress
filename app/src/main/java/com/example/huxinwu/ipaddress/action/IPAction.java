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

    public AddressResult getAddressResult(String latlng) throws Exception{
        AddressResult response = null;
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlng+"&sensor=true_or_false&key=AIzaSyBgkDffWb9-hePhEbFu-UM8Cg9ntyXkeQs";
        String result = OkHttpUtils.getInstance().get(url);
        if(!TextUtils.isEmpty(result)){
            Log.e("result", result);
            response = JSON.parseObject(result, AddressResult.class);
        }
        return response;
    }
}
