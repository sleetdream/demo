package com.example.basecore.mapper;


import com.example.basecore.entity.BaseDO;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 数据持久层接口
 *
 * @author caoshichuan
 * @date 2017-08-22  13:19
 **/
public interface IBaseMapper<T extends BaseDO> {

    <V extends T> V getOne(Map<String, Object> map);

    <V extends T> V getByPrimaryKey(Long id);

    <V extends T> List<V> listByParams(Map<String, Object> map);

    <V extends T> List<V> listAll();

    <V extends T> Page<V> listPage(T entity);

    Integer count(Map<String, Object> map);

    int insert(T entity);

    int insertSelective(T entity);

    int deleteByPrimaryKey(Long id);

    int deleteByIdFalse(Long id);

    int updateByPrimaryKey(T entity);

    int updateByPrimaryKeySelective(T entity);

    int addInBatch(List<T> entityList);

    int updateInBatch(List<T> entityList);

    int deleteByPrimaryKeyInBatch(List<String> idList);

}
