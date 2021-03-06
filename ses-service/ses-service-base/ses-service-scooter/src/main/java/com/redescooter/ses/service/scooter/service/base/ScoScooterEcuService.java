package com.redescooter.ses.service.scooter.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
public interface ScoScooterEcuService extends IService<ScoScooterEcu> {

    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterEcu record);

    int insertSelective(ScoScooterEcu record);

    ScoScooterEcu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterEcu record);

    int updateByPrimaryKey(ScoScooterEcu record);

}
