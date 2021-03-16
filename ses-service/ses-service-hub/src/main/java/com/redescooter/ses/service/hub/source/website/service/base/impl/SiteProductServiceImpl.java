package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dm.SiteProduct;
import com.redescooter.ses.service.hub.source.website.dao.SiteProductMapper;
@Service
@DS("website")
public class SiteProductServiceImpl extends ServiceImpl<SiteProductMapper, SiteProduct> implements SiteProductService {

}
