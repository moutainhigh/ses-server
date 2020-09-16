package com.redescooter.ses.web.ros.service.monday;

import com.redescooter.ses.web.ros.vo.monday.enter.*;
import com.redescooter.ses.web.ros.vo.monday.result.*;

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
     * 初始化集合
     */
    void initializationMondayBoardMap();

    /**
     * 初始化单据模板
     */
    void initializationMondaytemplate();

    /**
     * 初始化备份模版
     */
    void initializationBackMondaytemplate();

    /**
     * 官网联系我们
     *
     * @param enter
     * @return
     */
    MondayCreateResult websiteContantUs(MondayGeneralEnter enter);

    /**
     * 官网预订单
     *
     * @param enter
     * @return
     */
    MondayCreateResult websiteBookOrder(MondayGeneralEnter<MondayBookOrderEnter> enter);

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
