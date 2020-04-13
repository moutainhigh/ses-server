package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 15/1/2020 3:33 下午
 * @ClassName: EdOrderServiceImplTest
 * @Function: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EdOrderServiceImplTest {


    @Test
    public void contextLoads() {
    }

    @Reference
    private IdAppService idAppService;

    @Autowired
    private CorDeliveryMapper corDeliveryMapper;

    @Test
    public void saveDelivery() {
//        QueryWrapper<CorDelivery> corDeliveryQueryWrapper=new QueryWrapper<>();
//        corDeliveryQueryWrapper.eq(CorDelivery.COL_DELIVERER_ID,1115543L);
//        List<CorDelivery> corDeliveryList = corDeliveryMapper.selectList(corDeliveryQueryWrapper);
//
//        corDeliveryList.forEach(item->{
////            item.setId(idAppService.getId(SequenceName.COR_DELIVERY));
////            item.setCreatedTime(DateUtil.parse("2020-1-11 10:00:00",DateUtil.));
//        });
    }

}
