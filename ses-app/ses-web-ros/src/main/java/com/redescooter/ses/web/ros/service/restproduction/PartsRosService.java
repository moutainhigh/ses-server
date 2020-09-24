package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosCheckAnnounSafeCode;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;

import java.util.List;

/**
 * @ClassNamePartsRosService
 * @Description
 * @Author kyle
 * @Date2020/9/23 18:01
 * @Version V1.0
 **/
public interface PartsRosService {


    GeneralResult partsSave(List<RosPartsSaveOrUpdateEnter> enter);


    GeneralResult partsDelete(IdEnter enter);


    GeneralResult partsEdit(List<RosPartsSaveOrUpdateEnter> enter);



    PageResult<RosPartsListResult> partsList(RosPartsListEnter enter);


    GeneralResult partsAnnoun(IdEnter enter);


    ImportExcelPartsResult importParts(ImportPartsEnter enter);


    List<StaffDataResult> announUser(Long tenantId);


    Boolean checkAnnounUserSafeCode(RosCheckAnnounSafeCode enter);


    GeneralResult partsCopy(IdEnter enter);


    GeneralResult partsDisable(IdEnter enter);
}
