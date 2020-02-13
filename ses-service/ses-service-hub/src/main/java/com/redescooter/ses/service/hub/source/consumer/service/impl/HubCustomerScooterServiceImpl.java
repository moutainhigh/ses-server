package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.consumer.dao.HubConUserScooterMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserScooter;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:ScooterService
 * @description: ScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 19:17
 */
@Service
public class HubCustomerScooterServiceImpl implements CusotmerScooterService {

    @Autowired
    private HubConUserScooterMapper hubConUserScooterMapper;

    /**
     * 查询车辆分配信息
     *
     * @param enter 入参Id 为scooterId
     * @return
     */
    @Override
    public QueryDriverScooterResult queryDriverScooter(IdEnter enter) {
        QueryWrapper<HubConUserScooter> conUserScooterQueryWrapper = new QueryWrapper<>();
        if (enter.getUserId() != null && enter.getUserId() != 0) {
            conUserScooterQueryWrapper.eq(HubConUserScooter.COL_USER_ID, enter.getUserId());
        }
        if (enter.getId() != null && enter.getId() != 0) {
            conUserScooterQueryWrapper.eq(HubConUserScooter.COL_SCOOTER_ID, enter.getId());
        }
        HubConUserScooter hubConUserScooter = hubConUserScooterMapper.selectOne(conUserScooterQueryWrapper);
        if (hubConUserScooter == null) {
           // throw new SeSHubException(ExceptionCodeEnums.USER_IS_NOT_HAVE_SCOOTER.getCode(), ExceptionCodeEnums.USER_IS_NOT_HAVE_SCOOTER.getMessage());
            return null;
        }
        QueryDriverScooterResult result = new QueryDriverScooterResult();
        BeanUtils.copyProperties(hubConUserScooter, result);
        return result;
    }
}
