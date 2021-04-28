package com.redescooter.ses.service.hub.source.website.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.website.dm.SitePaymentType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付方式 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-04-28
 */
@Mapper
@DS("website")
public interface SitePaymentTypeMapper extends BaseMapper<SitePaymentType> {

}