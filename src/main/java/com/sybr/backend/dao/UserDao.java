package com.sybr.backend.dao;

import com.sybr.backend.entity.User;
import com.sybr.backend.utils.QueryListBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> list(QueryListBean queryListBean);
    int queryCount(QueryListBean queryListBean);
}
