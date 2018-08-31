package com.e3mall.search.service;

import com.e3mall.common.SearchResult;
import com.e3mall.utils.E3Result;

/**
 * Created by Enzo Cotter on 2018/8/4.
 */
public interface SearchItemService {
    E3Result importItems();
    SearchResult queryItems(String keyword, int page, int rows) throws Exception;
}
