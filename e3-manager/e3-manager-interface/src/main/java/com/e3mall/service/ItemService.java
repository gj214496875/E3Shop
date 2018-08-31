package com.e3mall.service;

import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.utils.E3Result;

public interface ItemService {
	EasyUIDataGridResult getPageList(int page, int rows);
	TbItem getItemById(long itemId);
	E3Result addItem(TbItem item, String descs);
	TbItemDesc getItemDescById(long itemId);
}
