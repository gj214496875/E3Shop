package com.e3mall.content.service.impl;

import com.e3mall.common.EasyUITreeNode;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.dao.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import com.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gj214
 * Created by Enzo Cotter on 2018/8/2.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper categoryMapper;

    @Override
    public List<EasyUITreeNode> selectContentCategory(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categories = categoryMapper.selectByExample(example);
        List<EasyUITreeNode> list = new ArrayList<>();
        for (TbContentCategory category : categories) {
            Long id = category.getId();
            Boolean isParent = category.getIsParent();
            String name = category.getName();
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(id);
            treeNode.setText(name);
            treeNode.setState(isParent ? "closed" : "open");
            list.add(treeNode);
        }
        return list;
    }

    @Override
    public E3Result createTreeNode(Long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        Date date = new Date();
        category.setCreated(date);
        category.setUpdated(date);
        category.setName(name);
        category.setIsParent(false);
        category.setSortOrder(1);
        category.setStatus(1);
        categoryMapper.insert(category);
        TbContentCategory categoryNode = categoryMapper.selectByPrimaryKey(parentId);
        if (!categoryNode.getIsParent()) {
            categoryNode.setIsParent(true);
            categoryMapper.updateByPrimaryKeySelective(categoryNode);
        }
        return E3Result.ok(category);
    }

    @Override
    public void updateTreeNode(Long id, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setId(id);
        category.setName(name);
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void delectTreeNode(Long id) {
        TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
        if (!category.getIsParent()) {
            categoryMapper.deleteByPrimaryKey(id);
            Long parentId = category.getParentId();
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            long count = categoryMapper.countByExample(example);
            if (count == 0) {
                TbContentCategory categoryParent = new TbContentCategory();
                categoryParent.setId(parentId);
                categoryParent.setIsParent(false);
                categoryMapper.updateByPrimaryKeySelective(categoryParent);
            }
        } else {
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> categories = categoryMapper.selectByExample(example);
            for (TbContentCategory tbContentCategory : categories) {
                delectTreeNode(tbContentCategory.getId());
            }
        }
    }
}
