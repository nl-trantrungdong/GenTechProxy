package com.gentech.proxy.model;

import com.gentech.proxy.model.Proxy;
import com.gentech.proxy.service.ProxyService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProxyCrawler {

    private static final String API_URL = "https://api.openproxy.space/lists/http";

    public List<Proxy> fetchProxies() throws IOException {
        System.out.println(" fetchProxies()");
        List<Proxy> proxyList = new ArrayList<>();

        Document doc = Jsoup.connect(API_URL).ignoreContentType(true).get();
        String responseBody = doc.text();
        System.out.println("In ra dữ liệu sau khi gọi api"+ responseBody);

        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        for (JsonElement element : dataArray) {
            JsonObject dataObject = element.getAsJsonObject();
            JsonArray itemsArray = dataObject.getAsJsonArray("items");

            for (JsonElement item : itemsArray) {
                String proxyString = item.getAsString();
                String[] parts = proxyString.split(":");
                String ip = parts[0];
                int port = Integer.parseInt(parts[1]);

                Proxy proxy = new Proxy();
                proxy.setIp(ip);
                proxy.setPort(port);
                proxyList.add(proxy);
            }
        }
        return proxyList;
    }
}