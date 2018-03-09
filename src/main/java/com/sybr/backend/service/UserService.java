package com.sybr.backend.service;

import com.sybr.backend.entity.User;
import com.sybr.backend.utils.PageBean;
import com.sybr.backend.utils.QueryListBean;

import java.util.Map;

public interface UserService {

    PageBean<User> list(QueryListBean queryListBean);
    PageBean<User> query(Map params);

    public User getById(String id);
}
