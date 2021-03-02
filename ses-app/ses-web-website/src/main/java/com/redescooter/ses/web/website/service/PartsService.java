package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.parts.AddPartsEnter;
import com.redescooter.ses.web.website.vo.parts.ModityPartsEnter;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;

import java.util.List;

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
    GeneralResult addParts(AddPartsEnter enter);

    /**
     * 编辑配件
     *
     * @param enter
     * @return
     */
    GeneralResult modityParts(ModityPartsEnter enter);

    /**
     * 移除配件
     *
     * @param enter
     * @return
     */
    GeneralResult removeParts(IdEnter enter);

    /**
     * 配件详情
     *
     * @param enter
     * @return
     */
    PartsDetailsResult partsDetails(IdEnter enter);

    /**
     * 配件列表
     *
     * @param enter
     * @return
     */
    List<PartsDetailsResult> getPartsList(GeneralEnter enter);
}
