package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeStockBillService extends IService<OpeStockBill> {


    int updateBatch(List<OpeStockBill> list);

    int batchInsert(List<OpeStockBill> list);

    int insertOrUpdate(OpeStockBill record);

    int insertOrUpdateSelective(OpeStockBill record);

}

