package com.redescooter.ses.mobile.rps.service.material;

import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.vo.materialqc.*;
import org.thymeleaf.expression.Ids;

import java.util.Map;

/**
 * @ClassName:RpsMaterialService
 * @description: RpsMaterialService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 16:07
 */
public interface MaterialService {

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countByStatus(GeneralEnter enter);

    /**
     * 来料质检列表
     *
     * @param enter
     * @return
     */
    PageResult<MaterialQcListResult> list(PageEnter enter);

    /**
     * 质检失败列表
     *
     * @param enter
     * @return
     */
    PageResult<MaterialQcListResult> failList(PageEnter enter);

    /**
     * 退回并完成
     *
     * @param enter
     * @return
     */
    GeneralResult returnedCompleted(IdsEnter enter);

    /**
     * 继续质检
     *
     * @param enter
     * @return
     */
    GeneralResult againQc(IdsEnter enter);

    /**
     * 来料质检详情
     *
     * @param enter
     * @return
     */
    PageResult<MaterialDetailResult> materialQcDetail(MaterialQcDetailEnter enter);

    /**
     * 来料质检失败详情
     *
     * @param enter
     * @return
     */
    PageResult<MaterialDetailResult> materialQcFailDetail(MaterialQcDetailEnter enter);

    /**
     * 部件质检模板
     *
     * @return
     */
    MaterialQcTemplateDetailResult MaterialQcTemplate(IdEnter enter);

    /**
     * 保存来料质检结果
     *
     * @param enter
     * @return
     */
    SaveMaterialQcResult saveMaterialQc(SaveMaterialQcEnter enter);

    /**
     * 保存采购单记录
     *
     * @param enter
     * @return
     */
    GeneralResult saveNode(SaveNodeEnter enter);
}
