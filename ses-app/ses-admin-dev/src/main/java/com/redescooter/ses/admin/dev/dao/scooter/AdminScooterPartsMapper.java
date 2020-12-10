package com.redescooter.ses.admin.dev.dao.scooter;

import com.redescooter.ses.admin.dev.dm.AdmScooterParts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆配件 Mapper接口
 * @author assert
 * @date 2020/12/9 10:23
 */
public interface AdminScooterPartsMapper {

    /**
     * 批量新增车辆配件信息
     * @param scooterPartsList
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int batchInsertAdminScooterParts(@Param("scooterPartsList") List<AdmScooterParts> scooterPartsList);

}
