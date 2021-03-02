package com.redescooter.ses.mobile.rps.service.restproductionorder.qctrace;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.ProductQcTraceListResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.SaveProductQcTraceEnter;

import java.util.List;

/**
 * @ClassName:ProductQcTempleteService
 * @description: ProductQcTempleteService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 18:34 
 */
public interface ProductQcTraceService {

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 6:36 下午
    * @Param:  enter
    * @Return:
    * @desc: 保存质检记录
    */
    GeneralResult save(SaveProductQcTraceEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 6:36 下午
    * @Param:  enter
    * @Return: ProductQcTempleteListResul
    * @desc: 查询质检记录
    */
    List<ProductQcTraceListResult> list();
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/4 6:37 下午
    * @Param:  enter
    * @Return:
    * @desc: 查询质检记录
    */
    ProductQcTraceListResult getById(IdEnter enter);
}
