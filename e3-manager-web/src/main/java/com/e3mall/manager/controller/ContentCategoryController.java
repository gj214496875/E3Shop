package com.e3mall.manager.controller;

import com.e3mall.common.EasyUITreeNode;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 *
 * @author gj214
 * Created by Enzo Cotter on 2018/8/2.
 */
@Controller
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService categoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> selectTreeNode(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> easyUITreeNodes = categoryService.selectContentCategory(parentId);
        return easyUITreeNodes;
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result createTreeNode(Long parentId, String name) {
        return categoryService.createTreeNode(parentId, name);
    }

    @RequestMapping("/content/category/update")
    public void updateTreeNode(Long id, String name) {
        categoryService.updateTreeNode(id, name);
    }

    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public String delectTreeNode(Long id){
        categoryService.delectTreeNode(id);
        return "ok";
    }
}
