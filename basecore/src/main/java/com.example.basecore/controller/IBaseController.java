package com.example.basecore.controller;

import com.example.basecore.entity.BaseDO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 请求处理层基类接口（控制层）
 *
 * @author caoshichuan
 * @date 2017-08-22  13:49
 **/
public interface IBaseController<T extends BaseDO>  {

    public T queryById(Long id) throws Exception;

    public T queryOne(Map<String, Object> map) throws Exception;

    public PageInfo<T> queryList(Map<String, Object> map) throws Exception;

    public List<T> queryAll()throws Exception;

    public int add(T entity) throws Exception;

    public int addSelective(T entity) throws Exception;

    public int delete(Long id) throws Exception;

    public int updateById(T entity) throws Exception;

    public int updateBySelective(T entity) throws Exception;

    public int insertInBatch(List<T> entityList)throws Exception;

    public int updateInBatch(List<T> entityList) throws Exception;

    public int deleteInBatch(List<String> idList) throws Exception;

}
