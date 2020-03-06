package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;
import com.redescooter.ses.web.ros.vo.inquiry.SaveInquiryEnter;

/**
 * @ClassName:InquiryService
 * @description: InquiryService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 14:47
 */
public interface InquiryService {

    /**
     * @desc: 保存询价单
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    GeneralResult saveInquiry(SaveInquiryEnter enter);

    /**
     * @desc: 询价单列表
     * @param: enter
     * @retrn: InquiryResult
     * @auther: alex
     * @date: 2020/3/5 15:44
     * @Version: Ros 1.3
     */
    PageResult<InquiryResult> inquiryList(InquiryListEnter enter);

    /**
     * @desc: 询价单详情
     * @param: enter
     * @retrn: InquiryResult
     * @auther: alex
     * @date: 2020/3/5 15:45
     * @Version: Ros 1.3
     */
    InquiryResult inquiryDetail(IdEnter enter);

    /**
     * 接受询价单 转化为潜在客户
     *
     * @param enter
     * @return
     */
    GeneralResult acceptInquiry(IdEnter enter);
}
