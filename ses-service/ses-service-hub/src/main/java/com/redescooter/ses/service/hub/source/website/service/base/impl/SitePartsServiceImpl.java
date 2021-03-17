package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dao.SitePartsMapper;
import com.redescooter.ses.service.hub.source.website.dm.SiteParts;
import com.redescooter.ses.service.hub.source.website.service.base.SitePartsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配件表 服务实现类
 * </p>
 *
 * @author chris
 * @since 2021-03-16
 */
@Service
@DS("website")
public class SitePartsServiceImpl extends ServiceImpl<SitePartsMapper, SiteParts> implements SitePartsService {


}