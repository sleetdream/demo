package com.example.basecore.service;

import com.example.basecore.entity.BaseDO;
import com.example.basecore.mapper.IBaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层基类
 *
 * @author caoshichuan
 * @date 2017-08-22  13:14
 **/
public abstract class BaseServiceImpl<T extends BaseDO> implements IBaseService<T> {

    protected abstract IBaseMapper<T> getBaseDao();

    @Override
    public T getById(Long id) throws Exception {
        return getBaseDao().getByPrimaryKey(id);
    }

    @Override
    public T getOne(Map<String, Object> map) throws Exception {
        return getBaseDao().getOne(map);
    }

    @Override
    public List<T> listByParams(Map<String, Object> map, int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        return getBaseDao().listByParams(map);
    }

    @Override
    public List<T> listAll(){
        return getBaseDao().listAll();
    }

    @Override
    public PageInfo<List<T>> listPage(Map<String, Object> map, int pageNum, int pageSize) throws Exception {
        List<T> list = listByParams(map, pageNum, pageSize);
        return new PageInfo(list);
    }

    @Override
    public int add(T entity) throws Exception {
        return getBaseDao().insert(entity);
    }

    @Override
    public int addSelective(T entity) throws Exception {
        return getBaseDao().insertSelective(entity);
    }

    @Override
    public int delete(Long id) throws Exception {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int deleteFalse(Long id) throws Exception {
        return getBaseDao().deleteByIdFalse(id);
    }

    @Override
    public int updateById(T entity) throws Exception {
        return getBaseDao().updateByPrimaryKey(entity);
    }

    @Override
    public int updateByIdSelective(T entity) throws Exception {
        return getBaseDao().updateByPrimaryKeySelective(entity);
    }

    @Override
    public int insertInBatch(List<T> entityList) throws Exception {
        return getBaseDao().addInBatch(entityList);
    }

    @Override
    public int updateInBatch(List<T> entityList) throws Exception {
        return getBaseDao().updateInBatch(entityList);
    }

    @Override
    public int deleteInBatch(List<String> idList) throws Exception {
        return getBaseDao().deleteByPrimaryKeyInBatch(idList);
    }


}
