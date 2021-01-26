package com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName:SaveProductQcTraceEnter
 * @description: SaveProductQcTraceEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 18:57 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveProductQcTraceEnter extends GeneralEnter {

    @ApiModelProperty(value="订单序列号绑表Id")
    private Long orderSerialId;

    @ApiModelProperty(value="序列号")
    private String serialNum;

    @ApiModelProperty(value="批次号")
    private String batchNo;

    @ApiModelProperty(value="质检结果1、PASS 2 NG")
    private Integer qcResult;

    @ApiModelProperty(value="附件 多个附件逗号分隔")
    private String annex;

    @ApiModelProperty(value="质检信息")
    private List<SaveProductQcInfoEnter> saveProductQcInfoEnterList;

}
