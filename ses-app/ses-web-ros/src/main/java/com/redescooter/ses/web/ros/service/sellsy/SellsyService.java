package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName:SellsyService
 * @description: SellsyService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:38
 */
public interface SellsyService {

    /**
     * sellsy 具体执行器
     *
     * @param enter
     * @return
     */
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter);

    /**
     * 上传文件
     * @param enter
     * @return
     */
    public SellsyGeneralResult sellsyExecutionByFile(SellsyExecutionEnter enter, MultipartFile file);


    /**
     * extractResponseList 会抛出异常所以进行统一的格式处理
     *
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    public <T> List<T> jsonArrayFormattingByPage(SellsyGeneralResult sellsyGeneralResult, T t);

    /**
     * 处理 以下格式数据 "dateResult":{ "id":{ 目标对象 } }
     *
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    public <T> List<T> jsonArrayFormatting(SellsyGeneralResult sellsyGeneralResult, T t);

    /**
     * 处理 以下格式数据 { "dateResult":{ "id":{ 目标对象 } }, "default": "3497458" }
     *
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    public <T> List<T> jsonChildFormatting(SellsyGeneralResult sellsyGeneralResult, T t);

    /**
     * json 转Map
     * 
     * @param sellsyGeneralResult
     * @param
     * @return
     */
    public <T> List<T> jsonMaptoList(SellsyGeneralResult sellsyGeneralResult, T t);

    /**
     * json 处理单个对象
     *
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    public <T> T jsontoJavaObj(SellsyGeneralResult sellsyGeneralResult, T t);

    /**
     *  处理 {"payment":{"payid":10476396,"doctype":"invoice","docid":20109028}} 结构
     * @param sellsyGeneralResult
     * @param t
     * @param <T>
     * @return
     */
    public <T> T jsontoMapJavaObj(SellsyGeneralResult sellsyGeneralResult, T t);


    /**
     *  格式化 创建业务 返回的Id 问题
     * @param sellsyGeneralResult
     * @return
     */
    public SellsyIdResult jsonCreateResut(SellsyGeneralResult sellsyGeneralResult);
}
