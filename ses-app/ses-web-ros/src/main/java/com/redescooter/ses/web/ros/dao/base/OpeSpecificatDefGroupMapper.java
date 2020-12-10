package com.redescooter.ses.web.ros.dao.base;

import com.redescooter.ses.web.ros.dm.OpeSpecificatDefGroup;

/**
 * @author assert
 * @date 2020/12/8 11:55
 */
public interface OpeSpecificatDefGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeSpecificatDefGroup record);

    int insertSelective(OpeSpecificatDefGroup record);

    OpeSpecificatDefGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeSpecificatDefGroup record);

    int updateByPrimaryKey(OpeSpecificatDefGroup record);
}