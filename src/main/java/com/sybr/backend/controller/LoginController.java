package com.sybr.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @PostMapping(value = "/login")
//    public Result login(@RequestBody String params,HttpServletRequest request) throws IOException {
//        logger.info(params);
//        // 登录
//        ObjectMapper mapper = new ObjectMapper();
//        Map map = mapper.readValue(params, Map.class);
//        Result result = loginService.login(map, request);
//        return result;
//    }

    @RequestMapping("/index")
    public ModelAndView login(Model model) {
        System.out.println("------------------------------------------");
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
