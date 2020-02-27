package com.redescooter.ses.web.ros.dao;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListResult;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListResult;

/**
 * @ClassName:BomRosServiceMapper
 * @description: BomRosServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 16:18
 */
public interface BomRosServiceMapper {

    /**
     * @desc: 整车列表
     * @paam: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/26 16:22
     * @Version: Ros 1.2
     */
    int scooterListCount(ScooterListEnter enter);

    /**
     * @desc: 整车列表
     * @param: enter
     * @retrn: List<ScooterListResult>
     * @auther: alex
     * @date: 2020/2/26 16:34
     * @Version: Ros 1.2
     */
    List<ScooterListResult> scooterList(ScooterListEnter enter);

    /**
     * 使用中的产品编号
     *
     * @param enter
     * @return
     */
    List<String> UsingProductNumList(GeneralEnter enter);

    /**
     * @desc: sec 列表
     * @paam: enter
     * @retrn: List<SecResult>
     * @auther: alex
     * @date: 2020/2/26 18:59
     * @Version: Ros 1.2
     */
    List<SecResult> secList(GeneralEnter enter);

    /**
     * @desc: 保存整车的部件列表
     * @paam: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/26 19:03
     * @Version: Ros 1.2
     */
    int partListCount(QueryPartListEnter enter);

    /**
     * @desc: 保存整车的部件列表
     * @paam: enter
     * @retrn: QueryPartListResult
     * @auther: alex
     * @date: 2020/2/26 19:03
     * @Version: Ros 1.2
     */
    List<QueryPartListResult> partList(QueryPartListEnter enter);

    /**
     * @desc: 产品的详情 部品列表（整车，组合）
     * @paam: enter
     * @retrn: List<PartListEnter>
     * @auther: alex
     * @date: 2020/2/26 19:21
     * @Version: Ros 1.2
     */
    List<QueryPartListResult> productDeatilPartList(Long id);

    /**
     * @desc: 组合列表
     * @paam: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/27 11:24
     * @Version: Ros 1.2
     */
    int combinationListCount(CombinationListEnter enter);

    /**
     * @desc: 组合详情
     * @param: enter
     * @retrn: List<CombinationResult>
     * @auther: alex
     * @date: 2020/2/27 11:25
     * @Version: Ros 1.2
     */
    List<CombinationListResult> combinationList(CombinationListEnter enter);
}
