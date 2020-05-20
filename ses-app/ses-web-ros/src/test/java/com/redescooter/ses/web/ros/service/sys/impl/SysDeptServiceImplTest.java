package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysDeptServiceImplTest {

    @Autowired
    private SysDeptService sysDeptService;

    @Test
    public void save() {

        SaveDeptEnter dept = new SaveDeptEnter();
        dept.setPrincipal(0);
        dept.setPId(new Long("1004220"));
        dept.setLevel(Integer.valueOf(DeptLevelEnums.DEPARTMENT.getValue()));
        dept.setName("财务部");
        dept.setCode("Finance Department");
        dept.setSort(1);
        dept.setUserId(0L);
        dept.setTenantId(0L);
        sysDeptService.save(dept);

    }

    @Test
    public void trees() {

        List<DeptTreeReslt> trees = sysDeptService.trees(new GeneralEnter());

        System.out.println(JSON.toJSONString(trees));
    }

    @Test
    public void topDeptartment() {

        DeptTreeReslt deptTreeReslt = sysDeptService.topDeptartment(new IdEnter(new Long("1004221")), null);

        System.out.println(JSON.toJSONString(deptTreeReslt));
    }


    @Test
    public  void payment(){
        String test="{\n" +
                "    \"amount\":100,\n" +
                "    \"amount_capturable\":0,\n" +
                "    \"amount_received\":0,\n" +
                "    \"application\":null,\n" +
                "    \"application_fee_amount\":null,\n" +
                "    \"canceled_at\":null,\n" +
                "    \"cancellation_reason\":null,\n" +
                "    \"capture_method\":\"automatic\",\n" +
                "    \"charges\":{\n" +
                "        \"object\":\"list\",\n" +
                "        \"data\":[\n" +
                "\n" +
                "        ],\n" +
                "        \"has_more\":false,\n" +
                "        \"url\":\"/v1/charges?payment_intent=pi_1GkpSnANSpAXANT5CoqfUgvg\",\n" +
                "        \"request_options\":null,\n" +
                "        \"request_params\":null\n" +
                "    },\n" +
                "    \"client_secret\":\"pi_1GkpSnANSpAXANT5CoqfUgvg_secret_ndnovr884KVYd5PC5rP4OSDZ6\",\n" +
                "    \"confirmation_method\":\"automatic\",\n" +
                "    \"created\":1589971673,\n" +
                "    \"currency\":\"usd\",\n" +
                "    \"customer\":null,\n" +
                "    \"description\":null,\n" +
                "    \"id\":\"pi_1GkpSnANSpAXANT5CoqfUgvg\",\n" +
                "    \"invoice\":null,\n" +
                "    \"last_payment_error\":null,\n" +
                "    \"livemode\":true,\n" +
                "    \"metadata\":{\n" +
                "        \"order_no\":\"\",\n" +
                "        \"integration_check\":\"accept_a_payment\",\n" +
                "        \"order_id\":\"1000000\"\n" +
                "    },\n" +
                "    \"next_action\":null,\n" +
                "    \"object\":\"payment_intent\",\n" +
                "    \"on_behalf_of\":null,\n" +
                "    \"payment_method\":null,\n" +
                "    \"payment_method_options\":{\n" +
                "        \"card\":{\n" +
                "            \"installments\":null,\n" +
                "            \"request_three_d_secure\":\"automatic\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"payment_method_types\":[\n" +
                "        \"card\"\n" +
                "    ],\n" +
                "    \"receipt_email\":\"etienne@redescooter.fr\",\n" +
                "    \"review\":null,\n" +
                "    \"setup_future_usage\":null,\n" +
                "    \"shipping\":null,\n" +
                "    \"source\":null,\n" +
                "    \"statement_descriptor\":null,\n" +
                "    \"statement_descriptor_suffix\":null,\n" +
                "    \"status\":\"requires_payment_method\",\n" +
                "    \"transfer_data\":null,\n" +
                "    \"transfer_group\":null\n" +
                "}";


        HashMap hashMap = JSON.parseObject(test, HashMap.class);
        System.out.println(hashMap.get("metadata").getClass().getName());

        HashMap mapType = JSON.parseObject(hashMap.get("metadata").toString(),HashMap.class);

        Long orderId=Long.valueOf(mapType.get("order_id").toString());
        System.out.println(orderId);

    }

}
