package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.vo.DriverDetailsResult;
import com.redescooter.ses.web.delivery.vo.ListDriverPage;
import com.redescooter.ses.web.delivery.vo.ListDriverResult;
import com.redescooter.ses.web.delivery.vo.SaveDriverEnter;

import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 6:54 上午
 * @ClassName: DriverService
 * @Function: TODO
 */
public interface DriverService {

    /**
     * 创建司机
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveDriverEnter enter);

    /**
     * 2B司机账号开通
     *
     * @param enter
     * @return
     */
    BaseUserResult openDriver2BAccout(SaveDriverEnter enter);

    /**
     * 司机状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 司机分页列表
     *
     * @param page
     * @return
     */
    PageResult<ListDriverResult> list(ListDriverPage page);

    /**
     * 司机详情
     *
     * @param enter
     * @return
     */
    DriverDetailsResult details(IdEnter enter);


    /**
     * 司机离职
     *
     * @param enter
     * @return
     */
    GeneralResult leave(IdEnter enter);

    /**
     * 再次发生激活邮件2B
     *
     * @param enter
     * @return
     */
    GeneralResult againSendEmail(IdEnter enter);
}
