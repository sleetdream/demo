package com.example.basecore.controller;

import com.example.basecore.controller.IBaseController;
import com.example.basecore.entity.BaseDO;
import com.example.basecore.service.IBaseService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求处理层基类（控制层）
 *
 * @author caoshichuan
 * @date 2017-08-22  13:42
 **/
public abstract  class BaseController<T extends BaseDO> implements IBaseController<T> {

    //日志
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected abstract IBaseService<T> getBaseService();

    @RequestMapping(value="/queryById",method= RequestMethod.POST)
    @Override
    public T queryById(@RequestBody Long id) throws Exception{
        Assert.notNull(id,"id不能为空");
        return getBaseService().getById(id);
    }

    @RequestMapping(value="/queryOne",method= RequestMethod.POST)
    @Override
    public T queryOne(@RequestBody Map<String, Object> map) throws Exception{
        return getBaseService().getOne(map);
    }

    @RequestMapping(value="/queryList",method= RequestMethod.POST)
    @Override
    public PageInfo<T> queryList(@RequestBody Map<String, Object> map) throws Exception{
        if(map == null){
            map = new HashMap<>();
        }
        int pageNum = map.get("pageNum") == null ? 0 : (int)map.get("pageNum");
        int pageSize = map.get("pageSize") == null ? 0 : (int)map.get("pageSize");
        List<T> list = (List<T>) getBaseService().listByParams(map,pageNum,pageSize);
        return new PageInfo<T>(list);
    }

    @RequestMapping(value="/queryAll",method= RequestMethod.POST)
    @Override
    public List<T> queryAll() throws Exception{
        return getBaseService().listAll();
    }

    @RequestMapping(value="/add",method= RequestMethod.POST)
    @Override
    public int add(@RequestBody T entity) throws Exception{
        int add = getBaseService().add(entity);
        return add;
    }

    @RequestMapping(value="/addSelective",method= RequestMethod.POST)
    @Override
    public int addSelective(@RequestBody T entity) throws Exception{
        return getBaseService().addSelective(entity);
    }

    @RequestMapping(value="/delete",method= RequestMethod.POST)
    @Override
    public int delete(@RequestBody Long id) throws Exception{
        return getBaseService().deleteFalse(id);
    }

    @RequestMapping(value="/updateById",method= RequestMethod.POST)
    @Override
    public int updateById(@RequestBody T entity) throws Exception{
        return getBaseService().updateById(entity);
    }

    @RequestMapping(value="/updateBySelective",method= RequestMethod.POST)
    @Override
    public int updateBySelective(@RequestBody T entity) throws Exception{
        return getBaseService().updateByIdSelective(entity);
    }

    @RequestMapping(value="/insertInBatch",method= RequestMethod.POST)
    @Override
    public int insertInBatch(@RequestBody List<T> entityList)throws Exception{
        return getBaseService().insertInBatch(entityList);
    }

    @RequestMapping(value="/updateInBatch",method= RequestMethod.POST)
    @Override
    public int updateInBatch(@RequestBody List<T> entityList) throws Exception{
        return getBaseService().updateInBatch(entityList);
    }

    @RequestMapping(value="/deleteInBatch",method= RequestMethod.POST)
    @Override
    public int deleteInBatch(@RequestBody List<String> idList) throws Exception{
        return getBaseService().deleteInBatch(idList);
    }




}
