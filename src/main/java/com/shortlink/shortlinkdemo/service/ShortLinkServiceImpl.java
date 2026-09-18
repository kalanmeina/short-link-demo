package com.shortlink.shortlinkdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shortlink.shortlinkdemo.entity.ShortLink;
import com.shortlink.shortlinkdemo.mapper.ShortLinkMapper;
import com.shortlink.shortlinkdemo.util.Base62Util;
import com.shortlink.shortlinkdemo.util.SnowFlakeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class ShortLinkServiceImpl implements ShortLinkService {
    @Resource
    private ShortLinkMapper shortLinkMapper;

    @Override
    public String generateShortLink(String originUrl) {

        long id = SnowFlakeUtil.getInstance().nextId();
        String shortUri = Base62Util.encode(id);

        ShortLink shortLink = new ShortLink();
        shortLink.setShortUri(shortUri);
        shortLink.setOriginUrl(originUrl);
        shortLink.setCreateTime(LocalDateTime.now());
        shortLink.setDelFlag(0);

        shortLink.setExpireTime(null);
        shortLinkMapper.insertShortLink(shortLink);
        return shortUri;

    }

    @Override
    public ShortLink getOriginUrl(String shortUri) {
        ShortLink shortLink = shortLinkMapper.selectByShortUri(shortUri);
        if (shortLink == null || shortLink.getDelFlag() == 1) {

            return null;
        }
        if (shortLink.getExpireTime() != null && LocalDateTime.now().isAfter(shortLink.getExpireTime())) {
            return null;
        }
        return shortLink;


    }

    @Override
    public String redirect(String shortUri) {
        // 直接查 MySQL
        ShortLink shortLink = shortLinkMapper.selectOne(
                new LambdaQueryWrapper<ShortLink>().eq(ShortLink::getShortUri, shortUri)
        );

        // 如果查不到，返回 null
        if (shortLink == null) {
            return null;
        }

        // 查到了，返回原链接
        return shortLink.getOriginUrl();
    }
}
