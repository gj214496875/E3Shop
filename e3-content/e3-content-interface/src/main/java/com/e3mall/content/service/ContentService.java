package com.e3mall.content.service;

import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.pojo.TbContent;
import com.e3mall.utils.E3Result;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018/8/3.
 */
public interface ContentService {
    EasyUIDataGridResult queryPageList(Long categoryId, int page, int rows);
    E3Result addContent(TbContent content);
    E3Result updateContent(TbContent content);
    E3Result deleteContent(List<Long> ids);
}
