package com.e3mall.search.dao;

import com.e3mall.common.SearchItem;
import com.e3mall.common.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gj214
 * Created by Enzo Cotter on 2018/8/5.
 */
public class SearchDao {
    @Autowired
    private SolrServer solrServer;

    public SearchResult queryItems(SolrQuery query) throws SolrServerException {
        //执行查询条件 查询结果
        QueryResponse queryResponse = solrServer.query(query);
        //处理结果，获得结果集
        SolrDocumentList results = queryResponse.getResults();
        //获得总记录数
        long recordCount = results.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(recordCount);
        //获得高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<SearchItem> list = new ArrayList<>();
        for (SolrDocument document : results) {
            SearchItem item = new SearchItem();
            item.setId((String) document.get("id"));
            //判断是否有高亮
            List<String> titleList = highlighting.get(item.getId()).get("item_title");
            if (titleList != null && titleList.size() > 0) {
                item.setTitle(titleList.get(0));
            } else {
                item.setTitle((String) document.get("item_title"));
            }
            item.setSell_point((String) document.get("item_sell_point"));
            item.setPrice((Long) document.get("item_price"));
            item.setImage((String) document.get("item_image"));
            item.setCategory_name((String) document.get("item_category_name"));
            list.add(item);
        }
        result.setItemList(list);
        return result;
    }
}
