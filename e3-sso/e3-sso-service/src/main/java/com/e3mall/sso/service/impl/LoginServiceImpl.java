package com.e3mall.sso.service.impl;

import com.e3mall.dao.TbUserMapper;
import com.e3mall.jedis.JedisClient;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.sso.service.LoginService;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by Enzo Cotter on 2018/8/20.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public E3Result login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return E3Result.build(400, "用户名或密码错误！");
        }
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if (tbUsers == null || tbUsers.size() == 0) {
            return E3Result.build(400, "用户名或密码错误！");
        }
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        TbUser tbUser = tbUsers.get(0);
        if (!md5Pass.equals(tbUser.getPassword())) {
            return E3Result.build(400, "用户名或密码错误！");
        }
        tbUser.setPassword(null);
        //如果登录成功，则生成UUID作为token
        String token = UUID.randomUUID().toString();
        //将信息存入session中
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(tbUser));
        //设置过期时间
        jedisClient.expire("SESSION:" + token, 30*60);
        return E3Result.ok(token);
    }
}
