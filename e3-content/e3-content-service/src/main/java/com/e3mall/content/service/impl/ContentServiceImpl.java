package com.e3mall.content.service.impl;

import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.content.service.ContentService;
import com.e3mall.dao.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.utils.E3Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gj214
 * Created by Enzo Cotter on 2018/8/3.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EasyUIDataGridResult queryPageList(Long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contents = contentMapper.selectByExample(example);
        //获取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
        //创建结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contents);
        return result;
    }

    @Override
    public E3Result addContent(TbContent content) {
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        contentMapper.insert(content);
        return E3Result.ok();
    }

    @Override
    public E3Result updateContent(TbContent content) {
        Date date = new Date();
        content.setUpdated(date);
        contentMapper.updateByPrimaryKey(content);
        return E3Result.ok();
    }
    @Override
    public E3Result deleteContent(List<Long> ids) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        contentMapper.deleteByExample(example);
        return E3Result.ok();
    }
}
