package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;

/**
*@author assert
*@date 2021/1/8 13:48
*/
public interface OpeProductionCombinBomService extends IService<OpeProductionCombinBom> {


    int deleteByPrimaryKey(Long id);

    int insert(OpeProductionCombinBom record);

    int insertSelective(OpeProductionCombinBom record);

    OpeProductionCombinBom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductionCombinBom record);

    int updateByPrimaryKey(OpeProductionCombinBom record);

}
