package com.example.masterslave.service.impl;

import com.example.masterslave.mapper.TestMapper;
import com.example.masterslave.model.Test;
import com.example.masterslave.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    TestMapper testMapper;

    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return testMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean insert(Test test) {
        return testMapper.insert(test) == 1 ? true : false;
    }
}