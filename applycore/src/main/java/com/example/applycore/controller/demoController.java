package com.example.applycore.controller;

import com.example.applycore.po.TestDO;
import com.example.applycore.service.TestService;
import com.example.basecore.controller.BaseController;
import com.example.basecore.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class demoController extends BaseController<TestDO>{

    @Autowired
    private TestService testService;

    @Override
    protected IBaseService<TestDO> getBaseService() {
        return testService;
    }
}
