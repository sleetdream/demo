package com.example.masterslave.service;


import com.example.masterslave.model.Test;

public interface TestService {
    boolean insert(Test test);

    boolean deleteByPrimaryKey(Integer id);

}
