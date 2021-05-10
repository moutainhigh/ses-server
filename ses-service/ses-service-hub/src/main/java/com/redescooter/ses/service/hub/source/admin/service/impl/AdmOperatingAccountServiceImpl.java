package com.redescooter.ses.service.hub.source.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.OperatingEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.oms.EditAccountEnter;
import com.redescooter.ses.api.common.vo.oms.SaveAccountEnter;
import com.redescooter.ses.api.common.vo.oms.SavePasswordAccountEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.admin.AdmOperatingAccountService;
import com.redescooter.ses.api.hub.vo.admin.AdmSysUser;
import com.redescooter.ses.api.hub.vo.admin.OperatingAccountListResult;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.admin.dao.AdmSysUserMapper;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Slf4j
@DS("admin")
@DubboService
public class AdmOperatingAccountServiceImpl extends ServiceImpl<AdmSysUserMapper, AdmSysUser> implements AdmOperatingAccountService {

    @Autowired
    private AdmSysUserMapper admOperatingAccountMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private AdmOperatingAccountService admOperatingAccountService;

    @DubboReference
    private IdAppService idSerService;

    public static final String PRIVATEKEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCchHDOG0HP2oE6neIA0Q4Y0BDdbzNCDJdfCk+GxKA/ma2xEya4huuIQ9xKjO0e0E3CqKrCdrc5mOZqfjI2vg0LiDzHmGfRG3JmmHGXocNJlG/EoQerWQqSAN3GsfHnxWiGamHSLbq2+YcBrHxS93z8nOqAEG0u6IHooiNPdVmKasMzPIr4hU7BcYKvZ88485qQBG5ySu4xggg2h6WJy7lphEjRO8qpZlUqvMcnpu+qzELcaV1vEcCPUhPBzUbCbOupdzbBvD/ebzdnjXvoX4NrtC6vmRD+xCSpNLxnP0OOy8T7erv6W7WfXcB9pvukRSBay2zNWDKCDPUQa0/aZTHXAgMBAAECggEAZrmR4+QF1KqfaF5ZB5otu2FLwFmMdcwXyTvWKCiC2Fs/oVllb3S1v1Byk8dclX23m4vhzxHlfXr5t2kuAbnIjAoVghZoBNkTw3xICXIU3wT6Oj5J707YXi0NYaxGjYyF5rqJi02SHyc0UYfobPeb45wFWJnvXvsg0BMiQ7NMWkhjJSRzm1dw2unKcIoEG28azWvoI6ItRGzMBGIIDgAG2nIoTmLI1NakEa72GvPSk1n5ec2iCz9WEl+O+p11Oj+y9VC89nc802ilUpdBupHR4HzNOg0lFv7FxkxHgjVIY9iejxSVvfZobu/kchbAOsxtsn0iYT9BQkw5TdzwrxDRQQKBgQDJCKJUl5LbX5+CvJ9meUAbd1dEGwoVrwspHAeCdDykgNvoHd+g64OYhZ6GHSN8+biXTBI4cghTjOns/SLS728JiRd99VVl0OsQ2VgYq0ju0ATHkAGUIryKgYnMFNNYc+BsZESx4kC7IEHWJg/jnFkP6ltEUxETbvdFJVxPoxp3NwKBgQDHT+FWKvuabdU6vj6coITdE43CzVIvAH82kAEjJkVRO3m0wMrc+SBBDN+w8HUYaNT7aDUgI3Vq1TMz/2+oGYB31EcFTNygxRffhUugkNxk5ceXmc0wYSPZqj3T3TE/v9KdR+jxOw8LAqnKG6AytAFeSaz/P37CviIlfMKsUikqYQKBgQCX2k3EWDo2OyTiPFirrysdlCf26fXUK0Ti6KlDgUnNeQijbO1fQIdGzqfP5nLIFFm671tUB88CTY7l1NBZRUM6kLOOEaNQJV9qesJw3/FXcwopND39B8AjX3YXEiQSzHTZa2P4ek8rClKw0E9wPsmHnB8UnSpo44kkUfRIs4tqQQKBgQC9Jr3/fMNTB7E5EptxIZV2Cbybhyfbm2kfawLpTMS0FvGl7OGZXISTt5gC3/EAvVyDS54/Zedk//ib/GsG6UzWbAsKqKspmGLTqjjfpRrEVp1Y7omgxE+1nq8WhfDbQzyqLziO1zGrjVd9i0I5XEOALAOWKbgvMSpO0VwfFcojgQKBgBXiUun7TpUcAudrPsvhuy819dCL1Xv/deaaf/O31AY6/xJzWd4mZCCUqb6/SVBqKuglHNHzOyYPE5lTkE5Z3NDIWYbEUlUvGYus4XpyCGA1vigxnckXFqt/VUiCDTDmQemPAe1+KeQfMMrCCgxQV+6GcoYUWy/LD7XkSB02qfvi";

    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveAdmOperatingAccount(SavePasswordAccountEnter enter) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>进来");
        if (enter.getLoginName().length()>50){
            throw new SeSHubException(ExceptionCodeEnums.EMAIL_TOO_LONG.getCode(), ExceptionCodeEnums.EMAIL_TOO_LONG.getMessage());
        }
        if (enter.getDeptName().length()>50){
            throw new SeSHubException(ExceptionCodeEnums.DEPT_TOO_LONG.getCode(), ExceptionCodeEnums.DEPT_TOO_LONG.getMessage());
        }
        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",enter.getLoginName());
        AdmSysUser admOperatingAccount = admOperatingAccountMapper.selectOne(wrapper);
        if(admOperatingAccount!=null){
            throw new SeSHubException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        //密码解密 无法解密时 就是提示密码错误
        if (StringUtils.isBlank(enter.getPassword())) {
            throw new SeSHubException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        String salt = String.valueOf(RandomUtils.nextInt(10000, 99999));
        LoginEnter loginEnter = new LoginEnter();
        loginEnter.setPassword(enter.getPassword());
        //密码解密
        String decryptPassword = checkPassWord(loginEnter);
        //密码MD5 加密
        String password = DigestUtils.md5Hex(decryptPassword + salt);
        admOperatingAccount =new AdmSysUser();
        admOperatingAccount.setId(idSerService.getId(SequenceName.ADM_SYS_USER));
        admOperatingAccount.setDr(Constant.DR_FALSE);
        admOperatingAccount.setSalt(salt);
        admOperatingAccount.setName(enter.getName());
        admOperatingAccount.setLoginName(enter.getLoginName());
        admOperatingAccount.setDeptName(enter.getDeptName());
        admOperatingAccount.setPassword(password);
        admOperatingAccount.setCreatedTime(new Date());
        admOperatingAccount.setCreatedBy(enter.getUserId());
        admOperatingAccount.setUpdatedBy(enter.getUserId());
        admOperatingAccount.setUpdatedTime(new Date());
        admOperatingAccount.setDef1(SysUserSourceEnum.SYSTEM.getValue());
        admOperatingAccount.setAppId("5");
        admOperatingAccount.setSystemId("REDE_DEV");
        admOperatingAccountMapper.insert(admOperatingAccount);

        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateByPk(EditAccountEnter enter) {
        AdmSysUser admOperatingAccount = admOperatingAccountMapper.selectById(enter.getId());
        if(admOperatingAccount==null){
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("dr",0);
        wrapper.ne("id",enter.getId());
        List<AdmSysUser> operatingAccountListResult = admOperatingAccountMapper.selectList(wrapper);
        List<String> emailList = operatingAccountListResult.stream().map(o -> o.getLoginName()).collect(Collectors.toList());
        if (emailList.contains(enter.getLoginName())){
            throw new SeSHubException(ExceptionCodeEnums.EMAIL_TO_REPEAT.getCode(), ExceptionCodeEnums.EMAIL_TO_REPEAT.getMessage());
        }
        AdmSysUser admSysUser = new AdmSysUser();
        admSysUser.setId(enter.getId());
        admSysUser.setDeptName(enter.getDeptName());
        admSysUser.setName(enter.getName());
        admSysUser.setLoginName(enter.getLoginName());
        admSysUser.setUpdatedBy(enter.getUserId());
        admSysUser.setUpdatedTime(new Date());
        admOperatingAccountMapper.updateById(admSysUser);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public Integer deleteByPk(IdEnter enter) {
        AdmSysUser AdmOperatingAccount = admOperatingAccountMapper.selectById(enter.getId());
        if(AdmOperatingAccount==null){
            throw new SeSHubException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return admOperatingAccountMapper.deleteById(enter.getId());
    }

    @Override
    @DS("admin")
    public PageResult<OperatingAccountListResult> list(OperatingEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = admOperatingAccountMapper.accountTotal(enter);
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OperatingAccountListResult> list = admOperatingAccountMapper.accountList(enter);
        return PageResult.create(enter, totalNum, list);
    }

//    @Override
//    @DS("admin")
//    public AdmSysUser accountDeatil(IdEnter enter) {
//        AdmSysUser admSysUser = admOperatingAccountMapper.selectById(enter.getId());
//        return admSysUser;
//    }

    /**
     * Rsa 解密校验
     *
     * @param enter
     * @return
     */
    private String checkPassWord(LoginEnter enter) {
        //密码解密 无法解密时 就是提示密码错误
        if (StringUtils.isBlank(enter.getPassword())) {
            throw new SeSHubException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));
        String decryptPassword = "";
        try {
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), PRIVATEKEY);
        } catch (Exception e) {
            throw new SeSHubException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        return decryptPassword;
    }

    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateStatus(IdEnter idEnter) {
        log.info(idEnter.getId()+"{>>>>>>>>>>>>>>>>11111111111111111}");
        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id",idEnter.getId());
        AdmSysUser admOperatingAccount = admOperatingAccountMapper.selectOne(wrapper);
        log.info(admOperatingAccount+"{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>}");
        if(admOperatingAccount==null){
            throw new SeSHubException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        if (admOperatingAccount.getLoginName().length()>50){
            throw new SeSHubException(ExceptionCodeEnums.EMAIL_TOO_LONG.getCode(), ExceptionCodeEnums.EMAIL_TOO_LONG.getMessage());
        }
        if (admOperatingAccount.getDeptName().length()>50){
            throw new SeSHubException(ExceptionCodeEnums.DEPT_TOO_LONG.getCode(), ExceptionCodeEnums.DEPT_TOO_LONG.getMessage());
        }
        String status = admOperatingAccount.getStatus();
        admOperatingAccount.setStatus(status .equals("0")  ?"1" : "0");
        int result = admOperatingAccountMapper.updateById(admOperatingAccount);
        return new GeneralResult(idEnter.getRequestId());
    }

    @Override
    public GeneralResult editPassword(SavePasswordAccountEnter enter) {
        AdmSysUser admSysUser = admOperatingAccountMapper.selectById(enter.getId());
        if (admSysUser==null){
            throw new SeSHubException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        LoginEnter loginEnter = new LoginEnter();
        loginEnter.setPassword(enter.getPassword());
        String decryptPassword = checkPassWord(loginEnter);
        log.info(decryptPassword+"{>>>>>>>>>>>>>>>>>>>>decryptPassword}");
        String salt = String.valueOf(RandomUtils.nextInt(10000, 99999));
        //密码MD5 加密
        String password = DigestUtils.md5Hex(decryptPassword + salt);
        admSysUser.setPassword(password);
        admSysUser.setId(enter.getId());
        admSysUser.setSalt(salt);
        admOperatingAccountMapper.updateById(admSysUser);
        if (StringUtils.isNotBlank(admSysUser.getLastLoginToken())) {
            // 清除原有token,重新登录
            jedisCluster.del(admSysUser.getLastLoginToken());
            jedisCluster.del(enter.getRequestId());
        }
        //token 为空为系统外设置密码 设置成功过后 清楚 缓存保证一个requestId 只能用一次
        if (StringUtils.isBlank(enter.getToken())) {
            if (jedisCluster.exists(enter.getRequestId())) {
                jedisCluster.del(enter.getRequestId());
            }
        }
        return new GeneralResult(enter.getRequestId());
    }


}