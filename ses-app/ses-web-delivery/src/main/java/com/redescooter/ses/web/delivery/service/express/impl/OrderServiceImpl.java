package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.service.express.OrderService;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 4:34 下午
 * @ClassName: OrderServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ExcelService excelService;

    /**
     * 表格订单导入
     *
     * @param enter
     * @return
     */
    @Override
    public ImportExcelOrderResult importOrders(ImportExcelOrderEnter enter) {
        return null;
    }
}
