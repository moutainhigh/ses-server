package com.redescooter.ses.web.ros.service.monday;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.vo.monday.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.MondayColumnResult;
import com.redescooter.ses.web.ros.vo.monday.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.MondayMutationDataEnter;
import com.redescooter.ses.web.ros.vo.monday.MondayTagResult;

import java.util.List;

/**
 * @ClassName:MondayService
 * @description: MondayService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:46
 */
public interface MondayService {


    MondayCreateResult websiteContantUs(OpeCustomerInquiry enter);

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
     * 数据保存
     *
     * @param enter
     * @return
     */
    MondayCreateResult mutationData(MondayMutationDataEnter enter);
}
