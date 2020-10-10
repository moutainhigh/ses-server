package com.redescooter.ses.web.ros.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;
import com.redescooter.ses.web.ros.vo.website.SaveAboutUsEnter;

import java.util.Map;

/**
 * @ClassName:InquiryService
 * @description: InquiryService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 14:47
 */
public interface InquiryService {


    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * @desc: 保存询价单
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    GeneralResult saveAboutUs(SaveAboutUsEnter enter);

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
     * 定金支付邮件
     *
     * @param enter
     * @return
     */
    GeneralResult depositPaymentEmail(IdEnter enter);

    /**
     * 尾款支付邮件
     *
     * @param enter
     * @return
     */
    GeneralResult lastParagraphEmail(IdEnter enter);

    /**
     * 接受询价单 转化为潜在客户
     *
     * @param enter
     * @return
     */
    GeneralResult acceptInquiry(IdEnter enter);

    /**
     * 拒绝询价单
     *
     * @param enter
     * @return
     */
    GeneralResult declineInquiry(IdEnter enter);

    /**
     * 询价单数据导出
     * @param enter
     * @return
     */
    GeneralResult inquiryExport(InquiryListEnter enter);
}
