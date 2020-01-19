package com.example.basecore.service;

import com.example.basecore.entity.BaseDO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层基类接口
 *
 * @author caoshichuan
 * @date 2017-08-22  10:27
 **/
public interface IBaseService<T extends BaseDO> {

    /**
     * 根据主键获取数据对象
     * @param id 主键ID
     * @return 数据对象
     * @throws Exception 异常
     */
    T getById(Long id) throws Exception;

    /**
     * 根据条件获取数据对象
     * @param map 查询条件
     * @return 数据对象
     * @throws Exception 异常
     */
    T getOne(Map<String, Object> map) throws Exception;

    /**
     * 根据查询条件获取数据对象集合
     * @param map 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数据条数
     * @return 数据对象集合
     * @throws Exception 异常
     */
    List<T> listByParams(Map<String, Object> map, int pageNum, int pageSize) throws Exception ;

    /**
     * 获取全部对象集合
     * @return 数据对象集合
     * @throws Exception 异常
     */
    List<T> listAll()throws Exception ;

    /**
     * 根据查询条件获取分页对象
     * @param map 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数据条数
     * @return 分页对象
     * @throws Exception 异常
     */
    PageInfo<List<T>> listPage(Map<String, Object> map, int pageNum, int pageSize) throws Exception ;

    /**
     * 新增一条数据对象记录到数据库
     * @param entity  数据对象
     * @throws Exception 异常
     */
    int add(T entity) throws Exception ;

    /**
     * 新增一条数据对象记录（非空值属性）到数据库
     * @param entity 数据对象
     * @throws Exception 异常
     */
    int addSelective(T entity) throws Exception ;

    /**
     * 物理删除
     * @param id 主键ID
     * @return 执行结果
     * @throws Exception 异常
     */
    int delete(Long id) throws Exception ;

    /**
     * 逻辑删除
     * @param id  主键ID
     * @return 执行结果
     * @throws Exception 异常
     */
    int deleteFalse(Long id) throws Exception ;

    /**
     * 更新一条数据对象记录到数据库
     * @param entity 数据对象（包含主键ID）
     * @return 执行结果
     * @throws Exception 异常
     */
    int updateById(T entity) throws Exception ;

    /**
     * 更新一条数据对象记录（非空值属性）到数据库
     * @param entity 数据对象（包含主键ID）
     * @return 执行结果
     * @throws Exception 异常
     */
    int updateByIdSelective(T entity) throws Exception ;


    public int insertInBatch(List<T> entityList) throws Exception ;

    public int updateInBatch(List<T> entityList) throws Exception ;

    public int deleteInBatch(List<String> idList) throws Exception ;

}
