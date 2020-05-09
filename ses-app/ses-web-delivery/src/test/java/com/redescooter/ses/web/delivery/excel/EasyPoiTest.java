package com.redescooter.ses.web.delivery.excel;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.redescooter.ses.starter.poi.EasyPoiUtils;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 15/1/2020 1:36 下午
 * @ClassName: EasyPoiTest
 * @Function: TODO
 */
public class EasyPoiTest {

    @Test
    public void test1() {
        ExpressOrderExcleData expressOrderExcleData = new ExpressOrderExcleData();
        expressOrderExcleData = null;

        Optional.ofNullable(expressOrderExcleData)
                .ifPresent(u -> {
                    System.out.println("---" + u);
                });

    }

    /**
     * 测试单sheet导出
     *
     * @throws IOException
     */
    @Test
    public void testExportExcel() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");


        boolean notEmpty = CollectionUtils.isNotEmpty(list);

        System.out.println(notEmpty + "===========");

    }

    /**
     * 测试导入
     */
    @Test
    public void testImportExcel() {
        List<ExpressOrderExcleData> list = EasyPoiUtils.importExcel(

                new File("/Users/lijiating/Desktop/快递demo.xls"),
                ExpressOrderExcleData.class, new ImportParams());
        list.forEach((user) -> {
            System.out.println(user);
        });

    }

    @Test
    public void path() throws FileNotFoundException, UnsupportedEncodingException {
        //获取跟目录
        File path = new File(ResourceUtils.getURL("classpath:template").getPath());
        if (!path.exists()) path = new File("");
        System.out.println("path:" + path.getAbsolutePath());

        //如果上传目录为/static/images/upload/，则可以如下获取：
//        File upload = new File(path.getAbsolutePath(), "static/images/upload/");
//        if (!upload.exists()) upload.mkdirs();
//        System.out.println("upload url:" + upload.getAbsolutePath());
        //在开发测试模式时，得到的地址为：{项目跟目录}/target/static/images/upload/
        //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/

    }

    @Test
    public void test09() {
        String a = "a";
        String b = "c";
        String c = "c";
        boolean noneBlank = StringUtils.isNoneBlank(a, b, c);

        System.out.println(noneBlank);
    }
}
