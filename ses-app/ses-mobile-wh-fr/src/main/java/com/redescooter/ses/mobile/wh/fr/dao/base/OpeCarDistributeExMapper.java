package com.redescooter.ses.mobile.wh.fr.dao.base;

import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/28 14:36
 */
@Mapper
public interface OpeCarDistributeExMapper {

    /**
     * app询价单列表count
     */
    int getInquiryListCount(@Param("param") InquiryListAppEnter enter, @Param("warehouseAccountId") Long warehouseAccountId);

    /**
     * app询价单列表
     */
    List<InquiryListResult> getInquiryList(@Param("param") InquiryListAppEnter enter, @Param("warehouseAccountId") Long warehouseAccountId);

    /**
     * app询价单详情
     */
    InquiryDetailResult getInquiryDetail(@Param("param") InquiryDetailEnter enter);

    /**
     * 检索数据下拉接口(客户姓名)
     */
    List<String> getNameDataList(@Param("param") StringEnter enter, @Param("warehouseAccountId") Long warehouseAccountId);

    /**
     * 检索数据下拉接口(询价单号)
     */
    List<String> getOrderNoDataList(@Param("param") StringEnter enter, @Param("warehouseAccountId") Long warehouseAccountId);

}
