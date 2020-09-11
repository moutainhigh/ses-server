package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.service.sys.EmployeeService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.sys.employee.SaveEmployeeEnter;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class EmployeeServiceImplTest extends SesWebRosApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void saveEmployee() {

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

    @Test
    public void  passWord(){
        String test="RedEScooter2019";
        System.out.println(DigestUtils.md5Hex(test+ "40382"));
    }
}
