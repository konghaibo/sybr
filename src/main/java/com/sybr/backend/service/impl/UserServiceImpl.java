package com.sybr.backend.service.impl;

import com.sybr.backend.dao.UserDao;
import com.sybr.backend.entity.User;
import com.sybr.backend.service.UserService;
import com.sybr.backend.utils.PageBean;
import com.sybr.backend.utils.QueryListBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;

    @Override
    public PageBean<User> query(Map params) {
        PageBean<User> pageBean = new PageBean<>();
        Integer pageSize = (Integer)params.get("length");
        Integer pageNum = (Integer)params.get("start");
        pageBean.setPageSize(pageSize);
        pageBean.setPageNum(pageNum);
        //根据条件查询用户数据
        List<User> users = userDao.queryAllByPage(params);
        pageBean.setData(users);
        //查询总记录条数
        int count = userDao.queryAllCount(params);
        pageBean.setTotalCount(count);
        int totalPage = (count - 1) / pageSize + 1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public PageBean<User> list(QueryListBean queryListBean){
        // 判断是否需要分页
        // 查询所有
        if(queryListBean.getPageSize()==null || queryListBean.getPageNum()==null){
            PageBean pageBean = new PageBean();
            List<User> users = userDao.list(queryListBean);
            pageBean.setTotalCount(users.size());
            pageBean.setData(users);

            //test,for jquery datatables
            pageBean.setDraw(1);
            pageBean.setRecordsTotal(users.size());
            pageBean.setRecordsFiltered(users.size());
            return pageBean;
        }
        // 需要分页
        int pageSize = queryListBean.getPageSize();
        int pageNum  = queryListBean.getPageNum();
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setPageSize(pageSize);
        pageBean.setPageNum(pageNum);
        // 查询角色数据
        List<User> roles=userDao.list(queryListBean);
        pageBean.setData(roles);
        // 查询总记录条数
        int count = userDao.queryCount(queryListBean);
        pageBean.setTotalCount(count);
        int totalPage = (count-1)/pageSize+1;
        pageBean.setTotalPage(totalPage);

        //test,for jquery datatables
        pageBean.setDraw(1);
        pageBean.setRecordsTotal(count);
        pageBean.setRecordsFiltered(count);

        return pageBean;
    }

    @Override
    public User getById(String id) {
        return null;
    }

}
