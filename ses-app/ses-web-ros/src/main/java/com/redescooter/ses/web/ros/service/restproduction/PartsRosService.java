package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassNamePartsRosService
 * @Description
 * @Author kyle
 * @Date2020/9/23 18:01
 * @Version V1.0
 **/
public interface PartsRosService {


    /**
     * @Author Aleks
     * @Description  草稿部件的新增
     * @Date  2020/9/27 13:01
     * @Param [enter]
     * @return
     **/
    GeneralResult partsSave(StringEnter enter);


    /**
     * @Author Aleks
     * @Description  草稿删除
     * @Date  2020/9/27 13:01
     * @Param [enter]
     * @return
     **/
    GeneralResult partsDelete(RosPartsBatchOpEnter enter);


    /**
     * @Author Aleks
     * @Description  草稿修改
     * @Date  2020/9/27 13:02
     * @Param [enter]
     * @return
     **/
    GeneralResult partsEdit(StringEnter enter);



    /**
     * @Author Aleks
     * @Description  部件  草稿列表接口
     * @Date  2020/9/27 13:02
     * @Param [enter] classType为1的时候是草稿  为2的时候是部件
     * @return
     **/
    PageResult<RosPartsListResult> partsList(RosPartsListEnter enter);


    /**
     * @Author Aleks
     * @Description  草稿发布
     * @Date  2020/9/27 13:02
     * @Param [enter]
     * @return
     **/
    GeneralResult partsAnnoun(DraftAnnounEnter enter);


    /**
     * @Author Aleks
     * @Description  新增时导入
     * @Date  2020/9/27 13:03
     * @Param [enter]
     * @return
     **/
    List<OpeProductionPartsDraft> importParts(ImportPartsEnter enter);


    /**
     * @Author Aleks
     * @Description  获取发布人的下拉数据源接口
     * @Date  2020/9/27 13:03
     * @Param [tenantId]
     * @return
     **/
    List<StaffDataResult> announUser(Long tenantId);


    /**
     * @Author Aleks
     * @Description  校验发布人的安全码
     * @Date  2020/9/27 13:03
     * @Param [enter]
     * @return
     **/
    BooleanResult checkAnnounUserSafeCode(RosCheckAnnounSafeCodeEnter enter);


    GeneralResult partsCopy(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  部件禁用
     * @Date  2020/9/27 13:03
     * @Param [enter]
     * @return
     **/
    GeneralResult partsDisable(RosPartsBatchOpEnter enter);


    /**
     * @Author Aleks
     * @Description  草稿  部件的列表统计
     * @Date  2020/9/27 13:03
     * @Param [enter]
     * @return
     **/
    Map<String,Integer> listCount(GeneralEnter enter);


    /**
     * @Author Aleks
     * @Description  部件的导出
     * @Date  2020/9/27 13:04
     * @Param [id, response]
     * @return
     **/
    GeneralResult partsExport(String id, HttpServletResponse response);


    /**
     * @Author Aleks
     * @Description  发布的校验（当前数据是否有重复的部件号）
     * @Date  2020/9/27 13:04
     * @Param [enter]
     * @return
     **/
    List<RosRepeatResult> saveAnnounCheck(StringEnter enter);


    /**
     * @Author Aleks
     * @Description  发布的校验（跟真实数据是否有重复的部件号）
     * @Date  2020/9/27 13:04
     * @Param [enter]
     * @return
     **/
    List<RosRepeatResult> saveAnnounCheck2(StringEnter enter);


    /**
     * @Author Aleks
     * @Description  禁用部件时校验  被使用的部件不能禁用
     * @Date  2020/9/27 14:39
     * @Param [enter]
     * @return
     **/
    List<RosRepeatResult> partsDisableCheck(RosPartsBatchOpEnter enter);
}
