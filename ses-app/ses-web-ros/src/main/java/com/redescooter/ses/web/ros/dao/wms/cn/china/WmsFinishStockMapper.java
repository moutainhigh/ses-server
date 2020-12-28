package com.redescooter.ses.web.ros.dao.wms.cn.china;

import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockRecordResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishScooterDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 成品库dao层
 * @author: Aleks
 * @create: 2020/12/28 15:12
 */
public interface WmsFinishStockMapper {

    /**
     * 成品库车辆list统计
     * @param enter
     * @return
     */
    int totalRows(@Param("enter") WmsFinishScooterListEnter enter);

    /**
     * 成品库车辆list
     * @param enter
     * @return
     */
    List<WmsFinishScooterListResult> finishScooterList(@Param("enter") WmsFinishScooterListEnter enter);


    /**
     * 成品库车辆详情
     * @param id
     * @return
     */
    WmsfinishScooterDetailResult finishScooterDetail(@Param("id")Long id);


    /**
     * 入库记录
     * @param id
     * @return
     */
    List<WmsStockRecordResult> inStockRecord(@Param("id")Long id);

}
