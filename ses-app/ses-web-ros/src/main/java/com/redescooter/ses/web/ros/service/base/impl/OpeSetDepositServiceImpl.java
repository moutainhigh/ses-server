package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import javax.annotation.Resource;

import com.redescooter.ses.web.ros.dao.base.OpeSetDepositMapper;
import com.redescooter.ses.web.ros.dm.OpeSetDeposit;
import com.redescooter.ses.web.ros.service.base.OpeSetDepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Courtney
 * @since 2021-06-10
 */
@Service
@Slf4j
public class OpeSetDepositServiceImpl extends ServiceImpl<OpeSetDepositMapper, OpeSetDeposit> implements OpeSetDepositService {


}
