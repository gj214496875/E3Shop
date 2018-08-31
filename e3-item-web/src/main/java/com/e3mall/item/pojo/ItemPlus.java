package com.e3mall.item.pojo;

import com.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Enzo Cotter on 2018/8/8.
 */
public class ItemPlus extends TbItem {
    public ItemPlus(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {
        String image = this.getImage();
        if (StringUtils.isNotBlank(image)) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
