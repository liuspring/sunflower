package com.fctx.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuJian on 2016/8/11.
 */
@Repository
public interface BaseMapper {

    /**
     * 获取最大ID
     */
    public int getMaxId();
    /**
     * 查询所有记录
     */
    public List<Object> getAll();
    /**
     * 检查数据是否重复
     */
    public int checkExist(Map<String, Object> maps);

    /**
     * 添加一条记录
     *
     * @param obj
     */
    public void insert(Object obj);

    /**
     * 更新一条记录
     *
     * @param obj
     */
    public void update(Object obj);

    /**
     * 删除一条记录
     *
     * @param id
     */
    public void delById(int id);

    /**
     * 查询一条记录
     *
     * @param id
     */
    public Object getEntityById(int id);

    /**
     * 按条件查询记录
     *
     * @param maps
     */
    public List<Object> selectAll(Map<String, Object> maps);

    /**
     * 按条件查询记录条数
     *
     * @param maps
     */
    public int selectCount(Map<String, Object> maps);
}