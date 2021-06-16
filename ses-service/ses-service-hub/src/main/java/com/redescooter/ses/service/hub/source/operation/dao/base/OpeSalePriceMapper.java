package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.redescooter.ses.service.hub.source.operation.dm.OpeSalePrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 销售价格表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-05-08
 */
@Mapper
public interface OpeSalePriceMapper extends BaseMapper<OpeSalePrice> {

}