package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;

import java.util.List;

public interface GroupSettingServiceMapper {

    /**
     * 分组列表
     * @param enter
     * @return
     */
    List<GroupResult> groupList(GroupListEnter enter);
}
