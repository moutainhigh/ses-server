package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 分配车辆节点表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-06-21
 */
@Mapper
public interface ScoScooterNodeMapper extends BaseMapper<ScoScooterNode> {

    /**
     * 列表count
     */
    int getListCount(@Param("enter") InquiryListAppEnter enter, @Param("warehouseAccountId") Long warehouseAccountId);

    /**
     * 列表
     */
    List<InquiryListResult> getList(@Param("enter") InquiryListAppEnter enter, @Param("warehouseAccountId") Long warehouseAccountId);

}