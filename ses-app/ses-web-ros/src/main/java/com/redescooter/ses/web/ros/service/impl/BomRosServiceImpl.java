package com.redescooter.ses.web.ros.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.bom.BomServiceTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartsAssembly;
import com.redescooter.ses.web.ros.dm.OpePartsAssemblyB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.BomRosService;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyBService;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyService;
import com.redescooter.ses.web.ros.vo.bom.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.CombinationResult;
import com.redescooter.ses.web.ros.vo.bom.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SaveCombinationEnter;
import com.redescooter.ses.web.ros.vo.bom.SaveScooterEnter;
import com.redescooter.ses.web.ros.vo.bom.ScooterDetailResult;
import com.redescooter.ses.web.ros.vo.bom.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.ScooterListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName:BomRosServiceImpl
 * @description: BomRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 16:17
 */
@Slf4j
@Service
public class BomRosServiceImpl implements BomRosService {

    @Autowired
    private BomRosServiceMapper bomRosServiceMapper;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private OpePartsAssemblyBService opePartsAssemblyBService;

    @Autowired
    private OpePartsAssemblyService opePartsAssemblyService;

    /**
     * @param enter
     * @desc: 车辆列表
     * @param: enter
     * @retrn: PageResult
     * @auther: alex
     * @date: 2020/2/25 10:31
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ScooterListResult> scooterList(ScooterListEnter enter) {
        int count=bomRosServiceMapper.scooterListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,bomRosServiceMapper.scooterList(enter));
    }

    /**
     * @param enter
     * @desc: 保存整车
     * @param: enter
     * @eturn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 10:36
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult saveScooter(SaveScooterEnter enter) {
        // 产品编号过滤
        List<String> productNList=bomRosServiceMapper.UsingProductNumList(enter);
        if (productNList.contains(enter.getProductN())){
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(),ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
        }
        Long assemblyId=idAppService.getId(SequenceName.OPE_PARTS_ASSEMBLY);

        // json 转换
        List<PartListEnter> partList=null;
        try {
            partList = JSONArray.parseArray(enter.getPartList(), PartListEnter.class );
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        int partAllQty=0;
        //子表都保存
        if (CollectionUtils.isNotEmpty(partList)){
           List<OpePartsAssemblyB> opePartsAssemblyList = new ArrayList<>();
            for (PartListEnter item : partList) {
                OpePartsAssemblyB opePartsAssemblyB = buildOpePartsAssemblyBSingle(enter, assemblyId, item);
//todo                partAllQty += item.getQty();
                opePartsAssemblyList.add(opePartsAssemblyB);
            }
            opePartsAssemblyBService.saveBatch(opePartsAssemblyList);
        }
        // 保存主表
        OpePartsAssembly opePartsAssembly = buildOpePartsAssemblySingle(enter, assemblyId, partAllQty);
        opePartsAssemblyService.save(opePartsAssembly);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: sec 区域查询
     * @param: enter
     * @retrn: SecResult
     * @auther: alex
     * @date: 2020/2/25 12:33
     * @Version: Ros 1.2
     */
    @Override
    public List<SecResult> secList(GeneralEnter enter) {
        return bomRosServiceMapper.secList(enter);
    }

    /**
     * @param enter
     * @desc: 详情部件列表查询
     * @param: SaveScooterPartListEnter
     * @retrn: SaveScooterPartListResult
     * @auther: alex
     * @date: 2020/2/25 12:43
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<QueryPartListResult> partList(QueryPartListEnter enter) {
        int count=bomRosServiceMapper.ScotoerPartListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,bomRosServiceMapper.ScotoerPartList(enter));
    }

    /**
     * @param enter
     * @desc: 整车详情
     * @param: enter
     * @retrn: ScooterDetailResult
     * @auther: alex
     * @date: 2020/2/25 13:19
     * @Version: Ros 1.2
     */
    @Override
    public ScooterDetailResult scooterDetail(IdEnter enter) {
//        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
//        if (scooter == null) {
//            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
//        }
//        List<PartListEnter> partList =bomRosServiceMapper.scooterDeatilPartList(enter.getId());
//        ScooterDetailResult scooterDetailResult = ScooterDetailResult.builder()
//                .id(scooter.getId())
//                .productN(scooter.getAssNumber())
//                .productCnName(scooter.getCnName())
//                .procurementCycle(scooter.getProductionCycle())
//                .build();
//        if (CollectionUtils.isNotEmpty(partList)){
//            scooterDetailResult.setPartsList(partList);
//        }
//        return scooterDetailResult;
        return null;
    }

    /**
     * @param enter
     * @desc: 删除整车的配件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 13:20
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteScooterPart(DeletePartEnter enter) {

        if (CollectionUtils.isEmpty(enter.getPartIds())){
            return new GeneralResult(enter.getRequestId());
        }
        // 整车查询
        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
//        if (StringUtils.equals(scooter.getAssType())){
//        }
        // 查询整车配件
//        List<PartListEnter> partList =bomRosServiceMapper.scooterDeatilPartList(enter.getId());
//        if (CollectionUtils.isEmpty(partList)){
//            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_HAS_NO_PARTS.getCode(),ExceptionCodeEnums.SCOOTER_HAS_NO_PARTS.getMessage());
//        }
//        enter.getPartIds().forEach(item->{
//            if (!partList.contains(item)){
//                throw new SesWebRosException(ExceptionCodeEnums.DATA_ILLEGAL.getCode(),ExceptionCodeEnums.DATA_ILLEGAL.getMessage());
//            }
//        });

        //数据删除
        opePartsAssemblyBService.removeByIds(enter.getPartIds());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 删除整车
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:37
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteScooter(IdEnter enter) {
        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
//        if (){
//
//        }

        return null;
    }

    /**
     * @param enter
     * @desc: 套餐列表
     * @paam: enter
     * @retrn: CombinationListResult
     * @auther: alex
     * @date: 2020/2/25 14:03
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<CombinationResult> combinationList(CombinationListEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 套餐详情
     * @param: id
     * @retrn: CombinationResult
     * @auther: alex
     * @date: 2020/2/25 14:05
     * @Version: Ros 1.2
     */
    @Override
    public CombinationResult combinationDetail(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 删除套餐里的部件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:07
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteCombinationPart(DeletePartEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 删除套餐
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:08
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteCombination(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 保存套餐
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:29
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult saveCombination(SaveCombinationEnter enter) {
        return null;
    }

    private OpePartsAssembly buildOpePartsAssemblySingle(SaveScooterEnter enter, Long assemblyId, int partAllQty) {
        return OpePartsAssembly.builder()
                .id(assemblyId)
                .dr(0)
                .tenantId(0L)
                .userId(0L)
                .status(null)
                .assNumber(enter.getProductN())
                .cnName(enter.getProductName())
                .frName(null)
                .enName(null)
                .inQty(partAllQty)
                .productionCycle(enter.getProcurementCycle())
                .assType(Integer.valueOf(BomServiceTypeEnums.SCOOTER.getValue()))
                .note(null)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpePartsAssemblyB buildOpePartsAssemblyBSingle(SaveScooterEnter enter, Long assemblyId, PartListEnter item) {
        return OpePartsAssemblyB.builder()
                .id(idAppService.getId(SequenceName.OPE_PARTS_ASSEMBLY_B))
                .dr(0)
                .tenantId(0L)
                .userId(0L)
                .status(null)
//                .partsId(item.getId())
                .partsAssemblyId(assemblyId)
//                .partsQty(item.getQty())
                .note(null)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }
}
