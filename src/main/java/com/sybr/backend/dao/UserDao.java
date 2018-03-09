package com.sybr.backend.dao;

import com.sybr.backend.entity.User;
import com.sybr.backend.utils.QueryListBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    List<User> list(QueryListBean queryListBean);
    List<User> queryAllByPage(Map map);
    int queryCount(QueryListBean queryListBean);
    int queryAllCount(Map params);
}
