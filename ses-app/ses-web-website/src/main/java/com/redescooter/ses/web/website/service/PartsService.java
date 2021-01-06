package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.parts.AddPartsEnter;
import com.redescooter.ses.web.website.vo.parts.ModityPartsEnter;

/**
 * @Author jerry
 * @Date 2021/1/6 3:23 上午
 * @Description 配件服务
 **/
public interface PartsService {

    /**
     * 创建配件
     *
     * @param enter
     * @return
     */
    Boolean addParts(AddPartsEnter enter);

    /**
     * 编辑配件
     *
     * @param enter
     * @return
     */
    Boolean modityParts(ModityPartsEnter enter);

    /**
     * 移除配件
     *
     * @param enter
     * @return
     */
    Boolean removeParts(IdEnter enter);
}
