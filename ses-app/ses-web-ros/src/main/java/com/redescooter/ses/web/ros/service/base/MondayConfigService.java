package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.MondayConfig;

/**
 * @Description: // monday配置相关信息(MondayConfig)表服务接口
 * @Date: 2021-06-28 17:36:58
 * @Author: Charles
 */
public interface MondayConfigService extends IService<MondayConfig> {

    void saveMondayConfig(String boardName, String boardId, String groupName, String workspaceId, String authorization);

    String queryBoardName(String boardName, String workSpaceId, String authorization);
}
