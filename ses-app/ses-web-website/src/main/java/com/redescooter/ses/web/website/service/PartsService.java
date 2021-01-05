package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddPartsEnter;
import com.redescooter.ses.web.website.vo.product.ModityPartsEnter;
import com.redescooter.ses.web.website.vo.product.PartsDetailsResult;

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


    /**
     * 获取配件详情
     *
     * @param enter
     */
    PartsDetailsResult getPartsDetails(IdEnter enter);


    /**
     * 获取配件列表
     *
     * @param enter
     */
    List<PartsDetailsResult> getPartsList(GeneralEnter enter);

}
