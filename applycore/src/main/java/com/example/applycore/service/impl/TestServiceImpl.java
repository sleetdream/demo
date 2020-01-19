package com.example.applycore.service.impl;


import com.example.applycore.mapper.TestDOMapper;
import com.example.applycore.po.TestDO;
import com.example.applycore.service.TestService;
import com.example.basecore.mapper.IBaseMapper;
import com.example.basecore.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @function 功能 : enterprise Service层业务实现
 * @author   创建人:sleetdream
 * @date     创建日期:2020-01-19 15:31:09
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<TestDO> implements TestService {

    @Autowired
    private TestDOMapper mapper;

    @Override
    protected IBaseMapper<TestDO> getBaseDao() {
        return mapper;
    }

}

