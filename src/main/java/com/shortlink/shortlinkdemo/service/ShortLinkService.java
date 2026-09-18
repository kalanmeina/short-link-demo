package com.shortlink.shortlinkdemo.service;

import com.shortlink.shortlinkdemo.entity.ShortLink;

public interface ShortLinkService {
    /**
     * 生成短链接
     * @param originUrl 原始长链接
     * @return 短码
     */
    String generateShortLink(String originUrl);
    /**
     * 根据短码查询原始链接
     * @param shortUri 短码
     * @return 短链接实体
     */
    ShortLink getOriginUrl(String shortUri);
    String redirect(String shortUri);
}
