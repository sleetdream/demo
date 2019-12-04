package com.example.masterslave.controller;

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

    //    @TargetDataSource(DataSourceTypeEnum.SLAVE)
    @GetMapping("/get")
    public Object getValue(Integer id){
        return testMapper.selectByPrimaryKey(id);
    }

    //    @TargetDataSource(DataSourceTypeEnum.MASTER)
    @PostMapping("/update")
    public Object postValue(Integer id,String value){
        Test test = new Test();
        test.setId(id);
        test.setValue(value);
        return testMapper.updateByPrimaryKey(test);
    }

    //    @TargetDataSource(DataSourceTypeEnum.MASTER)
    @PutMapping("/insert")
    public Object putValue(Integer id, String value){
        Test test = new Test();
        test.setId(id);
        test.setValue(value);
        return testService.insert(test);
    }

    //    @TargetDataSource(DataSourceTypeEnum.MASTER)
    @DeleteMapping("/delete")
    public Object deleteValue(Integer id){
        return testService.deleteByPrimaryKey(id);
    }
} 