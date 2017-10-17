package com._520it.wms.service.impl;

import com._520it.wms.domain.SaleAccount;
import com._520it.wms.mapper.SaleAccountMapper;
import com._520it.wms.service.ISaleAccountService;
import lombok.Setter;

/**
 * Created by 阎神 on 2017/9/26.
 */
public class SaleAccountServiceImpl implements ISaleAccountService {
    @Setter
    private SaleAccountMapper saleAccountMapper;

    @Override
    public void save(SaleAccount saleAccount) {
        saleAccountMapper.insert(saleAccount);
    }
}
