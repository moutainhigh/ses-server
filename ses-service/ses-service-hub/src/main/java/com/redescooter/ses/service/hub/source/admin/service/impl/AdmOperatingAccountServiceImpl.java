package com.redescooter.ses.service.hub.source.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.OperatingEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.admin.AdmOperatingAccountService;
import com.redescooter.ses.api.hub.vo.admin.AdmSysUser;
import com.redescooter.ses.api.hub.vo.admin.OperatingAccountListResult;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.admin.dao.AdmSysUserMapper;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.code.MainCode;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Slf4j
@DS("admin-dev")
@DubboService
public class AdmOperatingAccountServiceImpl extends ServiceImpl<AdmSysUserMapper, AdmSysUser> implements AdmOperatingAccountService {

    @Autowired
    private AdmSysUserMapper admOperatingAccountMapper;

    @DubboReference
    private AdmOperatingAccountService admOperatingAccountService;

    public static final String PUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnIRwzhtBz9qBOp3iANEOGNAQ3W8zQgyXXwpPhsSgP5mtsRMmuIbriEPcSoztHtBNwqiqwna3OZjman4yNr4NC4g8x5hn0RtyZphxl6HDSZRvxKEHq1kKkgDdxrHx58Vohmph0i26tvmHAax8Uvd8/JzqgBBtLuiB6KIjT3VZimrDMzyK+IVOwXGCr2fPOPOakARuckruMYIINoelicu5aYRI0TvKqWZVKrzHJ6bvqsxC3GldbxHAj1ITwc1GwmzrqXc2wbw/3m83Z4176F+Da7Qur5kQ/sQkqTS8Zz9DjsvE+3q7+lu1n13Afab7pEUgWstszVgyggz1EGtP2mUx1wIDAQAB";

    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public int saveAdmOperatingAccount(AdmSysUser admOperatingEnter) throws Exception {
        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",admOperatingEnter.getLoginName());
        AdmSysUser admOperatingAccount = admOperatingAccountMapper.selectOne(wrapper);
        if(admOperatingAccount!=null){
            throw new SeSHubException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        //密码解密 无法解密时 就是提示密码错误
        if (StringUtils.isBlank(admOperatingEnter.getPassword())) {
            throw new SeSHubException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        admOperatingEnter.setPassword(SesStringUtils.stringTrim(admOperatingEnter.getPassword()));

        String  decryptPassword = RsaUtils.encrypt(admOperatingEnter.getPassword(), PUBLICKEY);
        String salt = String.valueOf(RandomUtils.nextInt(10000, 99999));
        admOperatingEnter.setSalt(salt);
        admOperatingEnter.setPassword(DigestUtils.md5Hex(decryptPassword + salt));
        admOperatingEnter.setCreatedTime(new Date());
        admOperatingEnter.setAppId("5");
        admOperatingEnter.setSystemId("REDE_DEV");
        String code = MainCode.getRandomNumString(7);
        admOperatingEnter.setId(Long.valueOf(code));
        return admOperatingAccountMapper.insert(admOperatingEnter);
    }


    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public int updateByPk(AdmSysUser AdmOperatingAccount) {
        return admOperatingAccountMapper.updateById(AdmOperatingAccount);
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

    @Override
    @DS("admin")
    public AdmSysUser accountDeatil(IdEnter enter) {
        return admOperatingAccountMapper.selectById(enter.getId());
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
        String status = admOperatingAccount.getStatus();
        admOperatingAccount.setStatus(status .equals("Normal")  ?"Expired" : "Normal");
        int result = admOperatingAccountMapper.updateById(admOperatingAccount);
        return new GeneralResult(idEnter.getRequestId());
    }


}
