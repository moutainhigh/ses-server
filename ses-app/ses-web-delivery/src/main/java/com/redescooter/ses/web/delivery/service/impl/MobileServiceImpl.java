package com.redescooter.ses.web.delivery.service.impl;

import cn.hutool.db.PageResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.service.MobileService;
import com.redescooter.ses.web.delivery.vo.mobile.MobileDetailResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListResult;
import org.apache.dubbo.config.annotation.Service;

import java.util.Map;

/**
 * @ClassName:MobileServiceImpl
 * @description: MobileServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 16:01
 */
@Service
public class MobileServiceImpl implements MobileService {

    /**
     * 状态分组
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> statusByCount(GeneralEnter enter) {
        return null;
    }

    /**
     * 列表
     *
     * @param enter
     * @return
     */
    @Override
    public MobileListResult list(MobileListEnter enter) {
        return null;
    }

    /**
     * 车辆详情
     *
     * @param enter
     * @return
     */
    @Override
    public MobileDetailResult detail(IdEnter enter) {
        return null;
    }

    /**
     * 分配记录
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MobileHistroyResult> assignMobileHistroy(MobileHistroyEnter enter) {
        return null;
    }

    /**
     * 维修记录
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MobileHistroyResult> repairnMobileHistroy(MobileHistroyEnter enter) {
        return null;
    }
}
