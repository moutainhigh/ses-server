package com.redescooter.ses.service.mobile.b.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
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
@DubboService
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
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public JobResult countDelivery(GeneralEnter enter) {
        Calendar start = Calendar.getInstance();
        log.info("本次countDelivery任务开始执行");

        // 查找所有未统计的,订单状态为已送达或者超时完成的订单
        LambdaQueryWrapper<CorDelivery> qw = new LambdaQueryWrapper<>();
        qw.eq(CorDelivery::getStatus, DeliveryStatusEnums.COMPLETED.getValue()).or().eq(CorDelivery::getStatus, DeliveryStatusEnums.TIMEOUT_COMPLETE.getValue());
        qw.eq(CorDelivery::getStatistics, DeliveryStatisticsEnums.NOT_COUNTED.getValue());
        List<CorDelivery> list = corDeliveryMapper.selectList(qw);

        // 保存集合
        List<SaveDeliveryStatEnter> saveList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            for (CorDelivery item : list) {
                SaveDeliveryStatEnter param = new SaveDeliveryStatEnter();
                param.setDuration(Long.valueOf(item.getDrivenDuration()));
                param.setMileage(item.getDrivenMileage().doubleValue());
                param.setCo2(item.getCo2());
                param.setMoney(item.getSavings());
                param.setBizType(BizType.DELIVERY.getValue());
                param.setBizId(item.getId());
                param.setInputUserId(item.getDelivererId());
                param.setInputTenantId(item.getTenantId());
                param.setLastUpdateTime(item.getUpdatedTime());
                saveList.add(param);

                // 统计标识改为已统计
                item.setStatistics(DeliveryStatisticsEnums.COUNTED.getValue());
            }
        }

        // 抓取订单 进行数据保存
        if (CollectionUtils.isNotEmpty(saveList)) {
            // 保存司机骑行数据
            statisticalDataService.saveDriverRideStat(saveList);
            // 车辆统计数据
            statisticalDataService.saveScooterRideStat(saveList);
        }

        // 更新统计状态
        if (CollectionUtils.isNotEmpty(list)) {
            corDeliveryMapper.updateBatch(list);
        }

        log.info("本次任务执行完毕，耗时：" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒。");
        return JobResult.success();
    }
}
