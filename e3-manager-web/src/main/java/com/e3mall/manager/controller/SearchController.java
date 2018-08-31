package com.e3mall.manager.controller;

import com.e3mall.search.service.SearchItemService;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Enzo Cotter on 2018/8/4.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importItem() {
        return searchItemService.importItems();
    }
}
