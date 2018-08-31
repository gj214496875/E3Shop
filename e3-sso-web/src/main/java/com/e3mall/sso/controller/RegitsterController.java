package com.e3mall.sso.controller;

import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.UserService;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Enzo Cotter on 2018/8/20.
 */
@Controller
public class RegitsterController {
    @Autowired
    private UserService userService;

    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/user/check/{param}/{status}")
    @ResponseBody
    public E3Result checking(@PathVariable String param, @PathVariable Integer status) {
        return userService.checking(param, status);
    }

    @RequestMapping("/user/register")
    @ResponseBody
    public E3Result register(TbUser tbUser) {
        return userService.register(tbUser);
    }
}
