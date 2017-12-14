package com.vdsirotkin.telegram.resendbot.destiny2.executors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.BaseResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public abstract class BaseExecutor<R, T extends BaseResponse> {

    private static final Logger log = Logger.getLogger(BaseExecutor.class);

    private static HttpClient httpClient = HttpClientBuilder.create().build();
    private Gson gson = new Gson();
    private static String BUNGIE_KEY;
    public final static String BASE_URL_NO_PLATFORM = "https://bungie.net";
    final static String BASE_URL = BASE_URL_NO_PLATFORM + "/Platform";

    public abstract T executeMethod(R request);

    protected final T execute(HttpGet get, Class<T> tClass) {
        get.addHeader("X-API-Key", BUNGIE_KEY);
        try {
            HttpResponse response = httpClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String json = reader.lines().collect(Collectors.joining());
            T t = null;
            try {
                t = gson.fromJson(json, tClass);
            } catch (JsonSyntaxException e) {
                log.error(e, e);
            }
            get.releaseConnection();
            return t;
        } catch (IOException e) {
            log.error(e, e);
        }
        return null;
    }

    public static void setBungieKey(String bungieKey) {
        BaseExecutor.BUNGIE_KEY = bungieKey;
    }

}
