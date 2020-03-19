package com.redescooter.ses.web.ros.service.sys.impl;
import java.util.Date;

import com.redescooter.ses.web.ros.service.sys.EmployeeService;
import com.redescooter.ses.web.ros.vo.sys.employee.SaveEmployeeEnter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void saveEmployee() {

        /**
         * requestId:
         * id: 1004260
         * companyId: 1004220
         * companyName: RedEGroup
         * employeeFirstName: RedE
         * employeeLastName: Aron
         * telCountryCode: 33
         * telephone: 12345678912
         * email: aron@redescooter.com
         * deptId: 1004221
         * deptName: 研发部
         * positionId: 1008362
         * positionName: textt
         * entryDate: 2020-03-18 10:31:39
         * birthday: 1995-03-03 00:00:00
         * addressCountryCode: French
         * address: 地址
         * certificateType: 1
         * positivePicture: https://rede.oss-cn-shanghai.aliyuncs.com/1584411984837.jpg
         * negativePicture: https://rede.oss-cn-shanghai.aliyuncs.com/1584411987404.jpg
         * addressBureauId: 1000001
         * addressBureau: SHANGHAI
         * avatar: https://rede.oss-cn-shanghai.aliyuncs.com/1584347057644.png
         * countryCode: 86
         */
        SaveEmployeeEnter save = new SaveEmployeeEnter();
        save.setId(1004260L);
        save.setCompanyId(1004220L);
        save.setAddressBureauId(0L);
        save.setDeptId(1004221L);
        save.setPositionId(1008362L);
        save.setEmployeeFirstName("RedE");
        save.setEmployeeLastName("Aron");
        save.setTelCountryCode("33");
        save.setTelephone("12345678912");
        save.setEmail("aron@redescooter.com");
        save.setBirthday(new Date());
        save.setAddressCountryCode("French");
        save.setAddress("地址");
        save.setCertificateType("1");
        save.setPositivePicture("https://rede.oss-cn-shanghai.aliyuncs.com/1584411984837.jpg");
        save.setNegativePicture("https://rede.oss-cn-shanghai.aliyuncs.com/1584411984837.jpg");
        save.setAvatar("https://rede.oss-cn-shanghai.aliyuncs.com/1584411984837.jpg");
        save.setUserId(0L);
        save.setTenantId(0L);



        employeeService.saveEmployee(save);
    }
}
