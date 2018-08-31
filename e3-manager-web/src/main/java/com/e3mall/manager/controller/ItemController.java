package com.e3mall.manager.controller;

import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.pojo.TbItem;
import com.e3mall.service.ItemService;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理Controller
 * @author gj214
 * @version 1.0
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"/item/{itemId}","/rest/page/item-edit"})
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult pageList(int page, int rows) {
        EasyUIDataGridResult pageList = itemService.getPageList(page, rows);
        return pageList;
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result addItem(TbItem item, String desc) {
        E3Result e3Result = itemService.addItem(item, desc);
        return e3Result;
    }

}
