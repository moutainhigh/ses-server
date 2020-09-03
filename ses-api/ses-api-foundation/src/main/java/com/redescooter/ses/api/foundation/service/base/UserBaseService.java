package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.vo.tenant.SynchTenantEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeDetailResult;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.foundation.vo.user.SaveAccountNodeEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 7:14 下午
 * @ClassName: UserBaseService
 * @Function: TODO
 */
public interface UserBaseService {

    /**
     * 查询user
     *
     * @param enter
     * @return
     */
    QueryUserResult queryUserById(GeneralEnter enter);

    /**
     * 根据邮箱获取用户信息
     *
     * @param enter
     * @return
     */
    List<BaseUserResult> queryEmailInfo(StringEnter enter);

    /**
     * 保存账户节点
     *
     * @param enter
     * @return
     */
    GeneralResult saveAccountNode(SaveAccountNodeEnter enter);

    /**
     * 批量保存账户节点
     *
     * @param enter
     * @return
     */
    GeneralResult saveAccountNodeList(List<SaveAccountNodeEnter> enter);

    /**
     * 账户节点详情
     *
     * @param enter
     * @return
     */
    List<QueryAccountNodeDetailResult> accountNodeDetail(QueryAccountNodeEnter enter);

    /**
     * @Author Aleks
     * @Description  校验ROS中的客户是否已激活
     * @Date  2020/8/25 15:33
     * @Param
     * @return
     **/
    public boolean checkActivat(String email);


    /**
     * @Author Aleks
     * @Description 获取用户的id
     * @Date  2020/8/26 15:40
     * @Param
     * @return
     **/
    Long getUserId(String email,List<Integer> types);


    /**
     * @Author Aleks
     * @Description  ros 修改客户的时候，数据同步到platform数据库的租户表
     * @Date  2020/8/26 16:58
     * @Param
     * @return
     **/
    void custDataSynchTenant(SynchTenantEnter synchTenantEnter);

}
