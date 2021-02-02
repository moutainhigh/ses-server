package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;
    /**
 *@author assert
 *@date 2021/1/27 21:09
 */
public interface OpeCombinOrderCombinBService{


    int deleteByPrimaryKey(Integer id);

    int insertSelective(OpeCombinOrderCombinB record);

    OpeCombinOrderCombinB selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpeCombinOrderCombinB record);

    int updateByPrimaryKey(OpeCombinOrderCombinB record);

}
