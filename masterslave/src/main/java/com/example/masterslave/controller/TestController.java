package com.example.masterslave.controller;

import com.example.masterslave.annotation.TargetDataSource;
import com.example.masterslave.enums.DataSourceTypeEnum;
import com.example.masterslave.mapper.TestMapper;
import com.example.masterslave.model.Test;
import com.example.masterslave.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Resource
    private TestMapper testMapper;

    @GetMapping("/get")
    public Object getValue(Integer id){
        return testMapper.selectByPrimaryKey(id);
    }

    @PostMapping("/update")
    public Object postValue(Integer id,String value){
        Test test = new Test();
        test.setId(id);
        test.setValue(value);
        return testMapper.updateByPrimaryKey(test);
    }

    @TargetDataSource(DataSourceTypeEnum.SLAVE)
    @PutMapping("/insert")
    public Object putValue(Integer id, String value){
        Test test = new Test();
        test.setId(id);
        test.setValue(value);
        return testService.insert(test);
    }

    @TargetDataSource(DataSourceTypeEnum.SLAVE)
    @DeleteMapping("/delete")
    public Object deleteValue(Integer id){
        return testService.deleteByPrimaryKey(id);
    }
} 