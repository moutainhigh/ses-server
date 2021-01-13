package com.redescooter.ses.mobile.rps.vo.outwhorder;

import lombok.Data;

/**
 * 产品质检结果信息 DTO
 * @author assert
 * @date 2021/1/7 9:55
 */
@Data
public class QcProductResultDTO {

    /**
     * 质检模板id
     */
    private Long templateId;

    /**
     * 质检结果id
     */
    private Long templateResultId;

    /**
     * 图片url,多个用“,”号隔开
     */
    private String imageUrls;

    /**
     * 备注信息
     */
    private String remark;

}
