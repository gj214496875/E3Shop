package com.e3mall.search.mapper;

import com.e3mall.common.SearchItem;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018/8/4.
 */
public interface ItemMapper {
    List<SearchItem> getItemList();
    SearchItem getItem(Long id);
}
