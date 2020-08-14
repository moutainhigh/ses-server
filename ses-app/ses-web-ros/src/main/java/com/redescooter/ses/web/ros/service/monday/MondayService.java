package com.redescooter.ses.web.ros.service.monday;

import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationColumnEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationGroupEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MultipleWebhookEnter;
import com.redescooter.ses.web.ros.vo.monday.result.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayColumnResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGeneralResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationBoardEnter;
import com.redescooter.ses.web.ros.vo.monday.result.MondayTagResult;

import java.util.List;

/**
 * @ClassName:MondayService
 * @description: MondayService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:46
 */
public interface MondayService {

    /**
     * 初始化单据模板
     */
    void initializationMondaytemplate();

    /**
     * 官网联系我们
     *
     * @param enter
     * @return
     */
    MondayCreateResult websiteContantUs(OpeCustomerInquiry enter);

    /**
     * 官网预订单
     *
     * @param enter
     * @return
     */
    MondayCreateResult websiteBookOrder(OpeCustomerInquiry enter);

    /**
     * 官网订阅邮件
     *
     * @param email
     * @return
     */
    MondayCreateResult websiteSubscriptionEmail(String email);


    /**
     * 查询板子
     *
     * @return
     */
    List<MondayBoardResult> queryBoard();

    /**
     * 查询该板子下的分组
     *
     * @param boardId
     * @return
     */
    List<MondayGroupResult> queryGroupByBoardId(String boardId);

    /**
     * 获取标签列表
     *
     * @return
     */
    List<MondayTagResult> queryTagList();

    /**
     * 查询该板子所有列
     *
     * @return
     */
    List<MondayColumnResult> queryColumnResult(String boardId);

    /**
     * 插入板子
     *
     * @param enter
     * @return
     */
    MondayCreateResult mutationBoard(MondayMutationBoardEnter enter);

    /**
     * 插入分组
     *
     * @param enter
     * @return
     */
    MondayCreateResult mutationGroup(MondayMutationGroupEnter enter);

    /**
     * 创建列
     *
     * @param enter
     * @return
     */
    List<MondayColumnResult> multipleColumn(List<MondayMutationColumnEnter> enter);

    /**
     * 创建一个钩子
     *
     * @param enter
     */
    void multipleWebhook(MultipleWebhookEnter enter);

    /**
     * 执行gql 语句
     *
     * @param enter
     * @return
     */
    MondayGeneralResult mutationData(String enter);
}
