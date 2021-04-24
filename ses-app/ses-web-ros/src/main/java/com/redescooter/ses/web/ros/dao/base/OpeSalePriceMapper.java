package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.vo.restproduct.SalePriceListEnter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销售价格表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-04-23
 */
@Mapper
public interface OpeSalePriceMapper extends BaseMapper<OpeSalePrice> {

    /**
     * 销售价格列表
     */
    List<OpeSalePrice> getSalePriceList(@Param("enter") SalePriceListEnter enter);

    /**
     * 销售价格列表count
     */
    int getSalePriceCount(@Param("enter") SalePriceListEnter enter);

}