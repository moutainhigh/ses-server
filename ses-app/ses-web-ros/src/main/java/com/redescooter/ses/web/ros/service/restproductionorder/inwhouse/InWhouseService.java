package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/**
 * @ClassNameInWhouseService
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
public interface InWhouseService {

    /**
     * @Author Aleks
     * @Description  入库单列表接口
     * @Date  2020/11/11 10:50
     * @Param [enter]
     * @return
     **/
    PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单新增
     * @Date  2020/11/11 11:44
     * @Param
     * @return
     **/
    GeneralResult inWhouseSave(InWhouseSaveOrUpdateEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单编辑
     * @Date  2020/11/11 11:44
     * @Param
     * @return
     **/
    GeneralResult inWhouseEdit(InWhouseSaveOrUpdateEnter enter);

    /**
     * @Author Aleks
     * @Description  列表统计
     * @Date  2020/11/11 12:58
     * @Param
     * @return
     **/
    Map<String,Integer> listCount(GeneralEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单删除
     * @Date  2020/11/11 13:52
     * @Param
     * @return
     **/
    GeneralResult inWhouseDelete(IdEnter enter);

    /**
     * @Author Aleks
     * @Description  入库单详情
     * @Date  2020/11/11 14:42
     * @Param
     * @return
     **/
    InWhouseDetailResult inWhouseDetail(IdEnter enter);

    /**
     * @Author Aleks
     * @Description  入库确认
     * @Date  2020/11/11 14:45
     * @Param
     * @return
     **/
    GeneralResult inWhConfirm(IdEnter enter);

    /**
     * @Author Aleks
     * @Description 入库单准备质检
     * @Date  2020/11/11 14:52
     * @Param
     * @return
     **/
    GeneralResult inWhReadyQc(IdEnter enter);
}
