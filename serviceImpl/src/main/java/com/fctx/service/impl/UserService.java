package com.fctx.service.impl;

import com.fctx.mapper.UserMapper;
import com.fctx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liuJian on 2016/8/11.
 */
@Service("userService")
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    public int getMaxId() {
        return userMapper.getMaxId();
    }

    public int checkExist(Map<String, Object> maps) {
        return userMapper.checkExist(maps);
    }

    public void insert(Object obj) {
        userMapper.insert(obj);
    }

    public void update(Object obj) {
        userMapper.update(obj);
    }

    public void delById(int id) {
        userMapper.delById(id);
    }

    public Object getEntityById(int id) {
        return userMapper.getEntityById(id);
    }

    public List<Object> getAll() {
        return userMapper.getAll();
    }

    public List<Object> selectAll(Map<String, Object> maps) {
        return userMapper.selectAll(maps);
    }

    public int selectCount(Map<String, Object> maps) {
        return userMapper.selectCount(maps);
    }
}