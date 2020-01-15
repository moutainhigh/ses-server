package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 4:26 下午
 * @ClassName: OrderService
 * @Function: TODO
 */
public interface OrderService {

    /**
     * 快递订单模板下载
     *
     * @return
     */
    ResponseEntity<byte[]> downloadTemplate();

    void download(HttpServletResponse response, HttpServletRequest request);

    /**
     * 表格订单导入
     *
     * @param enter
     * @return
     */
    ImportExcelOrderResult importOrders(ImportExcelOrderEnter enter);

}
