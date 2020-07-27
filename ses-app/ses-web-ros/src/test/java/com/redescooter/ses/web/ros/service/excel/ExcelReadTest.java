package com.redescooter.ses.web.ros.service.excel;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityEnter;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.web.ros.vo.excle.CityExcelDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelReadTest {

    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private JedisService jedisService;

    @Reference
    private CityBaseService cityBaseService;


    private String url = "https://rede.oss-cn-shanghai.aliyuncs.com/Classeur.xlsx";
    private String url2 = "https://rede.oss-cn-shanghai.aliyuncs.com/Classeur25.xlsx";

    /**
     * 读取地址簿表格
     */
    @Test
    public void readDate() {

        long start = System.currentTimeMillis();

        ExcelImportResult ossExcel = importExcelService.importOssExcel(url2, CityExcelDate.class, new ImportParams());

        log.info("解析数据为：{}", JSON.toJSONString(ossExcel.getList()));
        log.info("消耗时间为：{}", System.currentTimeMillis() - start);

        this.save(ossExcel.getList());
    }


    /**
     * 保存地址簿
     *
     * @param list
     */
    public void save(List<CityExcelDate> list) {

        List<CityEnter> saves = new ArrayList<>();

        //赋值
        if (list.size() > 0) {
            removeDuplicateUser(list).forEach(e -> {
                CityEnter enter = new CityEnter();
                enter.setCode(e.getCityName());
                enter.setName(e.getCityName());
                enter.setLevel(2);
                enter.setPId(new Long("1000000"));
                enter.setStatus("1");
                enter.setLanguage("fr");
                enter.setLongitude(new BigDecimal(e.getGps().split(",")[0].trim()));
                enter.setLatitude(new BigDecimal(e.getGps().split(",")[1].trim()));
                enter.setDef1(e.getCodePostal());
                saves.add(enter);
            });

            cityBaseService.batchSaveCity(saves);
        }

    }

    private  ArrayList<CityExcelDate> removeDuplicateUser(List<CityExcelDate> citys) {
        Set<CityExcelDate> set = new TreeSet<CityExcelDate>(new Comparator<CityExcelDate>() {
            @Override
            public int compare(CityExcelDate o1, CityExcelDate o2) {
                // 字符串,则按照asicc码升序排列
                return o1.getCityName().compareTo(o2.getCityName());
            }
        });
        set.addAll(citys);
        return new ArrayList<CityExcelDate>(set);
    }
}
