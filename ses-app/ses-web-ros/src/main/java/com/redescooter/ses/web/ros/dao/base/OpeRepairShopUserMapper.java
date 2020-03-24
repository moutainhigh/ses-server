package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShopUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairShopUserMapper extends BaseMapper<OpeRepairShopUser> {
    int updateBatch(List<OpeRepairShopUser> list);

    int batchInsert(@Param("list") List<OpeRepairShopUser> list);

    int insertOrUpdate(OpeRepairShopUser record);

    int insertOrUpdateSelective(OpeRepairShopUser record);
}