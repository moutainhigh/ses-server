package com.redescooter.ses.web.ros.service.salearea;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.salearea.RoleAreaEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaOpEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaSaveEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleOpEnter;

import java.util.List;

/**
 * @ClassNameSaleAreaService
 * @Description
 * @Author kyle
 * @Date2020/9/3 14:04
 * @Version V1.0
 **/
public interface SaleAreaService {

     GeneralResult saleAreaSave(SaleAreaSaveEnter enter);


     GeneralResult saleAreaDetele(SaleAreaOpEnter enter);


     List roleAreaAuthShow(RoleOpEnter enter);


     GeneralResult roleAreaAuth(RoleAreaEnter enter);


}
