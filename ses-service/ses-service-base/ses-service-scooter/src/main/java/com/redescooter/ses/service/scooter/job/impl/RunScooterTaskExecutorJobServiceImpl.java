package com.redescooter.ses.service.scooter.job.impl;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.service.AppVersionUpdateLogService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO;
import com.redescooter.ses.api.scooter.job.RunScooterTaskExecutorJobService;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import com.redescooter.ses.tool.utils.thread.ThreadPoolExecutorUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/12/27 15:30
 */
@Service
public class RunScooterTaskExecutorJobServiceImpl implements RunScooterTaskExecutorJobService {

    @DubboReference
    private AppVersionService appVersionService;
    @DubboReference
    private AppVersionUpdateLogService appVersionUpdateLogService;
    @Resource
    private ScooterEcuService scooterEcuService;
    @Resource
    private MqttClientUtil mqttClientUtil;


    @Override
    public JobResult scooterUpdateFailRetry() {
        List<AppVersionUpdateLogDetailDTO> appVersionUpdateLogDetails = appVersionUpdateLogService.getAppVersionUpdateFailLog();
        if (CollectionUtils.isEmpty(appVersionUpdateLogDetails)) {
            return JobResult.success();
        }

        ScooterTabletUpdatePublishDTO tabletUpdatePublish = new ScooterTabletUpdatePublishDTO();

        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            appVersionUpdateLogDetails.forEach(updateLog -> {
                tabletUpdatePublish.setDownloadLink(updateLog.getUpdateLink());
                tabletUpdatePublish.setVersionCode(updateLog.getVersionCode());
                tabletUpdatePublish.setTabletSn(updateLog.getTabletSn());

                /**
                 * 发送车载平板升级EMQ X消息, 不同车可能对应不同的版本
                 */
                mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, updateLog.getTabletSn()),
                        JSONObject.toJSONString(tabletUpdatePublish));
            });

        });

        return JobResult.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JobResult scooterUpdateStatusSync() {
        List<String> updateSuccessTabletSnList = new ArrayList<>();

        List<AppVersionUpdateLogDetailDTO> appVersionUpdateLogDetails = appVersionUpdateLogService.getAppVersionUpdateFailLog();
        if (CollectionUtils.isEmpty(appVersionUpdateLogDetails)) {
            return JobResult.success();
        }

        /**
         * tabletSn
         */
        List<String> tabletSnList = appVersionUpdateLogDetails.stream().map(
                AppVersionUpdateLogDetailDTO::getTabletSn
        ).collect(Collectors.toList());

        List<ScooterEcuDTO> scooterEcus = scooterEcuService.getScooterEcuByTabletSnList(tabletSnList);

        /**
         * {tabletSn, versionCode}
         */
        Map<String, String> scooterEcuMap = scooterEcus.stream().collect(
                Collectors.toMap(ScooterEcuDTO::getTabletSn, ScooterEcuDTO::getVersionCode)
        );

        appVersionUpdateLogDetails.forEach(appVersionUpdateLog -> {
            String versionCode = scooterEcuMap.get(appVersionUpdateLog.getTabletSn());
            if (StringUtils.isNotBlank(versionCode) && versionCode.equals(appVersionUpdateLog.getVersionCode())) {
                updateSuccessTabletSnList.add(appVersionUpdateLog.getTabletSn());
            }
        });

        // 批量修改车载平板升级状态
        if (!CollectionUtils.isEmpty(updateSuccessTabletSnList)) {
            appVersionUpdateLogService.batchUpdateAppVersionUpdateLogStatus(updateSuccessTabletSnList);
        }

        return JobResult.success();
    }

}
