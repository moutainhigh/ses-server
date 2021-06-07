package com.redescooter.ses.wh.fr.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.wh.fr.vo.WarehouseAccountListEnter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 仓库账号表 Mapper 接口
 * </p>
 *
 * @author chris
 * @since 2021-05-10
 */
@Mapper
public interface OpeWarehouseAccountMapper extends BaseMapper<OpeWarehouseAccount> {

    /**
     * 仓库账号列表count
     */
    Integer getListCount(@Param("enter") WarehouseAccountListEnter enter);

    /**
     * 仓库账号列表
     */
    List<OpeWarehouseAccount> getList(@Param("enter") WarehouseAccountListEnter enter);

}