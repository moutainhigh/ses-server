package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
public interface OpeCombinOrderService {


    int deleteByPrimaryKey(Long id);

    int insert(OpeCombinOrder record);

    int insertSelective(OpeCombinOrder record);

    OpeCombinOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeCombinOrder record);

    int updateByPrimaryKey(OpeCombinOrder record);

}


