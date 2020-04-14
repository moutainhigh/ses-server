package com.redescooter.ses.mobile.rps.service.scooterqc.impl;


import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterqcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import org.apache.dubbo.config.annotation.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class ScooterqcServiceImpl implements ScooterqcService {


    /**
     * @Author kyle
     * @Description //查询整车质检列表并展示
     * @Date  2020/4/14 14:37
     * @Param [enter]
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcListResult>
     **/
    @Override
    public PageResult<ScooterQcListResult> scooterQcList(PageEnter enter) {
        ScooterQcListResult scooterQcListResult = new ScooterQcListResult();
        ScooterQcOneResult scooterQcOneResult1 = new ScooterQcOneResult();
        ScooterQcOneResult scooterQcOneResult2 = new ScooterQcOneResult();
        //test_1
        scooterQcOneResult1.setId(1L);
        scooterQcOneResult1.setScooterNum("#REDE_SCOOTERQC_01");
        scooterQcOneResult1.setScooterId(1L);
        scooterQcOneResult1.setCheckTime(new Date());
        scooterQcOneResult1.setWaitInWHName(2685);
        //test_2
        scooterQcOneResult2.setId(1L);
        scooterQcOneResult2.setScooterNum("#REDE_SCOOTERQC_01");
        scooterQcOneResult2.setScooterId(2L);
        scooterQcOneResult2.setCheckTime(new Date());
        scooterQcOneResult2.setWaitInWHName(2686);

        scooterQcListResult.setId(1L);
        scooterQcListResult.setScooterQcOneResultList(Arrays.asList(scooterQcOneResult1, scooterQcOneResult2));
        return PageResult.create(enter, 10, Arrays.asList(scooterQcListResult));
    }

    /**
     * @Author kyle
     * @Description //根据组装单id查询到对应的部件详情列表
     * @Date  2020/4/14 14:37
     * @Param [enter]
     * @return com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcPartsResult
     **/
    @Override
    public ScooterQcPartsResult partListById(IdEnter enter) {
        ScooterQcPartsResult scooterQcPartsResult = new ScooterQcPartsResult();
        //创建具体部件(test_x)
        ScooterQcPartResult scooterQcPartResult1 = new ScooterQcPartResult();
        ScooterQcPartResult scooterQcPartResult2 = new ScooterQcPartResult();
        //test_1
        scooterQcPartResult1.setId(1L);
        scooterQcPartResult1.setPartStr("#REDE_PART_01");
        scooterQcPartResult1.setScooterId(1L);
        scooterQcPartResult1.setPartId(1L);
        scooterQcPartResult1.setPartName("轮胎");
        scooterQcPartResult1.setPartNum(200);
        //test_2
        scooterQcPartResult2.setId(1L);
        scooterQcPartResult2.setPartStr("#REDE_PART_01");
        scooterQcPartResult2.setScooterId(1L);
        scooterQcPartResult2.setPartId(2L);
        scooterQcPartResult2.setPartName("轮胎圈");
        scooterQcPartResult2.setPartNum(300);

        scooterQcPartsResult.setId(1L);
        scooterQcPartsResult.setScooterQcPartsResultList(Arrays.asList(scooterQcPartResult1, scooterQcPartResult2));

        return scooterQcPartsResult;
    }

    /**
     * @Author kyle
     * @Description //根据部件id查询到对应的具体部件质检详情列表
     * @Date  2020/4/14 14:37
     * @Param [enter]
     * @return com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult
     **/
    @Override
    public ScooterQcItemResult scooterQcItem(IdEnter enter) {

        ScooterQcItemOptionResult scooterQcItemOptionResult1 = new ScooterQcItemOptionResult();
        ScooterQcItemOptionResult scooterQcItemOptionResult2 = new ScooterQcItemOptionResult();

        ScooterQcItemOptionsResult scooterQcItemOptionsResult1 = new ScooterQcItemOptionsResult();
        ScooterQcItemOptionsResult scooterQcItemOptionsResult2 = new ScooterQcItemOptionsResult();

        ScooterQcItemResult scooterQcItemResult = new ScooterQcItemResult();

        scooterQcItemOptionResult1.setId(1L);
        scooterQcItemOptionResult1.setPartId(1L);
        scooterQcItemOptionResult1.setQcId(1L);
        scooterQcItemOptionResult1.setQcStatus(1);
        scooterQcItemOptionResult1.setScooterId(1L);
        scooterQcItemOptionResult2 = scooterQcItemOptionResult1;
        scooterQcItemOptionResult2.setId(2L);

        scooterQcItemOptionsResult1.setId(1L);
        scooterQcItemOptionsResult1.setNum(1);
        scooterQcItemOptionsResult1.setScooterId(1L);
        scooterQcItemOptionsResult1.setPartId(1L);
        scooterQcItemOptionsResult1.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586786073258&di=d806d1eed7956761910f4be42dee796a&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F17%2F20181117112448_tGQSf.thumb.700_0.jpeg");
        scooterQcItemOptionsResult1.setScooterQcItemOptionResults(Arrays.asList(scooterQcItemOptionResult1, scooterQcItemOptionResult2));
        scooterQcItemOptionsResult2 = scooterQcItemOptionsResult1;
        scooterQcItemOptionsResult2.setId(2L);

        scooterQcItemResult.setId(1L);
        scooterQcItemResult.setScooterQcItemOptionsResultList(Arrays.asList(scooterQcItemOptionsResult1, scooterQcItemOptionsResult2));
        return scooterQcItemResult;
    }

    /**
     * @Author kyle
     * @Description //提交部件质检选项详情列表修改
     * @Date  2020/4/14 14:37
     * @Param [enter]
     * @return com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcResidueNumResult
     **/
    @Override
    public ScooterQcResidueNumResult setScooterQcItem(ScooterQcItemEnter enter) {
        ScooterQcResidueNumResult scooterQcResidueNumResult = new ScooterQcResidueNumResult();
        scooterQcResidueNumResult.setId(1L);
        scooterQcResidueNumResult.setResidueNum(32);
        return scooterQcResidueNumResult;
    }


}
