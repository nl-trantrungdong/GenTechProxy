package com.gentech.proxy.service.impl;

import com.gentech.proxy.model.Proxy;
import com.gentech.proxy.model.ProxyChecker;
import com.gentech.proxy.model.ProxyCrawler;
import com.gentech.proxy.model.ProxyManager;
import com.gentech.proxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProxyServiceImpl implements ProxyService {
    @Autowired
    ProxyManager proxyManager;

    @Override
    public List<Proxy> listProxy(String typeProxy) {
        return proxyManager.getActiveProxies();
    }

}
