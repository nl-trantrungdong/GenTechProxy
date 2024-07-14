package com.gentech.proxy.controller;

import com.gentech.proxy.model.Proxy;
import com.gentech.proxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/proxies")
public class ProxyController {

    @Autowired
    private ProxyService proxyService;

    @GetMapping(path = "/https")
    public String getProxiesHttps(Model model) throws IOException {
        System.out.println("ádfaf");
        List<Proxy> proxies = proxyService.listProxy("https");
        model.addAttribute("proxies", proxies);
        return "proxies"; // view name
    }

    @GetMapping(path = "/socs5")
    public String getProxiesSocs5(Model model) {
        List<Proxy> proxies = null;
        try {
            proxies = proxyService.listProxy("socs5");
            model.addAttribute("proxies", proxies);
            return "proxies"; // view name
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping(path = "/socs4")
    public String getProxiesSocs4(Model model) {
        List<Proxy> proxies = null;
        try {
            proxies = proxyService.listProxy("socs4");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("proxies", proxies);
        return "proxies"; // view name
    }
}