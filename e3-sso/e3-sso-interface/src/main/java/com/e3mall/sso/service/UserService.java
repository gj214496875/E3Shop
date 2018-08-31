package com.e3mall.sso.service;

import com.e3mall.pojo.TbUser;
import com.e3mall.utils.E3Result;

/**
 * Created by Enzo Cotter on 2018/8/20.
 */
public interface UserService {
    E3Result checking(String parameter, Integer status);
    E3Result register(TbUser tbUser);
}
