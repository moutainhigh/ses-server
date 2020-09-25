package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
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


    GeneralResult partsSave(StringEnter enter);


    GeneralResult partsDelete(RosPartsBatchOpEnter enter);


    GeneralResult partsEdit(StringEnter enter);



    PageResult<RosPartsListResult> partsList(RosPartsListEnter enter);


    GeneralResult partsAnnoun(DraftAnnounEnter enter);


    ImportExcelPartsResult importParts(ImportPartsEnter enter);


    List<StaffDataResult> announUser(Long tenantId);


    Boolean checkAnnounUserSafeCode(RosCheckAnnounSafeCodeEnter enter);


    GeneralResult partsCopy(IdEnter enter);


    GeneralResult partsDisable(RosPartsBatchOpEnter enter);


    Map<String,Integer> listCount(GeneralEnter enter);


    GeneralResult partsExport(String id, HttpServletResponse response);


    List<RosRepeatResult> saveAnnounCheck(StringEnter enter);


}
