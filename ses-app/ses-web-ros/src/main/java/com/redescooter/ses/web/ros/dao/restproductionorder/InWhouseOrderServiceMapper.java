package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InWhouseOrderServiceMapper {

    /**
     * @Author Aleks
     * @Description  列表的总数量
     * @Date  2020/11/11 11:10
     * @Param
     * @return
     **/
    int totalNum(@Param("enter") InWhouseListEnter enter);

    /**
     * @Author Aleks
     * @Description  入库单列表SQL
     * @Date  2020/11/11 11:12
     * @Param [enter]
     * @return
     **/
    List<InWhouseListResult> inWhList(@Param("enter") InWhouseListEnter enter);

    /**
     * @Author Aleks
     * @Description  找到生产采购单下面的入库单的所有部件信息
     * @Date  2020/11/16 14:24
     * @Param
     * @return
     **/
    List<OpeInWhousePartsB> inWhousePartsList(@Param("productionPurchaseId")Long productionPurchaseId);


    /**
     * @Author Aleks
     * @Description  通过组装单的id  找到下面所有的入库单的明细（组装类型的入库单）
     * @Date  2020/11/17 15:28
     * @Param
     * @return
     **/
    List<OpeInWhouseCombinB>  inWhouseCombinList(@Param("combinId")Long combinId);

    /**
     * @Author Aleks
     * @Description  通过组装单的id  找到下面所有的入库单的明细（整车类型的入库单）
     * @Date  2020/11/17 15:28
     * @Param
     * @return
     **/
    List<OpeInWhouseScooterB>  inWhouseScooterList(@Param("combinId")Long combinId);
}
