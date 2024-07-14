package com.gentech.proxy.model;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ProxyManager implements InitializingBean {

    private List<Proxy> activeProxies = new ArrayList<>();

    @Autowired
    private ProxyCrawler proxyCrawler;
    @Autowired
    private ProxyChecker proxyChecker;

    // Scheduled task to run every 5 minutes
    @Scheduled(fixedRate = 300000) // 300000 milliseconds = 5 minutes
    public void crawlAndCheckProxies() {
        long startTime = System.currentTimeMillis();
        System.out.println("bắt đầu chạy vào lúc: " + startTime);
        System.out.println(" crawlAndCheckProxies()");
        try {
            List<Proxy> proxies = proxyCrawler.fetchProxies();
            System.out.println("In ra list proxy sau khi craw"+proxies.toString());
            proxyChecker.checkProxies(proxies);
            System.out.println("In ra list proxy sau khi check"+ proxies.toString());
            updateActiveProxies(proxies);
            System.out.println("In ra activeProxies  sau khi updateActiveProxies");
            System.out.println(activeProxies.toString());
            long endTime = System.currentTimeMillis();
            System.out.println("Tiến trình kết thúc lúc: " + endTime);
            long executionTime = endTime - startTime;
            System.out.println("Thời gian thực thi: " + executionTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void updateActiveProxies(List<Proxy> proxies) {
        System.out.println(" updateActiveProxies(List<Proxy> proxies)");
        activeProxies.clear();
        for (Proxy proxy : proxies) {
            if (proxy.isLive()) {
                activeProxies.add(proxy);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(" crawlAndCheckProxies()");
//        crawlAndCheckProxies(); // Start initial crawling and checking when the application starts
    }
}