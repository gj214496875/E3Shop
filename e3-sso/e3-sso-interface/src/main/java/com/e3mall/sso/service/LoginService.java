package com.e3mall.sso.service;

import com.e3mall.utils.E3Result;

/**
 * Created by Enzo Cotter on 2018/8/20.
 */
public interface LoginService {
    E3Result login(String username, String password);
}
