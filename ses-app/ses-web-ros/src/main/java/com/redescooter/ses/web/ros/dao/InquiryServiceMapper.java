package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;

import java.util.List;

/**
 * @ClassName:InquiryServiceMapper
 * @description: InquiryServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 11:55
 */
public interface InquiryServiceMapper {

    /**
     * 状态统计
     *
     * @return
     */
    List<CountByStatusResult> countStatus();


    /**
     * 询价单列表
     *
     * @param enter
     * @return
     */
    int inquiryListCount(InquiryListEnter enter);

    /**
     * 询价单列表
     *
     * @param enter
     * @return
     */
    List<InquiryResult> inquiryList(InquiryListEnter enter);

    /**
     * 询价单详情
     *
     * @param enter
     * @return
     */
    InquiryResult inquiryDetail(IdEnter enter);

    /**
     * 查询使用中的邮箱
     *
     * @return
     */
    List<String> usingEmailList();
}
