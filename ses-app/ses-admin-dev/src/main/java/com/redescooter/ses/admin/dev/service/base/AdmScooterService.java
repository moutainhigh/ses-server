package com.redescooter.ses.admin.dev.service.base;

import com.redescooter.ses.admin.dev.dm.AdmScooter;
    /**
*@author assert
*@date 2020/12/10 22:42
*/
public interface AdmScooterService{


    int deleteByPrimaryKey(Long id);

    int insert(AdmScooter record);

    int insertSelective(AdmScooter record);

    AdmScooter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdmScooter record);

    int updateByPrimaryKey(AdmScooter record);

}
