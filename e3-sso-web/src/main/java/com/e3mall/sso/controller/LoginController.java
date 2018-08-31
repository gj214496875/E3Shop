package com.e3mall.sso.controller;

import com.e3mall.sso.service.LoginService;
import com.e3mall.utils.CookieUtils;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Enzo Cotter on 2018/8/20.
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/page/login")
    public String showRegister() {
        return "login";
    }

    @RequestMapping("/user/login")
    @ResponseBody
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        E3Result e3Result = loginService.login(username, password);
        if (e3Result.getStatus() == 200) {
            //表示登录成功，将用户信息写入cookie
            String token = e3Result.getData().toString();
            CookieUtils.setCookie(request, response, "token", token);
        }
        return e3Result;
    }
}
