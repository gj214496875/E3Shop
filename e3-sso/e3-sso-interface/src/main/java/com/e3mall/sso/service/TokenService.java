package com.e3mall.sso.service;

import com.e3mall.utils.E3Result;

/**
 * Created by Enzo Cotter on 2018/8/21.
 */
public interface TokenService {
    E3Result getUserByToken(String token);
}
