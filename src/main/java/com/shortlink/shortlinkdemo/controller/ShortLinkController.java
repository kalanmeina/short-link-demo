package com.shortlink.shortlinkdemo.controller;

import com.shortlink.shortlinkdemo.entity.ShortLink;
import com.shortlink.shortlinkdemo.service.ShortLinkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ShortLinkController {
    @Resource
    private ShortLinkService shortLinkService;

    @PostMapping("/generate")
    public Map<String, Object> generate(@RequestParam String originUrl) {

        Map<String, Object> result = new HashMap<>();
        try {
            String shortUri =shortLinkService.generateShortLink(originUrl);
            String shortUrl="http://localhost:8080/" +shortUri;
            result.put("code",200);
            result.put("shortUrl",shortUrl);
            result.put("shortUri",shortUri);

        }catch(Exception e){
            result.put("code",500);
            result.put("msg","生成失败"+e.getMessage());
        }
        return result;

    }
    // ... 上面是你原有的 generate 方法 ...

    /**
     * 跳转接口
     */
    @GetMapping("/{shortUri}")
    public void redirect(@PathVariable String shortUri, HttpServletResponse response) throws IOException, IOException {
        // 调用 Service 层查 MySQL（或者 Redis）
        String originUrl = shortLinkService.redirect(shortUri);

        if (originUrl != null) {
            // 302 临时重定向（推荐用 302，方便后面做点击量统计）
            response.sendRedirect(originUrl);
        } else {
            // 短码不存在，返回 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

} // 这是类最后的结束大括号





