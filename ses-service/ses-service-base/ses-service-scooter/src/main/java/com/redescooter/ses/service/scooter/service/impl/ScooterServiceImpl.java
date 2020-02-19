package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
import com.redescooter.ses.service.scooter.constant.ScooterDefaultData;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName:ScooterServiceImpl
 * @description: ScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:04
 */
@Service
@Log4j
public class ScooterServiceImpl implements ScooterService {

    @Autowired
    private ScooterServiceMapper scooterServiceMapper;
    @Autowired
    private ScoScooterService scoScooterService;

    @Override
    public List<BaseScooterResult> scooterInfor(List<Long> enter) {
        List<BaseScooterResult> scooterResultList = scooterServiceMapper.scooterInfor(enter);
        scooterResultList.forEach(item -> {
            Optional.ofNullable(item).ifPresent(it->{
                it.setNextMaintenanceKm(ScooterDefaultData.MAINTENANCE_KM.subtract(item.getTotalmileage()));
            });
        });
        return scooterResultList;
    }

    @Override
    public GeneralResult saveScooter(BaseScooterResult enter) {
        return null;
    }

    /**
     * 修改 车辆状态
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult updateStatus(UpdateStatusEnter enter) {
        ScoScooter scoScooter = scoScooterService.query().eq(ScoScooter.COL_ID, enter.getId()).one();
        if (scoScooter == null) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(enter.getAvailableStatus(), scoScooter.getAvailableStatus())) {
            return new GeneralResult(enter.getRequestId());
        }
        scoScooter.setAvailableStatus(enter.getAvailableStatus());
        scoScooter.setUpdatedBy(enter.getUserId());
        scoScooter.setUpdatedTime(new Date());
        scoScooterService.updateById(scoScooter);
        return new GeneralResult(enter.getRequestId());
    }
}
