package com.sybr.backend.entity;

public class User {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户代码
     */
    private String code;
    private String userDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }
}
