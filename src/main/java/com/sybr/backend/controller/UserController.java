package com.sybr.backend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sybr.backend.entity.User;
import com.sybr.backend.service.UserService;
import com.sybr.backend.utils.PageBean;
import com.sybr.backend.utils.QueryListBean;
import com.sybr.backend.utils.Result;
import com.sybr.backend.utils.ResultUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public UserService userService;

//    @GetMapping("/list")
//    public Result list() {
//        List users = userService.queryAll();
//        return ResultUtils.success(users);
//    }
//
//    @GetMapping("/{pageSize}/{pageNum}")
//    public Result pageList(@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNum") Integer pageNum) throws Exception {
//        PageBean<User> pageBean = userService.queryAllByPage(pageSize, pageNum);
//        return ResultUtils.success(pageBean);
//    }


//    @PostMapping("/list")
//    public Result list(@RequestBody String params) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//        QueryListBean<User> queryListBean = mapper.readValue(params, QueryListBean.class);
//        PageBean<User> pageBean = userService.list(queryListBean);
//        return ResultUtils.success(pageBean);
//    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("system/user");
        return mv;
    }

    @PostMapping("/query")
    @ResponseBody
    public Result query(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(params);
        ObjectMapper mapper = new ObjectMapper();
        //JSON转换为QueryListBean对象,queryConditions转换为map
        QueryListBean<User> queryListBean = mapper.readValue(params, QueryListBean.class);
        //JSON转换为QueryListBean对象,queryConditions转换为User
        //QueryListBean<User> queryListBean = mapper.readValue(params, new TypeReference<QueryListBean<User>>(){});
        PageBean<User> pageBean = userService.list(queryListBean);
        return ResultUtils.success(pageBean);
//        System.out.println("params----:"+params);
//        JSONObject param = JSONObject.fromObject(params);
//
//        JSONObject json = new JSONObject();
//        JSONArray dataArray = new JSONArray();
//
//        JSONObject data1 = new JSONObject();
//        data1.put("id","adfddsds3333333aaaaaa");
//        data1.put("url", "uuuuuu");
//        data1.put("title","这是一个测试222");
//        JSONObject data2 = new JSONObject();
//        data2.put("id","sdfsdfsdfs");
//        data2.put("url", "rrrrrrrr");
//        data2.put("title","这是一个测试2");
//        JSONObject data3 = new JSONObject();
//        data3.put("id","eeewewerwer");
//        data3.put("url", "lllllll");
//        data3.put("title","这是一个测试3");
//        JSONObject data4 = new JSONObject();
//        data4.put("id","sssssssssssss");
//        data4.put("url", "aaaaaa");
//        data4.put("title","这是一个测试4ddddddddddd");
//
//        dataArray.add(data1);
//        dataArray.add(data2);
//        dataArray.add(data3);
//        dataArray.add(data4);
//
//        json.put("data", dataArray);
//        return json.toString();
    }

    @GetMapping(value = "/{id}")
    public Result detail(@PathVariable("id") String id) throws Exception {
        User user = userService.getById(id);
        return ResultUtils.success(user);
    }

    public static void main(String[] args) throws Exception{
        String params = "{\"queryConditions\":{\"code\":\"\",\"userDesc\":\"测试账号\"}}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        QueryListBean<User> queryListBean = mapper.readValue(params, new TypeReference<QueryListBean<User>>(){});
        System.out.println(queryListBean);
    }
}
