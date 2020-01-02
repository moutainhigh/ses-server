package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.consumer.dao.ConUserScooterMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserScooter;
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
public class CustomerScooterServiceImpl implements CusotmerScooterService {

    @Autowired
    private ConUserScooterMapper conUserScooterMapper;

    /**
     * 查询车辆分配信息
     *
     * @param enter 入参Id 为scooterId
     * @return
     */
    @Override
    public QueryDriverScooterResult queryDriverScooter(IdEnter enter) {
        QueryWrapper<ConUserScooter> conUserScooterQueryWrapper = new QueryWrapper<>();
        if (enter.getUserId() != null && enter.getUserId() != 0) {
            conUserScooterQueryWrapper.eq(ConUserScooter.COL_USER_ID, enter.getUserId());
        }
        if (enter.getId() != null && enter.getId() != 0) {
            conUserScooterQueryWrapper.eq(ConUserScooter.COL_SCOOTER_ID, enter.getId());
        }
        ConUserScooter conUserScooter = conUserScooterMapper.selectOne(conUserScooterQueryWrapper);
        if (conUserScooter == null) {
            throw new SeSHubException(ExceptionCodeEnums.USER_IS_NOT_HAVE_SCOOTER.getCode(), ExceptionCodeEnums.USER_IS_NOT_HAVE_SCOOTER.getMessage());
        }
        QueryDriverScooterResult result = new QueryDriverScooterResult();
        BeanUtils.copyProperties(conUserScooter, result);
        return result;
    }
}
