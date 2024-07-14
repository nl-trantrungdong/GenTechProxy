package com.gentech.proxy.service;

import com.gentech.proxy.model.Proxy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public interface ProxyService {
    List<Proxy> listProxy(String typeProxy) throws IOException;

}