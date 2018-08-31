package com.e3mall.sso.service.impl;

import com.e3mall.dao.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.sso.service.UserService;
import com.e3mall.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018/8/20.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result checking(String parameter, Integer status) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        List<TbUser> tbUsers = new ArrayList<>();
        switch (status) {
            case 1:
                criteria.andUsernameEqualTo(parameter);
                tbUsers = userMapper.selectByExample(example);
                break;
            case 2:
                criteria.andPhoneEqualTo(parameter);
                tbUsers = userMapper.selectByExample(example);
                break;
            default:
                break;
        }
        if (tbUsers != null && tbUsers.size() > 0) {
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser tbUser) {
        //数据有效性校验
        if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())
                || StringUtils.isBlank(tbUser.getPhone())) {
            return E3Result.build(400, "用户数据不完整，注册失败");
        }

        E3Result result = this.checking(tbUser.getUsername(), 1);
        if (!(Boolean) result.getData()) {
            return E3Result.build(400, "用户名已存在！");
        }

        result = this.checking(tbUser.getPhone(), 2);
        if (!(Boolean) result.getData()) {
            return E3Result.build(400, "手机号已存在！");
        }

        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Pass);
        userMapper.insert(tbUser);
        return E3Result.ok();
    }
}
