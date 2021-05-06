package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.OperatingEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dm.OpeOperatingAccount;
import com.redescooter.ses.web.ros.vo.account.AccountDeatilResult;
import com.redescooter.ses.web.ros.vo.account.OperatingAccountListResult;
import com.redescooter.ses.web.ros.vo.account.OperatingUpdateStatus;
import org.apache.zookeeper.data.Id;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * @author Courtney
 * @since 2021-04-30
 */
public interface OpeOperatingAccountService extends IService<OpeOperatingAccount> {


    /**
     * 添加
     */
    int saveOpeOperatingAccount(OpeOperatingAccount opeOperatingAccount);

    /**
     * 修改
     */
    int updateByPk(OpeOperatingAccount opeOperatingAccount);

    /**
     * 删除
     */
    boolean deleteByPk(IdEnter enter);


    /**
     * 分页查询
     */
    PageResult<OperatingAccountListResult> list(OperatingEnter enter);


    /**
     * 详情
     * @param enter
     * @return
     */
    OpeOperatingAccount accountDeatil(IdEnter enter);

    OpeOperatingAccount updateStatus(OperatingUpdateStatus operatingUpdateStatus);
}