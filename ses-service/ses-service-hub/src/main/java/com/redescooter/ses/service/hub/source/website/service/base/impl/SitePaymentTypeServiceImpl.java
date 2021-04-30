package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dao.SitePaymentTypeMapper;
import com.redescooter.ses.service.hub.source.website.dm.SitePaymentType;
import com.redescooter.ses.service.hub.source.website.service.base.SitePaymentTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付方式 服务实现类
 * </p>
 *
 * @author chris
 * @since 2021-04-28
 */
@Service
@DS("website")
public class SitePaymentTypeServiceImpl extends ServiceImpl<SitePaymentTypeMapper, SitePaymentType> implements SitePaymentTypeService {


}
