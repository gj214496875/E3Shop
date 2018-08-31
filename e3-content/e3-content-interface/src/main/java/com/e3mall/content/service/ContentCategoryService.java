package com.e3mall.content.service;

import com.e3mall.common.EasyUITreeNode;
import com.e3mall.utils.E3Result;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018/8/2.
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> selectContentCategory(Long parentId);
    E3Result createTreeNode(Long parentId, String name);
    void updateTreeNode(Long id, String name);
    void delectTreeNode(Long id);
}
