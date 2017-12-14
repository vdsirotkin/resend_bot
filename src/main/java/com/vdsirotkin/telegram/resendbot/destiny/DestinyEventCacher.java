package com.vdsirotkin.telegram.resendbot.destiny;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by vitalijsirotkin on 26.03.17.
 */
@Component
public class DestinyEventCacher {

    @Autowired
    ConfigManager cm;

    private static HttpClient httpClient = HttpClientBuilder.create().build();

    @CacheEvict(cacheNames = "publicevents", allEntries = true)
    public void clearCache() {

    }

    @Cacheable(cacheNames = "publicevents")
    public AdvisorData getPublicEvents() throws IOException {
        HttpGet get = new HttpGet("https://www.bungie.net/Platform/Destiny/Advisors/V2/");
        get.addHeader("X-API-Key", cm.getBungieApiKey());
        HttpResponse response = httpClient.execute(get);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
        get.releaseConnection();

        AdvisorData data = new AdvisorData();
        JsonObject activities = object.getAsJsonObject("Response").getAsJsonObject("data").getAsJsonObject("activities");

        // 1. nightfall
        JsonObject nightfall = activities.getAsJsonObject("nightfall");
        String activityHash = nightfall.getAsJsonObject("display").getAsJsonPrimitive("activityHash").getAsString();
        List<String> modifiersArr = getModifiers(nightfall);
        data.getNightfall().getModifiers().addAll(modifiersArr);
        String finalActivityHash = activityHash;
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> getActivityName(finalActivityHash));
        cf1.thenApply(s -> {
            data.getNightfall().setName(s);
            return null;
        });

        // 2. heroic strikes
        JsonObject heroicstrike = activities.getAsJsonObject("heroicstrike");
        activityHash = heroicstrike.getAsJsonObject("display").getAsJsonPrimitive("activityHash").getAsString();
        modifiersArr = getModifiers(heroicstrike);
        data.getSivaHeroic().getModifiers().addAll(modifiersArr);
        String finalActivityHash1 = activityHash;
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> getActivityName(finalActivityHash1));
        cf2.thenApply(s -> {
            data.getSivaHeroic().setName(s);
            return null;
        });

        // 3. weekly crucible
        JsonObject weeklycrucible = activities.getAsJsonObject("weeklycrucible");
        activityHash = weeklycrucible.getAsJsonObject("display").getAsJsonPrimitive("activityHash").getAsString();
        String finalActivityHash2 = activityHash;
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> getActivityName(finalActivityHash2));
        cf3.thenApply(s -> {
            data.setWeeklyCrucible(s);
            return null;
        });

        // 4. weekly raid
        JsonObject weeklyraid = activities.getAsJsonObject("weeklyfeaturedraid");
        activityHash = weeklyraid.getAsJsonObject("display").getAsJsonPrimitive("activityHash").getAsString();
        modifiersArr = getModifiers(weeklyraid);
        data.getWeeklyRaid().getModifiers().addAll(modifiersArr);
        String finalActivityHash3 = activityHash;
        CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() -> getActivityName(finalActivityHash3));
        cf4.thenApply(s -> {
            data.getWeeklyRaid().setName(s);
            return null;
        });

        CompletableFuture.allOf(cf1, cf2, cf3, cf4).join();
        return data;
    }

    private List<String> getModifiers(JsonObject strike) {
        JsonArray modifiersArr;
        JsonObject extended = strike.getAsJsonObject("extended");
        if (extended == null) {
            JsonArray activityTiers = strike.getAsJsonArray("activityTiers");
            extended = activityTiers.get(0).getAsJsonObject();
        }

        JsonArray array = extended.getAsJsonArray("skullCategories");
        JsonElement modifiers = StreamSupport.stream(array.spliterator(), true).filter(jsonElement -> "Modifiers".equals(jsonElement.getAsJsonObject().get("title").getAsString())).findFirst().get();
        modifiersArr = modifiers.getAsJsonObject().getAsJsonArray("skulls");
        List<String> list = StreamSupport.stream(modifiersArr.spliterator(), true).map(jsonElement -> jsonElement.getAsJsonObject().get("displayName").getAsString()).collect(Collectors.toList());
        return list;
    }

    private String getActivityName(String activityHash) {
        HttpGet get = new HttpGet("https://www.bungie.net/Platform/Destiny/Manifest/Activity/" + activityHash);
        get.addHeader("X-API-Key", cm.getBungieApiKey());
        HttpResponse response = null;
        String strikeName = "";
        try {
            response = httpClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
            strikeName = object.getAsJsonObject("Response").getAsJsonObject("data").getAsJsonObject("activity").get("activityName").getAsString();
            get.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strikeName;
    }

}
