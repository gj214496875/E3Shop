package com.e3mall.manager.controller;

import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @author gj214
 * on 2018/8/3.
 */
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult pageList(Long categoryId, Integer page, Integer rows) {
        EasyUIDataGridResult result = contentService.queryPageList(categoryId, page, rows);
        return result;
    }

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent content) {
        return contentService.addContent(content);
    }

    @RequestMapping("/content/edit")
    @ResponseBody
    public E3Result updateContent(TbContent content) {
        return contentService.updateContent(content);
    }

    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        return contentService.deleteContent(longs);
    }


}
