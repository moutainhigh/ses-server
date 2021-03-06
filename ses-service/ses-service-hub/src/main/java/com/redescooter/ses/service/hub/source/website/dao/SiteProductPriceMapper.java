package com.redescooter.ses.service.hub.source.website.dao;

import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 产品报价表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-04-26
 */
@Mapper
public interface SiteProductPriceMapper extends BaseMapper<SiteProductPrice> {
    //官网统一同步定金
    int synchronizeDeposit(@Param("enter") SiteProductPrice siteProductPrice);
}