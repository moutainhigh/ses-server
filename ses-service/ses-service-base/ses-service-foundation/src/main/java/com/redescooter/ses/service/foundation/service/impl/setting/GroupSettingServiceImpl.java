package com.redescooter.ses.service.foundation.service.impl.setting;


import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.setting.GroupSettingService;
import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveGroupEnter;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:45
 *  @version：V ROS 1.7.1
 *  @Description:
 */
@Service
public class GroupSettingServiceImpl implements GroupSettingService {

    /**
     * 分组列表
     * @param enter
     * @return
     */
    @Override
    public PageResult<GroupResult> list(GroupListEnter enter) {
        GroupResult build = GroupResult.builder()
                .id(100000L)
                .groupName("你猜")
                .dec("你猜")
                .enable(Boolean.TRUE)
                .createdById(1000001L)
                .createdByFirstName("你猜")
                .createdByLastName("你猜")
                .updatedById(100002L)
                .updatedByFirstName("你猜")
                .updatedByLastName("你猜")
                .build();
        List<GroupResult> buildList = new ArrayList<>();
        buildList.add(build);
        return PageResult.create(enter, 1, buildList);
    }

    /**
     * 详情
     * @param enter
     * @return
     */
    @Override
    public GroupResult detail(IdEnter enter) {
        return GroupResult.builder()
                .id(100000L)
                .groupName("你猜")
                .dec("你猜")
                .enable(Boolean.TRUE)
                .createdById(1000001L)
                .createdByFirstName("你猜")
                .createdByLastName("你猜")
                .updatedById(100002L)
                .updatedByFirstName("你猜")
                .updatedByLastName("你猜")
                .build();
    }

    /**
     * 保存分组
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveGroupEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除分组
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult delete(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导出
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导入
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult importGroup(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }
}
