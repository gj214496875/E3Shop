package com.e3mall.service;

import com.e3mall.common.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getCatList(Long parentId);
}
