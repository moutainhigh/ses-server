package com.redescooter.ses.service.hub.source.website.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.website.dm.SitePaymentType;

/**
 * <p>
 * 支付方式 服务类
 * </p>
 * @author chris
 * @since 2021-04-28
 */
@DS("website")
public interface SitePaymentTypeService extends IService<SitePaymentType> {

}