package com.redescooter.ses.admin.dev.convert.scooter;

import com.redescooter.ses.admin.dev.dm.AdmScooterParts;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterPartsDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 车辆领域对象转换接口
 * @author assert
 * @date 2020/12/11 11:22
 */
@Mapper(componentModel = "spring")
public interface AdminScooterConvert {

    /**
     * 车辆配件dto集合转do集合
     * @param scooterPartsDTOS
     * @return java.util.List<com.redescooter.ses.admin.dev.dm.AdmScooterParts>
     * @author assert
     * @date 2020/12/11
    */
    List<AdmScooterParts> scooterPartsDtoListToDoList(List<AdminScooterPartsDTO> scooterPartsDTOS);

}
