package com.redescooter.ses.mobile.rps.service.scooterqc;


import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;

public interface ScooterqcService {
    /**
     * @Author kyle
     * @Description //查询整车质检列表并展示
     * @Date 2020/4/14 14:36
     * @Param [enter]
     **/
    PageResult<ScooterQcOneResult> scooterQcList(PageEnter enter);

    /**
     * @Author kyle
     * @Description //根据组装单id查询到对应的部件详情列表
     * @Date 2020/4/14 14:36
     * @Param [enter]
     **/
    PageResult<ScooterQcPartResult> partListById(ScooterQcIdEnter enter);

    /**
     * @Author kyle
     * @Description //根据组装单id和部件id查询到对应的具体部件质检详情列表
     * @Date 2020/4/14 14:36
     * @Param [enter]
     **/
    PageResult<ScooterQcItemResult> scooterQcItem(ScooterQcIdItemEnter enter);

    /**
     * @Author kyle
     * @Description //提交部件质检选项详情列表修改
     * @Date 2020/4/14 14:36
     * @Param [enter]
     **/
    ScooterQcResidueNumResult setScooterQcItem(ScooterQcItemEnter enter);
}
