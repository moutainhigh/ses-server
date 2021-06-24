package com.redescooter.ses.web.ros.service.base.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.config.MondayConfig;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.MondayRecordMapper;
import com.redescooter.ses.web.ros.dm.MondayRecord;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayRedeEnums;
import com.redescooter.ses.web.ros.service.base.MondayRecordService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.result.MondayData;
import com.redescooter.ses.web.ros.vo.monday.result.MondayItem;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * monday记录表(MondayRecord)表服务实现类
 *
 * @author Charles
 * @since 2021-06-23 12:50:19
 */
@Slf4j
@Service
public class MondayRecordServiceImpl extends ServiceImpl<MondayRecordMapper, MondayRecord> implements MondayRecordService {

    @Autowired
    private MondayRecordMapper recordMapper;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private MondayConfig mondayConfig;

    @Autowired
    private MondayService mondayService;

    private Map<String, String> map = MondayRedeEnums.getMondayRedeTitle();

    @GlobalTransactional
    @Override
    public void save(Long userId, ScoScooterResult scooterResult) {
        log.info("——————————————————————执行monday相关数据  start——————————————————————");
        if(null == scooterResult){
            log.error("scoScooter data is null,mondayRecord data save fail ");
            return;
        }

        log.info("——————————————————————上传 monday start——————————————————————");
        String boardId = mondayService.existBoardName(mondayConfig.getCustomerBoardName());
        // 创建行
        MondayItem item =new MondayItem();
        item.setBoardId(boardId);
        item.setGroupId(mondayConfig.getCustomerGroupName());
        item.setItemName(JSON.toJSONString(DateUtil.getDateTime()));
        String itemId = mondayService.insertItem(item);
        String[] split = scooterResult.getBattery().split(",");
        String replace = scooterResult.getBattery().replace(",", "\n");
        // 数据
        List<MondayData> list = new ArrayList<>();
        list.add(new MondayData(MondayRedeEnums.E50_OU_E100.getId(), ScooterModelEnum.getScooterModelByType(Integer.parseInt(scooterResult.getModel()))));
        list.add(new MondayData(MondayRedeEnums.NOMBRE_DE_BATTERIE.getId(), String.valueOf(split.length)));
        list.add(new MondayData(MondayRedeEnums.N_DE_SERIE_DE_LA_BATTERIE.getId(), JSON.toJSONString(replace)));
        list.add(new MondayData(MondayRedeEnums.ID_DASHBOARD.getId(), scooterResult.getTabletSn()));
        mondayService.batchItemData(list, boardId, itemId);
        log.info("——————————————————————上传 monday end——————————————————————");

        MondayRecord mondayRecord = new MondayRecord();
        mondayRecord.setId(idAppService.getId(SequenceName.MONDAY_RECORD));
        mondayRecord.setWorkspaceId(mondayConfig.getWorkspaceId());
        mondayRecord.setBoardId(boardId);
        mondayRecord.setGroupName(mondayConfig.getCustomerGroupName());
        mondayRecord.setColumnNames(JSON.toJSONString(map.values()));
        mondayRecord.setItemValue(JSON.toJSONString(list));
        mondayRecord.setRsn(scooterResult.getTabletSn());
        mondayRecord.setCreatedBy(userId);
        mondayRecord.setCreatedTime(new Date());
        recordMapper.insert(mondayRecord);
        log.info("——————————————————————执行monday相关数据  end——————————————————————");
    }
}
