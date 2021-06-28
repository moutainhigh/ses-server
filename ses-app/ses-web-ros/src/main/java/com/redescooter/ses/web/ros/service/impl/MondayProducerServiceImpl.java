package com.redescooter.ses.web.ros.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.service.MondayProducerService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.config.MondayConfigYml;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.MondayRecordMapper;
import com.redescooter.ses.web.ros.dm.MondayRecord;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayRedeEnums;
import com.redescooter.ses.web.ros.service.base.MondayConfigService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.result.MondayData;
import com.redescooter.ses.web.ros.vo.monday.result.MondayItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName MondayRecordServiceImpl
 * @Description dubbo
 * @Author Charles
 * @Date 2021/06/23 13:11
 */
@Slf4j
@DubboService
public class MondayProducerServiceImpl implements MondayProducerService {

    @Autowired
    private MondayRecordMapper recordMapper;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private MondayConfigYml mondayConfig;

    @Autowired
    private MondayService mondayService;

    @Autowired
    private MondayConfigService mondayConfigService;

    private Map<String, String> map = MondayRedeEnums.getMondayRedeTitle();

    @Override
    public void save(Long userId, String scooterResult, String email, String telphone) {
        CompletableFuture noReturn = CompletableFuture.runAsync(()->{
            ScoScooterResult scooter = JSONObject.parseObject(scooterResult, ScoScooterResult.class);
            log.info("scooter 数据{}_______", scooter);
            log.info("——————————————————————执行monday相关数据  start——————————————————————");
            if (null == scooter) {
                log.error("scooter data is null,mondayRecord data save fail ");
                return;
            }

            log.info("——————————————————————上传 monday start——————————————————————");
            log.info("monday 板名 {}", mondayConfig.getCustomerBoardName());
            String boardId = mondayConfigService.queryBoardName(mondayConfig.getCustomerBoardName(), mondayConfig.getWorkspaceId());
            // 创建行
            log.info("——————————————————————创建行 monday start——————————————————————组名{}", mondayConfig.getCustomerGroupName());
            MondayItem item = new MondayItem();
            item.setBoardId(boardId);
            item.setGroupId(mondayConfig.getCustomerGroupName());
            item.setItemName(JSON.toJSONString(DateUtil.getDateTime()));
            String itemId = mondayService.insertItem(item);
            log.info("——————————————————————创建行 monday end——————————————————————{}", itemId);
            String[] split = scooter.getBattery().split(",");
            String replace = scooter.getBattery().replace(",", "\n");
            String tableSn = scooter.getTabletSn() + "5";
            // 数据
            List<MondayData> list = new ArrayList<>();
            list.add(new MondayData(MondayRedeEnums.E50_OU_E100.getId(), ScooterModelEnum.getScooterModelByType(Integer.parseInt(scooter.getModel()))));
            list.add(new MondayData(MondayRedeEnums.NOMBRE_DE_BATTERIE.getId(), String.valueOf(split.length)));
            list.add(new MondayData(MondayRedeEnums.N_DE_SERIE_DE_LA_BATTERIE.getId(), replace));
            list.add(new MondayData(MondayRedeEnums.ID_DASHBOARD.getId(), tableSn));
            list.add(new MondayData(MondayRedeEnums.EMAIL.getId(), email));
            list.add(new MondayData(MondayRedeEnums.TELEPHONE.getId(), telphone));
            mondayService.batchItemData(list, boardId, itemId);
            log.info("——————————————————————上传 monday end——————————————————————");

            MondayRecord mondayRecord = new MondayRecord();
            mondayRecord.setId(idAppService.getId(SequenceName.MONDAY_RECORD));
            mondayRecord.setWorkspaceId(mondayConfig.getWorkspaceId());
            mondayRecord.setBoardId(boardId);
            mondayRecord.setItemId(itemId);
            mondayRecord.setGroupName(mondayConfig.getCustomerGroupName());
            mondayRecord.setColumnNames(JSON.toJSONString(map.values()));
            mondayRecord.setItemValue(JSON.toJSONString(list));
            mondayRecord.setRsn(scooter.getTabletSn());
            mondayRecord.setCreatedBy(userId);
            mondayRecord.setCreatedTime(new Date());
            recordMapper.insert(mondayRecord);
            log.info("——————————————————————执行monday相关数据  end——————————————————————");
        });
    }
}
