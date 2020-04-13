package com.redescooter.ses.web.ros.service.production.assembly.assembly;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class AssemblyServiceImplTest extends SesWebRosApplicationTests {

    @Autowired
    private OpePartsProductService partsProductService;
    @Autowired

    private OpePartsProductBService partsProductBService;
    @Autowired
    private OpeStockService stockService;

    @Autowired
    private AssemblyService assemblyService;


    /**
     * 创建组装单列表
     * 1. 根据所有部件，组合算出生产所有车辆的最大值
     * 2. 根据页面输入数量，动态加载所有部件生产车辆的最大值
     */
    @Test
    public void queryAssemblyProduct() {

        List<ProductionPartsEnter> list = new ArrayList<>();
//        List<OpePartsProduct> products = partsProductService.list();
//
//        if (CollectionUtil.isEmpty(products)) {
//            Stream.iterate(0, i -> i + 1).limit(products.size()).forEach(index -> {
//                OpePartsProduct product = products.get(index);
//                ProductionPartsEnter data = new ProductionPartsEnter();
//                data.setId(product.getId());
//                data.setQty(index + 2);
//                list.add(data);
//            });
//        }
//
//        SaveAssemblyProductEnter enter = new SaveAssemblyProductEnter();
//        enter.setProductList(JSON.toJSONString(list));
        List<SaveAssemblyProductResult> results = assemblyService.queryAssemblyProduct(new SaveAssemblyProductEnter());
        log.info("----------------------------------");
        System.out.println(JSON.toJSONString(results));

    }

    @Test
    public void queryAssemblyProduct01() {

        List<ProductionPartsEnter> enterBuilder = new ArrayList<>();
        List<OpePartsProduct> products = partsProductService.list();
        //1.获取所有已入生产仓库的部件总计列表
        LambdaQueryWrapper<OpeStock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeStock::getWhseId, 1000001);
        List<OpeStock> list = stockService.list(wrapper);

        //部品总数
        int sum = list.stream().mapToInt(OpeStock::getAvailableTotal).sum();

        /*****************************此部分可以放入到redis中去,在入库，出库的时候更新redis**************************/
        //2.获取所有车辆产品列表
        List<OpePartsProduct> productList = partsProductService.list();

        //3.所有产品车辆部件列表
        List<OpePartsProductB> productBList = partsProductBService.list();

        //4.创建车辆产品配方
        Map<OpePartsProduct, List<OpePartsProductB>> formula = new HashMap<>();
        if (CollectionUtil.isNotEmpty(productList)) {
            productList.forEach(p -> {
                List<OpePartsProductB> packList = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(productBList)) {
                    packList = productBList.stream().filter(pb -> p.getId().equals(pb.getPartsProductId())).collect(Collectors.toList());
                }
                formula.put(p, packList);
            });
        }
        log.info("----------------------------------");
        /*******************************************************/

        //5.根据库存，计算最大可生产数。
        List<SaveAssemblyProductResult> results = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(formula)) {
            formula.forEach((k, v) -> {
                SaveAssemblyProductResult assemblyProductResult = new SaveAssemblyProductResult();
                assemblyProductResult.setId(k.getId());
                assemblyProductResult.setCnName(k.getCnName());
                assemblyProductResult.setEnName(k.getEnName());
                assemblyProductResult.setProductN(k.getProductNumber());
                AtomicInteger count = new AtomicInteger();
                if (CollectionUtil.isNotEmpty(v)) {
                    Stream.iterate(0, i -> i + 1).limit(v.size()).forEach(index -> {
                        Integer qty = v.get(index).getPartsQty();
                        count.addAndGet(qty);
                    });
                    if (count.get() > sum) {
                        assemblyProductResult.setStocks(0);
                    } else {
                        //获取配方最大值
                        Integer max = v.stream().map(OpePartsProductB::getPartsQty).max(Integer::compareTo).get();
                        //排序取第一个
                        v.sort(Comparator.comparingInt(OpePartsProductB::getPartsQty));
                        OpePartsProductB partsProductB = v.get(0);


                    }
                } else {
                    assemblyProductResult.setStocks(0);
                }

                // System.out.println(count.get());

                results.add(assemblyProductResult);
            });
        }
        System.out.println(JSON.toJSONString(results));
    }
}
