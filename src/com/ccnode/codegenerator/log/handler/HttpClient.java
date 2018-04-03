//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log.handler;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class HttpClient {
    private static OkHttpClient client;
    private static ExecutorService executorService;
    private static String url;
    public static final MediaType JSON;
    private static Gson gson;

    public HttpClient() {
    }

    public static void sendDataToLog(MybatisLogRequest logRequest) {
        executorService.submit(() -> {
            String json = gson.toJson(logRequest);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = (new Builder()).url(url).post(body).build();
            Response response = null;

            try {
                response = client.newCall(request).execute();
            } catch (IOException var9) {
                ;
            } finally {
                if (response != null) {
                    response.close();
                }

            }

        });
    }

    static {
        client = (new OkHttpClient()).newBuilder().connectTimeout(5L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).build();
        executorService = Executors.newFixedThreadPool(1);
        url = "http://brucege.com/mybatislog/add";
        JSON = MediaType.parse("application/json; charset=utf-8");
        gson = new Gson();
    }
}
