package com.redescooter.ses.service.hub.source.website.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.website.dm.SiteProduct;

@DS("website")
public interface SiteProductMapper extends BaseMapper<SiteProduct> {
}
