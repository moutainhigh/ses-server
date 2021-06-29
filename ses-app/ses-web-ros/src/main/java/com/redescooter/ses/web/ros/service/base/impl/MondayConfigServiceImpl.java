package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.MondayConfigMapper;
import com.redescooter.ses.web.ros.dm.MondayConfig;
import com.redescooter.ses.web.ros.service.base.MondayConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: // monday配置相关信息(MondayConfig)表服务实现类
 * @Date: 2021-06-28 17:36:58
 * @Author: Charles
 */
@Slf4j
@Service
public class MondayConfigServiceImpl extends ServiceImpl<MondayConfigMapper, MondayConfig> implements MondayConfigService {

    @Autowired
    private IdAppService idAppService;

    @Override
    public void saveMondayConfig(String boardName, String boardId, String groupName, String workspaceId, String authorization) {
        LambdaUpdateWrapper<MondayConfig> uw = new LambdaUpdateWrapper<>();
        uw.eq(MondayConfig::getDr, Constant.DR_FALSE);
        uw.eq(MondayConfig::getBoardName, boardName);
        uw.eq(MondayConfig::getWorkspaceId, workspaceId);
        uw.eq(MondayConfig::getAuthorization, authorization);
        uw.setSql("dr = 1, updated_time = now()");
        this.update(uw);
        MondayConfig mondayConfig = new MondayConfig();
        mondayConfig.setId(idAppService.getId(SequenceName.MONDAY_CONFIG));
        mondayConfig.setBoardId(boardId);
        mondayConfig.setBoardName(boardName);
        mondayConfig.setGroupName(groupName);
        mondayConfig.setWorkspaceId(workspaceId);
        mondayConfig.setAuthorization(authorization);
        mondayConfig.setCreatedTime(new Date());
        this.save(mondayConfig);
    }

    @Override
    public String queryBoardName(String boardName, String workSpaceId, String authorization) {
        LambdaQueryWrapper<MondayConfig> qw = new LambdaQueryWrapper();
        qw.eq(MondayConfig::getDr, Constant.DR_FALSE);
        qw.eq(MondayConfig::getBoardName, boardName);
        qw.eq(MondayConfig::getWorkspaceId, workSpaceId);
        qw.eq(MondayConfig::getAuthorization, authorization);
        qw.orderByDesc(MondayConfig::getCreatedTime);
        qw.last("limit 1");
        MondayConfig mondayConfig = this.getOne(qw);
        return null == mondayConfig ? null : mondayConfig.getBoardId();
    }

}
