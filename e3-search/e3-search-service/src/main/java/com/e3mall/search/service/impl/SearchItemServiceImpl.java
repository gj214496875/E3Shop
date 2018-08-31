package com.e3mall.search.service.impl;

import com.e3mall.common.SearchItem;
import com.e3mall.common.SearchResult;
import com.e3mall.search.dao.SearchDao;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.search.service.SearchItemService;
import com.e3mall.utils.E3Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018/8/4.
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SearchDao searchDao;

    @Override
    public E3Result importItems() {
        try {
            List<SearchItem> itemList = itemMapper.getItemList();
            for (SearchItem item : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                document.addField("id", item.getId());
                solrServer.add(document);
            }
            solrServer.commit();
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "数据库导入异常！");
        }
    }

    @Override
    public SearchResult queryItems(String keyword, int page, int rows) throws SolrServerException {
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyword);
        //设置分页条件
        if (page <= 0) {
            page = 1;
        }
        if (rows <= 0) {
            rows = 10;
        }
        //设置当前页的开始索引
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df", "item_title");
        //开启高亮显示
        query.setHighlight(true);
        //设置要高亮显示的域
        query.addHighlightField("item_title");
        //设置高亮显示的标签
        query.setHighlightSimplePre("<em style='color:red'>");
        query.setHighlightSimplePost("</em>");
        //查询
        SearchResult result = searchDao.queryItems(query);
        //计算总页数
        int count = (int) result.getRecordCount();
        int totalPage = (count + rows - 1) / rows;
        result.setTotalPages(totalPage);
        return result;
    }
}
