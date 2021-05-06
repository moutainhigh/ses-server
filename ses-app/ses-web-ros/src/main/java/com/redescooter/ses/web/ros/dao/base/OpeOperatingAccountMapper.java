package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.api.common.vo.base.OperatingEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.dm.OpeOperatingAccount;
import com.redescooter.ses.web.ros.vo.account.OperatingAccountListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderListEnter;
import com.redescooter.ses.web.ros.vo.specificat.ColorListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Mapper
public interface OpeOperatingAccountMapper extends BaseMapper<OpeOperatingAccount> {

    int accountTotal(@Param("enter") OperatingEnter enter);

    List<OperatingAccountListResult> accountList(@Param("enter")  OperatingEnter enter);


    }