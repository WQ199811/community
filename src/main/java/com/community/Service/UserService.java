package com.community.Service;

import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createrOrUpdate(User user) {
        User byAccountId = userMapper.findByAccountId(user.getAccount_id());
        if (byAccountId==null){
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }else{
            user.setGmt_modified(System.currentTimeMillis());
            userMapper.update(user);
        }
    }
}
