package com.shortlink.shortlinkdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortlink.shortlinkdemo.entity.ShortLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLink> {
    ShortLink selectByShortUri(String shortUrl);

    int insertShortLink(ShortLink shortLink);



}
