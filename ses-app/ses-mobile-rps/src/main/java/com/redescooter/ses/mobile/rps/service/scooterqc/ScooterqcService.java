package com.redescooter.ses.mobile.rps.service.scooterqc;



import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;


public interface ScooterqcService {
    /**
     * @Author kyle
     * @Description //查询整车质检列表并展示
     * @Date  2020/4/14 14:36
     * @Param [enter]
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcListResult>
     **/
    PageResult<ScooterQcListResult> scooterQcList(PageEnter enter);

    /**
     * @Author kyle
     * @Description //根据组装单id查询到对应的部件详情列表
     * @Date  2020/4/14 14:36
     * @Param [enter]
     * @return com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcPartsResult
     **/
    ScooterQcPartsResult partListById(IdEnter enter);

    /**
     * @Author kyle
     * @Description //根据部件id查询到对应的具体部件质检详情列表
     * @Date  2020/4/14 14:36
     * @Param [enter]
     * @return com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult
     **/
    ScooterQcItemResult scooterQcItem(IdEnter enter);

    /**
     * @Author kyle
     * @Description //提交部件质检选项详情列表修改
     * @Date  2020/4/14 14:36
     * @Param [enter]
     * @return com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcResidueNumResult
     **/
    ScooterQcResidueNumResult setScooterQcItem(ScooterQcItemEnter enter);
}
