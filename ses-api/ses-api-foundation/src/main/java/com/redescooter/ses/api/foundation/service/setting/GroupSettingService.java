package com.redescooter.ses.api.foundation.service.setting;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.setting.GroupListEnter;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveGroupEnter;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:26
 *  @version：V 1.2
 *  @Description:
 */

public interface GroupSettingService {
    /**
     * 分组列表
     * @param enter
     * @return
     */
    PageResult<GroupResult> list(GroupListEnter enter);

    /**
     * 详情
     * @param enter
     * @return
     */
    GroupResult detail(IdEnter enter);

    /**
     * 保存分组
     * @param enter
     * @return
     */
    GeneralResult save(SaveGroupEnter enter);

    /**
     * 删除分组
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 导出
     * @param enter
     * @return
     */
    GeneralResult export(GeneralEnter enter);

    /**
     * 导入
     */
    GeneralResult importGroup(GeneralEnter enter);
}
