package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderScooterB;
    /**
 *@author assert
 *@date 2021/1/27 21:09
 */
public interface OpeCombinOrderScooterBService{


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeCombinOrderScooterB record);

    OpeCombinOrderScooterB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeCombinOrderScooterB record);

    int updateByPrimaryKey(OpeCombinOrderScooterB record);

}
