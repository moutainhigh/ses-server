package com.redescooter.ses.admin.dev.service.base;

import com.redescooter.ses.admin.dev.dm.AdmScooterParts;
    /**
*@author assert
*@date 2020/12/10 22:43
*/
public interface AdmScooterPartsService{


    int deleteByPrimaryKey(Long id);

    int insert(AdmScooterParts record);

    int insertSelective(AdmScooterParts record);

    AdmScooterParts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdmScooterParts record);

    int updateByPrimaryKey(AdmScooterParts record);

}
