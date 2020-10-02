package com.redescooter.ses.web.ros.controller.sales;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.BaseApplicationTest;
import com.redescooter.ses.web.ros.service.sales.SalesOrderServer;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderDetailsResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderListResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class SalesOrderControllerTest extends BaseApplicationTest {

    @Autowired
    private SalesOrderServer salesOrderServer;

    @Test
    public void countStatus() {
        Map<String, Integer> statusMap = salesOrderServer.countStatus(new GeneralEnter());
        statusMap.forEach((k, v) -> {
            log.info("k=={},v=={}", k, v);
        });
    }

    @Test
    public void list() {
        PageResult<SalesOrderListResult> list = salesOrderServer.list(new SalesOrderEnter());
        log.info(JSON.toJSONString(list));
    }

    @Test
    public void details() {
        SalesOrderDetailsResult details = salesOrderServer.details(new IdEnter(), new Long("1004209"));
        log.info(JSON.toJSONString(details));
    }

    @Test
    public void labels() {
        GeneralResult labels = salesOrderServer.labels(new IdEnter(), new Long("1004209"));
        log.info(labels.getRequestId());
    }

    @Test
    public void cancelWarn() {
        GeneralResult cancelWarn = salesOrderServer.cancelWarn(new IdEnter(), new Long("1004209"));
        log.info(cancelWarn.getRequestId());
    }
}