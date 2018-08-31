package com.e3mall.manager.controller;

import com.e3mall.common.EasyUITreeNode;
import com.e3mall.service.ItemCatService;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品目录
 * @author gj214
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping(value = "/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCat(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> catList = itemCatService.getCatList(parentId);
        return catList;
    }
}
