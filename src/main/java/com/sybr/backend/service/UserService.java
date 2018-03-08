package com.sybr.backend.service;

import com.sybr.backend.entity.User;
import com.sybr.backend.utils.PageBean;
import com.sybr.backend.utils.QueryListBean;

public interface UserService {

    PageBean<User> list(QueryListBean queryListBean);
    public User getById(String id);
}
