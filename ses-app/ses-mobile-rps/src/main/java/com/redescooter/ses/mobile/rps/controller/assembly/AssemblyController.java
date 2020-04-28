package com.redescooter.ses.mobile.rps.controller.assembly;

import cn.hutool.db.Page;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.assembly.AssemblyService;
import com.redescooter.ses.mobile.rps.vo.assembly.AssemblyDetailEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.PrintCodeEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.PrintCodeResult;
import com.redescooter.ses.mobile.rps.vo.assembly.ProductFormulaResult;
import com.redescooter.ses.mobile.rps.vo.assembly.QueryProductCodeResult;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaDateEnter;
import com.redescooter.ses.mobile.rps.vo.assembly.SaveFormulaDateResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyDetailResult;
import com.redescooter.ses.mobile.rps.vo.assembly.WaitAssemblyListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:AeeemblyController
 * @description: AeeemblyController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:58
 */
@Log4j2
@Api(tags = {"组装模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = WaitAssemblyListResult.class)
    public Response<PageResult<WaitAssemblyListResult>> list(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(assemblyService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = WaitAssemblyListResult.class)
    public Response<PageResult<WaitAssemblyDetailResult>> detail(@ModelAttribute @ApiParam("请求参数") AssemblyDetailEnter enter) {
        return new Response<>(assemblyService.detail(enter));
    }

    @PostMapping(value = "/formula")
    @ApiOperation(value = "产品配方", response = ProductFormulaResult.class)
    public Response<List<ProductFormulaResult>> formula(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.formula(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存组装数据", response = SaveFormulaDateResult.class)
    public Response<SaveFormulaDateResult> save(@ModelAttribute @ApiParam("请求参数") SaveFormulaDateEnter enter) {
        return new Response<>(assemblyService.save(enter));
    }

    @PostMapping(value = "/printCode")
    @ApiOperation(value = "打印条码", response = PrintCodeResult.class)
    public Response<PrintCodeResult> printCode(@ModelAttribute @ApiParam("请求参数") PrintCodeEnter enter) {
        return new Response<>(assemblyService.printCode(enter));
    }

    @PostMapping(value = "/queryProductCode")
    @ApiOperation(value = "查询打印产品条码结果", response = QueryProductCodeResult.class)
    public Response<QueryProductCodeResult> queryProductCode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.queryProductCode(enter));
    }
}
