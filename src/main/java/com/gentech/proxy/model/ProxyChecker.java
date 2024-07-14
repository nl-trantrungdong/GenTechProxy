package com.gentech.proxy.model;

import com.gentech.proxy.model.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Component
public class ProxyChecker {

    private static final int THREAD_POOL_SIZE = 20; // Số luồng tối đa

    public void checkProxies(List<Proxy> proxies) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        List<Future<Proxy>> futures = new ArrayList<>(); // Khởi tạo danh sách futures
        // Đối tượng đồng bộ để sử dụng trong lời gọi System.out.print
        Object lock = new Object();
        for (Proxy proxy : proxies) {
            Future<Proxy> future = executor.submit(new ProxyCheckTask(proxy,lock));
            futures.add(future);
        }

        for (Future<Proxy> future : futures) {
            try {
                Proxy proxy = future.get();
                // Cập nhật trạng thái của proxy sau khi kiểm tra xong
                // proxy.setLive(proxy.isLive()); // Không cần thiết, vì proxy đã được cập nhật trong ProxyCheckTask
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    private boolean checkProxy(String ip, int port) {

        try {
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.google.com").openConnection(proxy);
            conn.setConnectTimeout(300);
            conn.connect();
            return conn.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    private class ProxyCheckTask implements Callable<Proxy> {
        private final Proxy proxy;
        private final Object lock;

        public ProxyCheckTask(Proxy proxy, Object lock) {
            this.proxy = proxy;
            this.lock = lock;
        }

        @Override
        public Proxy call() {
            boolean isLive = checkProxy(proxy.getIp(), proxy.getPort());
            synchronized (lock) { // Sử dụng synchronized block với lock object
                System.out.print("checkProxy(String ip, int port)");
                System.out.print("\t" + proxy.getIp() + ":" + proxy.getPort());
                System.out.print("\t" + isLive + "\n");
            }
            proxy.setLive(isLive);
            return proxy;
        }
    }
}