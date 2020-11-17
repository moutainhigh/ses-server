package com.redescooter.ses.web.ros.service.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproduct.BomNameData;
import com.redescooter.ses.web.ros.vo.restproduct.BomNoEnter;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameData;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupDataResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyPurchasOrderService
 * @description: AssemblyPurchasOrderService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:09 
 */
public interface ProductionAssemblyOrderService {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 7:15 下午
    * @Param:  enter
    * @Return: map
    * @desc: 类型统计
    */
    Map<Integer, Integer> countByType(GeneralEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/10 2:34 下午
    * @Param:  enter
    * @Return: ProductionAssemblyOrderListResult
    * @desc: 列表
    */
    PageResult<ProductionAssemblyOrderListResult> list(ProductionAssemblyOrderListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 2:45 下午
    * @Param:  AssemblyOrderDetailEnter
    * @Return: ProductionAssemblyOrderDetailResult
    * @desc: 详情
    */
    ProductionAssemblyOrderDetailResult detail(AssemblyOrderDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date:   2020/11/11 4:18 下午
     * @Param:  enter
     * @Return: List<PurchasDetailProductListResult>
     * @desc: 产品详情
     */
    List<AssemblyDetailProductListResult> detailProductList(AssemblyOrderDetailEnter enter);
    /**
     * @Description
     * @Author: alex
     * @Date:   2020/11/11 5:02 下午
     * @Param:  enter
     * @Return: List<AssociatedOrderResult>
     * @desc: 关联订单
     */
    List<AssociatedOrderResult> associatedOrder(IdEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 2:47 下午
    * @Param:  enter
    * @Return: 
    * @desc: 
    */
    List<PurchasDetailProductListResult> productPartDetail(AssemblyOrderDetailEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 3:10 下午
    * @Param:  enter
    * @Return: GeneralResult
    * @desc: 保存组装单
    */
   GeneralResult save(SaveAssemblyOrderEnter enter);
   /**
   * @Description
   * @Author: alex
   * @Date:   2020/11/12 3:46 下午
   * @Param:  enter
   * @Return: GeneralResult
   * @desc: 备料
   */
   GeneralResult materialPreparation(IdEnter enter);
   /**
   * @Description
   * @Author: alex
   * @Date:   2020/11/12 3:46 下午
   * @Param:  enter
   * @Return: GeneralResult
   * @desc: 组装
   */
   GeneralResult assembly(IdEnter enter);
   /**
   * @Description
   * @Author: alex
   * @Date:   2020/11/12
   * @Param:  enter
   * @Return: GeneralResult
   * @desc: 删除
   */
   GeneralResult delete(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/13 10:15 上午
    * @Param:  enter
    * @Return: 车辆分组
    * @desc: 车辆分组
    */
  List<SpecificatGroupDataResult> scooterGroupData(GeneralEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/13 10:16 上午
    * @Param:  enter
    * @Return: ColorDataResult
    * @desc: 根据颜色查询分组
    */
    List<ColorDataResult> colorData(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/13 10:17 上午
    * @Param:  enter
    * @Return: CombinNameData
    * @desc: 查询组装件数据
    */
    List<CombinNameData> combinNameData(CombinNameEnter enter);

    /**
     * @Author Aleks
     * @Description  组装件编号下拉数据源接口
     * @Date  2020/10/20 13:19
     * @Param [enter]
     * @return
     **/
    List<BomNameData> bomNoData(BomNoEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/17 11:23 上午
    * @Param:  id,response
    * @Return: GeneralResult
    * @desc: 产品导出
    */
    GeneralResult export(Long id, HttpServletResponse response);

}
