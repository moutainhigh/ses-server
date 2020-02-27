package com.redescooter.ses.web.ros.service;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationDetailResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListResult;
import com.redescooter.ses.web.ros.vo.bom.combination.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.SaveCombinationEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.SaveScooterEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterDetailResult;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListResult;

/**
 * @ClassName:BomRosService
 * @description: BomRosService
 * @author: Alex
 * @Version：1.2
 * @create: 2020/02/25 10:18
 */
public interface BomRosService {

    /**
     * @desc: 车辆列表
     * @param: enter
     * @retrn: PageResult
     * @auther: alex
     * @date: 2020/2/25 10:31
     * @Version: Ros 1.2
     */
    PageResult<ScooterListResult> scooterList(ScooterListEnter enter);

    /**
     * @desc: 保存整车
     * @param: enter
     * @eturn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 10:36
     * @Version: Ros 1.2
     */
    GeneralResult saveScooter(SaveScooterEnter enter);

    /**
     * @desc: sec 区域查询
     * @param: enter
     * @retrn: SecResult
     * @auther: alex
     * @date: 2020/2/25 12:33
     * @Version: Ros 1.2
     */
    List<SecResult> secList(GeneralEnter enter);

    /**
     * @desc: 详情部件列表查询
     * @param: SaveScooterPartListEnter
     * @retrn: SaveScooterPartListResult
     * @auther: alex
     * @date: 2020/2/25 12:43
     * @Version: Ros 1.2
     */
    PageResult<QueryPartListResult> partList(QueryPartListEnter enter);

    /**
     * @desc: 整车详情
     * @param: enter
     * @retrn: ScooterDetailResult
     * @auther: alex
     * @date: 2020/2/25 13:19
     * @Version: Ros 1.2
     */
    ScooterDetailResult scooterDetail(IdEnter enter);

    /**
     * @desc: 删除整车的配件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 13:20
     * @Version: Ros 1.2
     */
    GeneralResult deleteScooterPart(DeletePartEnter enter);

    /**
     * @desc: 删除整车
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:37
     * @Version: Ros 1.2
     */
    GeneralResult deleteScooter(IdEnter enter);

    /**
     * @desc: 套餐列表
     * @paam: enter
     * @retrn: CombinationListResult
     * @auther: alex
     * @date: 2020/2/25 14:03
     * @Version: Ros 1.2
     */
    PageResult<CombinationListResult> combinationList(CombinationListEnter enter);

    /**
     * @desc: 列表部件列表
     * @param: enter
     * @retrn: List<QueryPartListResult>
     * @auther: alex
     * @date: 2020/2/27 11:44
     * @Version: Ros 1.2
     */
    List<QueryPartListResult> combinationListPartList(IdEnter enter);

    /**
     * @desc: 套餐详情
     * @param: id
     * @retrn: CombinationResult
     * @auther: alex
     * @date: 2020/2/25 14:05
     * @Version: Ros 1.2
     */
    CombinationDetailResult combinationDetail(IdEnter enter);

    /**
     * @desc: 删除套餐里的部件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:07
     * @Version: Ros 1.2
     */
    GeneralResult deleteCombinationPart(DeletePartEnter enter);

    /**
     * @desc: 删除套餐
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:08
     * @Version: Ros 1.2
     */
    GeneralResult deleteCombination(IdEnter enter);

    /**
     * @desc: 保存套餐
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:29
     * @Version: Ros 1.2
     */
    GeneralResult saveCombination(SaveCombinationEnter enter);


}