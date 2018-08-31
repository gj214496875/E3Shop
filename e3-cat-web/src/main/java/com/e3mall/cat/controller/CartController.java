package com.e3mall.cat.controller;

import com.e3mall.pojo.TbItem;
import com.e3mall.service.ItemService;
import com.e3mall.utils.CookieUtils;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018/8/23.
 */
@Controller
public class CartController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/cart/add/{itemId}")
    public String addItem2Cart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> tbItems = getCartListFromCookie(request);
        boolean flag = false;
        for (TbItem item : tbItems) {
            if (item.getId() == itemId.longValue()) {
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        if (!flag) {
            TbItem itemById = itemService.getItemById(itemId);
            itemById.setNum(num);
            String images = itemById.getImage();
            if (StringUtils.isNotBlank(images)) {
                String image = images.split(",")[0];
                itemById.setImage(image);
            }
            tbItems.add(itemById);
        }

        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(tbItems), 30 * 60, true);
        return "cartSuccess";
    }

    /**
     * 获得cookie中购物车商品集合
     *
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isBlank(cart)) {
            return new ArrayList<>();
        }
        List<TbItem> itemList = JsonUtils.jsonToList(cart, TbItem.class);
        return itemList;
    }

    @RequestMapping("/cart/cart")
    public String getCartList(HttpServletRequest request) {
        request.setAttribute("cartList", getCartListFromCookie(request));
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> cart = getCartListFromCookie(request);
        for (TbItem item : cart) {
            if (item.getId() == itemId.longValue()) {
                item.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cart), 30 * 60, true);
        return E3Result.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String delete(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> cart = getCartListFromCookie(request);
        for (TbItem item : cart) {
            if (item.getId() == itemId.longValue()) {
                cart.remove(item);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cart), 30 * 60, true);
        return "redirect:/cart/cart.html";
    }
}
