package com.favorites.service.impl;

import com.favorites.entity.User;
import com.favorites.mapper.UserMapper;
import com.favorites.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
private final UserMapper userMapper;
    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userMapper.findByUsernameOrEmail(username,email);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void setNewPassword(String pwd, String email) {
        userMapper.setNewPassword(pwd,email);
    }

    @Override
    public void setIntroduction(String introduction, String email) {
        userMapper.setIntroduction(introduction,email);
    }

    @Override
    public void setUsername(String username, String email) {
        userMapper.setUsername(username,email);
    }

    @Override
    public void setProfilePicture(String savePath, Long id) {
        userMapper.setProfilePicture(savePath,id);
    }

    @Override
    public void setBackgroundPicture(String savePath, Long id) {
        userMapper.setBackgroundPicture(savePath,id);
    }
}
