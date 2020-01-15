package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.service.express.OrderService;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;

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

    @Override
    public void download(HttpServletResponse response) {

        String fileName = "expressOrder.xls";
        String path = "src/main/resources/template/";

        excelService.download(fileName, path, response);
    }

    /**
     * 表格订单导入
     *
     * @param enter
     * @return
     */
    @Override
    public ImportExcelOrderResult importOrders(ImportExcelOrderEnter enter) {
        ImportExcelOrderResult orderResult = excelService.readExcelDataByOrder(enter);

        //解析成功后，进行数据的保存
        if (orderResult.getSuccess()) {

        }

        return orderResult;
    }


}
