package com.redescooter.ses.service.scooter.job.impl;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.service.AppVersionUpdateLogService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDetailDTO;
import com.redescooter.ses.api.scooter.job.RunScooterTaskExecutorJobService;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterUpdateRecord;
import com.redescooter.ses.service.scooter.service.base.ScoScooterUpdateRecordService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import com.redescooter.ses.tool.utils.thread.ThreadPoolExecutorUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/12/27 15:30
 */
@DubboService
@Slf4j
public class RunScooterTaskExecutorJobServiceImpl implements RunScooterTaskExecutorJobService {

    @DubboReference
    private AppVersionService appVersionService;

    @DubboReference
    private AppVersionUpdateLogService appVersionUpdateLogService;

    @DubboReference
    private IdAppService idAppService;

    @Resource
    private ScooterEcuService scooterEcuService;

    @Resource
    private MqttClientUtil mqttClientUtil;

    @Autowired
    private ScoScooterUpdateRecordService scoScooterUpdateRecordService;

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
                tabletUpdatePublish.setUpdateCode(UUID.randomUUID().toString().replaceAll("-", ""));

                /**
                 * ????????????????????????EMQ X??????, ????????????????????????????????????
                 */
                log.info("??????????????????:[{}]", tabletUpdatePublish);
                mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, updateLog.getTabletSn()),
                        JSONObject.toJSONString(tabletUpdatePublish));

                // ?????????????????????????????????
                ScoScooterUpdateRecord record = new ScoScooterUpdateRecord();
                record.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
                record.setDr(Constant.DR_FALSE);
                record.setTabletSn(tabletUpdatePublish.getTabletSn());
                record.setDownloadLink(tabletUpdatePublish.getDownloadLink());
                record.setVersionCode(tabletUpdatePublish.getVersionCode());
                record.setUpdateCode(tabletUpdatePublish.getUpdateCode());
                record.setFlag(1);
                record.setCreatedBy(0L);
                record.setCreatedTime(new Date());
                log.info("?????????????????????????????????????????????:[{}]", record);
                scoScooterUpdateRecordService.save(record);
            });

        });

        return JobResult.success();
    }

    @GlobalTransactional(rollbackFor = Exception.class)
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

        // ????????????????????????????????????
        if (!CollectionUtils.isEmpty(updateSuccessTabletSnList)) {
            appVersionUpdateLogService.batchUpdateAppVersionUpdateLogStatus(updateSuccessTabletSnList);
        }

        return JobResult.success();
    }

}
