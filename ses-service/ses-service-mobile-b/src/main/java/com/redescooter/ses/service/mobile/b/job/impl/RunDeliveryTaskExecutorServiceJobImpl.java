package com.redescooter.ses.service.mobile.b.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatisticsEnums;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.mobile.b.job.RunDeliveryTaskExecutorServiceJob;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryStatEnter;
import com.redescooter.ses.service.mobile.b.dao.base.CorDeliveryMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDelivery;
import com.redescooter.ses.tool.utils.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ClassName:RunDeliveryTaskExecutorServiceJobImpl
 * @description: RunDeliveryTaskExecutorServiceJobImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 13:22
 */
@Log4j2
@Service
public class RunDeliveryTaskExecutorServiceJobImpl implements RunDeliveryTaskExecutorServiceJob {

    @Autowired
    private CorDeliveryMapper corDeliveryMapper;
    @Autowired
    private StatisticalDataService statisticalDataService;


    /**
     * 订单骑行数据 数据
     *
     * @param enter
     * @return
     */
    @Override
    public JobResult countDelivery(GeneralEnter enter) {

        Calendar start = Calendar.getInstance();
        log.info("本次countDelivery任务开始执行");

        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.eq(CorDelivery.COL_STATUS, DeliveryStatusEnums.COMPLETED.getValue()).or().eq(CorDelivery.COL_STATUS, DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue());
        corDeliveryQueryWrapper.eq(CorDelivery.COL_STATISTICS, DeliveryStatisticsEnums.COUNTED.getValue());
        List<CorDelivery> deliveryList = corDeliveryMapper.selectList(corDeliveryQueryWrapper);

        List<SaveDeliveryStatEnter> saveDeliveryStatEnterList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(deliveryList)) {

            deliveryList.forEach(item -> {
                SaveDeliveryStatEnter saveDeliveryStatEnter = SaveDeliveryStatEnter.builder()
                        .bizId(item.getId())
                        .bizType(BizType.DELIVERY.getValue())
                        .mileage(item.getDrivenMileage().doubleValue())
                        .duration(DateUtil.timeComolete(item.getAtd(), item.getAta()))
                        .inputUserId(item.getDelivererId())
                        .inputTenantId(item.getTenantId())
                        .build();
                saveDeliveryStatEnterList.add(saveDeliveryStatEnter);

                item.setStatistics(DeliveryStatisticsEnums.COUNTED.getCode());
            });
        }
        // 抓取订单 进行数据保存
        if (CollectionUtils.isNotEmpty(saveDeliveryStatEnterList)) {
            statisticalDataService.saveDriverRideStat(saveDeliveryStatEnterList);
            statisticalDataService.saveScooterRideStat(saveDeliveryStatEnterList);
        }
        // 更新统计状态
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            corDeliveryMapper.updateBatch(deliveryList);
        }

        log.info("本次任务执行完毕，耗时：" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒。");

        return JobResult.success();
    }
}
