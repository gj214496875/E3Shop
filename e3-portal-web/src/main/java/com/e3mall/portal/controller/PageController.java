package com.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gj214
 * Created by Enzo Cotter on 2018/8/2.
 */
@Controller
public class PageController {
    @RequestMapping(value = "/index.html")
    public String showIndex(){
        return "index";
    }
}
