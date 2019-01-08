package com.example.huxinwu.ipaddress.action;


import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpUtils {

    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;

    private static OkHttpUtils instance;
    private OkHttpClient client;


    /**
     * 构造方法
     * @return
     */
    private OkHttpUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
 ;
        client = builder.build();
    }

    /**
     * 获取单例instance
     * @return
     */
    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取OkHttpClient
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return client;
    }


    public String get(String url) throws Exception{
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.get().url(url).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }
        return null;
    }

}
