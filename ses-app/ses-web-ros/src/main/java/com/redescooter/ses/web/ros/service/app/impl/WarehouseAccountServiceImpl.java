package com.redescooter.ses.web.ros.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.service.operation.FrWhAppService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWarehouseAccountMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.app.WarehouseAccountService;
import com.redescooter.ses.web.ros.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.app.UpdatePasswordEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountListEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountSaveEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountUpdateEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:07
 */
@Service
@Slf4j
public class WarehouseAccountServiceImpl implements WarehouseAccountService {

    @Autowired
    private OpeWarehouseAccountService opeWarehouseAccountService;

    @Autowired
    private OpeWarehouseAccountMapper opeWarehouseAccountMapper;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @DubboReference
    private FrWhAppService frWhAppService;

    @DubboReference
    private IdAppService idAppService;

    @Value("${Request.privateKey}")
    private String privateKey;

    /**
     * ??????tab???count
     */
    @Override
    public Map<String, Integer> getTabCount(GeneralEnter enter) {
        Map<String, Integer> result = Maps.newHashMapWithExpectedSize(2);
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        List<OpeWarehouseAccount> list = opeWarehouseAccountService.list(qw);
        List<OpeWarehouseAccount> companyList = list.stream().filter(o -> o.getType() == 1).collect(Collectors.toList());
        List<OpeWarehouseAccount> partList = list.stream().filter(o -> o.getType() == 2).collect(Collectors.toList());
        result.put("1", companyList.size());
        result.put("2", partList.size());
        return result;
    }

    /**
     * ??????
     */
    @Override
    public PageResult<OpeWarehouseAccount> getList(WarehouseAccountListEnter enter) {
        int count = opeWarehouseAccountMapper.getListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OpeWarehouseAccount> list = opeWarehouseAccountMapper.getList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult add(WarehouseAccountSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // ??????
        check(enter);

        // ????????????
        String decryptPassword;
        try {
            decryptPassword = RsaUtils.decrypt(enter.getNewPassword(), privateKey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        int salt = RandomUtils.nextInt(10000, 99999);
        String pwd = DigestUtils.md5Hex(decryptPassword + salt);
        OpeWarehouseAccount account = new OpeWarehouseAccount();
        account.setId(idAppService.getId(SequenceName.OPE_WAREHOUSE_ACCOUNT));
        account.setDr(Constant.DR_FALSE);
        account.setType(enter.getType());
        account.setName(enter.getName());
        account.setEmail(enter.getEmail());
        account.setPosition(enter.getPosition());
        account.setSystem(enter.getSystem());
        account.setPassword(pwd);
        account.setSalt(String.valueOf(salt));
        account.setStatus(StatusEnum.DISABLE.getCode());
        account.setCreatedBy(enter.getUserId());
        account.setCreatedTime(new Date());
        opeWarehouseAccountService.save(account);
        return new GeneralResult(enter.getRequestId());
    }

    public void check(WarehouseAccountSaveEnter enter) {
        if (StringUtils.isNotBlank(enter.getEmail())) {
            String email = enter.getEmail();
            int firstIndex = email.indexOf("@");
            int secondIndex = email.lastIndexOf(".");

            // 1.????????????@ 2.????????????. 3.@?????????.?????? 4..?????????????????????
            if (firstIndex == -1 || secondIndex == -1 || firstIndex > secondIndex || email.endsWith(".")) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
            }
        }

        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.eq(OpeWarehouseAccount::getEmail, enter.getEmail());
        qw.eq(OpeWarehouseAccount::getSystem, enter.getSystem());
        int count = opeWarehouseAccountService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    /**
     * ??????
     */
    @Override
    public OpeWarehouseAccount getDetail(IdEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(account)) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return account;
    }

    /**
     * ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult edit(WarehouseAccountUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(account)) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.ne(OpeWarehouseAccount::getId, enter.getId());
        qw.eq(OpeWarehouseAccount::getEmail, account.getEmail());
        qw.eq(OpeWarehouseAccount::getSystem, enter.getSystem());
        int count = opeWarehouseAccountService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        account.setName(enter.getName());
        account.setPosition(enter.getPosition());
        account.setSystem(enter.getSystem());
        account.setUpdatedBy(enter.getUserId());
        account.setUpdatedTime(new Date());
        opeWarehouseAccountService.updateById(account);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ?????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editStatus(IdEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(account)) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        Integer status = account.getStatus();

        // ???????????????,???????????????????????????????????????,????????????
        if (1 == status) {
            LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            qw.eq(OpeCarDistribute::getWarehouseAccountId, account.getId());
            List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(qw);
            if (CollectionUtils.isNotEmpty(list)) {
                boolean flag = false;
                for (OpeCarDistribute item : list) {
                    Long customerId = item.getCustomerId();
                    if (StringManaConstant.entityIsNotNull(customerId)) {
                        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
                        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
                        wrapper.eq(OpeCarDistributeNode::getCustomerId, customerId);
                        wrapper.last("limit 1");
                        OpeCarDistributeNode node = opeCarDistributeNodeMapper.selectOne(wrapper);
                        if (StringManaConstant.entityIsNotNull(node)) {
                            Integer nodeFlag = node.getFlag();
                            Integer appNode = node.getAppNode();

                            boolean e = nodeFlag == 1 && (appNode == 2 || appNode == 3 || appNode == 4 || appNode == 5);
                            if (e) {
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                if (flag) {
                    throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_HAS_INQUIRY.getCode(), ExceptionCodeEnums.ACCOUNT_HAS_INQUIRY.getMessage());
                }
            }
        }
        account.setStatus(status == 1 ? 2 : 1);
        boolean flag = opeWarehouseAccountService.updateById(account);
        if (flag) {
            OpeWarehouseAccount model = opeWarehouseAccountService.getById(enter.getId());
            if (2 == model.getStatus()) {
                if (StringUtils.isNotBlank(model.getLastLoginToken())) {
                    // ??????????????????app????????????
                    GeneralEnter generalEnter = new GeneralEnter();
                    generalEnter.setToken(model.getLastLoginToken());
                    frWhAppService.logout(generalEnter);
                }
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updatePassword(UpdatePasswordEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(account)) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        // ????????????
        String decryptPassword;
        try {
            decryptPassword = RsaUtils.decrypt(enter.getNewPassword(), privateKey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        String salt = account.getSalt();
        String pwd = DigestUtils.md5Hex(decryptPassword + salt);
        account.setEmail(enter.getEmail());
        account.setPassword(pwd);
        boolean flag = opeWarehouseAccountService.updateById(account);
        if (flag) {
            if (StringUtils.isNotBlank(account.getLastLoginToken())) {
                // ??????????????????app????????????
                GeneralEnter generalEnter = new GeneralEnter();
                generalEnter.setToken(account.getLastLoginToken());
                frWhAppService.logout(generalEnter);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(account)) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        boolean flag = opeWarehouseAccountService.removeById(enter.getId());
        if (flag) {
            if (StringUtils.isNotBlank(account.getLastLoginToken())) {
                // ??????????????????app????????????
                GeneralEnter generalEnter = new GeneralEnter();
                generalEnter.setToken(account.getLastLoginToken());
                frWhAppService.logout(generalEnter);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

}
